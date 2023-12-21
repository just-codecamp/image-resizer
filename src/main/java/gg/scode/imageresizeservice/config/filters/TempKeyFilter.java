package gg.scode.imageresizeservice.config.filters;

import gg.scode.imageresizeservice.utils.PrimitiveKeyUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Temporary deprecated by client request
 */
@Deprecated
//@Order(1)
//@WebFilter
//@Component
//@RequiredArgsConstructor
public class TempKeyFilter implements Filter {

//    private final PrimitiveKeyUtil keyUtil;
    private PrimitiveKeyUtil keyUtil;

    @Override
    public void doFilter(
            ServletRequest request
            , ServletResponse response
            , FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if ( keyUtil.verify(httpServletRequest.getHeader(keyUtil.getHeaderName()))) {
            chain.doFilter(request,response);
        } else {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpServletResponse.getWriter().write("Access Token is inaccurate. Please check your key again.");
        }

    }

}
