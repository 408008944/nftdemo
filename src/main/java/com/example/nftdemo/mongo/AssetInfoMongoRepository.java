package com.example.nftdemo.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetInfoMongoRepository extends MongoRepository<AssetInfoEntity,String> {
}
