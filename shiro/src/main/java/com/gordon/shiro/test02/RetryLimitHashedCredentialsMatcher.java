package com.gordon.shiro.test02;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试登录，密码输错5次锁住账号
 * Created by gordon on 2018/9/10.
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Ehcache passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher() {
//        CacheManager cacheManager = CacheManager.newInstance(CacheManager.class.getClassLoader()
//                .getResource("test02/ehcache.xml"));
//        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        // retry count + 1
        Element element = passwordRetryCache.get(username);
        if(element == null){
            element = new Element(username,new AtomicInteger(0));
            passwordRetryCache.put(element);
        }

        AtomicInteger retryCount = (AtomicInteger) element.getObjectValue();
        int count = retryCount.incrementAndGet();
        System.out.println("count : " + count);
        if(count >= 5){
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token,info);
        System.out.println("matches : " + matches);
        if(matches){
            // clear retry count
            passwordRetryCache.remove(username);
        }

        return matches;
    }
}
