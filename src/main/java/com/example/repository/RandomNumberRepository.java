package com.example.repository;

import com.example.model.RandomNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by  Yefimov Yevhen on 10.11.2020
 * A spring Data repository is used to communicate with the database.
 */
@Repository
public interface RandomNumberRepository extends JpaRepository<RandomNumber, Long> {

    /**
     * The method retrieves 10 most popular numbers
     * (the ones that get requested most often).
     *
     * @return an array of int with the most frequently requested numbers.
     */
    @Query(value = "SELECT number " +
            "FROM number " +
            "GROUP BY number " +
            "ORDER BY count(number)DESC " +
            "LIMIT 10",
            nativeQuery = true)
    int[] findPopularNumbers();

    /**
     * The method retrieves average success rate of the Numbers service.
     *
     * @return average success rate.
     */
    @Query(value = "SELECT " +
            "CASE WHEN COUNT(*)=0 THEN 0 " +
            "ELSE ROUND((COUNT(*)/(SELECT COUNT(*) FROM number)*100),2) " +
            "END " +
            "FROM number WHERE found=true", nativeQuery = true)
    double findAvgSuccess();

    /**
     * The method retrieves average average latency of the Numbers service.
     *
     * @return average latency.
     */
    @Query("SELECT coalesce(AVG(latency),0) FROM RandomNumber")
    long findAvgLatency();

}
