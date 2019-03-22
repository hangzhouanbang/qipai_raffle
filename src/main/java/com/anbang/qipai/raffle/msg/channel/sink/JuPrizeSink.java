package com.anbang.qipai.raffle.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface JuPrizeSink {

    String JUPRIZE = "juPrize";

    @Input
    SubscribableChannel juPrize();
}
