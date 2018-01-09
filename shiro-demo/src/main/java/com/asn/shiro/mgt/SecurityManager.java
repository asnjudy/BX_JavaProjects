package com.asn.shiro.mgt;

import com.asn.shiro.subject.Subject;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.session.mgt.SessionManager;

import javax.naming.AuthenticationException;

/**
 * Created by xuwentang on 2017/9/5.
 */
public interface SecurityManager extends Authenticator, Authorizer, SessionManager {



    Subject login(Subject subject, AuthenticationToken authenticationToken) throws AuthenticationException;

    void logout(Subject subject);

    Subject createSubject(Subject context);
}
