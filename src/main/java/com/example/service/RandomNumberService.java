package com.example.service;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Yefimov Yevhen on 10.11.2020
 * A service interface for processing business logic.
 */
public interface RandomNumberService {
    /**
     * The method  connect to external Numbers service and
     * get retrieve facts about accepted number, then it saves number to the database.
     * @param number a random number.
     * @return a HashMap containing: an average Latency, an average Success, popular Numbers.
     */
    Optional<Map<String, Object>> getNumberResp(Long number);

    /**
     * Method gets metrics from the database using {@link com.example.repository.RandomNumberRepository}.
     @return a HashMap containing: an average Latency, an average Success, popular Numbers.
     */
    Map<String, Object> getMetricsResp();
}
