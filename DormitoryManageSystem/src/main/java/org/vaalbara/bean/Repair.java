package org.vaalbara.bean;

/**
 * Created by Administrator on 2018/3/13.
 */
public class Repair {
    private int rId;
    private int sId;
    private int dId;
    private String rTitle;
    private String rReason;
    private String rBegin;
    private String rEnd;
    private String mName;
    private int mId;
    private int grade;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getrTitle() {
        return rTitle;
    }

    public void setrTitle(String rTitle) {
        this.rTitle = rTitle;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Repair(int sId, int dId, String rTitle, String rReason, String rBegin, String rEnd) {
        this.sId = sId;
        this.dId = dId;
        this.rTitle = rTitle;
        this.rReason = rReason;
        this.rBegin = rBegin;
        this.rEnd = rEnd;
    }

    public String getrBegin() {
        return rBegin;
    }

    public void setrBegin(String rBegin) {
        this.rBegin = rBegin;
    }

    public String getrEnd() {
        return rEnd;
    }

    public void setrEnd(String rEnd) {
        this.rEnd = rEnd;
    }


    public Repair() {
    }

    public Repair(int rId, int sId, int dId, String rReason) {
        this.rId = rId;
        this.sId = sId;
        this.dId = dId;
        this.rReason = rReason;
    }

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public int getdId() {
        return dId;
    }

    public void setdId(int dId) {
        this.dId = dId;
    }

    public String getrReason() {
        return rReason;
    }

    public void setrReason(String rReason) {
        this.rReason = rReason;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "rId=" + rId +
                ", sId=" + sId +
                ", dId=" + dId +
                ", rTitle='" + rTitle + '\'' +
                ", rReason='" + rReason + '\'' +
                ", rBegin='" + rBegin + '\'' +
                ", rEnd='" + rEnd + '\'' +
                ", mName='" + mName + '\'' +
                ", mId=" + mId +
                ", grade=" + grade +
                '}';
    }
}
