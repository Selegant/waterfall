package org.jeecg.modules.workflow.controller;

import org.jeecg.modules.workflow.service.DsLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("workflow")
@RestController
public class DsLoginController {

    @Autowired
    DsLoginService dsLoginService;

    @GetMapping("login")
    public String login(){
        return dsLoginService.login();
    }
}
