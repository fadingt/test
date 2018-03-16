package dao.impl;

import java.io.Serializable;

import dao.UnitDao;
import domain.Unit;

public class HibernateUnitDaoImpl implements UnitDao {
	@Override
	public void updateUnit(Unit unit) {
		utils.HibernateUtil.updateObject(unit);
	}
	@Override
	public void deleteUnit(Unit unit){
		utils.HibernateUtil.deleteObject(unit);
	}
	@Override
	public void addUnit(Unit unit){
		utils.HibernateUtil.addObject(unit);
	}
	@Override
	public Unit getUnitById(Serializable id){
		return utils.HibernateUtil.getObjectById(Unit.class, id);
	}
}
