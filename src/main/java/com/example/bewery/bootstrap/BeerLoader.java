package com.example.bewery.bootstrap;

import com.example.bewery.domain.Beer;
import com.example.bewery.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository repository){
        this.beerRepository = repository;
    }
    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if(beerRepository.count() == 0){
            beerRepository.save(Beer.builder()
                    .beerName("Pango Bobs")
                    .beerStyle("IPA")
                    .quantityToDrew(200)
                    .minOnHand(12)
                    .upc(3777777L)
                    .price(new BigDecimal(12.90))
                    .build());
            beerRepository.save(Beer.builder()
                    .beerName("Glagas")
                    .beerStyle("PALE_ALE")
                    .quantityToDrew(200)
                    .minOnHand(12)
                    .upc(3777771423532L)
                    .price(new BigDecimal(11.90))
                    .build());
        }
        System.out.println("Loaded Beers " +beerRepository.count());
    }
}
