package com.example.nftdemo.utils;

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
    // 身份认证器
    private static Credentials credentials =  Credentials.create("0475e34df2e6338ff522dc917c95488de31105d11a72080cc53790b17b79648b"); ;

    // 加密器名称
    private static String name = "MintManager" ;

    // 合约版本号
    private static String version = "v1.0.0" ;

    // chain Id
    private static Integer chainId = 1 ;

    // 合约地址
    //private static String contractAddr = "0x88E58b54b4Ec6B4Ef82c234905a61c1c87BD614e" ;
    private static String contractAddr = "0x270D376fD4f71041C847ED94781D8bcE4Ced44F0" ;

    // 签名申请钱包地址
    //private static String senderAddr = "0x27De63C5724aB7619404744A77c82A199D9bd18B" ;

    // domain hex string
    private static String eip712Domain = createEIP712Domain(name, version, chainId) ;

    /*public static void main(String[] args) throws IOException {

        log.info("身份认证器中的钱包地址信息 : {} ", credentials.getAddress());

        // 构造 EIP 712 域名头
        log.info("eip712Domain : {} ", eip712Domain);

        // 构造 EIP 712 方法内容
//        String eip712Content = createEIP712CheckMintContent(BigInteger.valueOf(2).pow(32), senderAddr, BigInteger.valueOf(2).pow(64)) ;
//        log.info("eip712Content : {} ", eip712Content);
//        String eip712Content = createEIP712CheckMintContent2(new BigInteger[]{ BigInteger.valueOf(1), BigInteger.valueOf(2)},
//                senderAddr, BigInteger.valueOf(2).pow(64), "Hello") ;
//        log.info("eip712Content : {} ", eip712Content);

//        // 铸造宇宙飞船NFT 构造签名结构体
//        String eip712Content = createEIP712CheckMintSpaceshipContent(new BigInteger[]{ BigInteger.valueOf(1), BigInteger.valueOf(2)},
//        senderAddr, BigInteger.valueOf(10000)) ;
//        log.info("eip712Content : {} ", eip712Content);

        // 铸造防御塔 构造签名结构体
//        String eip712Content = createEIP712CheckDefensiveFacilityContent(new BigInteger[]{ BigInteger.valueOf(1), BigInteger.valueOf(2)},
//                senderAddr, BigInteger.valueOf(10000)) ;
//        log.info("eip712Content : {} ", eip712Content);

        // 铸造英雄 构造签名结构体
        String eip712Content = createEIP712CheckHeroContent(new BigInteger[]{ BigInteger.valueOf(1), BigInteger.valueOf(2)},
                senderAddr, BigInteger.valueOf(10000)) ;
        log.info("eip712Content : {} ", eip712Content);

        String signature = signature(eip712Content) ;
        log.info("数据签名后的数据信息 : {} ", signature);
    }*/

    public static String createEIP712CheckHeroContent(BigInteger[] tokenIds, String senderAddr, BigInteger deadline) throws IOException {
        return  Hash.sha3(Numeric.toHexStringNoPrefix(Hash.sha3("MintHero(uint256[] tokenIds,address owner,uint256 deadline)".getBytes())) +
                Numeric.toHexStringNoPrefix(encodeIntArr(tokenIds)) +
                addressToHexStr(senderAddr) +
                Numeric.toHexStringNoPrefix(encodeInt(deadline))).substring(2) ;
    }

    public static String createEIP712CheckDefensiveFacilityContent(BigInteger[] tokenIds, String senderAddr, BigInteger deadline) throws IOException {
        return  Hash.sha3(Numeric.toHexStringNoPrefix(Hash.sha3("MintDefensiveFacility(uint256[] tokenIds,address owner,uint256 deadline)".getBytes())) +
                Numeric.toHexStringNoPrefix(encodeIntArr(tokenIds)) +
                addressToHexStr(senderAddr) +
                Numeric.toHexStringNoPrefix(encodeInt(deadline))).substring(2) ;
    }

    public static String createEIP712CheckMintSpaceshipContent(BigInteger[] tokenIds, String senderAddr, BigInteger deadline) throws IOException {
        return  Hash.sha3(Numeric.toHexStringNoPrefix(Hash.sha3("MintSpaceship(uint256[] tokenIds,address owner,uint256 deadline)".getBytes())) +
                Numeric.toHexStringNoPrefix(encodeIntArr(tokenIds)) +
                addressToHexStr(senderAddr) +
                Numeric.toHexStringNoPrefix(encodeInt(deadline))).substring(2) ;
    }

    public static String createEIP712CheckMintContent(BigInteger tokenId, String senderAddr, BigInteger deadline) {
        return  Hash.sha3(Numeric.toHexStringNoPrefix(Hash.sha3("checkMintSign(uint256 tokenId,address owner,uint256 deadline)".getBytes())) +
                Numeric.toHexStringNoPrefix(encodeInt(tokenId)) +
                addressToHexStr(senderAddr) +
                Numeric.toHexStringNoPrefix(encodeInt(deadline))).substring(2) ;
    }

    public static String createEIP712CheckMintContent2(BigInteger[] tokenId, String senderAddr, BigInteger deadline, String message) throws IOException {
        return  Hash.sha3( Numeric.toHexStringNoPrefix(Hash.sha3("checkMintSign(uint256[] tokenId,address owner,uint256 deadline,string memo)".getBytes())) +
                Numeric.toHexStringNoPrefix(encodeIntArr(tokenId)) +
                addressToHexStr(senderAddr) +
                Numeric.toHexStringNoPrefix(encodeInt(deadline)) +
                Numeric.toHexStringNoPrefix(Hash.sha3(message.getBytes()))).substring(2) ;
    }

    public static String signature(String eip712Content) {
        // 构造完整 EIP712 Hash
        String eip712StructHash = createEIP712StructHash(eip712Domain, eip712Content) ;
        log.info("eip712StructHash : {} ", eip712StructHash);

        // 使用私钥签名 数据
        Sign.SignatureData signatureData = Sign.signMessage(Numeric.hexStringToByteArray(eip712StructHash), credentials.getEcKeyPair(), false);

        // 转换签名字符串
        ByteBuffer sigBuffer = ByteBuffer.allocate(signatureData.getR().length + signatureData.getS().length + 1);
        sigBuffer.put(signatureData.getR());
        sigBuffer.put(signatureData.getS());
        sigBuffer.put(signatureData.getV());
        return Numeric.toHexString(sigBuffer.array()) ;
    }

    public static String createEIP712Domain(String name, String version, Integer chainId) {
        byte[] domainSep = Hash.sha3("EIP712Domain(string name,string version,uint256 chainId,address verifyingContract)".getBytes());
        // 构建 EIP 712 协议签名数据头
        return Hash.sha3(Numeric.toHexStringNoPrefix(domainSep) +
               Numeric.toHexString(Hash.sha3(name.getBytes())).substring(2) +
               Numeric.toHexString(Hash.sha3(version.getBytes())).substring(2) +
               Numeric.toHexStringNoPrefix(encodeInt(BigInteger.valueOf(chainId))) +
               addressToHexStr(contractAddr)).substring(2) ;
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

