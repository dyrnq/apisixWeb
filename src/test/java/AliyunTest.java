import com.dyrnq.cert.aliyun.Aliyun;
import com.dyrnq.cert.aliyun.vo.CertificateOrder;
import com.dyrnq.cert.aliyun.vo.DescribeCertificateStateResult;
import com.dyrnq.cert.aliyun.vo.Region;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AliyunTest {
    static final String ACCESS_KEY_ID = "";
    static final String ACCESS_KEY_SECRET = "";

    Aliyun aliyunSDK = null;

    @Before
    public void init() {
        aliyunSDK = new Aliyun(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    @Test
    public void test_describeRegions() {
        List<Region> list = aliyunSDK.describeRegions();
        for (Region r : list) {
            System.out.println(r.getRegionId());
        }
    }

    @Test
    public void test_listUserCertificateOrder() {
        List<CertificateOrder> list = aliyunSDK.listUserCertificateOrder(null, "ISSUED");
        for (CertificateOrder obj : list) {
            System.out.println(obj.getCertStartTime() + " domain " + obj.getCertEndTime() + " " + obj.getDomain() + obj.getOrderId());
            DescribeCertificateStateResult result = aliyunSDK.describeCertificateState(obj.getOrderId());
        }
    }


}
