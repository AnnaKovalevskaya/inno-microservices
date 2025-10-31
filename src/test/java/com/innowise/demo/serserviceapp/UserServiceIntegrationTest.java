package com.innowise.demo.serserviceapp;

import com.innowise.demo.serserviceapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@TestPropertySource(properties = {
        "spring.liquibase.enabled=true",  // Включаем Liquibase
        "spring.jpa.hibernate.ddl-auto=validate",  // Liquibase управляет схемой
        "spring.jpa.show-sql=true",
        "spring.cache.type=none"
})
public class UserServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configureProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        // Настраиваем Liquibase для TestContainers
        registry.add("spring.liquibase.url", postgres::getJdbcUrl);
        registry.add("spring.liquibase.user", postgres::getUsername);
        registry.add("spring.liquibase.password", postgres::getPassword);
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