/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author jake
 */
public class UserRoleFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        boolean loggedInOrNot = false;
        if(req.getSession().getAttribute("loggedIn") != null){
            loggedInOrNot = (boolean) req.getSession().getAttribute("loggedIn");
        }
        if(loggedInOrNot == false){
            HttpServletResponse r = (HttpServletResponse) response;
            r.sendRedirect(req.getContextPath());
            return;
        } 
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
    
}
