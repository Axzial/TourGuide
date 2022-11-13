package fr.axzial.model.user;

import com.google.common.collect.Iterables;
import fr.axzial.model.gps.VisitedLocation;
import fr.axzial.model.trip.Provider;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class User {

    private final UUID id;
    private final String userName;
    private String phoneNumber;
    private String emailAddress;
    private Date latestLocationTimestamp;
    private final List<VisitedLocation> visitedLocations = new ArrayList<>();
    private final List<UserReward> userRewards = new ArrayList<>();
    private UserPreferences userPreferences;
    private List<Provider> tripDeals = new ArrayList<>();

    public void addVisitedLocations(VisitedLocation visitedLocation) {
        visitedLocations.add(visitedLocation);
    }

    public void addUserReward(UserReward userReward) {
        if (userRewards.stream().allMatch(r -> r.attraction.attractionName.equals(userReward.attraction.attractionName))) {
            userRewards.add(userReward);
        }
    }

    public VisitedLocation getLastVisitedLocation() {
        return Iterables.getLast(visitedLocations);
    }

}
