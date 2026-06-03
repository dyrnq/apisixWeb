package com.dyrnq.cert.acme;

import cn.hutool.core.collection.CollectionUtil;
import com.dyrnq.HomeDir;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Route;
import com.dyrnq.apisix.plugins.ResponseRewrite;
import com.dyrnq.service.BusinessLogic;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.shredzone.acme4j.*;
import org.shredzone.acme4j.challenge.Challenge;
import org.shredzone.acme4j.challenge.Dns01Challenge;
import org.shredzone.acme4j.challenge.Http01Challenge;
import org.shredzone.acme4j.exception.AcmeException;
import org.shredzone.acme4j.util.CSRBuilder;
import org.shredzone.acme4j.util.KeyPairUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple client test tool.
 * <p>
 * Pass the names of the domains as parameters.
 */
@Component
public class AcmeClient {
    // Challenge type to be used
    private static final ChallengeType CHALLENGE_TYPE = ChallengeType.HTTP;
    // RSA key size of generated key pairs
    private static final int KEY_SIZE = 2048;
    private static final Logger logger = LoggerFactory.getLogger(AcmeClient.class);

    @Inject
    HomeDir homeDir;

    @Inject
    BusinessLogic businessLogic;

    // 兼容acme.sh,公用一个account.key
    public File getUserKeyFile() {
        return new File(StringUtils.joinWith(
                File.separator,
                homeDir.getAcmeHome(),
                "ca",
                "acme-v02.api.letsencrypt.org",
                "directory",
                "account.key"));
    }

    public String getFirstDomain(Collection<String> domains) {
        return CollectionUtil.getFirst(domains);
    }

