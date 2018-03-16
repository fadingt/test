package action;

import com.opensymphony.xwork2.ActionSupport;

import dao.impl.HibernateUnitDaoImpl;
import domain.Unit;

@SuppressWarnings("serial")
public class SearchUnitAction extends ActionSupport {
	private Unit unit;

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		System.out.println("validate");
		super.validate();
	}

	public String searchUnit() {
		 unit = new HibernateUnitDaoImpl().getUnitById(unit.getUnitid());
		 if(unit == null){
		 this.addFieldError(unit.getUnitid(), "no unit with unitid: " + unit.getUnitid());
		 System.out.println(unit.toString());
		 return "input";
		 }
		return "OK";
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
