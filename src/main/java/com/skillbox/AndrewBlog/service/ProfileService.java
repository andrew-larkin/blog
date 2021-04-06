package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.request.ProfileRequest;
import com.skillbox.AndrewBlog.model.User;
import com.skillbox.AndrewBlog.repository.UserRepository;
import com.skillbox.AndrewBlog.security.PersonDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileService {

    private byte removePhoto = 1;
    private boolean isEmailUpdated;
    private boolean isPasswordUpdated;

    private final UserRepository userRepository;
    private final PersonDetailsService personDetailsService;
    private final ImageService imageService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public ProfileService(UserRepository userRepository, PersonDetailsService personDetailsService,
                          ImageService imageService) {
        this.userRepository = userRepository;
        this.personDetailsService = personDetailsService;
        this.imageService = imageService;
    }

    public ResponseEntity<?> postApiProfileMy(MultipartFile photo, String name, String email,
                                              String password, byte removePhoto) throws Exception {
        isEmailUpdated = false;
        isPasswordUpdated = false;

        User user = getCurrentUser();

        if (photo != null) {
            imageService.upload(photo);
        }

        updateName(name, user);
        updateEmail(email, user);
        updatePassword(password, user);
        removePhoto(removePhoto, user);

        userRepository.save(user);

        if (isEmailUpdated || isPasswordUpdated) {
            SecurityContextHolder.clearContext();
        }

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    public ResponseEntity<?> postApiProfileMyWithoutPhoto(ProfileRequest profileRequest) {

        isEmailUpdated = false;
        isPasswordUpdated = false;

        User user = getCurrentUser();
        updateEmail(profileRequest.getEmail(), user);
        updateName(profileRequest.getName(), user);
        updatePassword(profileRequest.getPassword(), user);
        removePhoto(profileRequest.getRemovePhoto(), user);

        userRepository.save(user);

        if (isEmailUpdated || isPasswordUpdated) {
            SecurityContextHolder.clearContext();
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    private User getCurrentUser() {
        return userRepository.getUserByEmail(personDetailsService.getCurrentUser()
                .getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with email %s not found",
                        personDetailsService.getCurrentUser().getEmail()))
        );
    }

    private void updateEmail(String email, User user) {
        if(!user.getEmail().equals(email)) {
            user.setEmail(email);
            isEmailUpdated = true;
        }
    }

    private void updateName(String name, User user) {
        if(!user.getName().equals(name)) {
            if(name.length() > 3) {
                user.setName(name);
            }
        }
    }

    private void updatePassword(String password, User user) {
        if(password != null) {
            if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
                if (password.length() >= 6) {
                    String newPassword = bCryptPasswordEncoder.encode(password);
                    user.setPassword(newPassword);
                    isPasswordUpdated = true;
                }
            }
        }
    }

    private void removePhoto(byte remove, User user) {
        if(remove == removePhoto) {
            user.setPhoto("");
        }
    }

}
