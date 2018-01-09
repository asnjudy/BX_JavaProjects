package com.asn.shiro.subject.support;

import com.asn.shiro.subject.WebSubjectContext;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * Created by xuwentang on 2017/8/31.
 */
public class DefaultWebSubjectContext extends DefaultSubjectContext implements WebSubjectContext {


    @Override
    public ServletRequest getServletRequest() {
        return null;
    }

    @Override
    public void setServletRequest(ServletRequest request) {

    }

    @Override
    public ServletRequest resolveServletRequest() {
        return null;
    }

    @Override
    public ServletResponse getServletResponse() {
        return null;
    }

    @Override
    public void setServletResponse(ServletResponse response) {

    }

    @Override
    public ServletResponse resolveServletResponse() {
        return null;
    }
}
