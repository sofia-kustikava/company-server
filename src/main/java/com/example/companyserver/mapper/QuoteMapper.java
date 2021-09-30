package com.example.companyserver.mapper;

import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.entity.QuoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface QuoteMapper {

    QuoteMapper INSTANCE = Mappers.getMapper(QuoteMapper.class);

    QuoteDto quoteToDto(QuoteEntity quote);

    List<QuoteDto> quotesToDto (List<QuoteEntity> quotes);

    QuoteEntity dtoToQuote (QuoteDto reportsDto);
}
