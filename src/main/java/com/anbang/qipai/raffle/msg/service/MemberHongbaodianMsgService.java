package com.anbang.qipai.raffle.msg.service;

import java.util.HashMap;
import java.util.Map;

import com.anbang.qipai.raffle.msg.channel.source.MemberHongbaodianSource;
import com.anbang.qipai.raffle.msg.msjobs.CommonMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(MemberHongbaodianSource.class)
public class MemberHongbaodianMsgService {
	@Autowired
	private MemberHongbaodianSource memberHongbaodianSource;

	public void giveHongbaodianToMember(String memberId, int amount, String textSummary) {
		CommonMO mo = new CommonMO();
		mo.setMsg("give_hongbaodian_to_member");
		Map data = new HashMap();
		data.put("memberId", memberId);
		data.put("amount", amount);
		data.put("textSummary", textSummary);
		mo.setData(data);
		memberHongbaodianSource.memberHongbaodianAccounting().send(MessageBuilder.withPayload(mo).build());
	}
}
