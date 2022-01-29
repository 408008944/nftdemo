/*
package com.example.nftdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.nftdemo.domain.SysNftInfo;
import com.example.nftdemo.mapper.SysNftInfoMapper;
import com.example.nftdemo.utils.NFTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

@Slf4j
@RestController("mintNFT")
public class MintNFTController {

    @Autowired
    private SysNftInfoMapper sysNftInfoMapper;

    @PostMapping("/bsc")
    public Object mintByBSC(String senderAddr) {
        Random random = new Random();
        int kind = random.nextInt(3) + 1;

        SysNftInfo sysNftInfo = new SysNftInfo();
        sysNftInfoMapper.insert(sysNftInfo);

        BigInteger tokenId = BigInteger.valueOf(sysNftInfo.getId() * 100 + 1);


        BigInteger[] tokenIds = new BigInteger[1];
        tokenIds[0] = tokenId;

        System.out.println(tokenIds[0]);

        BigInteger deadline = BigInteger.valueOf(System.currentTimeMillis());


        log.info("自增ID:" + sysNftInfo.getId());
        log.info("tokenId:" + tokenId);
        log.info("deadline:" + deadline);
        log.info("kind:" + kind);

        String eip712Content = null;

        // 飞船 ( spaceship )
        // 英雄 ( hero )
        // 防御塔/火炮 ( defensivefacility )
        if (kind == 1) {
            eip712Content = NFTUtil.createEIP712CheckMintSpaceshipContent(
                    tokenIds,
                    senderAddr, deadline);
        }else if (kind == 2) {
            eip712Content = NFTUtil.createEIP712CheckHeroContent(
                    tokenIds,
                    senderAddr, deadline);
        }else if (kind == 3) {
            eip712Content = NFTUtil.createEIP712CheckDefensiveFacilityContent(
                    tokenIds,
                    senderAddr, deadline);
        }

        String signature = NFTUtil.signature(eip712Content);

        log.info("eip712Content : {} ", eip712Content);
        log.info("数据签名后的数据信息 : {} ", signature);

        HashMap<String, Object> map = new HashMap<>();

        map.put("signature",signature);
        map.put("kind",kind);
        map.put("tokenIds",tokenIds);
        map.put("deadline",deadline);
        map.put("price","10");


        return JSON.toJSONString(map);
    }
}
*/
