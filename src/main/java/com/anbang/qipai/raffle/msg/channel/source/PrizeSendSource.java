package com.anbang.qipai.raffle.msg.channel.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PrizeSendSource {
    @Output
    MessageChannel prizeSend();
}
