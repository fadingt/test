package org.vaalbara.bean;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by Administrator on 2018/3/13.
 */
public class Vocation {
    private int sId;//学生id
    private int dId;//寝室id
    private String vReason;  //请假理由

    private  String vBegin;  //请假开始时间
    private  String  vEnd;   //请假结束时间
    private int vStatus;   //请假审批状态

    public Vocation(int sId, int dId, String vReason, String vBegin, String vEnd, int vStatus) {
        this.sId = sId;
        this.dId = dId;
        this.vReason = vReason;
        this.vBegin = vBegin;
        this.vEnd = vEnd;
        this.vStatus = vStatus;
    }


    @Override
    public String toString() {
        return "Vocation{" +
                "sId=" + sId +
                ", dId=" + dId +
                ", vReason='" + vReason + '\'' +
                ", vBegin='" + vBegin + '\'' +
                ", vEnd='" + vEnd + '\'' +
                ", vStatus=" + vStatus +
                '}';
    }

    public int getdId() {
        return dId;
    }

    public void setdId(int dId) {
        this.dId = dId;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public String getvReason() {
        return vReason;
    }

    public void setvReason(String vReason) {
        this.vReason = vReason;
    }

    public String getvBegin() {
        return vBegin;
    }

    public void setvBegin(String vBegin) {
        this.vBegin = vBegin;
    }

    public String getvEnd() {
        return vEnd;
    }

    public void setvEnd(String vEnd) {
        this.vEnd = vEnd;
    }

    public int getvStatus() {
        return vStatus;
    }

    public void setvStatus(int vStatus) {
        this.vStatus = vStatus;
    }



    public Vocation() {
    }
}
