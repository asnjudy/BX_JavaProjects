package com.asn.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuwentang on 2017/8/8.
 */
public class CustomRealm1 extends AuthorizingRealm {

    private static final Logger LOG = LoggerFactory.getLogger(CustomRealm1.class);

    @Override
    public String getName() {
        return "customRealm1";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = (String) principals.getPrimaryPrincipal();
        List<String> permissions = new ArrayList<>();

        /**
         * 根据身份信息从数据库中查询权限数据
         * 。。。这里使用静态数据模型
         */
        permissions.add("user:create");
        permissions.add("user:delete");

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (String permission : permissions) {
            simpleAuthorizationInfo.addStringPermission(permission);
        }
        return simpleAuthorizationInfo;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        // 从token中获取用户身份信息
        String username = (String) token.getPrincipal();
        LOG.info("CustomRealm1.doGetAuthenticationInfo........");


        if (!username.equals("zhang")) {
            return null;
        }

        String password = "cb571f7bd7a6f73ab004a70322b963d5";
        String salt = "eteokues";

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                username, password, ByteSource.Util.bytes(salt), getName()
        );
        return simpleAuthenticationInfo;
    }
}
