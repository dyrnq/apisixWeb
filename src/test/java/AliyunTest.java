import cn.hutool.core.collection.CollectionUtil;
import com.dyrnq.cert.aliyun.Aliyun;
import com.dyrnq.cert.aliyun.DomainUtils;
import com.dyrnq.cert.aliyun.vo.CertificateOrder;
import com.dyrnq.cert.aliyun.vo.DescribeCertificateStateResult;
import com.dyrnq.cert.aliyun.vo.Region;
import org.apache.commons.lang3.StringUtils;
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

    @Test
    public void test_domain_listUserCertificateOrder() {
        String keyword = "";
        //等待证书审批下发
        List<CertificateOrder> list = null;
        while (CollectionUtil.isEmpty(list)) {
            try {
                list = aliyunSDK.listUserCertificateOrder(keyword, "ISSUED");
                if (CollectionUtil.isEmpty(list)) {
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (CertificateOrder i : list) {
            System.out.println("orderId=" + i.getOrderId() + "; aliyunOrderId=" + i.getAliyunOrderId() + "; domain=" + i.getDomain());
        }
    }

    @Test
    public void test_createCertificateForPackageRequest() {
        String domain = "bin.dyrnq.com";
        Long orderId = aliyunSDK.createCertificateForPackageRequest(domain, "DNS");
        String baseDomain = DomainUtils.base(domain);
        String recordDomain = null;
        String recodeValue = null;
        String recodeType = null;

        while (recordDomain == null) {
            try {
                DescribeCertificateStateResult result = aliyunSDK.describeCertificateState(orderId);
                recordDomain = result.getRecordDomain();

                if (recordDomain == null) {
                    Thread.sleep(100);
                    continue;
                }

                recodeValue = result.getRecordValue();
                recodeType = result.getRecordType();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String rr = StringUtils.replace(recordDomain, "." + baseDomain, "");
        System.out.println(recordDomain);
        System.out.println(recodeValue);
        System.out.println(recodeType);
        System.out.println(baseDomain);
        System.out.println(rr);


        //插入域名校验记录，腾讯云那边有AUTO_DNS，不需要这一步
        //aliyunSDK.addDomainRecord(baseDomain, rr, recodeType, recodeValue);


    }


}
