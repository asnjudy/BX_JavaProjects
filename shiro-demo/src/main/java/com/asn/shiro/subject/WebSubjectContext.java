package com.asn.shiro.subject;

import com.asn.shiro.subject.support.SubjectContext;
import com.asn.shiro.util.RequestPairSource;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by xuwentang on 2017/8/31.
 */
public interface WebSubjectContext extends SubjectContext, RequestPairSource {

    ServletRequest getServletRequest();
    void setServletRequest(ServletRequest request);
    ServletRequest resolveServletRequest();

    ServletResponse getServletResponse();
    void setServletResponse(ServletResponse response);
    ServletResponse resolveServletResponse();

}
