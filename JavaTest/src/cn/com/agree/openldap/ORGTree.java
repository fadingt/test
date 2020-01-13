package cn.com.agree.openldap;

import java.io.IOException;

public class ORGTree {
    //TODO a model of orgtree from agree.com.cn/建立组织机构树模型
    public static void main(String[] args) throws IOException {
        String result = new LDAPHelper().parsePath("|0|20000|30833|30835|30843|");
        System.out.println(result);
    }
}
