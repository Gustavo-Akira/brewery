package com.example.bewery.repositories;

import com.example.bewery.domain.Beer;
import com.example.bewery.web.model.enums.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@Transactional
public interface BeerRepository extends JpaRepository<Beer, UUID> {
    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, Pageable pageable);

    Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyleEnum, Pageable pageable);

    Page<Beer> findAllByBeerName(String beerName, Pageable of);

    @Query(nativeQuery = true,value = "SELECT * FROM beer where upc = ?1")
    Beer findByUpc(String upc);
}
