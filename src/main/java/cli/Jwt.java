package cli;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.noear.solon.sessionstate.jwt.JwtUtils;
import picocli.CommandLine.Command;

import java.security.Key;
import java.util.concurrent.Callable;

@Command(name = "jwt", aliases = {"j"}, description = "create jwt secret")
public class Jwt extends CommonOptions implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
//        String jwtSecret = JwtUtils.createKey();
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String jwtSecret = (String) Encoders.BASE64.encode(key.getEncoded());

        //System.out.println(jwtSecret);
        System.out.println("--server.session.state.jwt.secret="+jwtSecret);
        return 0;
    }
}

