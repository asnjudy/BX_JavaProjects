package com.asn;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xuwentang on 2017/8/2.
 */
public class Quickstart {

    public static final Logger log = LoggerFactory.getLogger(Quickstart.class);

    public static void main(String[] args) {

        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject currentUser = SecurityUtils.getSubject();

        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");

        if (value.equals("aValue")) {
            log.info("Retrieved the correct value! [" + value + "]");
        }

        // login当前用户，检查用户的角色和权限
        if (currentUser.isAuthenticated()) {

        } else {
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa22");
            token.setRememberMe(true);
            try {

                // Login
                currentUser.login(token);
            } catch (IncorrectCredentialsException ex) {
                log.error("输入的用户密码不匹配: "+ ex.getMessage());
            }

        }

        // 打印当前用户的一些信息
        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

        if (currentUser.hasRole("schwartz")) {
            log.info("May the Schwartz be with you!");
        } else {
            log.info("Hello, mere mortal.");
        }

        if (currentUser.isPermitted("lightsaber:weild")) {
            log.info("You may use a lightsaber ring. Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'. Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        currentUser.logout();

        System.exit(0);



    }
}
