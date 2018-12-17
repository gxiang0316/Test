package com.gordon.shiro.test03.credentials;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 扩展密码验证
 * Created by gordon on 2018/9/12.
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher{

    private Ehcache passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(){
//        CacheManager cacheManager = CacheManager.newInstance(
//                CacheManager.class.getClassLoader().getResource("test02/ehcache.xml"));
//
//        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    /**
     * 扩展密码验证
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        Element element = passwordRetryCache.get(username);
        if(element == null){
            element = new Element(username,new AtomicInteger(0));
            passwordRetryCache.put(element);
        }
        AtomicInteger retryCount = (AtomicInteger) element.getObjectValue();
        int count = retryCount.incrementAndGet();
        if(count > 5){
            // 密码输错5次了
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token,info);

        if(matches){
            passwordRetryCache.remove(username);
        }

        return matches;
    }
}
