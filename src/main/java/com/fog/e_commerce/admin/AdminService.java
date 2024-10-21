package com.fog.e_commerce.admin;

import com.fog.e_commerce.auth.SignInRequest;
import com.fog.e_commerce.config.JwtService;
import com.fog.e_commerce.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final JwtService service;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AdminService(AdminRepository adminRepository, UserRepository userRepository, JwtService service, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    // Create a new Admin
    public String adminSignUp(String username, String password, String email) {
        if (userRepository.findByEmail(email).orElse(null) != null){
            return "This email already used.";
        }
        else {
            Admin admin = new Admin(username, email, passwordEncoder.encode(password));
            adminRepository.save(admin);
            var jwtToken = service.generateToken(admin, admin.getId());
            return jwtToken;
        }
    }

    public String adminSignIn(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var admin = adminRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("can't find admin"));
        var jwtToken = service.generateToken(admin, admin.getId());
        return jwtToken;
    }

    // Find Admin by username
    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email).orElse(null);
    }
}
