import com.dyrnq.cert.tencent.Tencent;
import com.dyrnq.cert.tencent.vo.*;
import org.junit.Before;
import org.junit.Test;

/**
 * https://github.com/TencentCloud/signature-process-demo/blob/main/cvm/signature-v3/java/TencentCloudAPITC3Demo.java
 */
public class TencentTest {


    private final static String SECRET_ID = "";
    private final static String SECRET_KEY = "";
    Tencent tencentSDK = null;

    @Before
    public void init() {
        tencentSDK = new Tencent(SECRET_ID, SECRET_KEY);
    }

    @Test
    public void test_describeRegions() throws Exception {
        DescribeRegionsResult describeRegionsResult = tencentSDK.describeRegions();

        for (RegionInfo info : describeRegionsResult.getRegionSet()) {
            System.out.println(info.getRegionName());
        }
    }

    @Test
    public void test_applyCertificate() throws Exception {

        ApplyCertificateArg arg = new ApplyCertificateArg();
        String domain = "test.onka.cn";

        arg.setDomainName(domain);
        arg.setDvAuthMethod("DNS_AUTO");
        tencentSDK.applyCertificate(arg);


        String certificateId = null;

        while (certificateId == null) {
            DescribeCertificatesArg deArg = new DescribeCertificatesArg();
            deArg.setLimit(1000);
            deArg.setOffset(0);
            deArg.setSearchKey(domain);
            deArg.setCertificateStatus(new Integer[]{1});
            //查询已签发的证书
            DescribeCertificatesResult r = tencentSDK.describeCertificates(deArg);
            if (r != null && r.getTotalCount() != null && r.getTotalCount() >= 1 && r.getCertificates() != null && r.getCertificates().get(0) != null) {
                certificateId = r.getCertificates().get(0).getCertificateId();
            }
            Thread.sleep(2000L);
        }

    }


}