package view.mainPane.dialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hammer on 05.09.2017.
 */
public class SPassportValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String PASSPORT_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public SPassportValidator(){
        pattern = Pattern.compile(PASSPORT_PATTERN);
    }

    /**
     * Validate passport address with regular expression
     * @param passport passport address for validation
     * @return true valid passport address, false invalid passport address
     */
    public boolean validate(final String passport){
        matcher = pattern.matcher(passport);
        return matcher.matches();
    }
}
