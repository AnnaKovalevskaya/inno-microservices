package com.innowise.demo.serserviceapp;

import com.innowise.demo.serserviceapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.username=anna",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.liquibase.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=true",
        "spring.cache.type=none"
})
public class UserServiceIntegrationTest {

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