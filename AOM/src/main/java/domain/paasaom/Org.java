package domain.paasaom;

import utils.HibernateUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 12/1/2020 4:30 PM
 */
public class Org {
    private int id;
    private String orgcode;
    private String parentcode;
    private String orgname;
    private String fullorgname;
    private boolean deleted;
    private Set<User> users;

    public static String getFullorgname(String orgcode){
        Map<String,Object> prop = new HashMap<>(1);
        prop.put("orgcode",orgcode);
        return HibernateUtil.listObjectByProperty(prop, Org.class).get(0).getFullorgname();
    }

    @Override
    public String toString() {
        return "Org{" +
                "id=" + id +
                ", orgcode='" + orgcode + '\'' +
                ", parentcode='" + parentcode + '\'' +
                ", orgname='" + orgname + '\'' +
                ", fullorgname='" + fullorgname + '\'' +
                ", deleted=" + deleted +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getFullorgname() {
        return fullorgname;
    }

    public void setFullorgname(String fullorgname) {
        this.fullorgname = fullorgname;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getParentcode() {
        return parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
