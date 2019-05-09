package action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import domain.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * <p>
 * an example to learn spring mvc {@link RequestMapping @RequestMapping}
 * </p>
 *
 * @author liuxingyu
 */
@Controller
public class Index {

    /**
     * 使用 {@link RequestParam @RequestParam}传参
     *
     * @param message
     * @return
     */
    @RequestMapping("/index.do")
    public String index(@RequestParam(defaultValue = "hello world") String message) {
        return "index";
    }

    @RequestMapping("/index2.do")
    public String index2() {
        return "index2";
    }

    @RequestMapping("/index3.do")
    public void index3(HttpServletResponse resp) {
        try {
            resp.getWriter().append("get response from method para");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //	TODO get request from listner
//	@RequestMapping("/request.do")
//	public void getRequest() throws ServletException, IOException {
//		 HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//		 HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
////		 Cookie cookie = new Cookie("username", "liuxingyu");
////		 cookie.setMaxAge(3600);
////		 cookie.setPath("");
////		 resp.addCookie(cookie);
////		 try {
////		  resp.getWriter().append("get response from listener");
////		  } catch (IOException e) {
////		  throw new RuntimeException(e);
////		  }
//		 req.setAttribute("a", "hello world");
//		 req.getRequestDispatcher("/WEB-INF/views/index2.jsp").forward(req,
//		 resp);
//	}
    @RequestMapping("/vue.do")
    public String vue() {
        return "vue";
    }

    @PostMapping(produces = "application/JSON;charset=UTF-8", value = "/prod.do")
    @ResponseBody
    public String getProduces() {
        User user = new User();
        user.setUsername("liuxingyu");
        user.setDeptname("haubei");
        user.setCellphone("1234");
        JSONArray array = new JSONArray();
        array.add(user);
        return array.toString();
    }

    @PostMapping(value = "/cons.do")
    @ResponseBody
    public User getConsumes(@RequestBody User user) {
        System.out.println(user.toString());
        return user;
    }

    @RequestMapping("index4.do")
    public String index4() {
        return "index4";
    }

    @RequestMapping("index4_jsonobj.do")
    @ResponseBody
    public JSONObject index4_json() {
        JSONObject obj = new JSONObject();
        obj.put("username", "刘兴宇");
        return obj;
    }

    @RequestMapping(produces = "application/JSON;charset=UTF-8", value = "index4_jsonarr.do")
    @ResponseBody
    public String index4_arr() {
        JSONArray arr = new JSONArray();
        User u1 = new User("张鸿宇");
        User u2 = new User("王容峰");
        User u3 = new User("陈茁");
        arr.add(u1);
        arr.add(u2);
        arr.add(u3);
        return arr.toString();
    }
    @RequestMapping("vuex.do")
    public String vuex(){
        return "vuex";
    }
    @RequestMapping("render.do")
    public String render(){
        return "render";
    }
}
