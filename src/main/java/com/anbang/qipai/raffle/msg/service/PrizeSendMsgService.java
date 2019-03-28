package com.anbang.qipai.raffle.msg.service;

import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRecord;
import com.anbang.qipai.raffle.msg.channel.source.PrizeSendSource;
import com.anbang.qipai.raffle.msg.msjobs.CommonMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @Description: 对局抽奖奖品发送
 */
@EnableBinding(PrizeSendSource.class)
public class PrizeSendMsgService {
    @Autowired
    private PrizeSendSource prizeSendSource;

    public void juPrizeSend(JuPrizeRecord juPrizeRecord) {
        CommonMO mo = new CommonMO();
        mo.setMsg("JuPrizeRecord");
        mo.setData(juPrizeRecord);
        prizeSendSource.prizeSend().send(MessageBuilder.withPayload(mo).build());
    }
}
