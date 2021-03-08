package com.xiaonuo.gateway.filter;

import com.xiaonuo.gateway.domain.GateWay;
import com.xiaonuo.gateway.domain.Routers;
import com.xiaonuo.gateway.http.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

@Component
public class PreFilter extends IFilter {

    private static final String LOCAL_URI = "http://127.0.0.1:8080/";

    private ThreadLocal threadLocal = new ThreadLocal();

    @Autowired
    private GateWay gateway;


    @Override
    public void run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest servletRequest = ctx.getRequest();
        String targetURL = matchUri(getUri(servletRequest));
        if(null==targetURL){
            throw new RuntimeException("路径不存在");
        }
        RequestEntity<byte[]> requestEntity = null;
        try{
            requestEntity = createRequestEntity(servletRequest, targetURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ctx.setRequestEntity(requestEntity);
    }


    private RequestEntity createRequestEntity(HttpServletRequest request, String url) throws URISyntaxException, IOException {
        String method = request.getMethod();
        HttpMethod httpMethod = HttpMethod.resolve(method);        //1、封装请求头
        MultiValueMap<String, String> headers =createRequestHeaders(request);        //2、封装请求体
        byte[] body = createRequestBody(request);        //3、构造出RestTemplate能识别的RequestEntity
        RequestEntity requestEntity = new RequestEntity<byte[]>(body,headers,httpMethod, new URI(url));        return requestEntity;
    }

    private byte[] createRequestBody(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        return StreamUtils.copyToByteArray(inputStream);
    }

    private MultiValueMap<String, String> createRequestHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        List<String> headerNames = Collections.list(request.getHeaderNames());

        for(String headerName:headerNames) {
            List<String> headerValues = Collections.list(request.getHeaders(headerName));

            for(String headerValue:headerValues) {
                headers.add(headerName, headerValue);
            }
        }
        return headers;
    }


    public String matchUri(String uri){
        for(Routers routers : gateway.getRouters()) {
            String predicates = routers.getPredicates();
            if(uri.indexOf(predicates)!=-1){
                return routers.getUri() + uri;
            }
        }
        return null;
    }
    public String getUri(HttpServletRequest req){
        String requestURI = req.getRequestURI();
        // todo 这边有点水
        return requestURI;
    }


}
