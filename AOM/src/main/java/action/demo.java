package action;

import action.validate.UserValidator;
import domain.paasaom.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class demo {
    @InitBinder public void initBinder(DataBinder dataBinder){dataBinder.setValidator(new UserValidator());}
    @Value("#{systemProperties['user.country'] matches '[a-z][A-z]'}")private String demo2;

    @RequestMapping("demo1.do")
    public String demo1(@Validated User user, BindingResult result){
//        System.out.println(demo2);
        List<ObjectError> list = result.getAllErrors();
        if(result.hasErrors()){
            for(int i=0; i<list.size(); i++ ){
                System.out.println(list.get(i));
            }
            return "saveUser";
        }
        return "saveUser.jsp";
    }

    @RequestMapping("demo2.do")
    public String demo2(){
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'hello'");
        System.out.println(expression.getValue());
        System.out.println(demo2);
        return "saveUser.jsp";
    }

}