package action;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@WebServlet("/staticResourceServlet")
public class StaticResourceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        String filepath = "";
        File file = new File(filepath);
        ServletOutputStream sos = resp.getOutputStream();
        int len =0;
        if (file.exists() && file.isFile()){
            // TODO: 8/18/2020 write css file to browser
            FileReader fileReader = new FileReader(file);
            while((len=fileReader.read())!=-1){
                sos.write((char)len);
            }

        }else{
            sos.write("401".getBytes());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        doGet(req, resp);
    }
}
