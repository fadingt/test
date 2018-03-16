package web.tag;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ForEachTag extends SimpleTagSupport {
	private String var;
	private Object items;
	private Collection<?> collection;

	public void setVar(String var) {
		this.var = var;
	}

	public void setItems() {
		if(items instanceof Collection){
			this.collection = (Collection<?>) items;
		}
		if(items instanceof Map){
			this.collection = ((Map<?, ?>) items).entrySet();
		}
		if(items instanceof Object[]){
			Object[] obj = (Object[]) items;
			this.collection = Arrays.asList(obj);
		}


	}

	@Override
	public void doTag() throws JspException, IOException {
		Iterator<?> it = this.collection.iterator();
		while(it.hasNext()){
			this.getJspContext().setAttribute(var, it.next());
			this.getJspBody().invoke(null);
		}
	}

}
