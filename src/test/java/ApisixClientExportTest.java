import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Route;
import com.dyrnq.apisix.response.Multi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ApisixClientExportTest extends BaseJunit{
    //测试能否成功获取数据并导出
    @Test
    public void test_export() throws ApisixSDKException, IOException {
        List<Route>routes = client.listRoutes();
        Gson gson = new Gson();//创建gson对象，含有转化的toJson方法
        for (Route route1 : routes) {
        File file = new File("C:/Users/ash/Desktop/route/"+route1.getId()+".json");//创建file文件地址对象，作为载体
        FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

        String json = gson.toJson(route1);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
        writer.write(json);//执行写入方法。
        writer.close();//关闭写入方法。


        }
    }

    //测试能否将外部txt文件以对象形式导入数据库
    @Test
    public void test_import() throws ApisixSDKException,IOException {
        File folder = new File("C:/Users/ash/Desktop/route/");
        File[] files = folder.listFiles();
        Gson gson = new Gson();
        for (File file:files){
            FileReader reader = new FileReader(file);
            String jsonS = IOUtils.toString(reader);
            Route route = null;
            Type type = new TypeToken<Route>() {}.getType();
            route = gson.fromJson(jsonS, type);
            client.putRouteRaw(route.getId(), jsonS);
            reader.close();
        }
    }
}
