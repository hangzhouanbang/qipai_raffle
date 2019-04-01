package com.anbang.qipai.raffle.msg.receiver.juresult;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anbang.qipai.raffle.cqrs.c.service.JuPrizeCmdService;
import com.anbang.qipai.raffle.msg.channel.sink.juresult.DianpaoMajiangResultSink;
import com.anbang.qipai.raffle.msg.msjobs.CommonMO;
import com.anbang.qipai.raffle.msg.msjobs.panpalyer.DianpaoMajiangPanPlayerResultMO;
import com.anbang.qipai.raffle.plan.bean.PrizeCheck;
import com.anbang.qipai.raffle.plan.dao.PrizeCheckDao;
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

	@Autowired
	private PrizeCheckDao prizeCheckDao;

	private Gson gson = new Gson();

	Logger logger = LoggerFactory.getLogger(getClass());

	@StreamListener(DianpaoMajiangResultSink.DIANPAOMAJIANGRESULT)
	public void recordMajiangHistoricalResult(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		if ("dianpaomajiang pan result".equals(msg)) {
			try {
				JSONObject data = JSON.parseObject(json);

				long finishTime = data.getLong("finishTime");
				if (finishTime < 1553826334000L) {
					return;
				}

				// 防止重复消费
//				String gameId = data.getString("gameId");
//				PrizeCheck prizeCheck = prizeCheckDao.getPrizeCheck(gameId);
//				if (prizeCheck != null) {
//					return;
//				}
//				prizeCheck = new PrizeCheck();
//				prizeCheck.setId(gameId);
//				prizeCheckDao.save(prizeCheck);

				String playerResultJson = JSON.toJSONString(data.get("playerResultList"));
				List<DianpaoMajiangPanPlayerResultMO> playerResultList = JSON.parseArray(playerResultJson, DianpaoMajiangPanPlayerResultMO.class);
				for (DianpaoMajiangPanPlayerResultMO list : playerResultList) {
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
