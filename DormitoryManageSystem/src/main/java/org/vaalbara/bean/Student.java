package org.vaalbara.bean;

/**
 * Created by Administrator on 2018/3/12.
 */
public class Student {
    private int sId;
    private String sName;
    private String sEmail;
    private String sPhone;
    private String sClass;
    private String sPass;
    private Integer dId;
    private Integer pId;
    private Profesion profesion;
    private Vocation vocation;

    public Student(int sId, String sName, String sEmail, String sPhone, String sClass, String sPass, Integer pId, Profesion profesion, Vocation vocation) {
        this.sId = sId;
        this.sName = sName;
        this.sEmail = sEmail;
        this.sPhone = sPhone;
        this.sClass = sClass;
        this.sPass = sPass;
        this.pId = pId;
        this.profesion = profesion;
        this.vocation = vocation;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Student(int sId, String sName, String sEmail, String sPhone, String sClass, String sPass, Profesion profesion, Vocation vocation) {
        this.sId = sId;
        this.sName = sName;
        this.sEmail = sEmail;
        this.sPhone = sPhone;
        this.sClass = sClass;
        this.sPass = sPass;
        this.profesion = profesion;
        this.vocation = vocation;
    }

    public Student(int sId, String sName, String sEmail, String sPhone, String sClass, String sPass, Integer dId, Integer pId, Profesion profesion, Vocation vocation) {
        this.sId = sId;
        this.sName = sName;
        this.sEmail = sEmail;
        this.sPhone = sPhone;
        this.sClass = sClass;
        this.sPass = sPass;
        this.dId = dId;
        this.pId = pId;
        this.profesion = profesion;
        this.vocation = vocation;
    }

    public Vocation getVocation() {
        return vocation;
    }

    public void setVocation(Vocation vocation) {
        this.vocation = vocation;
    }

    public Student() {
    }

    public Student(String sEmail, String sPass) {
        this.sEmail = sEmail;
        this.sPass = sPass;
    }

    public Student(int sId, String sName, String sEmail, String sPhone, String sClass, String sPass, Profesion profesion) {
        this.sId = sId;
        this.sName = sName;
        this.sEmail = sEmail;
        this.sPhone = sPhone;
        this.sClass = sClass;
        this.sPass = sPass;
        this.profesion = profesion;
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getsPass() {
        return sPass;
    }

    public void setsPass(String sPass) {
        this.sPass = sPass;
    }

    public Profesion getProfesion() {
        return profesion;
    }

    public void setProfesion(Profesion profesion) {
        this.profesion = profesion;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sId=" + sId +
                ", sName='" + sName + '\'' +
                ", sEmail='" + sEmail + '\'' +
                ", sPhone='" + sPhone + '\'' +
                ", sClass='" + sClass + '\'' +
                ", sPass='" + sPass + '\'' +
                ", dId=" + dId +
                ", pId=" + pId +
                ", profesion=" + profesion +
                ", vocation=" + vocation +
                '}';
    }
}
