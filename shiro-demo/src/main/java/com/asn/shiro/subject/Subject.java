package com.asn.shiro.subject;

import com.asn.shiro.subject.support.DefaultSubjectContext;
import com.asn.shiro.subject.support.SubjectContext;
import com.asn.shiro.mgt.SecurityManager;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.Permission;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import com.asn.shiro.util.SecurityUtils;
import org.apache.shiro.util.ThreadContext;

/**
 * Created by xuwentang on 2017/9/5.
 */
public interface Subject {


    Object getPrincipal();
    PrincipalCollection getPrincipals();

    boolean isPermitted(String permission);
    boolean isPermitted(Permission permission);
    boolean[] isPermitted(String... permissions);
    boolean[] isPermitted(List<Permission> permissions);
    boolean isPermittedAll(String... permissions);
    boolean isPermittedAll(Collection<Permission> permissions);

    void checkPermission(String permission) throws AuthorizationException;
    void checkPermission(Permission permission) throws AuthorizationException;
    void checkPermission(String... permissions) throws AuthorizationException;
    void checkPermission(Collection<Permission> permissions) throws AuthorizationException;


    boolean hasRole(String roleIdentifier);
    boolean[] hasRoles(List<String> roleIdentifiers);
    boolean hasAllRoles(Collection<String> roleIdentifiers);
    void checkRole(String roleIdentifier) throws AuthorizationException;
    void checkRoles(Collection<String> roleIdentifiers) throws AuthorizationException;
    void checkRoles(String... roleIdentifiers) throws AuthorizationException;


    void login(AuthenticationToken token) throws AuthenticationException;

    boolean isAuthenticated();
    boolean isRemembered();

    Session getSession();
    Session getSession(boolean create);

    void logout();

    /**
     * 传入一个Callable，并在当前线程中执行它，能够获得该Subject实例的内容
     * @param callable
     * @param <V>
     * @return
     * @throws ExecutionException
     */
    <V> V execute(Callable<V> callable) throws ExecutionException;
    void execute(Runnable runnable);


    /**
     * 返回一个匹配给定参数的Callable实例，同时能额外确保该Callable实例将保留该主体身份，并在该主体身份下执行
     * 返回的Callable对象可以与ExecutorService一起使用，以该主体执行
     * @param callable
     * @param <V>
     * @return
     */
    <V> Callable<V> associateWith(Callable<V> callable);

    /**
     * 返回的Callable实例，可以与java.util.concurrent.Executor或另一个线程一起使用，以该主体执行
     * 这将高效地确保，在任何执行返回Callable实例的线程上，对SecurityUtils.getSubject()的任何调用和相关功能都将运行的很好
     * 注意：
     *      如果你需要一个返回值作为runnable执行的结果、或者你需要对异常进行响应，那么高度推荐您使用associateWith(callable)方法
     * @param runnable
     * @return
     */
    Runnable associateWith(Runnable runnable);


    void runAs(PrincipalCollection principals) throws NullPointerException, IllegalStateException;
    boolean isRunAs();
    PrincipalCollection getPreviousPrincipals();
    PrincipalCollection releaseRunAs();


    /**
     * 采用建造者设计模式实现，用于创建Subject实例，以一种简单的方式，而不需要Shiro的构建技术
     */
    public static class Builder {

        private final SubjectContext subjectContext;

        private final SecurityManager securityManager;

        public Builder() {
            this(SecurityUtils.getSecurityManager());
        }

        public Builder(SecurityManager securityManager) {
            if (securityManager == null) {
                throw new NullPointerException("SecurityManager method argument cannont be null.");
            }
            this.securityManager = securityManager;
            this.subjectContext = newSubjectContextInstance();
            if (this.subjectContext == null) {
                throw new IllegalStateException("Subject instance returned from 'newSubjectContextInstance' cannot be null.");
            }
            this.subjectContext.setSecurityManager(securityManager);
        }

        protected SubjectContext newSubjectContextInstance() {
            return new DefaultSubjectContext();
        }

        public Subject buildSubject() {
            return this.securityManager.createSubject(this.subjectContext);
        }
    }

}
