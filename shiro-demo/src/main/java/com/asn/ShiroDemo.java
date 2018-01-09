package com.asn;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.util.Arrays;


/**
 * Created by xuwentang on 2017/8/8.
 */
public class ShiroDemo {


    public static void testMD5Salt() {
        //md5加密，不加盐
        String password_md5 = new Md5Hash("111111").toString();
        System.out.println("md5加密，不加盐="+password_md5);

        //md5加密，加盐，一次散列
        String password_md5_sale_1 = new Md5Hash("111111", "eteokues", 1).toString();
        System.out.println("password_md5_sale_1="+password_md5_sale_1);
        String password_md5_sale_2 = new Md5Hash("111111", "uiwueylm", 1).toString();
        System.out.println("password_md5_sale_2="+password_md5_sale_2);
        //两次散列相当于md5(md5())

        //使用SimpleHash
        String simpleHash = new SimpleHash("MD5", "111111", "eteokues",1).toString();
        System.out.println(simpleHash);
    }


    // 加载INI配置文件，获取subject实例
    private static Subject getSubject(String iniResourcePath) {
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(iniResourcePath);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        return subject;
    }

    public static void testLoginLogout(String iniResourcePath, String username, String password) {

        Subject subject = getSubject(iniResourcePath);

        Session session = subject.getSession(true);
        System.out.println("sessionId: " + session.getId().toString());

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token); //登录，执行身份认证
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
        }

        Boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("Authentication status is " + isAuthenticated);
        subject.logout();
        isAuthenticated = subject.isAuthenticated();
        System.out.println("Authentication status is " + isAuthenticated);
    }

    public static void testPermission(String iniResourcePath, String username, String password) {
        Subject subject = getSubject(iniResourcePath);

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token); //登录，执行身份认证
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
        }

        Boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("用户认证状态： " + isAuthenticated);

        /**
         * 基于角色授权，是否用于某一、或多个角色
         */
        System.out.println("用户是否拥有角色role1: " + subject.hasRole("role1"));
        System.out.println("用户是否拥有角色role1和role2: " + subject.hasRoles(Arrays.asList("role1", "role2")));

        subject.checkRole("role1");

        System.out.println("是否拥有权限user:delete: " + subject.isPermitted("user:delete"));
        System.out.println("是否拥有权限 user:create:1 和 user:delete： " + subject.isPermittedAll("user:create:1", "user:delete"));

        subject.checkPermission("sys:user:delete");
        subject.checkPermissions("user:create:1", "user:delete");
    }

    public static void testPermission2(String iniResourcePath, String username, String password) {
        Subject subject = getSubject(iniResourcePath);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token); //登录，执行身份认证
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
        }

        Boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("用户认证状态： " + isAuthenticated);


        System.out.println("是否拥有权限user:delete: " + subject.isPermitted("user:delete"));
        System.out.println("是否拥有权限 user:create:1 和 user:delete： " + subject.isPermittedAll("user:create:1", "user:delete"));

        subject.checkPermission("sys:user:delete");
        subject.checkPermissions("user:create:1", "user:delete");
    }




    public static void main(String[] args) {
        System.out.println("你好！");
        //testMD5Salt();

        String password_md5_sale_2 = new Md5Hash("admin", "qwer", 1).toString();
        System.out.println(password_md5_sale_2);
        // testLoginLogout("classpath:shiro-customRealm.ini", "zhang", "123");
        // testLoginLogout("classpath:shiro-cryptography.ini", "zhang", "111111");
        // testPermission("classpath:shiro-permission.ini", "zhang", "123");
        testPermission2("classpath:shiro-permission2.ini", "zhang", "123");
    }

}
