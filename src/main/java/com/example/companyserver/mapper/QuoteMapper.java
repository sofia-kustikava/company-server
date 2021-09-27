package com.example.companyserver.mapper;

import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.entity.QuoteEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface QuoteMapper {
    QuoteDto quoteToDto(QuoteEntity quote);

    List<QuoteDto> quotesToDto (List<QuoteEntity> quotes);

    QuoteEntity dtoToQuote (QuoteDto reportsDto);
}
