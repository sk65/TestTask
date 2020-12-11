package com.example.controller;

import com.example.service.RandomNumberService;
import com.example.service.RandomNumberServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Yefimov Yevhen on 10.11.2020
 * The class for directly handling requests from the client and returning result.
 */

@RestController
@RequestMapping("/api/v1/")
public class NumberController {

    private final RandomNumberService numberService;

    public NumberController(RandomNumberServiceImpl numberService) {
        this.numberService = numberService;
    }

    /**
     * Method handles url request with a random number.
     * @param number a random number.
     * @return interesting fact and number in json format.
     */
    @GetMapping(value = "/numbers/{number}")
    public ResponseEntity<Map<String, Object>> getNumber(@PathVariable("number") Long number) {

        Optional<Map<String, Object>> optional = numberService.getNumberResp(number);

        if (optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    /**
     * Method handles url request with metrics".
     * @return metrics(10 most popular numbers,
     * average latency of the Numbers service,
     * average success rate of the Numbers service) in json format.
     */
    @GetMapping(value = "/metrics")
    public ResponseEntity<Map<String, Object>> getMetrics() {
        return new ResponseEntity<>(numberService.getMetricsResp(), HttpStatus.OK);
    }
}
