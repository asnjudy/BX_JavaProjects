package com.asn.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by xuwentang on 2017/8/9.
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {


    /**
     * 在验证账户前，校验验证码
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        HttpSession session = ((HttpServletRequest) request).getSession();
        // 页面输入的验证码
        String randomcode = request.getParameter("randomcode");
        // 从session中取出的验证码
        String validateCode = (String) session.getAttribute("validateCode");

        if (!randomcode.equals(validateCode)) {
            request.setAttribute("shiroLoginFailure", "randomCodeError");
            return true;
        }

        return super.onAccessDenied(request, response, mappedValue);
    }
}
