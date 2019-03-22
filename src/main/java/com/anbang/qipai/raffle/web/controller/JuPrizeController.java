package com.anbang.qipai.raffle.web.controller;

import com.alibaba.fastjson.JSON;
import com.anbang.qipai.raffle.cqrs.c.domain.juprize.JuPrizeResult;
import com.anbang.qipai.raffle.cqrs.c.service.JuPrizeCmdService;
import com.anbang.qipai.raffle.cqrs.c.service.MemberAuthService;
import com.anbang.qipai.raffle.cqrs.q.dao.JuPrizeReleaseDao;
import com.anbang.qipai.raffle.cqrs.q.dao.mongodb.MongodbJuPrizeReleaseDao;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrize;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeAccount;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRecord;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRelease;
import com.anbang.qipai.raffle.msg.service.MemberHongbaodianMsgService;
import com.anbang.qipai.raffle.msg.service.PrizeSendMsgService;
import com.anbang.qipai.raffle.plan.bean.Game;
import com.anbang.qipai.raffle.cqrs.q.service.JuPrizeBusinessService;
import com.anbang.qipai.raffle.util.CommonVOUtil;
import com.anbang.qipai.raffle.web.vo.CommonVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 */
@RestController
@RequestMapping("/juPrize")
public class JuPrizeController {

    @Autowired
    private JuPrizeCmdService juPrizeCmdService;

    @Autowired
    private JuPrizeBusinessService juPrizeBusinessService;

    @Autowired
    private PrizeSendMsgService prizeSendMsgService;

    @Autowired
    private MemberHongbaodianMsgService memberHongbaodianMsgService;

    @Autowired
    private MemberAuthService memberAuthService;

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 查询局奖励信息
     */
    @PostMapping("/queryJuPinzeInfo")
    public CommonVO queryJuPrizeInfo(String memberId) {
//        String memberId = memberAuthService.getMemberIdBySessionId(token);
//        if (memberId == null) {
//            return CommonVOUtil.invalidToken();
//        }

        Map data = new HashMap();
        JuPrizeAccount juPrizeAccount = juPrizeCmdService.getRewardTims(memberId).getJuPrizeAccount();
        juPrizeBusinessService.saveJuPrizeAccount(juPrizeAccount);
        data.put("juPrizeAccount", juPrizeAccount);
        return CommonVOUtil.success(data, "success");
    }


    /**
     * 对局抽奖
     */
    @PostMapping("/raffleJuPrize")
    public CommonVO raffleJuPrize(String memberId, Game game) {
//        String memberId = memberAuthService.getMemberIdBySessionId(token);
//        if (memberId == null) {
//            return CommonVOUtil.invalidToken();
//        }

        try {
            // 抽奖
            JuPrizeResult juPrizeResult = juPrizeCmdService.raffle(memberId, game);
            JuPrizeRecord juPrizeRecord = juPrizeBusinessService.saveRaffle(juPrizeResult);
            JuPrize juPrize = juPrizeResult.getJuPrize();

            // 发放奖励
            switch (juPrize.getPrizeType()) {
                case hongbaodian:
                    memberHongbaodianMsgService.giveHongbaodianToMember(memberId,juPrize.getSingleNum(),"对局抽奖");
                    break;
            }

        } catch (Exception e) {
            logger.error("raffleJuPrize-" + JSON.toJSONString(e) + memberId + game);
        }
        return CommonVOUtil.systemException();
    }


    /**
     * test
     */
    @Deprecated
    @PostMapping("/addCalTimes")
    public CommonVO addCalTimes(String memberId) {
//        String memberId = memberAuthService.getMemberIdBySessionId(token);
//        if (memberId == null) {
//            return CommonVOUtil.invalidToken();
//        }

        try {
            juPrizeCmdService.updateCalTimes(memberId);
        } catch (Exception e) {
            logger.error("raffleJuPrize-" + JSON.toJSONString(e) + memberId);
        }
        return CommonVOUtil.systemException();
    }
}
