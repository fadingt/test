package org.vaalbara.bean;

/**
 * Created by Huawei on 2018/3/12.
 */
public class News {
    private int newsId;  //公告id
    private String newsName;  //公告标题
    private String newsAuthor;  //发布人(根据用户id)
    private String newsStatus; //审核状态中文显示
    private String newsLook;  //是否开放浏览
    private String isShow;//是否展示
    private String newsTime;  //发帖时间

    public News() {
    }

    public News(int newsId, String newsName, String newsAuthor, String newsStatus, String newsLook, String isShow, String newsTime) {
        this.newsId = newsId;
        this.newsName = newsName;
        this.newsAuthor = newsAuthor;
        this.newsStatus = newsStatus;
        this.newsLook = newsLook;
        this.isShow = isShow;
        this.newsTime = newsTime;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public String getNewsStatus() {
        return newsStatus;
    }

    public void setNewsStatus(String newsStatus) {
        this.newsStatus = newsStatus;
    }

    public String getNewsLook() {
        return newsLook;
    }

    public void setNewsLook(String newsLook) {
        this.newsLook = newsLook;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }


    @Override
    public String toString() {
        return "Notice{" +
                "newsId=" + newsId +
                ", newsName='" + newsName + '\'' +
                ", newsAuthor='" + newsAuthor + '\'' +
                ", newsStatus='" + newsStatus + '\'' +
                ", newsLook='" + newsLook + '\'' +
                ", isShow='" + isShow + '\'' +
                ", newsTime='" + newsTime + '\'' +
                '}';
    }
}
