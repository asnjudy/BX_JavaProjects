package com.asn.shiro.subject.support;

import com.asn.shiro.mgt.MyMapContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.Serializable;

/**
 * Created by xuwentang on 2017/8/31.
 */
public class DefaultSubjectContext extends MyMapContext implements SubjectContext {


    // com.asn.shiro.subject.support.DefaultSubjectContext.DefaultSubjectContext
    private static final String SECURITY_MANAGER = DefaultSubjectContext.class.getName() + ".DefaultSubjectContext"; // default_subject_context
    private static final String SESSION_ID = DefaultSubjectContext.class.getName() + ".SESSION_ID";
    private static final String AUTHENTICATION_TOKEN = DefaultSubjectContext.class.getName() + ".AUTHENTICATION_TOKEN"; // authentication_token
    private static final String AUTHENTICATION_INFO = DefaultSubjectContext.class.getName() + ".AUTHENTICATION_INFO"; // authentication_info
    private static final String SUBJECT = DefaultSubjectContext.class.getName() + ".SUBJECT";
    private static final String PRINCIPALS = DefaultSubjectContext.class.getName() + ".PRINCIPALS"; // principals
    private static final String SESSION = DefaultSubjectContext.class.getName() + ".SESSION";
    private static final String AUTHENTICATED = DefaultSubjectContext.class.getName() + ".AUTHENTICATED"; // authenticated
    private static final String HOST = DefaultSubjectContext.class.getName() + ".HOST";
    private static final String SESSION_CREATION_ENABLED = DefaultSubjectContext.class.getName() + ".SESSION_CREATION_ENABLED"; // session_creation_enabled
    private static final String PRINCIPALS_SESSION_KEY = DefaultSubjectContext.class.getName() + ".PRINCIPALS_SESSION_KEY"; // principals_session_key
    private static final String AUTHENTICATED_SESSION_KEY = DefaultSubjectContext.class.getName() + ".AUTHENTICATED_SESSION_KEY"; // authenticated_session_key


    private static final Logger log = LoggerFactory.getLogger(DefaultSubjectContext.class);


    public DefaultSubjectContext() {
        super();
    }

    public DefaultSubjectContext(SubjectContext ctx) {
        super(ctx);
    }

    @Override
    public SecurityManager getSecurityManager() {
        return getTypedValue(SECURITY_MANAGER, SecurityManager.class);
    }

    @Override
    public void setSecurityManager(SecurityManager securityManager) {
        nullSafePut(SECURITY_MANAGER, securityManager);
    }


    @Override
    public SecurityManager resolveSecurityManager() {
        SecurityManager securityManager = getSecurityManager();

        if (securityManager == null) {

            if (log.isDebugEnabled()) {
                log.debug("No SecurityManager available in subject context map. " +
                    "Falling back to SecurityUtils.getSecurityManager() lookup.");
            }

            try {
                securityManager = (SecurityManager) SecurityUtils.getSecurityManager();
            } catch (UnavailableSecurityManagerException e) {
                log.debug("No SecurityManager available via SecurityUtils, Heuristics exhausted." , e);
            }
        }
        return securityManager;
    }

    @Override
    public Serializable getSessionId() {
        return getTypedValue(SESSION_ID, Serializable.class);
    }

    @Override
    public void setSessionId(Serializable sessionId) {
        nullSafePut(SESSION_ID, sessionId);
    }

    @Override
    public Subject getSubject() {
        return getTypedValue(SUBJECT, Subject.class);
    }

    @Override
    public void setSubject(Subject subject) {
        nullSafePut(SUBJECT, subject);
    }

    @Override
    public PrincipalCollection getPrincipals() {
        return getTypedValue(PRINCIPALS, PrincipalCollection.class);
    }
    @Override
    public void setPrincipals(PrincipalCollection principals) {
        if (!CollectionUtils.isEmpty(principals)) {
            put(PRINCIPALS, principals);
        }
    }

    @Override
    public PrincipalCollection resolvePrincipals() {
        PrincipalCollection principals = getPrincipals();

        if (CollectionUtils.isEmpty(principals)) {
            AuthenticationInfo info = getAuthenticationInfo();
            if (info != null) {
                principals = info.getPrincipals();
            }
        }

        if (CollectionUtils.isEmpty(principals)) {
            Subject subject = getSubject();
            if (subject != null) {
                principals = subject.getPrincipals();
            }
        }

        if (CollectionUtils.isEmpty(principals)) {
            Session session = resolveSession();
            if (session != null) {
                principals = (PrincipalCollection) session.getAttribute(PRINCIPALS_SESSION_KEY);
            }
        }

        return principals;
    }



    @Override
    public Session getSession() {
        return getTypedValue(SESSION, Session.class);
    }

    @Override
    public void setSession(Session session) {
        nullSafePut(SESSION, session);
    }

    @Override
    public Session resolveSession() {
        Session session = getSession();
        if (session == null) {
            Subject existingSubject = getSubject();
            if (existingSubject != null) {
                session = existingSubject.getSession(false);
            }
        }
        return session;
    }

    @Override
    public boolean isAuthenticated() {
        Boolean val = getTypedValue(SESSION_CREATION_ENABLED, Boolean.class);
        return val == null || val;
    }

    @Override
    public void setAuthenticated(boolean authc) {
        put(AUTHENTICATED, authc);
    }

    @Override
    public boolean isSessionCreationEnabled() {
        Boolean val = getTypedValue(SESSION_CREATION_ENABLED, Boolean.class);
        return val == null || val;
    }

    @Override
    public void setSessionCreationEnable(boolean enabled) {
        nullSafePut(SESSION_CREATION_ENABLED, enabled);
    }

    @Override
    public boolean resolveAuthenticated() {
        return false;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo() {
        return null;
    }

    @Override
    public void setAuthenticationInfo(AuthenticationInfo info) {

    }

    @Override
    public AuthenticationToken getAuthenticationToken() {
        return null;
    }

    @Override
    public void setAuthenticationToken(AuthenticationToken token) {

    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public void setHost(String host) {

    }

    @Override
    public String resolveHost() {
        return null;
    }
}
