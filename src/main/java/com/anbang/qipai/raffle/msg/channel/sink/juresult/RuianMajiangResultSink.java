package com.anbang.qipai.raffle.msg.channel.sink.juresult;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface RuianMajiangResultSink {
	String RUIANMAJIANGRESULT = "ruianMajiangResult";

	@Input
	SubscribableChannel ruianMajiangResult();
}
