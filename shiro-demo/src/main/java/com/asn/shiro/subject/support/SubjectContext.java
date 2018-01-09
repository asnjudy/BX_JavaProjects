package com.asn.shiro.subject.support;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by xuwentang on 2017/8/31.
 *
 *
 */
public interface SubjectContext extends Map<String, Object> {

    SecurityManager getSecurityManager();
    void setSecurityManager(SecurityManager securityManager);
    SecurityManager resolveSecurityManager();

    Serializable getSessionId();
    void setSessionId(Serializable sessionId);


    Subject getSubject();
    void setSubject(Subject subject);

    PrincipalCollection getPrincipals();
    void setPrincipals(PrincipalCollection principals);
    PrincipalCollection resolvePrincipals();

    Session getSession();
    void setSession(Session session);
    Session resolveSession();

    boolean isAuthenticated();
    void setAuthenticated(boolean authc);
    boolean resolveAuthenticated();

    boolean isSessionCreationEnabled();
    void setSessionCreationEnable(boolean enabled);

    AuthenticationInfo getAuthenticationInfo();
    void setAuthenticationInfo(AuthenticationInfo info);

    AuthenticationToken getAuthenticationToken();
    void setAuthenticationToken(AuthenticationToken token);

    String getHost();
    void setHost(String host);
    String resolveHost();
}
