package fr.axzial.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {

    private final TripPricer tripPricer;

    @GetMapping
    public List<Provider> getPrice(@RequestParam(name = "apiKey") String apiKey,
                                   @RequestParam(name = "attractionId") UUID attractionId,
                                   @RequestParam(name = "adults", defaultValue = "2") int adults,
                                   @RequestParam(name = "adults", defaultValue = "2") int children,
                                   @RequestParam(name = "adults", defaultValue = "2") int nightsStay,
                                   @RequestParam(name = "adults", defaultValue = "2") int rewardsPoints) {
        return tripPricer.getPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints);
    }

}
