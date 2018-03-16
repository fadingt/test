package servlet;

import java.io.*;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({ "/DownloadServlet" })
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int buf_size = 1024;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = this.getServletContext().getRealPath("/download/ฐฌหน.jpg");
		String fileName = path.substring(path.lastIndexOf("\\")+1);
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("content-disposition", "attachment;filename="+fileName);
		response.setContentType("image/JPEG;charset=UTF-8");
//		response.setHeader("content-type", "image/JPEG");
		FileInputStream fis = null;
		fis = new FileInputStream(path);
		byte[] buf = new byte[buf_size];
		ServletOutputStream sos = response.getOutputStream();
		int len = 0;
		while ((len = fis.read(buf)) != -1) {
			sos.write(buf, 0 ,len);
		}
		if (fis != null)
			fis.close();
		if (sos != null)
			sos.close();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}