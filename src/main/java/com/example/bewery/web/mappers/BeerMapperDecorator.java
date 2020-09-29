package com.example.bewery.web.mappers;

import com.example.bewery.domain.Beer;
import com.example.bewery.service.inventory.BeerInventoryService;
import com.example.bewery.web.model.BeerDTO;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper {
    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDTO beerToBeerDTO(Beer beer) {
        return mapper.beerToBeerDTO(beer);
    }

    @Override
    public BeerDTO beerToBeerDTOWithInventory(Beer beer) {
        BeerDTO dto = mapper.beerToBeerDTO(beer);
        dto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
        return dto;
    }

    @Override
    public Beer BeerDTotoBeer(BeerDTO beerDto) {
        return mapper.BeerDTotoBeer(beerDto);
    }
}
