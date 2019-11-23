package org.cent.dbbasedemo.controller;

import org.cent.dbbasedemo.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vincent
 * @version 1.0 2019/11/23
 */
@RestController
public class TestController {

    @Autowired
    private CounterService counterService;

    @GetMapping("/oid")
    String getOrderId() {
        return counterService.getId();
    }
}
