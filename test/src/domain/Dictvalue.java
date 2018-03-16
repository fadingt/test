package domain;

public class Dictvalue {
	private String dictitem;
	private String dictvalue;
	private String dictname;
	private String dictdesc;

	@Override
	public String toString() {
		return "Dictvalue [dictitem=" + dictitem + ", dictvalue=" + dictvalue + ", dictname=" + dictname + ", dictdesc="
				+ dictdesc + "]";
	}

	public String getDictitem() {
		return dictitem;
	}

	public void setDictitem(String dictitem) {
		this.dictitem = dictitem;
	}

	public String getDictvalue() {
		return dictvalue;
	}

	public void setDictvalue(String dictvalue) {
		this.dictvalue = dictvalue;
	}

	public String getDictname() {
		return dictname;
	}

	public void setDictname(String dictname) {
		this.dictname = dictname;
	}

	public String getDictdesc() {
		return dictdesc;
	}

	public void setDictdesc(String dictdesc) {
		this.dictdesc = dictdesc;
	}

}
