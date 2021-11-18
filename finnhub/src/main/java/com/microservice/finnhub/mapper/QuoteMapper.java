package com.microservice.finnhub.mapper;

import com.microservice.finnhub.dto.QuoteDto;
import com.microservice.finnhub.entity.QuoteEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface QuoteMapper {

    QuoteDto quoteToDto(QuoteEntity quote);
    List<QuoteDto> quotesToDto (List<QuoteEntity> quotes);

    QuoteEntity dtoToQuote (QuoteDto quoteDto);
    List<QuoteEntity> dtoToQuotes (List<QuoteDto> quoteDtos);
}
