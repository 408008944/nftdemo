package com.example.nftdemo.mongo;

import com.baomidou.mybatisplus.annotation.TableField;
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

    /**
     * 随机kind
     */
    @Field("kind")
    private String kind;

    /**
     * 币编号
     */
    @Field("token_id")
    private String tokenId;

    /**
     * 转账钱包
     */
    @Field("from")
    private String from;

    /**
     * 目标钱包
     */
    @Field("to")
    private String to;

    /**
     * 区块哈希
     */
    @Field("block_hash")
    private String blockHash;

    /**
     * 区块号码
     */
    @Field("block_number")
    private String blockNumber;

    /**
     * 合约地址
     */
    @Field("address")
    private String address;

    /**
     * 交易哈希
     */
    @Field("transaction_hash")
    private String transactionHash;

    /**
     * 创建时间
     */
    @Field("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Field("update_time")
    private Date updateTime;

    /**
     * 删除标记
     */
    @Field("deleted")
    private int deleted;

}
