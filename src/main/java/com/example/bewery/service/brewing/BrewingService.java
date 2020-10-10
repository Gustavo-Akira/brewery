package com.example.bewery.service.brewing;

import com.example.bewery.config.JmsConfig;
import com.example.bewery.domain.Beer;
import com.example.events.BrewBeerEvent;
import com.example.bewery.repositories.BeerRepository;
import com.example.bewery.service.inventory.BeerInventoryService;
import com.example.bewery.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper mapper;
    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory(){
        List<Beer> beers = beerRepository.findAll();
        beers.forEach(beer->{
            Integer inventoryOnHand = beerInventoryService.getOnHandInventory(beer.getId());
            log.debug("Min on hand is" + beer.getMinOnHand());
            log.debug("Inventory:" +inventoryOnHand);
            if(beer.getMinOnHand() >= inventoryOnHand){
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,new BrewBeerEvent(mapper.beerToBeerDTO(beer)));
            }
        });
    }
}
