package DataTier;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathInterpreter {

    public static String getPath(String s){
        return s.replaceAll("%", File.separator);
    }
}
