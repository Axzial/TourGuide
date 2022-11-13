package fr.axzial.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tripPricer.Provider;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PriceControllerTest {

    @Autowired
    private PriceController priceController;

    @Test
    void getPrice() {
        List<Provider> price = priceController.getPrice(UUID.randomUUID().toString(), UUID.randomUUID(), 2, 2, 2, 2);

        assertNotEquals(price.size(), 0);
    }
}
