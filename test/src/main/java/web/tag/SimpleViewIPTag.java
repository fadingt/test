package web.tag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SimpleViewIPTag extends SimpleTagSupport{
	private int count;
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public void doTag() throws JspException, IOException {
		JspFragment jf = this.getJspBody();
		JspWriter out = this.getJspContext().getOut();
		PageContext pc = (PageContext)this.getJspContext();
		HttpServletRequest req = (HttpServletRequest) pc.getRequest();
		
		String ip = req.getRemoteAddr();
		StringWriter sw = new StringWriter();
		sw.write(ip);
		jf.invoke(sw);			
		
		for (int i = 0; i < count; i++) {
			out.print(sw);
		}
	}

}
