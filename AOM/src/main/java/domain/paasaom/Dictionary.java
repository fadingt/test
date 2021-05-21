package domain.paasaom;

import utils.HibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 11/23/2020 11:16 AM
 */
public class Dictionary {
    private int ID;
    private String dictname;
    private String dictcode;
    private String dicttype;
    private String parentcode;
    private boolean status;
    private boolean deleted;

    public static String translatedict(String dicttype, String dictcode) {
        Map<String, Object> prop = new HashMap<>();
        prop.put("dicttype", dicttype);
        prop.put("dictcode", dictcode);
        prop.put("deleted", false);
        List<Dictionary> list = HibernateUtil.listObjectByProperty(prop, Dictionary.class, 0, 1);
        if (0 == list.size()) {
            return null;
        }
        return list.get(0).dictname;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDictname() {
        return dictname;
    }

    public void setDictname(String dictname) {
        this.dictname = dictname;
    }

    public String getDictcode() {
        return dictcode;
    }

    public void setDictcode(String dictcode) {
        this.dictcode = dictcode;
    }

    public String getDicttype() {
        return dicttype;
    }

    public void setDicttype(String dicttype) {
        this.dicttype = dicttype;
    }

    public String getParentcode() {
        return parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
