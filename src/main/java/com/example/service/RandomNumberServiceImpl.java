package com.example.service;

import com.example.model.RandomNumber;
import com.example.repository.RandomNumberCustomRepo;
import com.example.repository.RandomNumberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Yefimov Yevhen on 10.11.2020
 * A service class that implements {@link RandomNumberService}.
 */
@Service
public class RandomNumberServiceImpl implements RandomNumberService {
    
    /**
     * This field needed to store URL for the external Numbers service.
     */
    private final String url;

    /**
     * This field is needed to provide access to the database.
     */
    private final RandomNumberRepository repository;

    /**
     * This field is needed to provide access to the database.
     */
    private final RandomNumberCustomRepo customRepo;
    
    public RandomNumberServiceImpl(@Value("${numbersapi.url}") String url, RandomNumberRepository repository, RandomNumberCustomRepo customRepo) {
        this.url = url;
        this.repository = repository;
        this.customRepo = customRepo;
    }

    /**
     * @see RandomNumberService#getNumberResp(Long)
     */
    @Override
    public Optional<Map<String, Object>> getNumberResp(Long number) {
        if (number == null) {
            return Optional.empty();
        }

        RestTemplate restTemplate = new RestTemplate();

        long start = System.currentTimeMillis();
        RandomNumber randomNumber = restTemplate.getForObject(String.format(url, number), RandomNumber.class);
        long finish = System.currentTimeMillis();
        long latency = finish - start;

        if (randomNumber == null) {
            return Optional.empty();
        }

        randomNumber.setLatency(latency);
        saveRandomNumber(randomNumber);

        Map<String, Object> result = new HashMap<>() {{
            put("text", randomNumber.getText());
            put("number", number);
        }};
        return Optional.of(result);
    }

    /**
     * @see RandomNumberService#getMetricsResp().
     */
    @Override
    public Map<String, Object> getMetricsResp() {
        return new HashMap<>() {
            {
                put("popularNumbers", customRepo.findPopularNumbers());
                put("avgLatency", round(customRepo.findAvgLatency()));
                put("avgSuccess", round(getAvgSuccess()));
            }
        };
    }

    /**
     * Method saves a RandomNumber object to the database.
     */
    private void saveRandomNumber(RandomNumber number) {
        repository.save(number);
    }

    /**
     * @return average success rate.
     */
    private double getAvgSuccess() {
        double totalCountRecords = customRepo.findCountRecords();
        if (totalCountRecords == 0) {
            return 0;
        }
        double foundTrueCountRecords = customRepo.findCountRecordsByFound(true);
        return foundTrueCountRecords / totalCountRecords * 100;
    }

    /**
     * Method rounds the value to 2 decimal places.
     * @param value a double value
     * @return a rounded value.
     */
    private double round(double value) {
        return (double) Math.round(value * 100d) / 100d;
    }
}
