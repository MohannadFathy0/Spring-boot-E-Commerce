package com.fog.e_commerce.auth;

import com.fog.e_commerce.admin.AdminRepository;
import com.fog.e_commerce.cart.CartService;
import com.fog.e_commerce.user.Role;
import com.fog.e_commerce.user.User;
import com.fog.e_commerce.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/user")
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserRepository repository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;

    public AuthenticationController(AuthenticationService service, UserRepository repository, AdminRepository adminRepository, PasswordEncoder passwordEncoder, CartService cartService) {
        this.service = service;
        this.repository = repository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping("/signUp")
    public String signUp(@Valid @RequestBody SignupRequest request) {

        if (adminRepository.findByEmail(request.getEmail()).orElse(null) != null){
            return "This email already used as Admin.";
        }
        else if (repository.findByEmail(request.getEmail()).orElse(null) != null){
            return "This email already used as User.";
        }

        // Create a new user
        var user = new User(request.getFirstname(), request.getLastname(), request.getPhone(),
                null, request.getEmail(), passwordEncoder.encode(request.getPassword()),
                Role.ROLE_USER, null, false);


        // Save the user details, but not activate the account yet
        String verificationCode = generateVerificationCode();

        user.setVerificationCode(verificationCode);
        repository.save(user);
        cartService.addCart(user.getId());

        // Send the verification code via email
        service.sendVerificationEmail(request.getEmail(), verificationCode);

        return "Verification email sent";
    }

    @PostMapping("/signIn")
    public String signIn(@RequestBody SignInRequest request) {
        return service.userSignIn(request);
    }

    @PostMapping("/verify")
    public String verifyCode(@RequestParam String email, @RequestParam String code) {

        User user = repository.findByEmail(email).orElse(null);
        System.out.println(user);

        // Check if the user exists and if the verification code is not null
        if (user != null) {
            String userVerificationCode = user.getVerificationCode();

            if (userVerificationCode != null && userVerificationCode.equals(code)) {
                // Activate the user account by marking it as verified
                user.setVerified(true);
                repository.save(user);

                // Generate JWT token (assuming service.userSignUp() handles JWT generation)
                String jwtToken = service.userSignUp(user);
                return "Account verified successfully!\n" + jwtToken;
            } else {
                return "Invalid verification code";
            }
        }

        return "Invalid verification code";
    }

    public String generateVerificationCode() {
        int length = 6;
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10)); // Generate digits (0-9)
        }

        return code.toString();
    }
}
