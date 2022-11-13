package fr.axzial.service.trip;

import fr.axzial.model.trip.Provider;
import fr.axzial.service.ApiService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
public class TripPricerApiService extends ApiService {

    public TripPricerApiService(WebClient webClient) {
        super(webClient);
    }

    public List<Provider> getPrice(String apiKey, UUID userId, int numberOfAdults, int numberOfChildren, int tripDuration, int cumulatativeRewardPoints) {
        return getWebClient().get().uri("/trip-pricer/price")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Provider>>() {})
                .block();
    }
}
