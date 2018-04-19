package BuisnessTier.Seciurity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Creating and veryfying tokens for mdownloading files
 */
public class DownloadTokenManager {

    private Map<String, String> fileTokens;
    private String chars;
    private Random random;

    public DownloadTokenManager() {
        fileTokens = new HashMap<String, String>();
        chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        random = new Random();
    }

    public boolean isTokenValid(String file, String token) {
        return fileTokens.containsKey(file) && fileTokens.get(file).equals(token);
    }

    public String getToken(String file) {
        if (fileTokens.containsKey(file)) {
            return fileTokens.get(file);
        }
        else {
            String token = getRandomString();
            fileTokens.put(file, token);
            return token;
        }
    }

    private String getRandomString() {
        StringBuilder salt = new StringBuilder();
        while (salt.length() < 18) {
            int index = (int) (random.nextFloat() * chars.length());
            salt.append(chars.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
