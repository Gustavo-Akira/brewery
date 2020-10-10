package com.example.bewery.service.brewing;

import com.example.bewery.config.JmsConfig;
import com.example.bewery.domain.Beer;
import com.example.events.BrewBeerEvent;
import com.example.events.NewInventoryEvent;
import com.example.bewery.repositories.BeerRepository;
import com.example.bewery.web.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;
    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event){
        BeerDTO beerDTO = event.getBeerDTO();

        Beer beer = beerRepository.getOne(beerDTO.getId());

        beerDTO.setQuantityOnHand(beer.getQuantityToDrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDTO);

        log.debug("Brewed beer" + beer.getMinOnHand() + ": OH" +beerDTO.getQuantityOnHand());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
