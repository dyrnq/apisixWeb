import com.github.odiszapc.nginxparser.*;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class NginxparserTest {
    @Test
    public void testNginx() throws IOException {
        // 读取Nginx配置文件
        String nginxConf = "src/test/resources/nginx.conf";
        NgxConfig conf = NgxConfig.read(nginxConf);

        NgxParam workers = conf.findParam("worker_processes"); // Ex.1
        workers.getValue(); // "1"
        NgxParam listen = conf.findParam("http", "server", "listen"); // Ex.2
        // listen.getValue(); // "8889"

        List<NgxEntry> rtmpServers = conf.findAll(NgxConfig.BLOCK, "rtmp", "server"); // Ex.3
        for (NgxEntry entry : rtmpServers) {
            ((NgxBlock) entry).getName(); // "server"
            ((NgxBlock) entry).findParam("application", "live"); // "on" for the first iter, "off" for the second one
        }
        NgxDumper dumper = new NgxDumper(conf);
        dumper.dump(System.out);
    }
}
