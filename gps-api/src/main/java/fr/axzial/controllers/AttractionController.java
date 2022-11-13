package fr.axzial.controllers;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attractions")
@RequiredArgsConstructor
public class AttractionController {

    private final GpsUtil gpsUtil;

    @GetMapping
    public List<Attraction> getAttractions() {
        return gpsUtil.getAttractions();
    }

}
