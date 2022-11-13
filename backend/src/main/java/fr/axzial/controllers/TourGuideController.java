package fr.axzial.controllers;

import fr.axzial.model.gps.Attraction;
import fr.axzial.model.gps.Location;
import fr.axzial.model.gps.VisitedLocation;
import fr.axzial.model.trip.Provider;
import fr.axzial.model.user.User;
import fr.axzial.model.user.UserReward;
import fr.axzial.service.RewardsService;
import fr.axzial.service.TourGuideService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TourGuideController {

    private final TourGuideService tourGuideService;
    private final RewardsService rewardsService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }

    @RequestMapping("/getLocation")
    public Location getLocation(@RequestParam String userName) {
        VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
        return visitedLocation.location;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class NearbyAttractionDTO {
        private Location userLocation;
        private List<NearbyAttractionDistanceDTO> nearbyAttractionDistanceList;
        private int totalRewardPoints;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class NearbyAttractionDistanceDTO {
        private Attraction attraction;
        private double distance;
    }

    @RequestMapping("/getNearbyAttractions")
    public NearbyAttractionDTO getNearbyAttractions(@RequestParam String userName) {
        VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
        List<Attraction> nearByAttractions = tourGuideService.getNearByAttractions(visitedLocation);
        Integer totalRewardPoints = tourGuideService.getUserRewards(getUser(userName)).stream()
                .map(UserReward::getRewardPoints)
                .reduce(Integer::sum)
                .orElse(0);

        return NearbyAttractionDTO.builder()
                .userLocation(visitedLocation.location)
                .nearbyAttractionDistanceList(nearByAttractions.stream()
                        .map(x -> NearbyAttractionDistanceDTO.builder()
                                .attraction(x)
                                .distance(rewardsService.getDistance(x, visitedLocation.location))
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .totalRewardPoints(totalRewardPoints)
                .build();
    }

    @RequestMapping("/getRewards")
    public List<UserReward> getRewards(@RequestParam String userName) {
        return tourGuideService.getUserRewards(getUser(userName));
    }

    @RequestMapping("/getAllCurrentLocations")
    public Map<UUID, List<VisitedLocation>> getAllCurrentLocations() {
        return tourGuideService.getAllUsers()
                .stream()
                .collect(Collectors.toMap(User::getId, User::getVisitedLocations));
    }

    @RequestMapping("/getTripDeals")
    public List<Provider> getTripDeals(@RequestParam String userName) {
        return tourGuideService.getTripDeals(getUser(userName));
    }

    private User getUser(String userName) {
        return tourGuideService.getUser(userName);
    }


}
