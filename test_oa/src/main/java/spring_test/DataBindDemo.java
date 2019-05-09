package spring_test;

import action.validate.UserValidator;
import domain.User;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.List;

public class DataBindDemo {

    public static void main(String[] args) {

    }

    private static void Demo2() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("new String('hello').toUpperCase()");
        String message = expression.getValue(String.class);
        System.out.println(message);
    }

    private static void Demo1() {
        User user = new User();
//        user.setPassword("123");
        user.setUsercode("a");
        DataBinder binder = new DataBinder(user);
        binder.setValidator(new UserValidator());
        binder.validate();
        List<ObjectError> errorList = binder.getBindingResult().getAllErrors();
        for (int i = 0; i<errorList.size(); i++){
            System.out.println(errorList.get(i));
        }
    }
}
