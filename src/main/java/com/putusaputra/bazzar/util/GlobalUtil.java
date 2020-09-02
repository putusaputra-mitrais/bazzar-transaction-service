package com.putusaputra.bazzar.util;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.putusaputra.bazzar.dto.ResponseWrapper;

public class GlobalUtil {
    public static ResponseWrapper createResponse(String message, Object object, List<String> errors) {
        return errors.size() == 0 ? new ResponseWrapper(message, object, HttpStatus.OK.value(), errors) : 
            new ResponseWrapper(message, object, HttpStatus.INTERNAL_SERVER_ERROR.value(), errors);
    }
    
    public static JsonNode convertStringToJsonObject(String json) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(json);
    }
    
    public static String convertObjectToJson(Object responseWrapper) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(responseWrapper);
    }
}
