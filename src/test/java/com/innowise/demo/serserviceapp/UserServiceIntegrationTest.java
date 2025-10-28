package com.innowise.demo.serserviceapp;

import com.innowise.demo.serserviceapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertySource;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceIntegrationTest {

    @DynamicPropertySource
    static void configureProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        // Используем embedded H2 для тестов
        registry.add("spring.datasource.url", () -> "jdbc:h2:mem:testdb");
        registry.add("spring.datasource.username", () -> "anna");
        registry.add("spring.datasource.password", () -> "");
        registry.add("spring.datasource.driver-class-name", () -> "org.h2.Driver");
        registry.add("spring.liquibase.enabled", () -> "true");
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        assertNotNull(userRepository);
    }

    @Test
    void shouldCreateAndRetrieveUser() {
        com.innowise.demo.serserviceapp.model.User user = new com.innowise.demo.serserviceapp.model.User();
        user.setName("John");
        user.setSurname("Doe");
        user.setBirthDate(java.time.LocalDate.of(1990, 1, 1));
        user.setEmail("john@example.com");

        com.innowise.demo.serserviceapp.model.User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());

        com.innowise.demo.serserviceapp.model.User retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals("John", retrievedUser.getName());
    }
}
