package fr.axzial.controllers;

import gpsUtil.location.Attraction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AttractionControllerTest {

    @Autowired
    private AttractionController attractionController;

    @Test
    void getAttractions() {
        List<Attraction> attractions = attractionController.getAttractions();

        assertNotEquals(attractions.size(), 0);
    }
}
