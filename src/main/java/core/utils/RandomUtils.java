package core.utils;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.security.SecureRandom;

public class RandomUtils {

    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static String randomString(int len) {
        String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static boolean randomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();

    }
}


