package com.example.companyserver.decoder;

import com.example.companyserver.exceptions.BadRequestException;
import com.example.companyserver.exceptions.ForbiddenException;
import com.example.companyserver.exceptions.NotFoundException;
import com.example.companyserver.exceptions.ServerErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;


//@Component
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
