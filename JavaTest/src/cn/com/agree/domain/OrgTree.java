package cn.com.agree.domain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class OrgTree {
    private class OrgNode {
        private String root;
        private String orgcode;
        private String orgname;
    }

    //TODO a model of orgtree from agree.com.cn/建立组织机构树模型
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
//        String result = new LDAPHelper().parsePath("|0|20000|30833|30835|30843|");
//        System.out.println(result);

//        获取orgMap
//        Map orgmap = JDBCUtils.getOrgMap(JDBCUtils.makeSQL(new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\org.sql")));
//        System.out.println(orgmap.size());
//        for (Map.Entry<String,String> a: orgmap.entrySet()) {
//            System.out.println(a.getKey()+"="+a.getValue());
//        }
//        测试orgmap数据
//        Iterator iterator = orgmap.entrySet().iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

    }

    public boolean parsePath(Map<String, String> orgMap) {
        return false;
    }

}
