package com.example.nftdemo.service;

import java.io.IOException;
import java.util.Map;

public interface SysNftServiceApi {
    Map<String, Object> mintByBsc(String senderAddr) throws IOException;
}
