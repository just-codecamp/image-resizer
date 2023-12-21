package gg.scode.imageresizeservice.config.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
//@Order(1)
//@WebFilter
//@Component
//@RequiredArgsConstructor
public class CacheFilter implements Filter {

    private final String ETAG = "etag";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String ifNoneMatch = httpServletRequest.getHeader("If-None-Match");

        log.info("if NOne Match = {}", ifNoneMatch);

        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = httpServletRequest.getHeader(headerName);
            log.info("{} : {}",headerName, headerValue);
        }

        chain.doFilter(request,response);
    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request
//            , HttpServletResponse response
//            , FilterChain filterChain
//    ) throws ServletException, IOException {
//
//        String header = request.getHeader("ETag");
//        log.info("header = {}", header);
//
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            String headerValue = request.getHeader(headerName);
//            log.info("{} : {}",headerName, headerValue);
//        }
//
//        filterChain.doFilter(request,response);
//    }

}