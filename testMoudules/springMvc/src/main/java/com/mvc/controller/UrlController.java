package com.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * auth: shi yi
 * create date: 2018/9/28
 */
@RestController
@RequestMapping("url/")
public class UrlController {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Autowired
    private HttpServletRequest request;

    /**
     * 获取当前项目中的全部接口
     * */
    @RequestMapping(value = "getAllUrl")
    @ResponseBody
    public String getAllUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("URL").append("--").append("Class").append("--").append("Function").append('\n');

        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        int i=1;
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            sb.append(i+":").append(info.getPatternsCondition()).append("--");
            sb.append(method.getMethod().getDeclaringClass()).append("--");
            sb.append(method.getMethod().getName()).append('\n');
            i++;
        }

        System.out.println(sb);

        return sb.toString();

    }

    @RequestMapping("getAllUrl")
    @ResponseBody
    public Set<String> getAllUrl1(HttpServletRequest request) {
        Set<String> result = new HashSet<String>();
        WebApplicationContext wc = (WebApplicationContext) request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        RequestMappingHandlerMapping bean = wc.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
        for (RequestMappingInfo rmi : handlerMethods.keySet()) {
            PatternsRequestCondition pc = rmi.getPatternsCondition();
            Set<String> pSet = pc.getPatterns();
            result.addAll(pSet);
        }
        return result;
    }

    /*
     * 获取当前访问url
     * */
    @RequestMapping(value = "getUrl")
    @ResponseBody
    public String getUrl() {
        String url = "";
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String servletPath = request.getServletPath();

        url = scheme +"://" + serverName +":" +serverPort+servletPath;
        if (request.getQueryString() != null){
            url += "?" + request.getQueryString();
        }

        System.out.println(url);

        return url;
    }
}
