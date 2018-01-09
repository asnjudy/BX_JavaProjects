package com.asn.shiro.util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by xuwentang on 2017/8/31.
 */

/**
 * 一个RequestPairSource是一个组件，能够提供与当前正在执行请求关联的ServletRequest和ServletResponse对。
 * 用于框架开发支持，很少被终端用户使用
 */
public interface RequestPairSource {

    ServletRequest getServletRequest();

    ServletResponse getServletResponse();
}
