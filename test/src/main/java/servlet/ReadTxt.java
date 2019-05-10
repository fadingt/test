package servlet;
import java.io.BufferedReader;
/*
 * 读取txt文件
 * 1.输入流读txt
 * 2.将txt分页存入list集合
 * 
 * */
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReadTxt")
public class ReadTxt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReadTxt() {
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> list = new ArrayList<String>();
		String filepath = "D://readme2.txt";
		File txt = new File(filepath);
		BufferedReader bis = new BufferedReader(new FileReader(txt));
		String line;
		while( (line = bis.readLine()) != null){
			list.add(line);
		}
		System.out.println(list.size());
		this.getServletContext().setAttribute("list", list);
		request.getRequestDispatcher("/showTXT.jsp").forward(request, response);
		bis.close();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
