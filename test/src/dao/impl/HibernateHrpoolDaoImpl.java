package dao.impl;

import java.io.Serializable;

import dao.HrpoolDao;
import domain.Hrpool;

public class HibernateHrpoolDaoImpl implements HrpoolDao {

	@Override
	public void updateHrpool(Hrpool hrpool) {
		utils.HibernateUtil.updateObject(hrpool);
	}

	@Override
	public void deleteHrpool(Hrpool hrpool) {
		utils.HibernateUtil.deleteObject(hrpool);
	}

	@Override
	public void addHrpool(Hrpool hrpool) {
		utils.HibernateUtil.addObject(hrpool);
	}

	@Override
	public Hrpool getHrpoolById(Serializable id) {
		return utils.HibernateUtil.getObjectById(Hrpool.class, id);
	}

}
