package com.example.repository;

import com.example.model.RandomNumber;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by  Yefimov Yevhen on 10.11.2020
 * A spring Data repository is used to communicate with the database.
 */
@Repository
public interface RandomNumberRepository extends ElasticsearchRepository<RandomNumber, String> {
}
