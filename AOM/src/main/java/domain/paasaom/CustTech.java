package domain.paasaom;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 12/2/2020 4:30 PM
 */
public class CustTech {
    private int id;
    private Integer techid;
    private Integer custid;
    private boolean deleted;
    private char deptline;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTechid() {
        return techid;
    }

    public void setTechid(Integer techid) {
        this.techid = techid;
    }

    public Integer getCustid() {
        return custid;
    }

    public void setCustid(Integer custid) {
        this.custid = custid;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public char getDeptline() {
        return deptline;
    }

    public void setDeptline(char deptline) {
        this.deptline = deptline;
    }
}
