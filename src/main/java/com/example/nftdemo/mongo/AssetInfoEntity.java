package com.example.nftdemo.mongo;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@ToString
@Document("coinex_asset_info")
public class AssetInfoEntity {

    @Id
    private String id;

    @Field("available")
    private String available;


}
