package com.example.bewery.repositories;

import com.example.bewery.domain.Beer;
import com.example.bewery.web.model.enums.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, Pageable pageable);

    Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyleEnum, Pageable pageable);

    Page<Beer> findAllByBeerName(String beerName, Pageable of);
}
