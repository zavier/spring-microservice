package com.zavier.zuulserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class TrackingFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    private final FilterUtils filterUtils;

    public TrackingFilter(FilterUtils filterUtils) {
        this.filterUtils = filterUtils;
    }

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private boolean isCorrelationIdPresent() {
        if (filterUtils.getCorrelationId() != null) {
            return true;
        }
        return false;
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Object run() throws ZuulException {
       if (isCorrelationIdPresent()) {
           log.debug("tmx-correlation-id found in tracking filter:{}.", filterUtils.getCorrelationId());
       } else {
           filterUtils.setCorrelationId(generateCorrelationId());
           log.debug("tmx-correlation-id generated in tracking filter:{}.", filterUtils.getCorrelationId());
       }
        RequestContext ctx = RequestContext.getCurrentContext();
       log.debug("Processing incoming request for {}.", ctx.getRequest().getRequestURI());
       return null;
    }
}
