package fr.axzial.service;

import fr.axzial.model.gps.Attraction;
import fr.axzial.model.gps.Location;
import fr.axzial.model.gps.VisitedLocation;
import fr.axzial.model.user.User;
import fr.axzial.model.user.UserReward;
import fr.axzial.service.gps.GpsApiService;
import fr.axzial.service.reward.RewardApiService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class RewardsService {

    private final GpsApiService gpsApiService;
    private final RewardApiService rewardApiService;

    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

    // proximity in miles
    private final int DEFAULT_PROXIMITY_BUFFER = 10;

    @Setter
    private int proximityBuffer = DEFAULT_PROXIMITY_BUFFER;

    public void calculateRewards(User user) {
        List<VisitedLocation> userLocations = user.getVisitedLocations();
        List<Attraction> attractions = gpsApiService.getAttractions();

        userLocations.forEach(visitedLocation -> attractions.stream()
                .filter(attraction -> user.getUserRewards().stream().noneMatch(r -> r.attraction.attractionName.equals(attraction.attractionName)))
                .filter(attraction -> nearAttraction(visitedLocation, attraction))
                .forEach(attraction -> user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)))));
    }

    public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
        int ATTRACTION_PROXIMITY_RANGE = 200;
        return !(getDistance(attraction, location) > ATTRACTION_PROXIMITY_RANGE);
    }

    private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
        return !(getDistance(attraction, visitedLocation.location) > proximityBuffer);
    }

    private int getRewardPoints(Attraction attraction, User user) {
        return rewardApiService.getAttractionRewardPoints(attraction.getAttractionId(), user.getId());
    }

    public double getDistance(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.getLatitude());
        double lon1 = Math.toRadians(loc1.getLongitude());
        double lat2 = Math.toRadians(loc2.getLatitude());
        double lon2 = Math.toRadians(loc2.getLongitude());

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
    }

}
