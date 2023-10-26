package AuthenticationModule.Authentication.utils;

import java.util.regex.Pattern;

public class EmailValidatorUtils {

    private static final String EMAIL_REGEX =
            "^[\\w\\.-]+@[\\w\\.-]+\\.[a-z]{2,6}$";
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(EMAIL_REGEX);

    public static boolean isValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}

