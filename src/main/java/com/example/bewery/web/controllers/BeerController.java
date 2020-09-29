package com.example.bewery.web.controllers;

import com.example.bewery.repositories.BeerRepository;
import com.example.bewery.service.BeerService;
import com.example.bewery.service.BeerServiceImpl;
import com.example.bewery.web.mappers.BeerMapper;
import com.example.bewery.web.model.BeerDTO;
import com.example.bewery.web.model.BeerPagedList;
import com.example.bewery.web.model.enums.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RequestMapping("/api/v1/beer")
@RestController
@RequiredArgsConstructor
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value="pageNumber",required = false) Integer pageNumber,
                                                   @RequestParam(value="pageSize",required = false) Integer pageSize,
                                                   @RequestParam(value="beerName",required = false) String beerName,
                                                   @RequestParam(value="beerStyle",required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value="showInventoryOnHand",required=false) Boolean showInventoryOnHand
    ){
        if(showInventoryOnHand == null){
            showInventoryOnHand = false;
        }
        if(pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if(pageSize == null || pageSize < 0){
            pageSize = DEFAULT_PAGE_SIZE;
        }
        BeerPagedList beerPagedList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber,pageSize), showInventoryOnHand);
        return ResponseEntity.ok(beerPagedList);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable("beerId")UUID beerId){
        return ResponseEntity.ok(beerService.getById(beerId));
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody @Validated BeerDTO beerDTO){
        beerService.saveNewBeer(beerDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody @Validated BeerDTO beerDTO) throws ChangeSetPersister.NotFoundException {
        beerService.updateBeer(beerId, beerDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
