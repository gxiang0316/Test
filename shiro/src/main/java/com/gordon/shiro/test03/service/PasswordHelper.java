package com.gordon.shiro.test03.service;

import com.gordon.shiro.test03.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by gordon on 2018/9/11.
 */
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private String algorithmName = "md5";
    private final int hashIterations = 2;

    public void encryptPassword(User user){
        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                    algorithmName,// 解密类型
                    user.getPassword(),// 要加密的数据
                    ByteSource.Util.bytes(user.getCredentialSalt()),// 加密的盐
                    hashIterations // 对目标对象加密次数
                ).toHex();

        user.setPassword(newPassword);
    }



}
