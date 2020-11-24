package Utils;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class StringManager {

    private static final String DIGITS = "0123456789-.";
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String VALID_TITLE_CHARS = DIGITS + LETTERS + " _";
    private static final String VALID_TITLE_CHARS2 = DIGITS + LETTERS + " _@";
    private static final String VALID_TITLE_CHARS3 = DIGITS + LETTERS + " _@/?:;<>#$%^&*(),|+{}!\"'=~`[]\\";

    public static boolean validString(String title) {
        if (title.length() >= 20) return false;
        for(int i = 0; i < title.length(); i++) {
            if(VALID_TITLE_CHARS.indexOf(title.charAt(i)) < 0) {
                return false;
            }
        }
        return true;
    }
    public static boolean validIntegers(String str){
        for(int i = 0; i < str.length(); i++) {
            if(DIGITS.indexOf(str.charAt(i)) < 0) {
                return false;
            }
        }
        return true;
    }
    public static boolean validStringforEmail(String title) {
        if (title.length() >= 20) return false;
        for(int i = 0; i < title.length(); i++) {
            if(VALID_TITLE_CHARS2.indexOf(title.charAt(i)) < 0) {
                return false;
            }
        }
        return true;
    }
    public static boolean validStringforChat(String title) {
        if (title.length() >= 20) return false;
        for(int i = 0; i < title.length(); i++) {
            if(VALID_TITLE_CHARS3.indexOf(title.charAt(i)) < 0) {
                return false;
            }
        }
        return true;
    }

    public static String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
