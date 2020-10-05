package com.example.bewery.events;

import com.example.bewery.web.model.BeerDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {
    public BrewBeerEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }
}
