package fr.axzial.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rewardCentral.RewardCentral;

import java.util.UUID;

@RestController
@RequestMapping("/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardCentral rewardCentral;

    @GetMapping("/attractions/{attractionId}")
    public int getAttractionRewards(@PathVariable UUID attractionId, @RequestParam UUID userId) {
        return rewardCentral.getAttractionRewardPoints(attractionId, userId);
    }

}
