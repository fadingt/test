package domain.paasaom;

import domain.paasaom.User;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 11/23/2020 10:26 AM
 */

@Entity
public class Staff {
    private int id;
    private Integer userid;
    private Date enterTime;
    private Date hireTime;
    private Integer leader;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public Date getHireTime() {
        return hireTime;
    }

    public void setHireTime(Date hireTime) {
        this.hireTime = hireTime;
    }

    public Integer getLeader() {
        return leader;
    }

    public void setLeader(Integer leader) {
        this.leader = leader;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
