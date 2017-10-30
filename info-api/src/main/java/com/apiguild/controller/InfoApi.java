
package com.apiguild.controller;

import com.apiguild.model.UserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bingwang on 10/30/17.
 */
@RestController
public class InfoApi {
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("idolice");
        userInfo.setAge(18);
        return userInfo;
    }
}