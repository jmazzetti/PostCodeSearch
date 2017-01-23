package com.postcodesearch.controller;

import com.postcodesearch.config.AppBoot;
import com.postcodesearch.entity.AddressData;
import com.postcodesearch.exception.PostCodeException;
import com.postcodesearch.protocol.ResponseObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Validated
@RestController
public class PostCodeController {

    private static Logger log = LogManager.getLogger(PostCodeController.class);

    private static final String POSTCODE = "/postcode/{code}";

    //https://en.wikipedia.org/wiki/Postcodes_in_the_United_Kingdom#Validation
    //@Pattern(regexp = "(GIR 0AA)|((([A-Z-[QVX]][0-9][0-9]?)|(([A-Z-[QVX]][A-Z-[IJZ]][0-9][0-9]?)|(([A-Z-[QVX]][0-9][A-HJKPSTUW])|([A-Z-[QVX]][A-Z-[IJZ]][0-9][ABEHMNPRVWXY])))) [0-9][A-Z-[CIKMOV]]{2})\n")
    //private static String VALIDATION_REGEX = "(GIR 0AA)|((([A-Z-[QVX]][0-9][0-9]?)|(([A-Z-[QVX]][A-Z-[IJZ]][0-9][0-9]?)|(([A-Z-[QVX]][0-9][A-HJKPSTUW])|([A-Z-[QVX]][A-Z-[IJZ]][0-9][ABEHMNPRVWXY])))) [0-9][A-Z-[CIKMOV]]{2})\n";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AppBoot appBoot;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping(value = POSTCODE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseObject postcode(@PathVariable("code") final String code) throws PostCodeException {
        final ResponseEntity<AddressData> rsp = restTemplate.getForEntity(
                appBoot.getEndpoint()+code,
                AddressData.class);
        ResponseObject response = null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("response", "SUCCESS");
        parameters.put("entity", rsp.getBody());
        response = ResponseObject.responseSuccess("1", parameters);
        return response;
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(ConstraintViolationException exception) {
        return error(exception.getConstraintViolations()
                              .stream()
                              .map(ConstraintViolation::getMessage)
                              .collect(Collectors.toList()));
    }

    private Map error(Object message) {
        return Collections.singletonMap("error", message);
    }

}
