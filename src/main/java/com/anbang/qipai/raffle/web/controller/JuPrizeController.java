package com.anbang.qipai.raffle.web.controller;

import com.alibaba.fastjson.JSON;
import com.anbang.qipai.raffle.cqrs.c.domain.juprize.JuPrizeResult;
import com.anbang.qipai.raffle.cqrs.c.domain.juprize.NoFindJuPrizeException;
import com.anbang.qipai.raffle.cqrs.c.service.JuPrizeCmdService;
import com.anbang.qipai.raffle.cqrs.c.service.MemberAuthService;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.*;
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

import java.util.HashMap;
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
    @RequestMapping("/queryJuPinzeInfo")
    public CommonVO queryJuPrizeInfo(String token, Game game) {
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            return CommonVOUtil.invalidToken();
        }

        JuPrizeRelease juPrizeRelease = juPrizeBusinessService.getJuPrizeReleaseById(game);
        if (juPrizeRelease == null) {
            return CommonVOUtil.success("not_release");
        }

        Map data = new HashMap();
        JuPrizeAccount juPrizeAccount = juPrizeCmdService.getRewardTims(memberId).getJuPrizeAccount();
        juPrizeBusinessService.saveJuPrizeAccount(juPrizeAccount);
        data.put("juPrizeAccount", juPrizeAccount);
        return CommonVOUtil.success(data, "success");
    }


    /**
     * 对局抽奖
     */
    @RequestMapping("/raffleJuPrize")
    public CommonVO raffleJuPrize(String token, Game game) {
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            return CommonVOUtil.invalidToken();
        }

        try {
            // 抽奖
            JuPrizeResult juPrizeResult = juPrizeCmdService.raffle(memberId, game);
            JuPrizeRecord juPrizeRecord = juPrizeBusinessService.saveRaffle(juPrizeResult);
            JuPrize juPrize = juPrizeResult.getJuPrize();

            // 发放奖励
            switch (juPrize.getPrizeType()) {
                case HONGBAODIAN:
                    memberHongbaodianMsgService.giveHongbaodianToMember(memberId,juPrize.getSingleNum(),"对局抽奖");
                    break;
            }

            // 抽奖记录
            prizeSendMsgService.juPrizeSend(juPrizeRecord);

            Map data = new HashMap();
            data.put("name",juPrize.getName());
            data.put("prizeType",juPrize.getPrizeType());
            data.put("iconUrl",juPrize.getIconUrl());
            data.put("singleNum",juPrize.getSingleNum());
            return CommonVOUtil.success(data, "success");
        } catch (NoFindJuPrizeException e){
            return CommonVOUtil.error(e.getClass().getName());
        } catch (Exception e) {
            logger.error("raffleJuPrize-" + JSON.toJSONString(e) + memberId + game);
        }
        return CommonVOUtil.systemException();
    }


    /**
     * test
     */
    @Deprecated
    @RequestMapping("/addCalTimes")
    public CommonVO addCalTimes(String token) {
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            return CommonVOUtil.invalidToken();
        }

        try {
            JuPrizeResult juPrizeResult = juPrizeCmdService.updateCalTimes(memberId);
            return CommonVOUtil.success(juPrizeResult, "success");
        } catch (Exception e) {
            logger.error("raffleJuPrize-" + JSON.toJSONString(e) + memberId);
        }
        return CommonVOUtil.systemException();
    }
}
