package com.thienan.product_service.handler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thienan.product_service.handler.exceptions.common.CustomBadRequestException;
import feign.FeignException;
import feign.FeignException.BadRequest;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.micrometer.core.instrument.util.IOUtils;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpStatus.*;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorResponse message = null;

        try (InputStream bodyIs = response.body()
            .asInputStream()) {

            String body = IOUtils.toString(bodyIs, StandardCharsets.UTF_8);
            log.info(body);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            message = mapper.readValue(body, ErrorResponse.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }

        log.info(message.toString());

        return switch (response.status()) {
            case 400 -> {
                if (message.getDetails() != null) {
                    throw new CustomBadRequestException(message.getMessage(), message.getDetails());
                }
                yield new ResponseStatusException(BAD_REQUEST, message.getMessage() != null ? message.getMessage() : "Bad Request");
            }
            case 404 -> new ResponseStatusException(NOT_FOUND, message.getMessage() != null ? message.getMessage() : "Not Found");
            case 500 -> new ResponseStatusException(INTERNAL_SERVER_ERROR, message.getMessage() != null ? message.getMessage() : "Internal Server Error");
            default -> errorDecoder.decode(methodKey, response);
        };

    }
}

