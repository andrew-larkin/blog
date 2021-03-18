package com.skillbox.AndrewBlog.security;

import com.skillbox.AndrewBlog.api.request.LoginRequest;
import com.skillbox.AndrewBlog.api.response.ResultResponse;
import com.skillbox.AndrewBlog.api.response.ResultUserResponse;
import com.skillbox.AndrewBlog.api.response.UserResponse;
import com.skillbox.AndrewBlog.model.ModerationStatus;
import com.skillbox.AndrewBlog.model.Role;
import com.skillbox.AndrewBlog.model.User;
import com.skillbox.AndrewBlog.repository.PostRepository;
import com.skillbox.AndrewBlog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonDetailsService implements UserDetailsService {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public PersonDetailsService(//BCryptPasswordEncoder bCryptPasswordEncoder,
                                UserRepository userRepository, PostRepository postRepository) {
        //this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("user with email %s not found", email)));
       Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
       for (Role role : user.getRoles()) {
           grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
       }

       return new org.springframework.security.core.userdetails.User(
               user.getUsername(),
               user.getPassword(),
               grantedAuthorities
       );
    }

    public String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean isPasswordCorrect(User user, String password) {
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

    public User findUserByLogin(String email) {
        return userRepository.findByEmail(email).get();
    }

    public User getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new SecurityException("Session is not authorized");
        }

        String email = auth.getName();

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new SecurityException("Session is not authorized");
        }

        return user.get();
    }

    public ResponseEntity<?> postApiAuthLogin (LoginRequest loginRequest) {

        User authUser = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("user with email %s not found",
                                loginRequest.getEmail())));

        if (authUser == null) {
            return new ResponseEntity<>(new ResultResponse(false), HttpStatus.OK);
        }

        if (isPasswordCorrect(authUser, loginRequest.getPassword())) {
            authUser(authUser.getEmail(), loginRequest.getPassword());
        } else return new ResponseEntity<>(new ResultResponse(false), HttpStatus.OK);

        return ResponseEntity.status(HttpStatus.OK).body(new ResultUserResponse(
                true,
                getUserResponse(authUser.getEmail())
        ));

    }

    public ResponseEntity<?> getApiAuthLogout() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        User user = findUserByLogin(securityContext.getAuthentication().getName());

        boolean result = false;
        if (user != null) {
            Set<GrantedAuthority> grantedAuthority = new HashSet<>();
            grantedAuthority.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));

            securityContext.setAuthentication(
                    new AnonymousAuthenticationToken(
                            String.valueOf(System.currentTimeMillis()),
                            new org.springframework.security.core.userdetails.User(
                                    "anonymous",
                                    "anonymous",
                                    grantedAuthority
                            ),
                            grantedAuthority
                    ));

            result = true;
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResultResponse(true));
    }

    private void authUser(String email, String password) {
        UserDetails user = loadUserByUsername(email);
        SecurityContextHolder
                .getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities()));
    }

    private UserResponse getUserResponse(String email) {
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        byte isModerator = currentUser.getIsModerator();
        return new UserResponse(
                currentUser.getId(),
                currentUser.getName(),
                currentUser.getPhoto(),
                currentUser.getEmail(),
                isModerator == 1,
                isModerator == 1 ? postRepository.countIdByModerationStatus(ModerationStatus.NEW) : 0,
                isModerator == 1
        );

    }

}
