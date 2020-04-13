package cn.com.agree.domain;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * a model of orgtree from agree.com.cn
 * （1）创建树IntTree(&T)。
 * （2）销毁树DestroyTree(&T)
 * （3）构造树CreatTree(&T，deinition)
 * （4）置空树ClearTree(&T)
 * （5）判空树TreeEmpty(T)
 * （6）求树的深度TreeDepth(T)
 * （7）获得树根Root(T)
 * （8）获取结点Value(T，cur_e，&e)
 * 将树中结点cur_e存入e单元中。
 * （9）数据赋值Assign(T，cur_e，value)
 * 将结点value，赋值于树T的结点cur_e中。
 * （10）获得双亲Parent(T，cur_e)
 * 返回树T中结点cur_e的双亲结点。
 * （11）获得最左孩子LeftChild(T，cur_e)
 * 返回树T中结点cur_e的最左孩子。
 * （12）获得右兄弟RightSibling(T，cur_e)
 * 返回树T中结点cur_e的右兄弟。
 * （13）插入子树InsertChild(&T，&p，i，c)
 * 将树c插入到树T中p指向结点的第i个子树之前。
 * （14）删除子树DeleteChild(&T，&p，i)
 * 删除树T中p指向结点的第i个子树。
 * （15）遍历树TraverseTree(T，visit())
 */
public class orgNode {
    //    0001001027014003	应用开发事业部-西南应用开发部-西南开发三部
    private String orgcode;
    private String fullOrgname;
    private orgNode parent;
    private List<orgNode> child;

    @Override
    public String toString() {
        return "orgNode{" +
                "orgcode='" + orgcode + '\'' +
                ", orgname='" + fullOrgname + '\'' +
                '}';
    }

    public orgNode(String orgCode, String fullOrgname) {
        this.orgcode = orgCode;
        this.fullOrgname = fullOrgname;
    }

    private boolean isChild(orgNode child) {
        return this.orgcode.equals(child.getParent().orgcode);
    }

    public boolean addChild(orgNode child) {
        if (this.isChild(child)) {
            this.child.add(child);
            return true;
        }
        return false;
    }

    public orgNode getParent() {
        if (this.getLevel() != -1) {
            return new orgNode(this.orgcode.substring(0, this.orgcode.length() - 3), this.fullOrgname.substring(0, this.fullOrgname.lastIndexOf("-")));
        }
        return null;
    }

    public orgNode initOrgTree(Map<String, String> orgmap) {
//        todo
        orgNode root = new orgNode("0001", "赞同");
//        root.
        return null;
    }

    /**
     * @return 部门层级 例:赞同-赞同科技-信息技术部是一级部门返回1 赞同科技返回0 赞同根节点返回-1
     */
    public int getLevel() {
        return (this.orgcode.length() - 4) / 3 - 1;
    }

    //TODO a model of orgtree from agree.com.cn/建立组织机构树模型
    public static void main(String[] args) {

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

    public boolean parsePath(Map<String, String> orgMap) throws ParseException {
        return false;
    }
}
