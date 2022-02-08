package com.example.nftdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.nftdemo.service.SysNftServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController("mint")
public class MintNftController {

    @Autowired
    private SysNftServiceApi sysNftServiceApi;

    @PostMapping("/bsc")
    public Object mintByBsc(String senderAddr) throws IOException {
        if (senderAddr == null) {
            return -1;
        }
        Map<String,Object> map = sysNftServiceApi.mintByBsc(senderAddr);
        return JSON.toJSONString(map);
    }

    @PostMapping("/eth")
    public Object mintByEth(String senderAddr) throws IOException {
        if (senderAddr == null) {
            return -1;
        }
        Map<String,Object> map = sysNftServiceApi.mintByEth(senderAddr);
        return JSON.toJSONString(map);
    }

}
