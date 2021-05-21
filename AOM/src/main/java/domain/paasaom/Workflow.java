package domain.paasaom;

import java.util.Date;

public class Workflow {
    private int id;
    private int I_TECHID;
    private Date LAST_UPDATE_TIME;
    private String S_MAPFLAG;
    private String S_TARGET1;
    private String S_TARGET2;
    private String S_SOURCE;
    private String S_TYPE;
    private String S_DESC;
    private int IS_DELETE;

    @Override
    public String toString() {
        // TODO: 10/10/2020 rewrite to human understandable digest
        return "Workflow{" +
                "id=" + id +
                ", I_TECHID=" + I_TECHID +
                ", LAST_UPDATE_TIME=" + LAST_UPDATE_TIME +
                ", S_MAPFLAG='" + S_MAPFLAG + '\'' +
                ", S_TARGET1='" + S_TARGET1 + '\'' +
                ", S_TARGET2='" + S_TARGET2 + '\'' +
                ", S_SOURCE='" + S_SOURCE + '\'' +
                ", S_TYPE='" + S_TYPE + '\'' +
                ", S_DESC='" + S_DESC + '\'' +
                '}';
    }

    public int getIS_DELETE() {
        return IS_DELETE;
    }

    public void setIS_DELETE(int IS_DELETE) {
        this.IS_DELETE = IS_DELETE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getI_TECHID() {
        return I_TECHID;
    }

    public void setI_TECHID(int i_TECHID) {
        I_TECHID = i_TECHID;
    }

    public Date getLAST_UPDATE_TIME() {
        return LAST_UPDATE_TIME;
    }

    public void setLAST_UPDATE_TIME(Date LAST_UPDATE_TIME) {
        this.LAST_UPDATE_TIME = LAST_UPDATE_TIME;
    }

    public String getS_MAPFLAG() {
        return S_MAPFLAG;
    }

    public void setS_MAPFLAG(String s_MAPFLAG) {
        S_MAPFLAG = s_MAPFLAG;
    }

    public String getS_TARGET1() {
        return S_TARGET1;
    }

    public void setS_TARGET1(String s_TARGET1) {
        S_TARGET1 = s_TARGET1;
    }

    public String getS_TARGET2() {
        return S_TARGET2;
    }

    public void setS_TARGET2(String s_TARGET2) {
        S_TARGET2 = s_TARGET2;
    }

    public String getS_SOURCE() {
        return S_SOURCE;
    }

    public void setS_SOURCE(String s_SOURCE) {
        S_SOURCE = s_SOURCE;
    }

    public String getS_TYPE() {
        return S_TYPE;
    }

    public void setS_TYPE(String s_TYPE) {
        S_TYPE = s_TYPE;
    }

    public String getS_DESC() {
        return S_DESC;
    }

    public void setS_DESC(String s_DESC) {
        S_DESC = s_DESC;
    }
}
