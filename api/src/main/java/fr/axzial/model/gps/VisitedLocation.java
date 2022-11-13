package fr.axzial.model.gps;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VisitedLocation {

    public UUID id;
    public Location location;
    public Date timeVisited;

}
