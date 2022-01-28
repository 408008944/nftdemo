package com.example.nftdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.Random;

@SpringBootTest
class NftdemoApplicationTests {


    @Test
    public void test() throws IOException {
        Web3j web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/62fc727578294f57929545abf01a39b0"));
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();

        System.out.println("打印： " + clientVersion);

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(3) + 1);
        }
    }

}
