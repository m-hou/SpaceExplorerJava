package com.example.java.Util;

/**
 * Created by HOU on 2016/5/6.
 */
public class MathHelper {
    public static int modulo (int x, int n, int shift) {
        // http://stackoverflow.com/questions/4403542/how-does-java-do-modulus-calculations-with-negative-numbersint r = x % n;
        int r = (x - shift) % n;
        if (r < 0)
        {
            r += n;
        }
        return r + shift;
    }
}
