package fr.axzial;

import fr.axzial.model.gps.Attraction;
import fr.axzial.model.gps.VisitedLocation;
import fr.axzial.model.trip.Provider;
import fr.axzial.model.user.User;
import fr.axzial.service.TourGuideService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
public class TestTourGuideService {

    @Autowired
    private TourGuideService tourGuideService;

    @Test
    public void getUserLocation() {
        InternalTestHelper.setInternalUserNumber(0);

        User user = User.builder().id(UUID.randomUUID()).userName("jon").phoneNumber("000").emailAddress("jon@tourGuide.com").build();
        VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);
        tourGuideService.tracker.stopTracking();
        assertEquals(visitedLocation.id, user.getId());
    }

    @Test
    public void addUser() {
        InternalTestHelper.setInternalUserNumber(0);

        User user = User.builder().id(UUID.randomUUID()).userName("jon").phoneNumber("000").emailAddress("jon@tourGuide.com").build();
        User user2 = User.builder().id(UUID.randomUUID()).userName("jon2").phoneNumber("000").emailAddress("jon2@tourGuide.com").build();

        tourGuideService.addUser(user);
        tourGuideService.addUser(user2);

        User retrivedUser = tourGuideService.getUser(user.getUserName());
        User retrivedUser2 = tourGuideService.getUser(user2.getUserName());

        tourGuideService.tracker.stopTracking();

        assertEquals(user, retrivedUser);
        assertEquals(user2, retrivedUser2);
    }

    @Test
    public void getAllUsers() {
        InternalTestHelper.setInternalUserNumber(0);

        User user = User.builder().id(UUID.randomUUID()).userName("jon").phoneNumber("000").emailAddress("jon@tourGuide.com").build();
        User user2 = User.builder().id(UUID.randomUUID()).userName("jon2").phoneNumber("000").emailAddress("jon2@tourGuide.com").build();

        tourGuideService.addUser(user);
        tourGuideService.addUser(user2);

        List<User> allUsers = tourGuideService.getAllUsers();

        tourGuideService.tracker.stopTracking();

        assertTrue(allUsers.contains(user));
        assertTrue(allUsers.contains(user2));
    }

    @Test
    public void trackUser() {
        InternalTestHelper.setInternalUserNumber(0);

        User user = User.builder().id(UUID.randomUUID()).userName("jon").phoneNumber("000").emailAddress("jon@tourGuide.com").build();
        VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);

        tourGuideService.tracker.stopTracking();

        assertEquals(user.getId(), visitedLocation.id);
    }

    @Test
    public void getNearbyAttractions() {
        InternalTestHelper.setInternalUserNumber(0);

        User user = User.builder().id(UUID.randomUUID()).userName("jon").phoneNumber("000").emailAddress("jon@tourGuide.com").build();
        VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);

        List<Attraction> attractions = tourGuideService.getNearByAttractions(visitedLocation);

        tourGuideService.tracker.stopTracking();

        assertEquals(5, attractions.size());
    }

    @Test
    public void getTripDeals() {
        InternalTestHelper.setInternalUserNumber(0);

        User user = User.builder().id(UUID.randomUUID()).userName("jon").phoneNumber("000").emailAddress("jon@tourGuide.com").build();

        List<Provider> providers = tourGuideService.getTripDeals(user);

        tourGuideService.tracker.stopTracking();

        assertEquals(10, providers.size());
    }


}
