package com.fog.e_commerce.admin;

import com.fog.e_commerce.auth.SignInRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @GetMapping
    private Admin findAdminByEmail(@RequestBody String email) {
        return service.findByEmail(email);
    }

    @PostMapping("/signUp")
    private String adminSignUp (@Valid @RequestBody AdminDto dto) {
        return service.adminSignUp(dto.getUsername(), dto.getPassword(), dto.getEmail());
    }

    @PostMapping("/signIn")
    private String adminSignIn (@RequestBody SignInRequest request) {
        return service.adminSignIn(request);
    }
}