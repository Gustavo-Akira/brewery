package com.example.bewery.web.controllers;

import com.example.bewery.repositories.BeerRepository;
import com.example.bewery.web.mappers.BeerMapper;
import com.example.bewery.web.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RestController
@RequiredArgsConstructor
public class BeerController {

    private final BeerMapper mapper;
    private final BeerRepository repository;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable("beerId")UUID beerId){
        return new ResponseEntity<>(mapper.beerToBeerDTO(repository.findById(beerId).get()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody @Validated BeerDTO beerDTO){
        repository.save(mapper.BeerDTotoBeer(beerDTO));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody @Validated BeerDTO beerDTO){
        repository.findById(beerId).ifPresent(x -> {
            x.setBeerName(beerDTO.getBeerName());
            x.setBeerStyle(beerDTO.getBeerStyle().name());
            x.setPrice(beerDTO.getPrice());
            x.setUpc(beerDTO.getUpc());
            repository.save(x);
        });
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
