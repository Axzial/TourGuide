package fr.axzial.controllers;

import gpsUtil.GpsUtil;
import gpsUtil.location.VisitedLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users/{uuid}/location")
@RequiredArgsConstructor
public class UserLocationController {

    private final GpsUtil gpsUtil;

    @GetMapping
    public VisitedLocation getUserLocation(@PathVariable UUID uuid) {
        return gpsUtil.getUserLocation(uuid);
    }

}

