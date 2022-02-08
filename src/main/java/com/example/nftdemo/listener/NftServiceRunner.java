package com.example.nftdemo.listener;

import com.alibaba.fastjson.JSON;
import com.example.nftdemo.mongo.MgNftInfo;
import com.example.nftdemo.mongo.MgNftInfoRepository;
import com.example.nftdemo.utils.MITNft;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Async;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 服务监听器，继承ApplicationRunner，在spring启动时启动
 */
@Component
@Slf4j
public class NftServiceRunner implements ApplicationRunner {

    @Autowired
    private MgNftInfoRepository mgNftInfoRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始监听nftMint事件");
        startlistener();
    }

    private void startlistener() {
        Web3j web3j = Web3j.build(new HttpService("http://192.168.50.146:7545"), 1000, Async.defaultExecutorService());

        EthFilter ethFilter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST,
                Arrays.asList("0xFa4564E2f61818230EEDC6e52BB4dd177d7F0433"
                        ,"0xa8676f3fa2540555d78372F944F3e215eaD864c9"
                        ,"0x16e0Fe82021c733Ae1e85904968F12b2B96AaF32"));

        ContractGasProvider contractGasProvider = new DefaultGasProvider() ;
        // 私钥
        Credentials credentials = Credentials.create("46a90a29de39024f14bf8474aaa9a6ba46bcbfae62ec1618b910b268f6ef967c") ;
        MITNft t1 = MITNft.load("0xFa4564E2f61818230EEDC6e52BB4dd177d7F0433", web3j, credentials ,contractGasProvider) ;
        MITNft t2 = MITNft.load("0xa8676f3fa2540555d78372F944F3e215eaD864c9", web3j, credentials ,contractGasProvider) ;
        MITNft t3 = MITNft.load("0x16e0Fe82021c733Ae1e85904968F12b2B96AaF32", web3j, credentials ,contractGasProvider) ;

        t1.transferEventFlowable(ethFilter).subscribe(event -> {
            String eventStr = JSON.toJSONString(event);
            if (!"{}".equals(eventStr)) {
                this.saveInfo(eventStr,"1");
                log.info("Kind1(飞船)Mint事件MongoDB存储完成");
            }
        }) ;

        t2.transferEventFlowable(ethFilter).subscribe(event -> {
            String eventStr = JSON.toJSONString(event);
            if (!"{}".equals(eventStr)) {
                this.saveInfo(eventStr,"2");
                log.info("Kind2(英雄)Mint事件MongoDB存储完成");
            }
        }) ;

        t3.transferEventFlowable(ethFilter).subscribe(event -> {
            String eventStr = JSON.toJSONString(event);
            if (!"{}".equals(eventStr)) {
                this.saveInfo(eventStr,"2");
                log.info("Kind3(防御塔)Mint事件MongoDB存储完成");
            }
        }) ;
    }

    private void saveInfo(String eventStr,String Kind) {
        Map map = (Map) JSON.parse(eventStr);
        if (map.get("from").toString().equals("0x0000000000000000000000000000000000000000")) {
            Map log = (Map) map.get("log");
            MgNftInfo mgNftInfo = new MgNftInfo();
            mgNftInfo.setAddress(log.get("address").toString());
            mgNftInfo.setBlockHash(log.get("blockHash").toString());
            mgNftInfo.setFrom(map.get("from").toString());
            mgNftInfo.setTo(map.get("to").toString());
            mgNftInfo.setTokenId(map.get("tokenId").toString());
            mgNftInfo.setTransactionHash(log.get("transactionHash").toString());
            mgNftInfo.setBlockNumber(log.get("blockNumber").toString());
            mgNftInfo.setKind(Kind);

            Date now = new Date();
            mgNftInfo.setCreateTime(now);
            mgNftInfo.setUpdateTime(now);
            mgNftInfo.setDeleted(0);

            mgNftInfoRepository.save(mgNftInfo);
        }
    }

}
