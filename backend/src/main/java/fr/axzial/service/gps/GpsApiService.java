package fr.axzial.service.gps;

import fr.axzial.model.gps.Attraction;
import fr.axzial.model.gps.VisitedLocation;
import fr.axzial.service.ApiService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
public class GpsApiService extends ApiService {

    public GpsApiService(WebClient webClient) {
        super(webClient);
    }

    public List<Attraction> getAttractions() {
        return getWebClient().get().uri("/gps-api/attractions").retrieve().bodyToMono(new ParameterizedTypeReference<List<Attraction>>() {}).block();
    }

    public VisitedLocation getUserLocation(UUID userId) {
        return getWebClient().get().uri("/gps-api/users/" + userId + "/location").retrieve().bodyToMono(VisitedLocation.class).block();
    }
}
