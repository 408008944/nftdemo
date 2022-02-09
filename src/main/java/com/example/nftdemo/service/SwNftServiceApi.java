package com.example.nftdemo.service;

import java.io.IOException;
import java.util.Map;

public interface SwNftServiceApi {
    /**
     * BSC方式Mint
     * @param senderAddr 用户地址
     * @return  单个tokenId
     * @throws IOException
     */
    Map<String, Object> mintByBsc(String senderAddr) throws IOException;

    /**
     * ETH方式Mint
     * @param senderAddr 用户地址
     * @return  套装tokenId(三个)
     * @throws IOException
     */
    Map<String, Object> mintByEth(String senderAddr) throws IOException;
}