    private File getFile(Collection<String> domains, String fileName) {
        String domain = getFirstDomain(domains);
        File file = new File(StringUtils.joinWith(File.separator, homeDir.getAcmeHome(), domain, fileName));
        try {
            FileUtils.forceMkdirParent(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    private File getDomainKeyFile(Collection<String> domains) {
        return getFile(domains, "domain.key");
    }

    private File getDomainKeyFile(String domain) {
        Collection<String> domains = new ArrayList<>();
        domains.add(domain);
        return getFile(domains, "domain.key");
    }

    private File getDomainCsrFile(Collection<String> domains) {
        return getFile(domains, "domain.csr");
    }

    public File getDomainChainFile(Collection<String> domains) {
        return getFile(domains, "domain-chain.crt");
    }

    /**
     * Generates a certificate for the given domains. Also takes care for the registration
     * process.
     *
     * @param domains Domains to get a common certificate for
     */
    public void fetchCertificate(Collection<String> domains) throws IOException, AcmeException {
        // Load the user key file. If there is no key file, create a new one.
        KeyPair userKeyPair = loadOrCreateUserKeyPair(false);

        // Create a session for Let's Encrypt.
        // Use "acme://letsencrypt.org" for production server
        // Session session = new Session("acme://letsencrypt.org/staging");
        Session session = new Session("acme://letsencrypt.org");
        // Get the Account.
        // If there is no account yet, create a new one.
        Account acct = findOrRegisterAccount(session, userKeyPair);

        // Load or create a key pair for the domains. This should not be the userKeyPair!
        KeyPair domainKeyPair = loadOrCreateDomainKeyPair(getFirstDomain(domains));

        // Order the certificate
        Order order = acct.newOrder().domains(domains).create();

        // Perform all required authorizations
        for (Authorization auth : order.getAuthorizations()) {
            authorize(auth, domains);
        }

        // Generate a CSR for all of the domains, and sign it with the domain key pair.
        CSRBuilder csrb = new CSRBuilder();
        csrb.addDomains(domains);
        csrb.sign(domainKeyPair);

        // Write the CSR to a file, for later use.
        try (Writer out = new FileWriter(getDomainCsrFile(domains))) {
            csrb.write(out);
        }

        // Order the certificate
        order.execute(csrb.getEncoded());

        // Wait for the order to complete
        try {
            int attempts = 10;
            while (order.getStatus() != Status.VALID && attempts-- > 0) {
                // Did the order fail?
                if (order.getStatus() == Status.INVALID) {
                    logger.error("Order has failed, reason: {}", order.getError());
                    throw new AcmeException("Order failed... Giving up.");
                }

                // Wait for a few seconds
                Thread.sleep(3000L);

                // Then update the status
                order.update();
            }
        } catch (InterruptedException ex) {
            logger.error("interrupted", ex);
            Thread.currentThread().interrupt();
        }

        // Get the certificate
        Certificate certificate = order.getCertificate();

        logger.info("Success! The certificate for domains {} has been generated!", domains);
        logger.info("Certificate URL: {}", certificate.getLocation());

        // Write a combined file containing the certificate and chain.
        try (FileWriter fw = new FileWriter(getDomainChainFile(domains))) {
            certificate.writeCertificate(fw);
        }

        // That's all! Configure your web server to use the DOMAIN_KEY_FILE and
        // DOMAIN_CHAIN_FILE for the requested domains.
    }

    /**
     * Loads a user key pair. If the file does not exist, a
     * new key pair is generated and saved.
     * <p>
     * Keep this key pair in a safe place! In a production environment, you will not be
     * able to access your account again if you should lose the key pair.
     *
     * @return User's {@link KeyPair}.
     */
    private KeyPair loadOrCreateUserKeyPair(boolean create) throws IOException {
        if (getUserKeyFile().exists()) {
            // If there is a key file, read it
            try (FileReader fr = new FileReader(getUserKeyFile())) {
                return KeyPairUtils.readKeyPair(fr);
            }

        } else {
            if (create) {
                // If there is none, create a new key pair and save it
                KeyPair userKeyPair = KeyPairUtils.createKeyPair(KEY_SIZE);
                try (FileWriter fw = new FileWriter(getUserKeyFile())) {
                    KeyPairUtils.writeKeyPair(userKeyPair, fw);
                }
                return userKeyPair;
            }
            {
                return null;
            }
        }
    }

    /**
     * Loads a domain key pair. If the file does not exist,
     * a new key pair is generated and saved.
     *
     * @return Domain {@link KeyPair}.
     */
    private KeyPair loadOrCreateDomainKeyPair(String domain) throws IOException {
        if (getDomainKeyFile(domain).exists()) {
            try (FileReader fr = new FileReader(getDomainKeyFile(domain))) {
                return KeyPairUtils.readKeyPair(fr);
            }
        } else {
            KeyPair domainKeyPair = KeyPairUtils.createKeyPair(KEY_SIZE);
            try (FileWriter fw = new FileWriter(getDomainKeyFile(domain))) {
                KeyPairUtils.writeKeyPair(domainKeyPair, fw);
            }
            return domainKeyPair;
        }
    }

    /**
     * Finds your {@link Account} at the ACME server. It will be found by your user's
     * public key. If your key is not known to the server yet, a new account will be
     * created.
     * <p>
     * This is a simple way of finding your {@link Account}. A better way is to get the
     * URL of your new account with {@link Account#getLocation()} and store it somewhere.
     * If you need to get access to your account later, reconnect to it via {@link
     * Session#login(URL, KeyPair)} by using the stored location.
     *
     * @param session {@link Session} to bind with
     * @return {@link Account}
     */
    private Account findOrRegisterAccount(Session session, KeyPair accountKey) throws AcmeException {
        // Ask the user to accept the TOS, if server provides us with a link.
        URI tos = session.getMetadata().getTermsOfService();
        if (tos != null) {
            acceptAgreement(tos);
        }

        Account account = new AccountBuilder()
                .agreeToTermsOfService()
                .useKeyPair(accountKey)
                .create(session);
        logger.info("Registered a new user, URL: {}", account.getLocation());

        return account;
    }

    /**
     * Authorize a domain. It will be associated with your account, so you will be able to
     * retrieve a signed certificate for the domain later.
     *
     * @param auth {@link Authorization} to perform
     */
    private void authorize(Authorization auth, Collection<String> domains) throws AcmeException {
        logger.info("Authorization for domain {}", auth.getIdentifier().getDomain());

        // The authorization is already valid. No need to process a challenge.
        if (auth.getStatus() == Status.VALID) {
            return;
        }

        // Find the desired challenge and prepare it.
        Challenge challenge = null;
        challenge = httpChallenge(auth, domains);
        switch (CHALLENGE_TYPE) {
            case HTTP:
                challenge = httpChallenge(auth, domains);
                break;

            case DNS:
                challenge = dnsChallenge(auth, domains);
                break;
        }

        if (challenge == null) {
            throw new AcmeException("No challenge found");
        }

        // If the challenge is already verified, there's no need to execute it again.
        if (challenge.getStatus() == Status.VALID) {
            return;
        }

        // Now trigger the challenge.
        challenge.trigger();

        // Poll for the challenge to complete.
        try {
            int attempts = 10;
            while (challenge.getStatus() != Status.VALID && attempts-- > 0) {
                // Did the authorization fail?
                if (challenge.getStatus() == Status.INVALID) {
                    logger.error("Challenge has failed, reason: {}", challenge.getError());
                    throw new AcmeException("Challenge failed... Giving up.");
                }

                // Wait for a few seconds
                Thread.sleep(3000L);

                // Then update the status
                challenge.update();
            }
        } catch (InterruptedException ex) {
            logger.error("interrupted", ex);
            Thread.currentThread().interrupt();
        }

        // All reattempts are used up and there is still no valid authorization?
        if (challenge.getStatus() != Status.VALID) {
            throw new AcmeException("Failed to pass the challenge for domain "
                    + auth.getIdentifier().getDomain() + ", ... Giving up.");
        }

        logger.info("Challenge has been completed. Remember to remove the validation resource.");
        completeChallenge("Challenge has been completed.\nYou can remove the resource again now.");
    }

    /**
     * Prepares a HTTP challenge.
     * <p>
     * The verification of this challenge expects a file with a certain content to be
     * reachable at a given path under the domain to be tested.
     * <p>
     * This example outputs instructions that need to be executed manually. In a
     * production environment, you would rather generate this file automatically, or maybe
     * use a servlet that returns {@link Http01Challenge#getAuthorization()}.
     *
     * @param auth {@link Authorization} to find the challenge in
     * @return {@link Challenge} to verify
     */
    public Challenge httpChallenge(Authorization auth, Collection<String> domains) throws AcmeException {
        // Find a single http-01 challenge
        Http01Challenge challenge = auth.findChallenge(Http01Challenge.class);
        if (challenge == null) {
            throw new AcmeException("Found no " + Http01Challenge.TYPE + " challenge, don't know what to do...");
        }

        // Output the challenge, wait for acknowledge...
        logger.info("Please create a file in your web server's base directory.");
        logger.info(
                "It must be reachable at: http://{}/.well-known/acme-challenge/{}",
                auth.getIdentifier().getDomain(),
                challenge.getToken());
        logger.info("File name: {}", challenge.getToken());
        logger.info("Content: {}", challenge.getAuthorization());
        logger.info("The file must not contain any leading or trailing whitespaces or line breaks!");
        logger.info("If you're ready, dismiss the dialog...");

        // 使用当前apisix instance充当80端口验证
        try {
            AdminClient client = businessLogic.getAdminClient();
            Route r = new Route();
            r.setHosts(new ArrayList<>(domains));
            r.setName(auth.getIdentifier().getDomain() + " acme-challenge");
            r.setUri("/.well-known/acme-challenge/" + challenge.getToken());
            Map<String, Object> map = new HashMap<>();
            ResponseRewrite responseRewrite = new ResponseRewrite();
            responseRewrite.body = challenge.getAuthorization();
            responseRewrite.statusCode = 200;
            map.put(ResponseRewrite.PLUGIN_NAME, responseRewrite);
            r.setPlugins(map);
            client.putRoute(auth.getIdentifier().getDomain(), r);
        } catch (ApisixSDKException e) {
            throw new RuntimeException(e);
        }

        String message = "Please create a file in your web server's base directory.\n\n" + "http://"
                + auth.getIdentifier().getDomain()
                + "/.well-known/acme-challenge/"
                + challenge.getToken()
                + "\n\n"
                + "Content:\n\n"
                + challenge.getAuthorization();
        acceptChallenge(message);

        return challenge;
    }

    /**
     * Prepares a DNS challenge.
     * <p>
     * The verification of this challenge expects a TXT record with a certain content.
     * <p>
     * This example outputs instructions that need to be executed manually. In a
     * production environment, you would rather configure your DNS automatically.
     *
     * @param auth {@link Authorization} to find the challenge in
     * @return {@link Challenge} to verify
     */
    public Challenge dnsChallenge(Authorization auth, Collection<String> domains) throws AcmeException {
        // Find a single dns-01 challenge
        Dns01Challenge challenge = auth.findChallenge(Dns01Challenge.TYPE);
        if (challenge == null) {
            throw new AcmeException("Found no " + Dns01Challenge.TYPE + " challenge, don't know what to do...");
        }

        // Output the challenge, wait for acknowledge...
        logger.info("Please create a TXT record:");
        logger.info("{} IN TXT {}", Dns01Challenge.toRRName(auth.getIdentifier()), challenge.getDigest());
        logger.info("If you're ready, dismiss the dialog...");

        String message = "Please create a TXT record:\n\n" + Dns01Challenge.toRRName(auth.getIdentifier())
                + " IN TXT "
                + challenge.getDigest();
        acceptChallenge(message);

        return challenge;
    }

    /**
     * Presents the instructions for preparing the challenge validation, and waits for
     * dismissal. If the user cancelled the dialog, an exception is thrown.
     *
     * @param message Instructions to be shown in the dialog
     */
    public void acceptChallenge(String message) throws AcmeException {
        logger.info(message);
    }

    /**
     * Presents the instructions for removing the challenge validation, and waits for
     * dismissal.
     *
     * @param message Instructions to be shown in the dialog
     */
    public void completeChallenge(String message) throws AcmeException {
        logger.info(message);
    }

    /**
     * Presents the user a link to the Terms of Service, and asks for confirmation. If the
     * user denies confirmation, an exception is thrown.
     *
     * @param agreement {@link URI} of the Terms of Service
     */
    public void acceptAgreement(URI agreement) throws AcmeException {
        logger.warn("Please review carefully and accept TOS {}", agreement);
    }

    private enum ChallengeType {
        HTTP,
        DNS
    }
}
