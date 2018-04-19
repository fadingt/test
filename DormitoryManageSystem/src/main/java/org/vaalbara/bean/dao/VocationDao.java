package org.vaalbara.bean.dao;

/**
 * Created by Administrator on 2018/3/14.
 */
public class VocationDao {
    private int newsId;
    private String newsName;
    private int newsAuthor;
    private String  newsStatus;
    private int newsLook;
    private String newsTime1;
    private String newsTime2;

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

    public int getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(int newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public String getNewsStatus() {
        return newsStatus;
    }

    public void setNewsStatus(String newsStatus) {
        this.newsStatus = newsStatus;
    }

    public int getNewsLook() {
        return newsLook;
    }

    public void setNewsLook(int newsLook) {
        this.newsLook = newsLook;
    }

    public String getNewsTime1() {
        return newsTime1;
    }

    public void setNewsTime1(String newsTime1) {
        this.newsTime1 = newsTime1;
    }

    public String getNewsTime2() {
        return newsTime2;
    }

    public void setNewsTime2(String newsTime2) {
        this.newsTime2 = newsTime2;
    }

    public VocationDao(String newsName, int newsAuthor, String newsStatus, int newsLook, String newsTime1, String newsTime2) {
        this.newsName = newsName;
        this.newsAuthor = newsAuthor;
        this.newsStatus = newsStatus;
        this.newsLook = newsLook;
        this.newsTime1 = newsTime1;
        this.newsTime2 = newsTime2;
    }

    public VocationDao() {
    }

    public VocationDao(int newsId, String newsName, int newsAuthor, String newsStatus, int newsLook, String newsTime1, String newsTime2) {
        this.newsId = newsId;
        this.newsName = newsName;
        this.newsAuthor = newsAuthor;
        this.newsStatus = newsStatus;
        this.newsLook = newsLook;
        this.newsTime1 = newsTime1;
        this.newsTime2 = newsTime2;
    }

    @Override
    public String toString() {
        return "VocationDao{" +
                "newsId=" + newsId +
                ", newsName='" + newsName + '\'' +
                ", newsAuthor=" + newsAuthor +
                ", newsStatus='" + newsStatus + '\'' +
                ", newsLook=" + newsLook +
                ", newsTime1='" + newsTime1 + '\'' +
                ", newsTime2='" + newsTime2 + '\'' +
                '}';
    }
}
