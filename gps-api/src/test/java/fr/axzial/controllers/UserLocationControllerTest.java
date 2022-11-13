package fr.axzial.controllers;

import gpsUtil.location.VisitedLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserLocationControllerTest {

    @Autowired
    private UserLocationController userLocationController;

    @BeforeAll
    static void beforeAll() {
        Locale.setDefault(Locale.US);
    }

    @Test
    void getUserLocation() {
        UUID userId = UUID.randomUUID();
        VisitedLocation userLocation = userLocationController.getUserLocation(userId);

        Assertions.assertNotNull(userLocation);
        Assertions.assertEquals(userLocation.userId, userId);
    }
}
