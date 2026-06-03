import cn.hutool.json.JSONUtil;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.*;
import com.dyrnq.service.op.Factory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

public class YamlTest extends BaseJunit {

    public Class get(String name) {
        try {
            if (StringUtils.contains(name, ".")) {
                Class.forName(name);
            }
            return Class.forName("com.dyrnq.apisix.domain." + name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testYaml() throws FileNotFoundException, ApisixSDKException {
        File file = new File("src/test/resources/file.yaml");

        Yaml yaml = new Yaml();

        //        Gson gson = new GsonBuilder()
        //                .setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
        //                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
        //                .setPrettyPrinting()
        //                .disableHtmlEscaping().create();

        Iterable<Object> blocks = yaml.loadAll(new FileInputStream(file));

        for (Object block : blocks) {
            // System.out.println(BeanUtil.getProperty(block,"kind"));
            if (block instanceof Map) {
                Map<String, ?> map = (Map) block;
                if (map.containsKey("kind")) {
                    String className = map.get("kind").toString();
                    String id = map.get("id").toString();
                    Class cz = get(className);
                    String json = JSONUtil.toJsonStr(map);
                    Factory.create(className).putRaw(client, id, json);
                }
                // System.out.println("Block: " + block.toString());

            }
        }
    }
}
