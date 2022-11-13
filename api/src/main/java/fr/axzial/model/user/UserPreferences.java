package fr.axzial.model.user;

import lombok.Data;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

@Data
public class UserPreferences {

    private int attractionProximity;
    private CurrencyUnit currency;
    private Money lowerPricePoint;
    private Money highPricePoint;
    private int tripDuration;
    private int ticketQuantity;
    private int numberOfAdults;
    private int numberOfChildren;

    public UserPreferences() {
        currency = Monetary.getCurrency("USD");
        lowerPricePoint = Money.of(0, currency);
        highPricePoint = Money.of(Integer.MAX_VALUE, currency);
        attractionProximity = Integer.MAX_VALUE;
        tripDuration = 1;
        ticketQuantity = 1;
        numberOfAdults = 1;
        numberOfChildren = 0;
    }
}
