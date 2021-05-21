package domain.paasaom;

import java.util.Date;
import java.util.List;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 11/26/2020 3:42 PM
 */
public class UserRole {
    private int id;
    private String rolename;
    private String rolecode;
    private String roledesc;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer deleted;
    private Integer admin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRolecode() {
        return rolecode;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

    public String getRoledesc() {
        return roledesc;
    }

    public void setRoledesc(String desc) {
        this.roledesc = desc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }
}
