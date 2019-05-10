package dao;

import java.io.Serializable;

import domain.Hrpool;

public interface HrpoolDao {

	void updateHrpool(Hrpool hrpool);

	void deleteHrpool(Hrpool hrpool);

	void addHrpool(Hrpool hrpool);

	Hrpool getHrpoolById(Serializable id);

}
