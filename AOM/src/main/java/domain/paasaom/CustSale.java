package domain.paasaom;

import java.util.Date;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 11/26/2020 3:42 PM
 */
public class CustSale {
    private int id;
    private Integer saleid;
    private Integer custid;
    private boolean deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSaleid() {
        return saleid;
    }

    public void setSaleid(Integer saleid) {
        this.saleid = saleid;
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
}
