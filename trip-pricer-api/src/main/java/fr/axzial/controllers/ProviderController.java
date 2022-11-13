package fr.axzial.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.TripPricer;

@RestController
@RequestMapping("/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final TripPricer tripPricer;

    @GetMapping
    public String getProviderName(@RequestParam(name = "apiKey") String apiKey, @RequestParam(name = "adults", defaultValue = "2") int adults) {
        return tripPricer.getProviderName(apiKey, adults);
    }

}
