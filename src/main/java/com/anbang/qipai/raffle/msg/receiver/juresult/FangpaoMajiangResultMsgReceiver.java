package com.anbang.qipai.raffle.msg.receiver.juresult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anbang.qipai.raffle.cqrs.c.service.JuPrizeCmdService;
import com.anbang.qipai.raffle.msg.channel.sink.juresult.FangpaoMajiangResultSink;
import com.anbang.qipai.raffle.msg.msjobs.CommonMO;
import com.anbang.qipai.raffle.msg.msjobs.panpalyer.FangpaoMajiangPanPlayerResultMO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.google.gson.Gson;

@EnableBinding(FangpaoMajiangResultSink.class)
public class FangpaoMajiangResultMsgReceiver {

	@Autowired
	private JuPrizeCmdService juPrizeCmdService;

	private Gson gson = new Gson();

	Logger logger = LoggerFactory.getLogger(getClass());

	@StreamListener(FangpaoMajiangResultSink.FANGPAOMAJIANGRESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		if ("fangpaomajiang pan result".equals(msg)) {
			try {
				JSONObject data = JSON.parseObject(json);

				long finishTime = data.getLong("finishTime");
				if (finishTime < 1554944836000L) {
					return;
				}

				String playerResultJson = JSON.toJSONString(data.get("playerResultList"));
				List<FangpaoMajiangPanPlayerResultMO> playerResultList = JSON.parseArray(playerResultJson, FangpaoMajiangPanPlayerResultMO.class);
				for (FangpaoMajiangPanPlayerResultMO list : playerResultList) {
					if (list.isHu()) {
						juPrizeCmdService.updateCalTimes(list.getPlayerId(), System.currentTimeMillis());
					}
				}
			} catch (Exception e) {
				logger.error("updateCalTimes----" + JSON.toJSONString(json) + JSON.toJSONString(e));
			}
		}
	}
}
