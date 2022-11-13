package fr.axzial.service.reward;

import fr.axzial.service.ApiService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Service
public class RewardApiService extends ApiService {

    public RewardApiService(WebClient webClient) {
        super(webClient);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class AttractionRewardClassDTO {
        private int amount;
    }

    public int getAttractionRewardPoints(@NotNull UUID attractionId, @NotNull UUID userId) {
        AttractionRewardClassDTO response = getWebClient().get()
                .uri(uriBuilder -> uriBuilder.path("/rewards-api/rewards/attractions/{attractionId}")
                        .queryParam("userId", userId)
                        .build(attractionId))
                .retrieve().bodyToMono(AttractionRewardClassDTO.class).block();
        return Objects.requireNonNull(response).getAmount();
    }
}
