package com.asn.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xuwentang on 2017/8/9.
 */
@Controller
public class OperatingController {

    private static final Logger LOG = LoggerFactory.getLogger(OperatingController.class);


    @ResponseBody
    @RequestMapping(value = "/queryItem", method = RequestMethod.GET)
    @RequiresPermissions("item:query")
    public String query(Model model) {
        LOG.info("executing query with item:query permissions");
        return "{\"result\" : \"ok\"}";
    }
}
