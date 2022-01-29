package com.example.nftdemo.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MgNftInfoRepository extends MongoRepository<MgNftInfo,String> {
}
