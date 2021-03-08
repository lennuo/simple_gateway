package com.xiaonuo.gateway.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gateway")
public class GateWay {
    private Routers []routers;

    public Routers[] getRouters() {
        return routers;
    }

    public void setRouters(Routers[] routers) {
        this.routers = routers;
    }
}
