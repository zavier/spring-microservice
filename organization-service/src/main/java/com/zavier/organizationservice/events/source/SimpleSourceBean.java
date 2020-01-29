package com.zavier.organizationservice.events.source;

import com.zavier.organizationservice.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleSourceBean {

    private Source source;

    public SimpleSourceBean(Source source) {
        this.source = source;
    }

    public void publishOrgChange(String action, String orgId) {
        log.debug("Sending Kafka message {} for OrganizationId:{}", action, orgId);
        OrganizationChangeModel change = new OrganizationChangeModel(OrganizationChangeModel.class.getTypeName(), action, orgId, UserContextHolder.getContext().getCorrelationId());
        // 使用source中的通道进行消息发送
        source.output().send(MessageBuilder.withPayload(change).build());
    }
}
