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
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Async;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 服务监听器，继承ApplicationRunner，在spring启动时启动
 * @author liqiang
 */
@Component
@Slf4j
public class ServiceRunner implements ApplicationRunner {



    /*@Autowired
    private Web3j web3j;*/

    /*//如果多个监听，必须要注入新的过滤器
    @Autowired
    private EthFilter uploadProAuth;*/

    @Override
    public void run(ApplicationArguments var1) throws Exception{
        this.log.info("开始监听事件");
//        uploadProAuth();
        startlistener();
    }

    //private String contractAddress = "0xaf0895260ef377ceea0086c99e3eff7999f742c9";

    private static String contractAddr = "0xcfdee3b93394f1c94E921D162C3376C48C447Bcd" ;



    /**
     * 收到上链事件
     */
    /*public void uploadProAuth(){



        Web3j web3j = Web3j.build(new HttpService("http://192.168.50.146:7545"), 1000, Async.defaultExecutorService());

        EthFilter filter = new EthFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                Arrays.asList("0xFa4564E2f61818230EEDC6e52BB4dd177d7F0433"
                        ,"0xa8676f3fa2540555d78372F944F3e215eaD864c9"
                        ,"0x16e0Fe82021c733Ae1e85904968F12b2B96AaF32"));
        Event event = new Event("Transfer",
                Arrays.asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.asList(new TypeReference<Uint256>() {})
        );

        String topicData = EventEncoder.encode(event);
        filter.addSingleTopic(topicData);

        web3j.ethLogObservable(filter).subscribe(x -> {

            log.info("输入日志" + JSON.toJSONString(x));

          *//*  System.out.println(log.getBlockNumber());
            System.out.println(log.getTransactionHash());
            List<String> topics = log.getTopics();
            for (String topic : topics) {
                System.out.println("topic:" + topic);
            }
            EventValues eventValues = Contract.staticExtractEventParameters(event, log);
            String from = (String) eventValues.getIndexedValues().get(0).getValue();
            String to = (String) eventValues.getIndexedValues().get(1).getValue();
            BigInteger value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            System.out.println("form:" + from);
            System.out.println("to:" + to);
            System.out.println("value:" + value);*//*



        });

    }
*/
    @Autowired
    private MgNftInfoRepository mgNftInfoRepository;


    public void startlistener() {

        Web3j web3j = Web3j.build(new HttpService("http://192.168.50.146:7545"), 1000, Async.defaultExecutorService());

        /*EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST, Arrays.asList("0xFa4564E2f61818230EEDC6e52BB4dd177d7F0433"
                ,"0xa8676f3fa2540555d78372F944F3e215eaD864c9"
                ,"0x16e0Fe82021c733Ae1e85904968F12b2B96AaF32"));


        web3j.ethLogObservable(filter).subscribe(log -> {
            System.out.println(log.toString());
        });*/

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
            if (JSON.toJSONString(event) != null) {
                log.info("接收到事件信息 1: {} ", JSON.toJSONString(event));
            }
        }) ;

        t2.transferEventFlowable(ethFilter).subscribe(event -> {
            if (JSON.toJSONString(event) != null) {
                log.info("接收到事件信息 2: {} ", JSON.toJSONString(event));
            }
        }) ;

        t3.transferEventFlowable(ethFilter).subscribe(event -> {

            if (!"{}".equals(JSON.toJSONString(event))) {
                Map map = (Map) JSON.parse(JSON.toJSONString(event));
                Object tokenId = map.get("tokenId");
                Object from = map.get("from");
                Object to = map.get("to");
                Map log = (Map) map.get("log");
                Object blockHash = log.get("blockHash");
                Object blockNumber = log.get("blockNumber");
                Object address = log.get("address");
                Object transactionHash = log.get("transactionHash");

                MgNftInfo mgNftInfo = new MgNftInfo();
                mgNftInfo.setAddress(address.toString());
                mgNftInfo.setBlockHash(blockHash.toString());
                mgNftInfo.setFrom(from.toString());
                mgNftInfo.setTo(to.toString());
                mgNftInfo.setTokenId(tokenId.toString());
                mgNftInfo.setTransactionHash(transactionHash.toString());
                mgNftInfo.setBlockNumber(blockNumber.toString());
                mgNftInfo.setKind("3");

                mgNftInfoRepository.save(mgNftInfo);

                System.out.println("MongoDB存储完成");
            }


        }) ;
    }
}
