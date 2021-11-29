package com.microservice.finnhub.decoder;

import com.microservice.finnhub.exception.BadRequestException;
import com.microservice.finnhub.exception.ForbiddenException;
import com.microservice.finnhub.exception.NotFoundException;
import com.microservice.finnhub.exception.ServerErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()) {
            case 400:
                return new BadRequestException(response.reason());
            case 403:
                return new ForbiddenException(response.reason());
            case 404: {
                return new NotFoundException(response.reason());
            }
            case 500:
                return new ServerErrorException(response.reason());
            default:
                return new Exception(response.reason());
        }
    }

}
