package com.zhenshu.parent.common.constant.exception.utils;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author jing
 * @version 1.0
 * @desc
 * @date 2020/11/20 0020 14:29
 **/
@Component
public class LedgerReportFilter implements Filter {

    private static final String FILE_HEADER = "multipart/form-data";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String contentType = request.getContentType();
        if (contentType != null && !contentType.contains(FILE_HEADER)) {
            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
            chain.doFilter(requestWrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }


}
