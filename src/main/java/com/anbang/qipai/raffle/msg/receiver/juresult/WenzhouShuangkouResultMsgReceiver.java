package com.anbang.qipai.raffle.msg.receiver.juresult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anbang.qipai.raffle.cqrs.c.service.JuPrizeCmdService;
import com.anbang.qipai.raffle.msg.channel.sink.juresult.WenzhouShuangkouResultSink;
import com.anbang.qipai.raffle.msg.msjobs.CommonMO;
import com.anbang.qipai.raffle.msg.msjobs.panpalyer.WenzhouMajiangPanPlayerResultMO;
import com.anbang.qipai.raffle.msg.msjobs.panpalyer.WenzhouShuangkouPanPlayerResultMO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.google.gson.Gson;

@EnableBinding(WenzhouShuangkouResultSink.class)
public class WenzhouShuangkouResultMsgReceiver {

	@Autowired
	private JuPrizeCmdService juPrizeCmdService;

	private Gson gson = new Gson();

	Logger logger = LoggerFactory.getLogger(getClass());

	@StreamListener(WenzhouShuangkouResultSink.WENZHOUSHUANGKOURESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		if ("wenzhoushuangkou pan result".equals(msg)) {
			try {
				JSONObject data = JSON.parseObject(json);

				long finishTime = data.getLong("finishTime");
				if (finishTime < 1553826334000L) {
					return;
				}

				String playerResultJson = JSON.toJSONString(data.get("playerResultList"));
				List<WenzhouShuangkouPanPlayerResultMO> playerResultList = JSON.parseArray(playerResultJson, WenzhouShuangkouPanPlayerResultMO.class);
				for (WenzhouShuangkouPanPlayerResultMO list : playerResultList) {
					if (list.getMingcifen().isYing()) {
						juPrizeCmdService.updateCalTimes(list.getPlayerId(), System.currentTimeMillis());
					}
				}
			} catch (Exception e) {
				logger.error("updateCalTimes----" + JSON.toJSONString(json) + JSON.toJSONString(e));
			}
		}

	}
}
