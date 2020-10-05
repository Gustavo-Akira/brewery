package com.example.bewery.events;

import com.example.bewery.web.model.BeerDTO;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID= -578980080L;

    private BeerDTO beerDTO;

}
