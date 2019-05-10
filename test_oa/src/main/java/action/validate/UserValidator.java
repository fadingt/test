package action.validate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import domain.User;

public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors,"password","password.empty");
        ValidationUtils.rejectIfEmpty(errors,"usercode","usercode.empty");
        User user = (User) target;
        if (user.getUsercode().length() < 5) {
            errors.reject("usercode", "usercode should have 5 chars at least");
        }
    }

}
