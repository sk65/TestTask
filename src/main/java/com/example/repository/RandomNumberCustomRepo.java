package com.example.repository;

import java.util.List;
/**
 * Created by Yefimov Yevhen on 10.11.2020
 * A repository interface for connect to database.
 */
public interface RandomNumberCustomRepo {

    /**
     * @return average average latency of the Numbers service.
     */
    double findAvgLatency();

    /**
     * @return total number of records
     */
    double findCountRecords();

    /**
     * @return the number of records for which found is equal to true.
     */
    double findCountRecordsByFound(boolean found);

    /**
     * The method retrieves 10 most popular numbers
     * (the ones that get requested most often).
     *
     * @return a list of long with the most frequently requested numbers.
     */
    List<Long> findPopularNumbers();
}
