package fr.axzial.model.user;

import com.google.common.collect.Iterables;
import fr.axzial.model.gps.VisitedLocation;
import fr.axzial.model.trip.Provider;
import lombok.Builder;
import lombok.Data;

import java.util.*;

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
        this.visitedLocations.add(visitedLocation);
    }

    public void addAllVisitedLocations(Collection<VisitedLocation> visitedLocations) {
        this.visitedLocations.addAll(visitedLocations);
    }

    public void addUserReward(UserReward userReward) {
        userRewards.add(userReward);
    }

    public VisitedLocation getLastVisitedLocation() {
        return Iterables.getLast(visitedLocations);
    }

}
