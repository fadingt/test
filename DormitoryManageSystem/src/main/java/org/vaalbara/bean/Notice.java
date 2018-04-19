package org.vaalbara.bean;

/**
 * Created by Huawei on 2018/3/12.
 */
public class Notice {
    private int nId;        //公告id
    private String nTitle;  //公告标题
    private String nContent;//公告内容
    private String nPic;    //公告摘要
    private String nDate;   //公告时间
    private int aId;        //管理员id
    private String aName;   //管理员姓名


    public Notice(int nId, String nTitle, String nContent, String nPic, String nDate, int aId, String aName) {
        this.nId = nId;
        this.nTitle = nTitle;
        this.nContent = nContent;
        this.nPic = nPic;
        this.nDate = nDate;
        this.aId = aId;
        this.aName = aName;
    }

    public Notice(String nTitle, String nContent) {
        this.nTitle = nTitle;
        this.nContent = nContent;
    }

    public Notice(String nTitle, String nContent, String nPic) {
        this.nTitle = nTitle;
        this.nContent = nContent;
        this.nPic = nPic;
    }

    public Notice() {
    }

    public Notice(int nId, String nTitle, String nContent, String nPic, String nDate, int aId) {
        this.nId = nId;
        this.nTitle = nTitle;
        this.nContent = nContent;
        this.nPic = nPic;
        this.nDate = nDate;
        this.aId = aId;
    }

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public String getnTitle() {
        return nTitle;
    }

    public void setnTitle(String nTitle) {
        this.nTitle = nTitle;
    }

    public String getnContent() {
        return nContent;
    }

    public void setnContent(String nContent) {
        this.nContent = nContent;
    }

    public String getnPic() {
        return nPic;
    }

    public void setnPic(String nPic) {
        this.nPic = nPic;
    }

    public String getnDate() {
        return nDate;
    }

    public void setnDate(String nDate) {
        this.nDate = nDate;
    }

    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "nId=" + nId +
                ", nTitle='" + nTitle + '\'' +
                ", nContent='" + nContent + '\'' +
                ", nPic='" + nPic + '\'' +
                ", nDate='" + nDate + '\'' +
                ", aId=" + aId +
                ", aName='" + aName + '\'' +
                '}';
    }
}
