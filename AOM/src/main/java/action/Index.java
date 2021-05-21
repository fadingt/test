package action;

import domain.paasaom.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class Index {
    @RequestMapping("/index.do")
    public String index(@RequestParam(defaultValue = "hello world") String message) {
        System.out.println(message);
        return "index.jsp";
    }

    @RequestMapping("/index2.do")
    public String index2() {
        return "index2.jsp";
    }

    @RequestMapping("/index3.do")
    public void index3(HttpServletResponse resp) {
//        Math.random(); new Random().nextInt();
//        Instant
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
        return "vue.jsp";
    }

    @ResponseBody
    @PostMapping(produces = "application/JSON;charset=UTF-8", value = "/prod.do")
    public String getProduces() {
        User user = new User();
        user.setUsername("liuxingyu");
//        user.setOrgname("haubei");
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
        return "index4.jsp";
    }

    @RequestMapping("index4_jsonobj.do")
    @ResponseBody
    public JSONObject index4_json() {
        JSONObject obj = new JSONObject();
        obj.put("username", "刘兴宇");
        return obj;
    }

    @RequestMapping("vuex.do")
    public String vuex() {
        return "vuex.jsp";
    }

    @PostMapping(value = "/commitForm.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String commitForm() {
        JSONArray arr = new JSONArray();
        arr.add(0, "ok");
        return arr.toString();
    }

    @RequestMapping("shoelaceFormDemo.do")
    public String shoelaceFormDemo() {
        return "shoelaceFormDemo.html";
    }

    /*
     * Simply put, the model can supply attributes used for rendering views.
     *  To provide a view with usable data, we simply add this data to its Model object.
     * Additionally, maps with attributes can be merged with Model instances:
     * */
    @GetMapping("/showViewPage")
    public String passParametersWithModel(Model model) {
        Map<String, String> map = new HashMap<>();
        map.put("spring", "mvc");
        model.addAttribute("message", "Baeldung");
        model.mergeAttributes(map);
        return "viewPage";
    }

    /*
     *Just like the Model interface above, ModelMap is also used to pass values to render a view.
     *The advantage of ModelMap is it gives us the ability to pass a collection of values and treat these values as if they were within a Map:
     * */
    @GetMapping("/printViewPage")
    public String passParametersWithModelMap(ModelMap map) {
        map.addAttribute("welcomeMessage", "welcome");
        map.addAttribute("message", "Baeldung");
        return "viewPage";
    }
}
