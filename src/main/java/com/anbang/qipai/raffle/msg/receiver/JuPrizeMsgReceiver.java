package com.anbang.qipai.raffle.msg.receiver;

import com.alibaba.fastjson.JSON;
import com.anbang.qipai.raffle.cqrs.c.service.JuPrizeCmdService;
import com.anbang.qipai.raffle.cqrs.q.dao.JuPrizeReleaseDao;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRelease;
import com.anbang.qipai.raffle.msg.channel.sink.JuPrizeSink;
import com.anbang.qipai.raffle.msg.msjobs.CommonMO;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 */
@EnableBinding(JuPrizeSink.class)
public class JuPrizeMsgReceiver {

    @Autowired
    private JuPrizeCmdService juPrizeCmdService;

    @Autowired
    private JuPrizeReleaseDao juPrizeReleaseDao;

    private Gson gson = new Gson();

    Logger logger = LoggerFactory.getLogger(getClass());

    @StreamListener(JuPrizeSink.JUPRIZE)
    public void juPrizeRelease(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());


        if ("juPrizeRelease".equals(msg)) {
            try {
                List<JuPrizeRelease> juPrizeReleases = JSON.parseArray(json, JuPrizeRelease.class);
                juPrizeCmdService.release(new ArrayList(juPrizeReleases));

                for (JuPrizeRelease list : juPrizeReleases) {
                    juPrizeReleaseDao.save(list);
                }
            } catch (Exception e) {
                logger.error("juPrizeRelease---发布新局奖励设置---" + json + "-" + JSON.toJSONString(e));
            }
        }
    }
}
