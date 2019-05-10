package web.tag;

import java.io.IOException;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ViewIPTag extends TagSupport {

	private static final long serialVersionUID = -468418801333914994L;
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest req = (HttpServletRequest) this.pageContext.getRequest();
		JspWriter out = this.pageContext.getOut();
		try {
			out.print(req.getRemoteAddr());
		} catch (IOException e) {
			throw new RollbackException(e);
		}

		return super.doStartTag();
	}

}
