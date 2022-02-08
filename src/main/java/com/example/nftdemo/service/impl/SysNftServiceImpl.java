package com.example.nftdemo.service.impl;

import com.example.nftdemo.constant.NftConstant;
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

    /**
     *  BSC
     */
    @Override
    public Map<String, Object> mintByBsc(String senderAddr) throws IOException {
        /*
            随机一个kind
            1.飞船 ( spaceship )
            2.英雄 ( hero )
            3.防御塔/火炮 ( defensivefacility )
         */
        int kind = new Random().nextInt(3) + 1;

        Map<String, Object> map = this.mintNft(senderAddr, kind, 1,new Date());

        return map;
    }

    /**
     *  ETH
     */
    @Override
    public Map<String, Object> mintByEth(String senderAddr) throws IOException {

        Date now = new Date();
        Map<String, Object> Data = null;
        Map<String, Object> map ;

        for (int kind = 1; kind <= 3; kind++) {
            map = this.mintNft(senderAddr, kind, 1,now);
            Data.put(String.valueOf(kind),map);
            map.clear();
        }

        return Data;
    }

    /**
     * 通用Mint
     */
    private Map<String,Object> mintNft(String senderAddr,int kind,int logo,Date createTime) throws IOException {

        SysNftInfo sysNftInfo = new SysNftInfo();
        sysNftInfoMapper.insert(sysNftInfo);

        // 生成自增tokenId
        BigInteger tokenId = BigInteger.valueOf(sysNftInfo.getId() * 100 + logo);
        BigInteger[] tokenIds = new BigInteger[1];
        tokenIds[0] = tokenId;

        BigInteger deadline = BigInteger.valueOf(System.currentTimeMillis());

        String eip712Content = null;

        // 根据kind铸造
        switch (kind) {
            case 1 : eip712Content = NFTUtil.createEIP712CheckMintContent(tokenIds, senderAddr, deadline, NftConstant.MINT_CONTENT_SPACESHIP);break;
            case 2 : eip712Content = NFTUtil.createEIP712CheckMintContent(tokenIds, senderAddr, deadline,NftConstant.MINT_CONTENT_HERO);break;
            case 3 : eip712Content = NFTUtil.createEIP712CheckMintContent(tokenIds, senderAddr, deadline,NftConstant.MINT_CONTENT_DEFENSIVEFACILITY);break;
        }

        // 生成数据签名
        String signature = NFTUtil.signature(eip712Content);

        // 更新nft信息
        sysNftInfo.setKind(kind);
        sysNftInfo.setSenderAddr(senderAddr);
        sysNftInfo.setTokenId(tokenId.toString());
        sysNftInfo.setLogo(logo);
        sysNftInfo.setPrice("10");
        sysNftInfo.setDeleted(0);
        sysNftInfo.setCreateTime(createTime);

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
