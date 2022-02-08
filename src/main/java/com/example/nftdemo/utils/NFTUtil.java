package com.example.nftdemo.utils;

import com.example.nftdemo.constant.NftConstant;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;

// 入口函数
@Slf4j
public class NFTUtil {

    // domain hex string
    private static final String EIP712_DOMAIN = createEIP712Domain(NftConstant.NAME, NftConstant.VERSION, NftConstant.CHAINID) ;

    public static String createEIP712CheckMintContent(BigInteger[] tokenIds, String senderAddr, BigInteger deadline,String mintContent) throws IOException {
        return  Hash.sha3(Numeric.toHexStringNoPrefix(Hash.sha3(mintContent.getBytes())) +
                Numeric.toHexStringNoPrefix(encodeIntArr(tokenIds)) +
                addressToHexStr(senderAddr) +
                Numeric.toHexStringNoPrefix(encodeInt(deadline))).substring(2) ;
    }

    public static String signature(String eip712Content) {
        // 构造完整 EIP712 Hash
        String eip712StructHash = createEIP712StructHash(EIP712_DOMAIN, eip712Content) ;
        log.info("eip712StructHash : {} ", eip712StructHash);

        // 使用私钥签名 数据
        Sign.SignatureData signatureData = Sign.signMessage(Numeric.hexStringToByteArray(eip712StructHash)
                , NftConstant.credentials.getEcKeyPair(), false);

        // 转换签名字符串
        ByteBuffer sigBuffer = ByteBuffer.allocate(signatureData.getR().length + signatureData.getS().length + 1);
        sigBuffer.put(signatureData.getR());
        sigBuffer.put(signatureData.getS());
        sigBuffer.put(signatureData.getV());
        return Numeric.toHexString(sigBuffer.array()) ;
    }

    public static String createEIP712Domain(String name, String version, Integer chainId) {
        byte[] domainSep = Hash.sha3(NftConstant.EIP712_DOMAIN_CONTENT.getBytes());
        // 构建 EIP 712 协议签名数据头
        return Hash.sha3(Numeric.toHexStringNoPrefix(domainSep) +
               Numeric.toHexString(Hash.sha3(name.getBytes())).substring(2) +
               Numeric.toHexString(Hash.sha3(version.getBytes())).substring(2) +
               Numeric.toHexStringNoPrefix(encodeInt(BigInteger.valueOf(chainId))) +
               addressToHexStr(NftConstant.CONTRACT_ADDR)).substring(2) ;
    }

    public static String createEIP712StructHash(String eip712Domain, String eip712Content) {
        return Numeric.toHexStringNoPrefix(Hash.sha3(
                Numeric.hexStringToByteArray(
                        "0x1901" + eip712Domain + eip712Content
                )
        )) ;
    }

    // 将地址字符串转换 补位
    private static String addressToHexStr(String srcAddr) {
        String newAddr = srcAddr ;
        if(srcAddr.startsWith("0x")) {
            newAddr = srcAddr.substring(2) ;
        }

        int zeroCount = 64 - newAddr.length() ;

        for(int i = 0; i < zeroCount; i++ ) {
            newAddr = "0" + newAddr ;
        }
        return newAddr.toLowerCase();
    }

    // 构造函数
    private static byte[] encodeInt(BigInteger src) {
        return Numeric.toBytesPadded(src, 32) ;
    }

    private static byte[] encodeIntArr(BigInteger []src) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream() ;
        for(int i = 0 ;i < src.length ; i ++) {
            outputStream.write(encodeInt(src[i]));
        }
        return Hash.sha3(outputStream.toByteArray()) ;
    }
}

