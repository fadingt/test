package org.vaalbara.bean;

/**
 * Created by Administrator on 2018/3/15.
 */
public class ManyIds {
    private int rId;
    private int mId;
    private int sId;

    public ManyIds() {
    }

    public ManyIds(int rId, int mId, int sId) {
        this.rId = rId;
        this.mId = mId;
        this.sId = sId;
    }

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    @Override
    public String toString() {
        return "ManyIds{" +
                "rId=" + rId +
                ", mId=" + mId +
                ", sId=" + sId +
                '}';
    }
}
