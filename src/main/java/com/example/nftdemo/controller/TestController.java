package com.example.nftdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.nftdemo.domain.SysNftInfo;
import com.example.nftdemo.mapper.SysNftInfoMapper;
import com.example.nftdemo.utils.NFTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Async;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Slf4j
@RestController
public class TestController {

    @Autowired
    private SysNftInfoMapper sysNftInfoMapper;

    @PostMapping("/test")
    public Object test(String senderAddr) throws IOException {

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

    @PostMapping("/hello")
    public Object hello() {

        String str = "雷猴啊！";

        HashMap<String, Object> map = new HashMap<>();

        map.put("1",str);


        return JSON.toJSONString(map);
    }

    //private Web3j web3j = Web3j.build(new HttpService("http://192.168.50.224:7545"), 1000, Async.defaultExecutorService());

    @PostMapping("/test2")
    public Object test2() {


        /*web3j.blockObservable(true).subscribe(ethBlock -> {
            EthBlock.Block block = ethBlock.getBlock();
            log.info("hash: " + block.getHash());
            log.info("number: " + block.getNumber());
            List<EthBlock.TransactionResult> transactions = block.getTransactions();
            transactions.forEach(tx -> {
                Transaction o = (Transaction) tx.get();
                log.info("Hash: " + o.getHash());
                log.info("From: "+o.getFrom());
                log.info("To: "+o.getTo());
                log.info("Value: "+o.getValue());
            });
        });
*/

        return null;
    }


    @PostMapping("/test3")
    public Object test3() {

        String s = "{\"from\":\"0x0000000000000000000000000000000000000000\",\"log\":{\"address\":\"0xfa4564e2f61818230eedc6e52bb4dd177d7f0433\",\"blockHash\":\"0xbe7b93a11f0c214f9c23ec0aa81654c1a368c71c37de1d4053b52bc554135c08\",\"blockNumber\":8,\"blockNumberRaw\":\"0x8\",\"data\":\"0x\",\"logIndex\":0,\"logIndexRaw\":\"0x0\",\"removed\":false,\"topics\":[\"0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef\",\"0x0000000000000000000000000000000000000000000000000000000000000000\",\"0x0000000000000000000000004d0ae32f636edcbb52bbcf741b01bcafc72adc5e\",\"0x000000000000000000000000000000000000000000000000000000000000000a\"],\"transactionHash\":\"0x379681a0cdb6185b820046e685f30b605fb77e89a2d3332614175208e3d5871e\",\"transactionIndex\":0,\"transactionIndexRaw\":\"0x0\",\"type\":\"mined\"},\"to\":\"0x4d0ae32f636edcbb52bbcf741b01bcafc72adc5e\",\"tokenId\":10}\n";

        Map map = (Map) JSON.parse(s);

        System.out.println(map);

        Object tokenId = map.get("tokenId");
        System.out.println("tokenId = " + tokenId.toString());

        Object from = map.get("from");
        System.out.println("from = " + from.toString());

        Object to = map.get("to");
        System.out.println("to = " + to.toString());

        Map log = (Map) map.get("log");
        System.out.println("log = " + log);

        System.out.println("-------------------------------------");

        Object blockHash = log.get("blockHash");
        System.out.println("blockHash = " + blockHash.toString());

        Object blockNumber = log.get("blockNumber");
        System.out.println("blockNumber = " + blockNumber.toString());

        Object address = log.get("address");
        System.out.println("address = " + address.toString());

        Object transactionHash = log.get("transactionHash");
        System.out.println("transactionHash = " + transactionHash.toString());

        return map;
    }


}
