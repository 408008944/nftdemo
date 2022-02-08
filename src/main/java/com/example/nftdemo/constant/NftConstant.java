package com.example.nftdemo.constant;

import org.web3j.crypto.Credentials;

public class NftConstant {

    // 身份认证器
    public static final Credentials credentials =  Credentials.create("0475e34df2e6338ff522dc917c95488de31105d11a72080cc53790b17b79648b"); ;

    // 加密器名称
    public static final String NAME = "MintManager" ;

    // 合约版本号
    public static final String VERSION = "v1.0.0" ;

    // chain Id
    public static final Integer CHAINID = 1 ;

    // 合约地址
    public static final String CONTRACT_ADDR = "0xcfdee3b93394f1c94E921D162C3376C48C447Bcd" ;

    // 签名申请钱包地址
    //private static final String senderAddr = "0x27De63C5724aB7619404744A77c82A199D9bd18B" ;

    // 飞船
    public static final String MINT_CONTENT_SPACESHIP = "MintSpaceship(uint256[] tokenIds,address owner,uint256 deadline)";
    // 英雄
    public static final String MINT_CONTENT_HERO = "MintHero(uint256[] tokenIds,address owner,uint256 deadline)";
    // 防御塔
    public static final String MINT_CONTENT_DEFENSIVEFACILITY = "MintDefensiveFacility(uint256[] tokenIds,address owner,uint256 deadline)";

    // EIP712Domain
    public static final String EIP712_DOMAIN_CONTENT = "EIP712Domain(string name,string version,uint256 chainId,address verifyingContract)";

}
