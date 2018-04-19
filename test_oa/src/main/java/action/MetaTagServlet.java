package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MetaTagServlet")
public class MetaTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
//		response.getWriter().append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">").append("中文");
		ServletOutputStream out = response.getOutputStream();
		out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">".getBytes("UTF-8"));
		out.write("中文".getBytes("UTF-8"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}