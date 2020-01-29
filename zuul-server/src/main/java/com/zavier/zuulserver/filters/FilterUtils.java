package com.zavier.zuulserver.filters;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class FilterUtils {
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";

    public static final String CORRELATION_ID = "tmx-correlation-id";

    public String getCorrelationId() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getHeader(CORRELATION_ID) != null) {
            return ctx.getRequest().getHeader(CORRELATION_ID);
        } else {
            return ctx.getZuulRequestHeaders().get(CORRELATION_ID);
        }
    }

    public void setCorrelationId(String correlationId) {
        final RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(CORRELATION_ID, correlationId);
    }
}
