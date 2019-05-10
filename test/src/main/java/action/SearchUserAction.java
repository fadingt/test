package action;

import java.util.List;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dao.impl.HibernateUserDaoImpl;
import domain.User;

public class SearchUserAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private User user;

	public String getuserbyid() {
//		user = utils.HibernateDaoImpl.getObjectById(User.class, user.getUserid());
		String sql = "from User where 1=1";
		String[] criteria = {user.getUsername(),user.getDeptname(),user.getSex().toString()};
		for(int i=0; i<criteria.length; i++){
			if(!"".equals(criteria[i]) && criteria[i]!=null){
				sql = sql + "and" + criteria[i].trim() + "=?";
			}
		}
		List<User> users = new HibernateUserDaoImpl().getUser(sql, criteria);
//		List<User> users = new HibernateUserDaoImpl().getUserByName(user.getUsername());
		ServletActionContext.getRequest().setAttribute("users", users);
		return "OK";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
