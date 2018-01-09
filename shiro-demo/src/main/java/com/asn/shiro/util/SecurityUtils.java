package com.asn.shiro.util;


import com.asn.shiro.subject.Subject;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.util.ThreadContext;

/**
 * Created by xuwentang on 2017/9/5.
 */
public abstract class SecurityUtils {


    private static SecurityManager securityManager;

    public static Subject getSubject() {
        Subject subject = (Subject) ThreadContext.getSubject();
        if (subject == null) {
            subject = new Subject.Builder().buildSubject();
        }
        return subject;
    }


    public static SecurityManager getSecurityManager() throws UnavailableSecurityManagerException {
        SecurityManager securityManager = (SecurityManager) ThreadContext.getSecurityManager();
        if (securityManager == null) {
            securityManager = SecurityUtils.securityManager;
        }
        if (securityManager == null) {
            String msg = "No SecurityManager accessible to the calling code, either bound to the "
                    + ThreadContext.class.getName() + " or as a vm static singleton."
                    + " This is an invalid application configuration.";
            //throw new UnavaliableSecurityManagerException(msg);
        }
        return securityManager;
    }
}
