package com.anbang.qipai.raffle.msg.receiver.juresult;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.anbang.qipai.raffle.cqrs.c.service.JuPrizeCmdService;
import com.anbang.qipai.raffle.msg.channel.sink.juresult.DianpaoMajiangResultSink;
import com.anbang.qipai.raffle.msg.msjobs.CommonMO;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(DianpaoMajiangResultSink.class)
public class DianpaoMajiangResultMsgReceiver {

	@Autowired
	private JuPrizeCmdService juPrizeCmdService;

	private Gson gson = new Gson();

	Logger logger = LoggerFactory.getLogger(getClass());

	@StreamListener(DianpaoMajiangResultSink.DIANPAOMAJIANGRESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("dianpaomajiang ju result".equals(msg)) {
			Object dyjId = map.get("dayingjiaId");
			try {
				juPrizeCmdService.updateCalTimes((String)dyjId);
			} catch (Exception e) {
				logger.error("updateCalTimes----" + JSON.toJSONString(dyjId) + JSON.toJSONString(e));
			}

		}
	}
}
