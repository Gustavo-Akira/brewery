package com.example.bewery.web.controllers;

import com.example.bewery.domain.Beer;
import com.example.bewery.repositories.BeerRepository;
import com.example.bewery.web.model.BeerDTO;
import com.example.bewery.web.model.enums.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static  org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.snippet.Attributes.key;
@WebMvcTest(BeerController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https",uriHost = "dev.gustavoakira.beer", uriPort = 80)
@ComponentScan(basePackages = "com.example.bewery.web.mappers")
class BeerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BeerRepository beerRepository;
    @Test
    void getBeerById() throws Exception {
        given(beerRepository.findById(any())).willReturn(Optional.of(Beer.builder().build()));
        mockMvc.perform(get("/api/v1/beer/{beerId}",UUID.randomUUID().toString())
                .param("iscold","yes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get", pathParameters(
                        parameterWithName("beerId").description("UUID of the desired beer")
                        ),
                        requestParameters(
                              parameterWithName("iscold").description("Parameter for check if the beer is cold")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of the beer"),
                                fieldWithPath("version").description("The version of the beer"),
                                fieldWithPath("createdDate").description("Date Created"),
                                fieldWithPath("lastModifiedDate").description("Date Updated"),
                                fieldWithPath("beerName").description("Beer Name"),
                                fieldWithPath("beerStyle").description("Beer Style"),
                                fieldWithPath("upc").description("UPC of Beer"),
                                fieldWithPath("price").description("Price of Beer"),
                                fieldWithPath("quantityOnHand").description("The quantity on Hand")
                        )
                ));
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDTO beerDTO = getValidBeerDTO();
        String beerDTOtoJSON = objectMapper.writeValueAsString(beerDTO);
        ConstrainedFields fields = new ConstrainedFields(BeerDTO.class);
        mockMvc.perform(post("/api/v1/beer").
                contentType(MediaType.APPLICATION_JSON).
                content(beerDTOtoJSON))
                .andExpect(status().isCreated())
        .andDo(document("v1/beer-new",
                requestFields(
                        fields.withPath("id").ignored(),
                        fields.withPath("version").ignored(),
                        fields.withPath("createdDate").ignored(),
                        fields.withPath("lastModifiedDate").ignored(),
                        fields.withPath("beerName").description("The beer name"),
                        fields.withPath("beerStyle").description("Style of the beer"),
                        fields.withPath("upc").description("UPC of Beer"),
                        fields.withPath("price").description("Price of Beer"),
                        fields.withPath("quantityOnHand").ignored()
                )
        ))
        ;
    }

    @Test
    void updateBeerById() throws Exception {
        BeerDTO beerDTO = getValidBeerDTO();
        String beerDTOtoJson = objectMapper.writeValueAsString(beerDTO);
        mockMvc.perform(put("/api/v1/beer/{beerId}",UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDTOtoJson))
                .andExpect(status().isNoContent());
    }
    BeerDTO getValidBeerDTO(){
        return BeerDTO.builder()
                .beerName("My beer")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal(12.74))
                .upc(123123123L)
                .build();
    }
    private static class ConstrainedFields{
        private final ConstraintDescriptions constraintDescriptions;
        ConstrainedFields(Class<?> input){
             this.constraintDescriptions = new ConstraintDescriptions(input);
        }
        private FieldDescriptor withPath(String path){
            return  fieldWithPath(path).attributes(key("constraints").value(
                    StringUtils.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path),". ")
            ));
        }
    }
}