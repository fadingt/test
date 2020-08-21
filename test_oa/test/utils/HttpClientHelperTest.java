package utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientHelperTest {

    @Test
    void sendPost() throws IOException {
//        设置post参数:
//        Map<String, Object> param = new HashMap<>();
//        param.put("grant_type", "password");
//        param.put("username", "admin%40AOM");
//        param.put("password", "16a8a85ebd9df4e48787dea5c3bf568c");
        //TODO 登录amidn获取token
        String result;
        StringBuffer params = new StringBuffer("grant_type=password&username=admin%40AOM&password=16a8a85ebd9df4e48787dea5c3bf568c");
        result = HttpClientHelper.sendPost("http://192.168.180.160:16088/api/uaa/oauth/token", params, "UTF-8");
        System.out.println(result);
        JSONObject object = JSONObject.fromObject(result);
        System.out.println(object);
        System.out.println(object.get("access_token"));
        //TODO 获取权限集合
/*        JSONObject param = new JSONObject();
        param.getJSONObject("{realName: \"admin\"}");
        String result = sendJSONPost("http://192.168.180.160:16088/api/rpcServer/afa4j/3/auth/A02005",param,"UTF-8");
        System.out.println(result);
*/
    }

    @Test
    void testSendPost() {
    }

    @Test
    void sendGetAndSaveFile() {
    }

    @Test
    void sendGet() {
    }

    @Test
    void sendGet2() {
    }

    @Test
    void httpClientPost() {
    }

    @Test
    void httpClientGet() {
    }

    @Test
    void sendSocketPost() {
    }

    @Test
    void sendSocketGet() {
    }
}