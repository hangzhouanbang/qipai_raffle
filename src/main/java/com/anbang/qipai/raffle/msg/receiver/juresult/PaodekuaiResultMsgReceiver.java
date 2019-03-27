package com.anbang.qipai.raffle.msg.receiver.juresult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anbang.qipai.raffle.cqrs.c.service.JuPrizeCmdService;
import com.anbang.qipai.raffle.msg.channel.sink.juresult.PaodekuaiResultSink;
import com.anbang.qipai.raffle.msg.msjobs.CommonMO;
import com.anbang.qipai.raffle.msg.msjobs.panpalyer.PaodekuaiPanPlayerResultMO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.google.gson.Gson;

@EnableBinding(PaodekuaiResultSink.class)
public class PaodekuaiResultMsgReceiver {

	@Autowired
	private JuPrizeCmdService juPrizeCmdService;

	private Gson gson = new Gson();

	Logger logger = LoggerFactory.getLogger(getClass());

	@StreamListener(PaodekuaiResultSink.PAODEKUAIRESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		if ("paodekuai pan result".equals(msg)) {
			try {
				JSONObject data = JSON.parseObject(json);
				String playerResultJson = JSON.toJSONString(data.get("playerResultList"));
				List<PaodekuaiPanPlayerResultMO> playerResultList = JSON.parseArray(playerResultJson, PaodekuaiPanPlayerResultMO.class);
				for (PaodekuaiPanPlayerResultMO list : playerResultList) {
					if (list.isYing()) {
						juPrizeCmdService.updateCalTimes(list.getPlayerId());
					}
				}
			} catch (Exception e) {
				logger.error("updateCalTimes----" + JSON.toJSONString(json) + JSON.toJSONString(e));
			}
		}

	}
}
