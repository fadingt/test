package domain.ngoss;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 5/20/2021 2:49 PM
 */
public class BiRole {
    private int id;
    private String roleType;
    private String roleName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
