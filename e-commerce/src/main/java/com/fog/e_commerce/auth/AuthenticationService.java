package com.fog.e_commerce.auth;

import com.fog.e_commerce.admin.AdminRepository;
import com.fog.e_commerce.cart.CartService;
import com.fog.e_commerce.config.JwtService;
import com.fog.e_commerce.user.Role;
import com.fog.e_commerce.user.User;
import com.fog.e_commerce.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final AdminRepository adminRepository;
    private final JavaMailSender mailSender;
    private final JwtService service;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, AdminRepository adminRepository, JavaMailSender mailSender,
                                 JwtService service, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.adminRepository = adminRepository;
        this.mailSender = mailSender;
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public String userSignUp(User user) {

        var jwtToken = service.generateToken(user, user.getId());
        return jwtToken;
    }

    public String userSignIn(SignInRequest request) {
        if (adminRepository.findByEmail(request.getEmail()) != null){
            return "This email already used.";
        }
        else {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));;
            if (!user.isVerified()) {
                throw new RuntimeException("Email not verified. Please verify your email before logging in.");
            }
            var jwtToken = service.generateToken(user, user.getId());
            return jwtToken;
        }
    }

    public void sendVerificationEmail(String email, String code) {
        String subject = "Verify your email";
        String message = "Your verification code is: " + code ;

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject(subject);
        emailMessage.setText(message);

        mailSender.send(emailMessage);
    }
}
