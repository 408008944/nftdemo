package com.example.nftdemo.mongo;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@ToString
@Document("mg_nft_info")
public class MgNftInfo {

    @Id
    private String id;

    @Field("kind")
    private String kind;

    @Field("token_id")
    private String tokenId;

    @Field("from")
    private String from;

    @Field("to")
    private String to;

    @Field("block_hash")
    private String blockHash;

    @Field("block_number")
    private String blockNumber;

    @Field("address")
    private String address;

    @Field("transaction_hash")
    private String transactionHash;

}
