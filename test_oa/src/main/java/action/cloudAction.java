package action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
// TODO: 8/7/2020 FTP server
public class cloudAction {
    @PostMapping("/upload.do")
    public boolean upload(){
        return false;
    }

}
