package com.xiaonuo.gateway.init;

import com.xiaonuo.gateway.domain.GateWay;
import com.xiaonuo.gateway.filter.IFilter;
import com.xiaonuo.gateway.filter.PreFilter;
import com.xiaonuo.gateway.filter.RoutingFilter;
import com.xiaonuo.gateway.http.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GatewayAutoConfig{

    public void init(HttpServletRequest req, HttpServletResponse resp) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setRequest(req);
        ctx.setResponse(resp);
    }


}
