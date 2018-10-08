package sctest.serverfeign.service.impl;

import org.springframework.stereotype.Component;
import sctest.serverfeign.service.SchedualServiceHi;

/**
 * auth: shi yi
 * create date: 2018/8/21
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry " + name;
    }
}
