package com.anbang.qipai.raffle.msg.receiver.juresult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anbang.qipai.raffle.cqrs.c.service.JuPrizeCmdService;
import com.anbang.qipai.raffle.msg.channel.sink.juresult.ChayuanShuangkouResultSink;
import com.anbang.qipai.raffle.msg.msjobs.CommonMO;
import com.anbang.qipai.raffle.msg.msjobs.panpalyer.ChayuanShuangkouPanPlayerResultMO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.google.gson.Gson;

@EnableBinding(ChayuanShuangkouResultSink.class)
public class ChayuanShuangkouResultMsgReceiver {

	@Autowired
	private JuPrizeCmdService juPrizeCmdService;

	Logger logger = LoggerFactory.getLogger(getClass());

	private Gson gson = new Gson();

	@StreamListener(ChayuanShuangkouResultSink.CHAYUANSHUANGKOURESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		Map map = gson.fromJson(json, Map.class);
		if ("chayuanshuangkou pan result".equals(msg)) {
			try {
				JSONObject data = JSON.parseObject(json);

				long finishTime = data.getLong("finishTime");
				if (finishTime < 1555640563000L) {
					return;
				}

				String playerResultJson = JSON.toJSONString(data.get("playerResultList"));
				List<ChayuanShuangkouPanPlayerResultMO> playerResultList = JSON.parseArray(playerResultJson, ChayuanShuangkouPanPlayerResultMO.class);
				for (ChayuanShuangkouPanPlayerResultMO list : playerResultList) {
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
