package org.example.mseventmanager.config;


import feign.Response;
import feign.codec.ErrorDecoder;
import org.example.mseventmanager.exceptions.InvalidCepException;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.BAD_REQUEST.value()) {
            return new InvalidCepException("Invalid CEP provided.");
        }
        return new Exception("Generic error");
    }
}
