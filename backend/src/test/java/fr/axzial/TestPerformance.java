package fr.axzial;

import fr.axzial.model.gps.Attraction;
import fr.axzial.model.gps.VisitedLocation;
import fr.axzial.model.user.User;
import fr.axzial.service.RewardsService;
import fr.axzial.service.gps.GpsApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
public class TestPerformance {

    @Autowired
    private GpsApiService gpsApiService;

    @Autowired
    private RewardsService rewardsService;

    /*
     * A note on performance improvements:
     *
     *     The number of users generated for the high volume tests can be easily adjusted via this method:
     *
     *     		InternalTestHelper.setInternalUserNumber(100000);
     *
     *
     *     These tests can be modified to suit new solutions, just as long as the performance metrics
     *     at the end of the tests remains consistent.
     *
     *     These are performance metrics that we are trying to hit:
     *
     *     highVolumeTrackLocation: 100,000 users within 15 minutes:
     *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     *
     *     highVolumeGetRewards: 100,000 users within 20 minutes:
     *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     */

    // Approx 37 seconds
    @Test
    public void highVolumeTrackLocation() {
        int TEST_AMOUNT = 100_000;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        UUID uuid = UUID.randomUUID();
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "256");
        List<VisitedLocation> visitedLocations = IntStream.generate(() -> 1)
                .parallel()
                .limit(TEST_AMOUNT)
                .mapToObj(i -> gpsApiService.getUserLocation(uuid)).collect(Collectors.toList());
        assertEquals(visitedLocations.size(), TEST_AMOUNT);
        stopWatch.stop();
        log.info("Job Finished in " + stopWatch.getTime(TimeUnit.SECONDS) + " seconds");
        assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }

    // Approx 116 seconds
    @Test
    public void highVolumeGetRewards() {
        int TEST_AMOUNT = 100_000;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "1024");
        List<Attraction> attractions = gpsApiService.getAttractions();
        assertFalse(attractions.isEmpty());
        Attraction attraction = attractions.get(0);
        List<User> list = IntStream.generate(() -> 1)
                .limit(TEST_AMOUNT)
                .parallel()
                .mapToObj(i -> User.builder().id(UUID.randomUUID()).build())
                .peek(u -> u.addVisitedLocations(VisitedLocation.builder().id(u.getId()).location(attraction).timeVisited(new Date()).build()))
                .peek(u -> rewardsService.calculateRewards(u))
                .collect(Collectors.toList());
        assertEquals(list.size(), TEST_AMOUNT);
        stopWatch.stop();
        log.info("Amount : " + list.size());
        log.info("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }

}
