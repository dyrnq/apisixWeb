import com.dyrnq.utils.BCryptPasswordEncoder;
import org.junit.Test;

public class BCryptPasswordEncoderTest {

    @Test
    public void test_bcrypt(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        System.out.println(encoder.encode("admin"));
    }
}
