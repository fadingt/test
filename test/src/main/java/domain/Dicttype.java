package domain;

public class Dicttype {
	private String dictitem;
	private String dictename;
	private String applyarea;
	private String edition;
	private String datatype;
	private String secondaryvalue;

	@Override
	public String toString() {
		return "Dicttype [dictitem=" + dictitem + ", dictename=" + dictename + ", applyarea=" + applyarea + ", edition="
				+ edition + ", datatype=" + datatype + ", secondaryvalue=" + secondaryvalue + "]";
	}

	public String getDictitem() {
		return dictitem;
	}

	public void setDictitem(String dictitem) {
		this.dictitem = dictitem;
	}

	public String getDictename() {
		return dictename;
	}

	public void setDictename(String dictename) {
		this.dictename = dictename;
	}

	public String getApplyarea() {
		return applyarea;
	}

	public void setApplyarea(String applyarea) {
		this.applyarea = applyarea;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getSecondaryvalue() {
		return secondaryvalue;
	}

	public void setSecondaryvalue(String secondaryvalue) {
		this.secondaryvalue = secondaryvalue;
	}

}
