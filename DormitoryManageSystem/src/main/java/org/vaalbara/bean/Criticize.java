package org.vaalbara.bean;

/**
 * Created by Huawei on 2018/3/14.
 */
public class Criticize {
    private int sId;        //学生id
    private String cRason;  //处分原因
    private int cStatus;    //处分状态
    private String cDate;   //处分时间
    private int cId;        //处分id

    public Criticize() {
    }

    public Criticize(int sId, String cRason, String cDate, int cId) {
        this.sId = sId;
        this.cRason = cRason;
        this.cDate = cDate;
        this.cId = cId;
    }

    public Criticize(int sId, String cRason, int cStatus, String cDate, int cId) {
        this.sId = sId;
        this.cRason = cRason;
        this.cStatus = cStatus;
        this.cDate = cDate;
        this.cId = cId;
    }


    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public String getcRason() {
        return cRason;
    }

    public void setcRason(String cRason) {
        this.cRason = cRason;
    }

    public int getcStatus() {
        return cStatus;
    }

    public void setcStatus(int cStatus) {
        this.cStatus = cStatus;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }


    @Override
    public String toString() {
        return "Criticize{" +
                "sId=" + sId +
                ", cRason='" + cRason + '\'' +
                ", cStatus=" + cStatus +
                ", cDate='" + cDate + '\'' +
                ", cId=" + cId +
                '}';
    }
}
