package fr.axzial;

import com.google.common.collect.Lists;
import fr.axzial.model.gps.Attraction;
import fr.axzial.model.gps.Location;
import fr.axzial.model.gps.VisitedLocation;
import fr.axzial.model.user.User;
import fr.axzial.model.user.UserReward;
import fr.axzial.service.RewardsService;
import fr.axzial.service.TourGuideService;
import fr.axzial.service.gps.GpsApiService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
public class TestRewardsService {

    @Autowired
    private GpsApiService gpsApiService;
    @Autowired
    private RewardsService rewardsService;
    @Autowired
    private TourGuideService tourGuideService;

    @Test
    public void userGetRewards() {

        InternalTestHelper.setInternalUserNumber(0);

        User user = User.builder().id(UUID.randomUUID()).userName("jon").phoneNumber("000").emailAddress("jon@tourGuide.com").build();

        List<Attraction> attractions = gpsApiService.getAttractions();
        assertFalse(attractions.isEmpty());
        Attraction attraction = attractions.get(0);

        user.addVisitedLocations(new VisitedLocation(user.getId(), attraction, new Date()));

        tourGuideService.trackUserLocation(user);

        List<UserReward> userRewards = user.getUserRewards();

        assertFalse(userRewards.isEmpty());

        tourGuideService.tracker.stopTracking();
    }

    @Test
    public void isWithinAttractionProximity() {
        List<Attraction> attractions = gpsApiService.getAttractions();
        assertFalse(attractions.isEmpty());
        Attraction attraction = attractions.get(0);
        Assertions.assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction));
    }

    public List<VisitedLocation> getLocations() {
        return IntStream.generate(() -> 1)
                .limit(gpsApiService.getAttractions().size())
                .mapToObj(i -> VisitedLocation.builder()
                        .location(new Location(tourGuideService.generateRandomLongitude(), tourGuideService.generateRandomLatitude()))
                        .timeVisited(new Date())
                        .id(UUID.randomUUID())
                        .build())
                .collect(Collectors.toList());
    }

    @Test
    public void nearAllAttractions() {
        User user = User.builder().id(UUID.randomUUID()).userName("jon").phoneNumber("000").emailAddress("jon@tourGuide.com").build();
        user.addAllVisitedLocations(getLocations());
        List<UserReward> rewards = IntStream.generate(() -> 1)
                .limit(1)
                .mapToObj(i -> user)
                .peek(u -> rewardsService.calculateRewards(u))
                .map(u -> tourGuideService.getUserRewards(u))
                .findFirst()
                .get();

        assertEquals(gpsApiService.getAttractions().size(), 26);
    }

}
