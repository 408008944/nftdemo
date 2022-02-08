package com.example.nftdemo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_nft_info")
public class SysNftInfo {

    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * tokenId
     */
    @TableField("token_id")
    private String tokenId;

    /**
     * 用户地址
     */
    @TableField("sender_addr")
    private String senderAddr;

    /**
     * 随机kind
     */
    @TableField("kind")
    private int kind;

    /**
     * 链标识
     */
    @TableField("logo")
    private int logo;

    /**
     * 价格
     */
    @TableField("price")
    private String price;

    /**
     * 删除标识
     */
    @TableField("deleted")
    private int deleted;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
}
