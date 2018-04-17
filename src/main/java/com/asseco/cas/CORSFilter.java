package com.asseco.cas;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class CORSFilter extends GenericFilterBean implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin","*");
        httpResponse.setHeader("Access-Control-Allow-Methods","*");


        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
        /*httpResponse.setHeader("Access-Control-Allow-Credentials", "false");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");*/


        chain.doFilter(request, response);

    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}
