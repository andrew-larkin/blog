package com.skillbox.AndrewBlog.service;

import com.github.cage.Cage;
import com.github.cage.image.Painter;
import com.skillbox.AndrewBlog.api.request.EmailRequest;
import com.skillbox.AndrewBlog.api.request.LoginRequest;
import com.skillbox.AndrewBlog.api.request.PasswordRequest;
import com.skillbox.AndrewBlog.api.request.RegisterRequest;
import com.skillbox.AndrewBlog.api.response.*;
import com.skillbox.AndrewBlog.model.CaptchaCode;
import com.skillbox.AndrewBlog.model.ModerationStatus;
import com.skillbox.AndrewBlog.model.User;
import com.skillbox.AndrewBlog.repository.CaptchaRepository;
import com.skillbox.AndrewBlog.repository.PostRepository;
import com.skillbox.AndrewBlog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthService {

    private final CaptchaRepository captchaRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    public AuthService(CaptchaRepository captchaRepository, UserRepository userRepository, PostRepository postRepository) {
        this.captchaRepository = captchaRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public ResponseEntity<?> getApiAuthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body(new CheckResponse("false"));
    }

    public ResponseEntity<?> getApiAuthCaptcha() {

        deleteOldCaptchas();
        CaptchaCode captchaCode = captchaRepository.saveAndFlush(new CaptchaCode(
                new Date(System.currentTimeMillis()),
                generateCaptchaCode(4),
                generateCaptchaCode(20)
        ));
        Cage cage = new Cage(new Painter(100, 35, null,
                null, null, null), null, null,
                null, null, null, null);

        return ResponseEntity.status(HttpStatus.OK).body(new SecretImageResponse(
                captchaCode.getSecretCode(),
                "data:image/png;base64, " + Base64.getEncoder().encodeToString(cage.draw(captchaCode.getCode()))
        ));
    }

    public ResponseEntity<?> postApiAuthRegister(RegisterRequest registerRequest) {

        Map<String, String> errors = new HashMap<>();

        if (!getEmailCorrect(registerRequest.getEmail()).equals(registerRequest.getEmail())) {
            errors.put("email", getEmailCorrect(registerRequest.getEmail()));
            return ResponseEntity.status(HttpStatus.OK).body(new ResultErrorsResponse(
                    false,
                    errors
            ));
        } else
        if (registerRequest.getName().length() < 3) {
            errors.put("name", "Имя указано неверно");
            return ResponseEntity.status(HttpStatus.OK).body(new ResultErrorsResponse(
                    false,
                    errors
            ));
        } else
        if (registerRequest.getPassword().length() < 6) {
            errors.put("password", "Пароль короче 6-ти символов");
            return ResponseEntity.status(HttpStatus.OK).body(new ResultErrorsResponse(
                    false,
                    errors
            ));
        } else
        if (!getCaptchaCorrect(registerRequest).equals(registerRequest.getCaptcha())) {
            errors.put("captcha", getCaptchaCorrect(registerRequest));
            return ResponseEntity.status(HttpStatus.OK).body(new ResultErrorsResponse(
                    false,
                    errors
            ));
        } else {
            userRepository.save(new User(
                    new Date(System.currentTimeMillis()),
                    registerRequest.getName(),
                    registerRequest.getEmail(),
                    registerRequest.getPassword(),
                    registerRequest.getCaptcha()
            ));
            return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse(
                            true
                    )
            );
        }

    }

    public ResponseEntity<?> postApiAuthLogin (LoginRequest loginRequest) {

        Map<String, Integer> sessions = new HashMap<>();

        if (userRepository.getUserByEmail(loginRequest.getEmail()).isEmpty() &&
                !userRepository.getUserByEmail(loginRequest.getEmail()).get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse(
                    false
            ));
        }

        sessions.put(loginRequest.getEmail(), userRepository.getUserByEmail(loginRequest.getEmail()).get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(new ResultUserResponse(
                true,
                getUserByEmail(loginRequest.getEmail())
        ));
    }

    public ResponseEntity<?> postApiAuthRestore (EmailRequest emailRequest) {
        if (userRepository.getUserByEmail(emailRequest.getEmail()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse(
                    false
            ));
        }
        String hash = generateCaptchaCode(20);
        User user = userRepository.getUserByEmail(emailRequest.getEmail()).get();
        user.setCode(hash);
        userRepository.save(user);

        String message = String.format("Hello, %s!\n" +
               "Visit next link to restore your password: http://localhost:8080/login/change-password/%s",
                user.getName(),
                user.getCode());
        mailSender.send(user.getEmail(), "Activation code", message);

        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse(
                true
        ));
    }

    public ResponseEntity<?> postApiAuthPassword (PasswordRequest passwordRequest) {

        Map<String, String> errors = new HashMap<>();

        User user = userRepository.getUserByCode(passwordRequest.getCode());

        if (user == null) {
            errors.put("code", "Ссылка для восстановления пароля устарела." +
                    " <a href=\n" +
                    "        \\\"/auth/restore\\\">Запросить ссылку снова</a>\"");
            return ResponseEntity.status(HttpStatus.OK).body(new ResultErrorsResponse(
                    false,
                    errors
            ));
        } else
        if (passwordRequest.getPassword().length() < 6) {
            errors.put("password", "Пароль короче 6-ти символов");
            return ResponseEntity.status(HttpStatus.OK).body(new ResultErrorsResponse(
                    false,
                    errors
            ));
        } else
        if (!getCaptchaCorrect(passwordRequest).equals(passwordRequest.getCaptcha())) {
            errors.put("captcha", getCaptchaCorrect(passwordRequest));
            return ResponseEntity.status(HttpStatus.OK).body(new ResultErrorsResponse(
                    false,
                    errors
            ));
        } else {

                return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse(
                                true
                        )
                );
            }
    }

    public ResponseEntity<?> getApiAuthLogout () {
        return ResponseEntity.status(HttpStatus.OK).body("Заглушка");
    }

    private UserResponse getUserByEmail(String email) {
        return new UserResponse(
                userRepository.getUserByEmail(email).get().getId(),
                userRepository.getUserByEmail(email).get().getName(),
                userRepository.getUserByEmail(email).get().getPhoto(),
                userRepository.getUserByEmail(email).get().getEmail(),
                userRepository.getUserByEmail(email).get().getIsModerator() == 1,
                userRepository.getUserByEmail(email).get().getIsModerator() == 1
                        ? postRepository.countIdByModerationStatus(ModerationStatus.NEW) : 0,
                userRepository.getUserByEmail(email).get().getIsModerator() == 1
        );
    }


    private String getEmailCorrect(String email) {

        Pattern pattern = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}");
        Matcher matcher = pattern.matcher(email);
        boolean matches = matcher.matches();

        if (!matches || email.equals("")) {
            return "Некорректный e-mail";
        } else if (userRepository.getUserByEmail(email).isPresent()) {
            return "Этот e-mail уже зарегестрирован";
        } else {
            return email;
        }

    }

    private String getCaptchaCorrect(RegisterRequest registerRequest) {
        if (captchaRepository.findSecretCodeByCode(registerRequest.getCaptcha())
                .get().getSecretCode().equals(registerRequest.getCaptchaSecret())) {
            return registerRequest.getCaptcha();
        }
        return "Код с картинки введен неверно";
    }

    private String getCaptchaCorrect(PasswordRequest passwordRequest) {
        if (captchaRepository.findSecretCodeByCode(passwordRequest.getCaptcha())
                .get().getSecretCode().equals(passwordRequest.getCaptchaSecret())) {
            return passwordRequest.getCaptcha();
        }
        return "Код с картинки введен неверно";
    }

    private String generateCaptchaCode(int captchaLength) {

        String captcha = "abcdefghijklmnopqrstuvwxyz0123456789";

        StringBuffer captchaBuffer = new StringBuffer();
        Random random = new Random();

        while (captchaBuffer.length() < captchaLength) {
            int index = (int) (random.nextFloat() * captcha.length());
            captchaBuffer.append(captcha.substring(index, index+1));
        }
        return captchaBuffer.toString();
    }

    private void deleteOldCaptchas() {
        List<CaptchaCode> oldCaptchas = captchaRepository.findByTimeBefore(new Date(System.currentTimeMillis() - 1000*360*4));
        if(!oldCaptchas.isEmpty()) {
            for (CaptchaCode captchaCode : oldCaptchas) {
                captchaRepository.delete(captchaCode);
            }
        }
    }

}
