package com.example.nftdemo.service.impl;

import com.example.nftdemo.domain.SysNftInfo;
import com.example.nftdemo.mapper.SysNftInfoMapper;
import com.example.nftdemo.service.SysNftServiceApi;
import com.example.nftdemo.utils.NFTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class SysNftServiceImpl implements SysNftServiceApi {

    @Autowired
    private SysNftInfoMapper sysNftInfoMapper;

    @Override
    public Map<String, Object> mintByBsc(String senderAddr) throws IOException {
        /*
            随机一个kind
            1.飞船 ( spaceship )
            2.英雄 ( hero )
            3.防御塔/火炮 ( defensivefacility )
         */
        int kind = new Random().nextInt(3) + 1;

        SysNftInfo sysNftInfo = new SysNftInfo();
        sysNftInfoMapper.insert(sysNftInfo);

        // 生成自增tokenId
        BigInteger tokenId = BigInteger.valueOf(sysNftInfo.getId() * 100 + 1);
        BigInteger[] tokenIds = new BigInteger[1];
        tokenIds[0] = tokenId;

        BigInteger deadline = BigInteger.valueOf(System.currentTimeMillis());

        String eip712Content = null;

        // 根据随机kind铸造
        switch (kind) {
            case 1 : eip712Content = NFTUtil.createEIP712CheckMintSpaceshipContent(tokenIds, senderAddr, deadline);break;
            case 2 : eip712Content = NFTUtil.createEIP712CheckHeroContent(tokenIds, senderAddr, deadline);break;
            case 3 : eip712Content = NFTUtil.createEIP712CheckDefensiveFacilityContent(tokenIds, senderAddr, deadline);break;
        }

        // 生成数据签名
        String signature = NFTUtil.signature(eip712Content);

        // 更新nft信息
        sysNftInfo.setKind(kind);
        sysNftInfo.setSenderAddr(senderAddr);
        sysNftInfo.setTokenId(tokenId.toString());
        sysNftInfo.setLogo(2);
        sysNftInfo.setPrice("10");
        sysNftInfo.setDeleted(0);
        sysNftInfo.setCreateTime(new Date());

        sysNftInfoMapper.updateById(sysNftInfo);

        Map<String, Object> map = new HashMap<>();
        map.put("signature",signature);
        map.put("kind",kind);
        map.put("tokenIds",tokenIds);
        map.put("deadline",deadline);
        map.put("price","10");

        return map;
    }
}
