package com.example.bewery.web.mappers;

import com.example.bewery.domain.Beer;
import com.example.bewery.web.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDTO beerToBeerDTO(Beer beer);
    Beer BeerDTotoBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDTOWithInventory(Beer beer);
}
