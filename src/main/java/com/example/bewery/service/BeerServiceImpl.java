package com.example.bewery.service;

import com.example.bewery.domain.Beer;
import com.example.bewery.repositories.BeerRepository;
import com.example.bewery.web.mappers.BeerMapper;
import com.example.bewery.web.model.BeerDTO;

import com.example.bewery.web.model.BeerPagedList;
import com.example.bewery.web.model.enums.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerDTO getById(UUID beerId) {
        return beerMapper.beerToBeerDTO(beerRepository.findById(beerId).get());
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDto) {
        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.BeerDTotoBeer(beerDto)));
    }

    @Override
    public BeerDTO updateBeer(UUID beerId, BeerDTO beerDto) throws ChangeSetPersister.NotFoundException {
        Beer beer = beerRepository.findById(beerId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return  beerMapper.beerToBeerDTO(beerRepository.save(beer));
    }

    @Override
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventory == false")
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest of, Boolean showInventory) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;
        if(!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle,of);
        }else if(!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)){
            beerPage = beerRepository.findAllByBeerName(beerName,of);
        }else if(StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, of);
        }else{
            beerPage = beerRepository.findAll(of);
        }

        if(showInventory){
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDTOWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }else{
            beerPagedList = new BeerPagedList(
                    beerPage.
                            getContent().
                            stream().
                            map(beerMapper::beerToBeerDTO).
                            collect(Collectors.toList()),
                    PageRequest.of(
                            beerPage.getPageable().getPageNumber(),
                            beerPage.getPageable().getPageSize()
                    ),
                    beerPage.getTotalElements()
            );
        }
        return beerPagedList;
    }
}
