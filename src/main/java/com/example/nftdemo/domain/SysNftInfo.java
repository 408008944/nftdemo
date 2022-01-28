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

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("token_id")
    private String tokenId;

    @TableField("sender_addr")
    private String senderAddr;

    @TableField("kind")
    private int kind;

    @TableField("logo")
    private int logo;

    @TableField("price")
    private String price;

    @TableField("style")
    private int style;

    @TableField("create_time")
    private Date createTime;
}
