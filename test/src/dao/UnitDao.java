package dao;

import java.io.Serializable;

import domain.Unit;

public interface UnitDao {

	void updateUnit(Unit unit);

	void deleteUnit(Unit unit);

	void addUnit(Unit unit);

	Unit getUnitById(Serializable id);

}