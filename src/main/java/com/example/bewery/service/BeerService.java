package com.example.bewery.service;

import com.example.bewery.web.model.BeerDTO;
import com.example.bewery.web.model.BeerPagedList;
import com.example.bewery.web.model.enums.BeerStyleEnum;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface BeerService {

    BeerDTO getById(UUID beerId);

    BeerDTO saveNewBeer(BeerDTO beerDto);

    BeerDTO updateBeer(UUID beerId, BeerDTO beerDto) throws ChangeSetPersister.NotFoundException;

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest of, Boolean showInventory);
}