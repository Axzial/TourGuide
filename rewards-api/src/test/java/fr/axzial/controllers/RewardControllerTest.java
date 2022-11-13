package fr.axzial.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RewardControllerTest {

    @Autowired
    private RewardController rewardController;

    @Test
    void getAttractionRewards() {
        int attractionRewards = rewardController.getAttractionRewards(UUID.randomUUID(), UUID.randomUUID());

        assertNotEquals(attractionRewards, -1);
    }

}
