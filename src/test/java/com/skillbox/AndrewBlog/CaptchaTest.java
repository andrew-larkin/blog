package com.skillbox.AndrewBlog;

import com.github.cage.Cage;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CaptchaTest {

    @Test
    void testCage() {
        Cage cage = new Cage();
        String token1 = cage.getTokenGenerator().next();

        String image = Base64.getEncoder().encodeToString(cage.draw("token1"));

    }

    @Test
    void generateCaptchaCode() {

        String captcha = "abcdefghijklmnopqrstuvwxyz0123456789";

        StringBuffer captchaBuffer = new StringBuffer();
        Random random = new Random();

        while (captchaBuffer.length() < 4) {
            int index = (int) (random.nextFloat() * captcha.length());
            String dd = captcha.substring(index, index+1);
            captchaBuffer.append(dd);
        }

    }

}
