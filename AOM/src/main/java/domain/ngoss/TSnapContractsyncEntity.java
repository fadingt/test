package domain.ngoss;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 5/21/2021 11:22 AM
 */
@Entity
@Table(name = "t_snap_contractsync", schema = "ngoss", catalog = "ngoss")
public class TSnapContractsyncEntity {
    private String id;
    private String sConcode;
    private String sConname;
    private String sStacode;
    private String sStatype;
    private String sContype;
    private String sSaleman;
    private String sSaleorg;
    private String sCustname;
    private String sCompname;
    private String sFinalname;
    private String sTechnan;
    private String sTechdept;
    private BigDecimal dlConamt;
    private BigDecimal dlOldyearamt;
    private BigDecimal dlOldmonthamt;
    private BigDecimal dlStatemamt;
    private BigDecimal dlOldamt;
    private BigDecimal dlYearamt;
    private BigDecimal dlAmonthamt;
    private BigDecimal dlPmonthamt;
    private BigDecimal dlLastbilamt;
    private BigDecimal dlMbilldev;
    private BigDecimal dlOlbackamt;
    private BigDecimal dlYbackamt;
    private BigDecimal dlAmbackamt;
    private BigDecimal dlMonsyamt;
    private BigDecimal dlSumsyamt;
    private BigDecimal dlPmbackamt;
    private BigDecimal dlLasbacamt;
    private BigDecimal dlMbackdev;
    private BigDecimal dlPmbilamt1;
    private BigDecimal dlPmbilamt2;
    private BigDecimal dlPmbilamt3;
    private BigDecimal dlPmbilamt4;
    private BigDecimal dlPmbilamt5;
    private BigDecimal dlPmbilamt6;
    private BigDecimal dlPmbacamt1;
    private BigDecimal dlPmbacamt2;
    private BigDecimal dlPmbacamt3;
    private BigDecimal dlPmbacamt4;
    private BigDecimal dlPmbacamt5;
    private BigDecimal dlPmbacamt6;
    private BigDecimal dlBillwarn;
    private BigDecimal dlBackwarn;
    private String dlAbillper;
    private String dlAbackper;
    private BigDecimal dlBillcheck;
    private BigDecimal dlBackcheck;
    private Integer iBillcount;
    private String sConstatus;
    private BigDecimal dlAbillsum;
    private BigDecimal dlAbacksum;
    private BigDecimal dlHasbill;
    private String sConnum;
    private String sAccdate;
    private String sStage;
    private Date sBackdate;
    private Timestamp dtSnaptime;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "S_CONCODE")
    public String getsConcode() {
        return sConcode;
    }

    public void setsConcode(String sConcode) {
        this.sConcode = sConcode;
    }

    @Basic
    @Column(name = "S_CONNAME")
    public String getsConname() {
        return sConname;
    }

    public void setsConname(String sConname) {
        this.sConname = sConname;
    }

    @Basic
    @Column(name = "S_STACODE")
    public String getsStacode() {
        return sStacode;
    }

    public void setsStacode(String sStacode) {
        this.sStacode = sStacode;
    }

    @Basic
    @Column(name = "S_STATYPE")
    public String getsStatype() {
        return sStatype;
    }

    public void setsStatype(String sStatype) {
        this.sStatype = sStatype;
    }

    @Basic
    @Column(name = "S_CONTYPE")
    public String getsContype() {
        return sContype;
    }

    public void setsContype(String sContype) {
        this.sContype = sContype;
    }

    @Basic
    @Column(name = "S_SALEMAN")
    public String getsSaleman() {
        return sSaleman;
    }

    public void setsSaleman(String sSaleman) {
        this.sSaleman = sSaleman;
    }

    @Basic
    @Column(name = "S_SALEORG")
    public String getsSaleorg() {
        return sSaleorg;
    }

    public void setsSaleorg(String sSaleorg) {
        this.sSaleorg = sSaleorg;
    }

    @Basic
    @Column(name = "S_CUSTNAME")
    public String getsCustname() {
        return sCustname;
    }

    public void setsCustname(String sCustname) {
        this.sCustname = sCustname;
    }

    @Basic
    @Column(name = "S_COMPNAME")
    public String getsCompname() {
        return sCompname;
    }

    public void setsCompname(String sCompname) {
        this.sCompname = sCompname;
    }

    @Basic
    @Column(name = "S_FINALNAME")
    public String getsFinalname() {
        return sFinalname;
    }

    public void setsFinalname(String sFinalname) {
        this.sFinalname = sFinalname;
    }

    @Basic
    @Column(name = "S_TECHNAN")
    public String getsTechnan() {
        return sTechnan;
    }

    public void setsTechnan(String sTechnan) {
        this.sTechnan = sTechnan;
    }

    @Basic
    @Column(name = "S_TECHDEPT")
    public String getsTechdept() {
        return sTechdept;
    }

    public void setsTechdept(String sTechdept) {
        this.sTechdept = sTechdept;
    }

    @Basic
    @Column(name = "DL_CONAMT")
    public BigDecimal getDlConamt() {
        return dlConamt;
    }

    public void setDlConamt(BigDecimal dlConamt) {
        this.dlConamt = dlConamt;
    }

    @Basic
    @Column(name = "DL_OLDYEARAMT")
    public BigDecimal getDlOldyearamt() {
        return dlOldyearamt;
    }

    public void setDlOldyearamt(BigDecimal dlOldyearamt) {
        this.dlOldyearamt = dlOldyearamt;
    }

    @Basic
    @Column(name = "DL_OLDMONTHAMT")
    public BigDecimal getDlOldmonthamt() {
        return dlOldmonthamt;
    }

    public void setDlOldmonthamt(BigDecimal dlOldmonthamt) {
        this.dlOldmonthamt = dlOldmonthamt;
    }

    @Basic
    @Column(name = "DL_STATEMAMT")
    public BigDecimal getDlStatemamt() {
        return dlStatemamt;
    }

    public void setDlStatemamt(BigDecimal dlStatemamt) {
        this.dlStatemamt = dlStatemamt;
    }

    @Basic
    @Column(name = "DL_OLDAMT")
    public BigDecimal getDlOldamt() {
        return dlOldamt;
    }

    public void setDlOldamt(BigDecimal dlOldamt) {
        this.dlOldamt = dlOldamt;
    }

    @Basic
    @Column(name = "DL_YEARAMT")
    public BigDecimal getDlYearamt() {
        return dlYearamt;
    }

    public void setDlYearamt(BigDecimal dlYearamt) {
        this.dlYearamt = dlYearamt;
    }

    @Basic
    @Column(name = "DL_AMONTHAMT")
    public BigDecimal getDlAmonthamt() {
        return dlAmonthamt;
    }

    public void setDlAmonthamt(BigDecimal dlAmonthamt) {
        this.dlAmonthamt = dlAmonthamt;
    }

    @Basic
    @Column(name = "DL_PMONTHAMT")
    public BigDecimal getDlPmonthamt() {
        return dlPmonthamt;
    }

    public void setDlPmonthamt(BigDecimal dlPmonthamt) {
        this.dlPmonthamt = dlPmonthamt;
    }

    @Basic
    @Column(name = "DL_LASTBILAMT")
    public BigDecimal getDlLastbilamt() {
        return dlLastbilamt;
    }

    public void setDlLastbilamt(BigDecimal dlLastbilamt) {
        this.dlLastbilamt = dlLastbilamt;
    }

    @Basic
    @Column(name = "DL_MBILLDEV")
    public BigDecimal getDlMbilldev() {
        return dlMbilldev;
    }

    public void setDlMbilldev(BigDecimal dlMbilldev) {
        this.dlMbilldev = dlMbilldev;
    }

    @Basic
    @Column(name = "DL_OLBACKAMT")
    public BigDecimal getDlOlbackamt() {
        return dlOlbackamt;
    }

    public void setDlOlbackamt(BigDecimal dlOlbackamt) {
        this.dlOlbackamt = dlOlbackamt;
    }

    @Basic
    @Column(name = "DL_YBACKAMT")
    public BigDecimal getDlYbackamt() {
        return dlYbackamt;
    }

    public void setDlYbackamt(BigDecimal dlYbackamt) {
        this.dlYbackamt = dlYbackamt;
    }

    @Basic
    @Column(name = "DL_AMBACKAMT")
    public BigDecimal getDlAmbackamt() {
        return dlAmbackamt;
    }

    public void setDlAmbackamt(BigDecimal dlAmbackamt) {
        this.dlAmbackamt = dlAmbackamt;
    }

    @Basic
    @Column(name = "DL_MONSYAMT")
    public BigDecimal getDlMonsyamt() {
        return dlMonsyamt;
    }

    public void setDlMonsyamt(BigDecimal dlMonsyamt) {
        this.dlMonsyamt = dlMonsyamt;
    }

    @Basic
    @Column(name = "DL_SUMSYAMT")
    public BigDecimal getDlSumsyamt() {
        return dlSumsyamt;
    }

    public void setDlSumsyamt(BigDecimal dlSumsyamt) {
        this.dlSumsyamt = dlSumsyamt;
    }

    @Basic
    @Column(name = "DL_PMBACKAMT")
    public BigDecimal getDlPmbackamt() {
        return dlPmbackamt;
    }

    public void setDlPmbackamt(BigDecimal dlPmbackamt) {
        this.dlPmbackamt = dlPmbackamt;
    }

    @Basic
    @Column(name = "DL_LASBACAMT")
    public BigDecimal getDlLasbacamt() {
        return dlLasbacamt;
    }

    public void setDlLasbacamt(BigDecimal dlLasbacamt) {
        this.dlLasbacamt = dlLasbacamt;
    }

    @Basic
    @Column(name = "DL_MBACKDEV")
    public BigDecimal getDlMbackdev() {
        return dlMbackdev;
    }

    public void setDlMbackdev(BigDecimal dlMbackdev) {
        this.dlMbackdev = dlMbackdev;
    }

    @Basic
    @Column(name = "DL_PMBILAMT1")
    public BigDecimal getDlPmbilamt1() {
        return dlPmbilamt1;
    }

    public void setDlPmbilamt1(BigDecimal dlPmbilamt1) {
        this.dlPmbilamt1 = dlPmbilamt1;
    }

    @Basic
    @Column(name = "DL_PMBILAMT2")
    public BigDecimal getDlPmbilamt2() {
        return dlPmbilamt2;
    }

    public void setDlPmbilamt2(BigDecimal dlPmbilamt2) {
        this.dlPmbilamt2 = dlPmbilamt2;
    }

    @Basic
    @Column(name = "DL_PMBILAMT3")
    public BigDecimal getDlPmbilamt3() {
        return dlPmbilamt3;
    }

    public void setDlPmbilamt3(BigDecimal dlPmbilamt3) {
        this.dlPmbilamt3 = dlPmbilamt3;
    }

    @Basic
    @Column(name = "DL_PMBILAMT4")
    public BigDecimal getDlPmbilamt4() {
        return dlPmbilamt4;
    }

    public void setDlPmbilamt4(BigDecimal dlPmbilamt4) {
        this.dlPmbilamt4 = dlPmbilamt4;
    }

    @Basic
    @Column(name = "DL_PMBILAMT5")
    public BigDecimal getDlPmbilamt5() {
        return dlPmbilamt5;
    }

    public void setDlPmbilamt5(BigDecimal dlPmbilamt5) {
        this.dlPmbilamt5 = dlPmbilamt5;
    }

    @Basic
    @Column(name = "DL_PMBILAMT6")
    public BigDecimal getDlPmbilamt6() {
        return dlPmbilamt6;
    }

    public void setDlPmbilamt6(BigDecimal dlPmbilamt6) {
        this.dlPmbilamt6 = dlPmbilamt6;
    }

    @Basic
    @Column(name = "DL_PMBACAMT1")
    public BigDecimal getDlPmbacamt1() {
        return dlPmbacamt1;
    }

    public void setDlPmbacamt1(BigDecimal dlPmbacamt1) {
        this.dlPmbacamt1 = dlPmbacamt1;
    }

    @Basic
    @Column(name = "DL_PMBACAMT2")
    public BigDecimal getDlPmbacamt2() {
        return dlPmbacamt2;
    }

    public void setDlPmbacamt2(BigDecimal dlPmbacamt2) {
        this.dlPmbacamt2 = dlPmbacamt2;
    }

    @Basic
    @Column(name = "DL_PMBACAMT3")
    public BigDecimal getDlPmbacamt3() {
        return dlPmbacamt3;
    }

    public void setDlPmbacamt3(BigDecimal dlPmbacamt3) {
        this.dlPmbacamt3 = dlPmbacamt3;
    }

    @Basic
    @Column(name = "DL_PMBACAMT4")
    public BigDecimal getDlPmbacamt4() {
        return dlPmbacamt4;
    }

    public void setDlPmbacamt4(BigDecimal dlPmbacamt4) {
        this.dlPmbacamt4 = dlPmbacamt4;
    }

    @Basic
    @Column(name = "DL_PMBACAMT5")
    public BigDecimal getDlPmbacamt5() {
        return dlPmbacamt5;
    }

    public void setDlPmbacamt5(BigDecimal dlPmbacamt5) {
        this.dlPmbacamt5 = dlPmbacamt5;
    }

    @Basic
    @Column(name = "DL_PMBACAMT6")
    public BigDecimal getDlPmbacamt6() {
        return dlPmbacamt6;
    }

    public void setDlPmbacamt6(BigDecimal dlPmbacamt6) {
        this.dlPmbacamt6 = dlPmbacamt6;
    }

    @Basic
    @Column(name = "DL_BILLWARN")
    public BigDecimal getDlBillwarn() {
        return dlBillwarn;
    }

    public void setDlBillwarn(BigDecimal dlBillwarn) {
        this.dlBillwarn = dlBillwarn;
    }

    @Basic
    @Column(name = "DL_BACKWARN")
    public BigDecimal getDlBackwarn() {
        return dlBackwarn;
    }

    public void setDlBackwarn(BigDecimal dlBackwarn) {
        this.dlBackwarn = dlBackwarn;
    }

    @Basic
    @Column(name = "DL_ABILLPER")
    public String getDlAbillper() {
        return dlAbillper;
    }

    public void setDlAbillper(String dlAbillper) {
        this.dlAbillper = dlAbillper;
    }

    @Basic
    @Column(name = "DL_ABACKPER")
    public String getDlAbackper() {
        return dlAbackper;
    }

    public void setDlAbackper(String dlAbackper) {
        this.dlAbackper = dlAbackper;
    }

    @Basic
    @Column(name = "DL_BILLCHECK")
    public BigDecimal getDlBillcheck() {
        return dlBillcheck;
    }

    public void setDlBillcheck(BigDecimal dlBillcheck) {
        this.dlBillcheck = dlBillcheck;
    }

    @Basic
    @Column(name = "DL_BACKCHECK")
    public BigDecimal getDlBackcheck() {
        return dlBackcheck;
    }

    public void setDlBackcheck(BigDecimal dlBackcheck) {
        this.dlBackcheck = dlBackcheck;
    }

    @Basic
    @Column(name = "I_BILLCOUNT")
    public Integer getiBillcount() {
        return iBillcount;
    }

    public void setiBillcount(Integer iBillcount) {
        this.iBillcount = iBillcount;
    }

    @Basic
    @Column(name = "S_CONSTATUS")
    public String getsConstatus() {
        return sConstatus;
    }

    public void setsConstatus(String sConstatus) {
        this.sConstatus = sConstatus;
    }

    @Basic
    @Column(name = "DL_ABILLSUM")
    public BigDecimal getDlAbillsum() {
        return dlAbillsum;
    }

    public void setDlAbillsum(BigDecimal dlAbillsum) {
        this.dlAbillsum = dlAbillsum;
    }

    @Basic
    @Column(name = "DL_ABACKSUM")
    public BigDecimal getDlAbacksum() {
        return dlAbacksum;
    }

    public void setDlAbacksum(BigDecimal dlAbacksum) {
        this.dlAbacksum = dlAbacksum;
    }

    @Basic
    @Column(name = "DL_HASBILL")
    public BigDecimal getDlHasbill() {
        return dlHasbill;
    }

    public void setDlHasbill(BigDecimal dlHasbill) {
        this.dlHasbill = dlHasbill;
    }

    @Basic
    @Column(name = "S_CONNUM")
    public String getsConnum() {
        return sConnum;
    }

    public void setsConnum(String sConnum) {
        this.sConnum = sConnum;
    }

    @Basic
    @Column(name = "S_ACCDATE")
    public String getsAccdate() {
        return sAccdate;
    }

    public void setsAccdate(String sAccdate) {
        this.sAccdate = sAccdate;
    }

    @Basic
    @Column(name = "S_STAGE")
    public String getsStage() {
        return sStage;
    }

    public void setsStage(String sStage) {
        this.sStage = sStage;
    }

    @Basic
    @Column(name = "S_BACKDATE")
    public Date getsBackdate() {
        return sBackdate;
    }

    public void setsBackdate(Date sBackdate) {
        this.sBackdate = sBackdate;
    }

    @Basic
    @Column(name = "dt_snaptime")
    public Timestamp getdtSnaptime() {
        return dtSnaptime;
    }

    public void setDtSnaptime(Timestamp dtSnaptime) {
        this.dtSnaptime = dtSnaptime;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TSnapContractsyncEntity entity = (TSnapContractsyncEntity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(sConcode, entity.sConcode) &&
                Objects.equals(sConname, entity.sConname) &&
                Objects.equals(sStacode, entity.sStacode) &&
                Objects.equals(sStatype, entity.sStatype) &&
                Objects.equals(sContype, entity.sContype) &&
                Objects.equals(sSaleman, entity.sSaleman) &&
                Objects.equals(sSaleorg, entity.sSaleorg) &&
                Objects.equals(sCustname, entity.sCustname) &&
                Objects.equals(sCompname, entity.sCompname) &&
                Objects.equals(sFinalname, entity.sFinalname) &&
                Objects.equals(sTechnan, entity.sTechnan) &&
                Objects.equals(sTechdept, entity.sTechdept) &&
                Objects.equals(dlConamt, entity.dlConamt) &&
                Objects.equals(dlOldyearamt, entity.dlOldyearamt) &&
                Objects.equals(dlOldmonthamt, entity.dlOldmonthamt) &&
                Objects.equals(dlStatemamt, entity.dlStatemamt) &&
                Objects.equals(dlOldamt, entity.dlOldamt) &&
                Objects.equals(dlYearamt, entity.dlYearamt) &&
                Objects.equals(dlAmonthamt, entity.dlAmonthamt) &&
                Objects.equals(dlPmonthamt, entity.dlPmonthamt) &&
                Objects.equals(dlLastbilamt, entity.dlLastbilamt) &&
                Objects.equals(dlMbilldev, entity.dlMbilldev) &&
                Objects.equals(dlOlbackamt, entity.dlOlbackamt) &&
                Objects.equals(dlYbackamt, entity.dlYbackamt) &&
                Objects.equals(dlAmbackamt, entity.dlAmbackamt) &&
                Objects.equals(dlMonsyamt, entity.dlMonsyamt) &&
                Objects.equals(dlSumsyamt, entity.dlSumsyamt) &&
                Objects.equals(dlPmbackamt, entity.dlPmbackamt) &&
                Objects.equals(dlLasbacamt, entity.dlLasbacamt) &&
                Objects.equals(dlMbackdev, entity.dlMbackdev) &&
                Objects.equals(dlPmbilamt1, entity.dlPmbilamt1) &&
                Objects.equals(dlPmbilamt2, entity.dlPmbilamt2) &&
                Objects.equals(dlPmbilamt3, entity.dlPmbilamt3) &&
                Objects.equals(dlPmbilamt4, entity.dlPmbilamt4) &&
                Objects.equals(dlPmbilamt5, entity.dlPmbilamt5) &&
                Objects.equals(dlPmbilamt6, entity.dlPmbilamt6) &&
                Objects.equals(dlPmbacamt1, entity.dlPmbacamt1) &&
                Objects.equals(dlPmbacamt2, entity.dlPmbacamt2) &&
                Objects.equals(dlPmbacamt3, entity.dlPmbacamt3) &&
                Objects.equals(dlPmbacamt4, entity.dlPmbacamt4) &&
                Objects.equals(dlPmbacamt5, entity.dlPmbacamt5) &&
                Objects.equals(dlPmbacamt6, entity.dlPmbacamt6) &&
                Objects.equals(dlBillwarn, entity.dlBillwarn) &&
                Objects.equals(dlBackwarn, entity.dlBackwarn) &&
                Objects.equals(dlAbillper, entity.dlAbillper) &&
                Objects.equals(dlAbackper, entity.dlAbackper) &&
                Objects.equals(dlBillcheck, entity.dlBillcheck) &&
                Objects.equals(dlBackcheck, entity.dlBackcheck) &&
                Objects.equals(iBillcount, entity.iBillcount) &&
                Objects.equals(sConstatus, entity.sConstatus) &&
                Objects.equals(dlAbillsum, entity.dlAbillsum) &&
                Objects.equals(dlAbacksum, entity.dlAbacksum) &&
                Objects.equals(dlHasbill, entity.dlHasbill) &&
                Objects.equals(sConnum, entity.sConnum) &&
                Objects.equals(sAccdate, entity.sAccdate) &&
                Objects.equals(sStage, entity.sStage) &&
                Objects.equals(sBackdate, entity.sBackdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sConcode, sConname, sStacode, sStatype, sContype, sSaleman, sSaleorg, sCustname, sCompname, sFinalname, sTechnan, sTechdept, dlConamt, dlOldyearamt, dlOldmonthamt, dlStatemamt, dlOldamt, dlYearamt, dlAmonthamt, dlPmonthamt, dlLastbilamt, dlMbilldev, dlOlbackamt, dlYbackamt, dlAmbackamt, dlMonsyamt, dlSumsyamt, dlPmbackamt, dlLasbacamt, dlMbackdev, dlPmbilamt1, dlPmbilamt2, dlPmbilamt3, dlPmbilamt4, dlPmbilamt5, dlPmbilamt6, dlPmbacamt1, dlPmbacamt2, dlPmbacamt3, dlPmbacamt4, dlPmbacamt5, dlPmbacamt6, dlBillwarn, dlBackwarn, dlAbillper, dlAbackper, dlBillcheck, dlBackcheck, iBillcount, sConstatus, dlAbillsum, dlAbacksum, dlHasbill, sConnum, sAccdate, sStage, sBackdate);
    }
}
