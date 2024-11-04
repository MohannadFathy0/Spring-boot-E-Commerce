package com.fog.e_commerce.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    void findById() {
        Admin admin = new Admin("some", "mo@gmail.com", "1234");
        
    }

    @Test
    void findByEmail() {
    }
}