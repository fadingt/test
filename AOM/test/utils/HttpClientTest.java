package utils;

import dao.WorkflowDao;
import dao.impl.WorkflowDaoImpl;
import domain.Token;
import domain.paasaom.Workflow;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class HttpClientTest {

    @Test
    void sendPost2() throws IOException {
//        TODO 新增AOM流程
        String token;
        String tokentype;
        String temp;
//        1.获取admin token
        String username = "admin";
        String password = "16a8a85ebd9df4e48787dea5c3bf568c";//AGREE123
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("Authorization", "Basic d2ViYXBwOndlYmFwcA==");
        temp = HttpClient.sendPost("http://192.168.180.160:16088/api/uaa/oauth/token", "grant_type=password" + "&username=" + username + "%40AOM" + "&password=" + password, "UTF-8", headers);
        token = JSONObject.fromObject(temp).getString("access_token");
        tokentype = JSONObject.fromObject(temp).getString("token_type");
//        2.生成请求内容
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", tokentype + " " + token);
        headers.put("Connection", "keep-alive");
        HttpClient.sendPost("http://192.168.180.160:16088/api/rpcServer/afa4j/3/common/workflowParam_save", "{\"I_TECHID\":\"140769\",\"S_MAPFLAG\":\"1\",\"S_DESC\":\"\",\"S_SOURCE\":\"0001001003003\",\"S_TARGET1\":\"3@15\",\"S_TARGET2\":\"\"}", "UTF-8", headers);
    }

    @Test
    void sendPost1() throws IOException {
//        TODO 修改AOM流程
 /*
        1.获取admin token
        2.自动生成请求内容
            2.1修改流程
            http://192.168.180.160:16088/api/rpcServer/afa4j/3/common/workflowParam_edit
            2.2新增流程
        3.发送请求
*/
        String accessToken;
        String tokenType;
//        String temp;
//        1.获取admin token
        String usercode = "admin";
        String password = "AGREE123";//agree123
        AomClinet client = new AomClinet();
        client.login(usercode, password, AomClinet.PWD_ORIGIN);
        Token token = client.getToken();
        accessToken = token.getAccess_token();
        tokenType = token.getToken_type();
        Map<String, String> headers = new HashMap<>();
//        2.生成请求内容
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", tokenType + " " + accessToken);
        WorkflowDao workflowDao = new WorkflowDaoImpl();
        Map<String, Object> flowpop = new HashMap<>();
        flowpop.put("S_TARGET1", "3@618442");//王清峰-A12691
        flowpop.put("S_TYPE", "01");
//        flowpop.put("ID",3053);
//        flowpop.put("ID",3725);
        flowpop.put("IS_DELETE", 0);
        List<Workflow> list = workflowDao.getWorkflowByProperty(flowpop);
        for (Workflow workflow : list) {
            StringBuilder flowinfo = new StringBuilder();
            flowinfo.append("{\"ID\":").append(workflow.getId()).append(",");
            flowinfo.append("\"I_TECHID\":").append(workflow.getI_TECHID()).append(",");
            flowinfo.append("\"S_MAPFLAG\":\"").append(workflow.getS_MAPFLAG()).append("\",");
            flowinfo.append("\"S_DESC\":\"").append("批处理:王清峰-A12691=>何凤弟-A11349\",");
            flowinfo.append("\"S_SOURCE\":\"").append(workflow.getS_SOURCE()).append("\",");
            flowinfo.append("\"S_TARGET1\":\"3@617158\",");
            if (workflow.getS_TARGET2() == null) {
                flowinfo.append("\"S_TARGET2\":\"").append("\"}");
            } else {
                flowinfo.append("\"S_TARGET2\":\"").append(workflow.getS_TARGET2()).append("\"}");
            }
            System.out.println(flowinfo);
            HttpClient.sendPost("http://192.168.180.160:16088/api/rpcServer/afa4j/3/common/workflowParam_edit", flowinfo.toString(), "UTF-8", headers);
        }
    }

    @Test
    void testSendPost() throws IOException {
        Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
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