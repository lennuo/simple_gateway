package com.xiaonuo.gateway.http;

import com.xiaonuo.gateway.domain.GateWay;
import com.xiaonuo.gateway.filter.IFilter;
import com.xiaonuo.gateway.filter.PreFilter;
import com.xiaonuo.gateway.filter.ResponseFilter;
import com.xiaonuo.gateway.filter.RoutingFilter;
import com.xiaonuo.gateway.init.GatewayAutoConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/*")
public class WebGlobalServlet extends HttpServlet {


    @Autowired
    private GatewayAutoConfig gatewayAutoConfig;

    @Autowired
    private PreFilter preFilter;

    @Autowired
    private RoutingFilter routingFilter;

    @Autowired
    private ResponseFilter responseFilter;

    private static Map<String, IFilter> map = new HashMap<>();

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        gatewayAutoConfig.init(req,resp);
        try{
            preFilter.run();
            routingFilter.run();
            responseFilter.run();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException("没有找到该路径");
        }
    }


}
