package com.anbang.qipai.raffle.cqrs.c.service.disruptor;

import com.anbang.qipai.raffle.cqrs.c.domain.juprize.JuPrizeResult;
import com.anbang.qipai.raffle.cqrs.c.domain.juprize.NoFindJuPrizeException;
import com.anbang.qipai.raffle.cqrs.c.domain.juprize.NoRewardTimesException;
import com.anbang.qipai.raffle.cqrs.c.service.JuPrizeCmdService;
import com.anbang.qipai.raffle.cqrs.c.service.impl.JuPrizeCmdServiceImpl;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRelease;
import com.anbang.qipai.raffle.plan.bean.Game;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 */
@Component(value = "juPrizeCmdService")
public class DisruptorJuPrizeCmdService extends DisruptorCmdServiceBase implements JuPrizeCmdService {
    @Autowired
    private JuPrizeCmdServiceImpl juPrizeCmdService;

    @Override
    public void release(ArrayList<JuPrizeRelease> juPrizeReleases) {
        CommonCommand cmd = new CommonCommand(JuPrizeCmdServiceImpl.class.getName(), "release", juPrizeReleases);
        DeferredResult<String> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            juPrizeCmdService.release(cmd.getParameter());
            return "";
        });
    }

    @Override
    public JuPrizeResult raffle(String id, Game game, Long nowTime) throws NoFindJuPrizeException, NoRewardTimesException {
        CommonCommand cmd = new CommonCommand(JuPrizeCmdServiceImpl.class.getName(), "raffle", id, game, nowTime);
        DeferredResult<JuPrizeResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            JuPrizeResult juPrizeResult = juPrizeCmdService.raffle(cmd.getParameter(), cmd.getParameter(), cmd.getParameter());
            return juPrizeResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            if (e instanceof NoFindJuPrizeException) {
                throw (NoFindJuPrizeException) e;
            } else if (e instanceof NoRewardTimesException) {
                throw (NoRewardTimesException) e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public JuPrizeResult updateCalTimes(String id, Long nowTime) {
        CommonCommand cmd = new CommonCommand(JuPrizeCmdServiceImpl.class.getName(), "updateCalTimes", id, nowTime);
        DeferredResult<JuPrizeResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            JuPrizeResult juPrizeResult = juPrizeCmdService.updateCalTimes(cmd.getParameter(), cmd.getParameter());
            return juPrizeResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JuPrizeResult getRewardTims(String id, Long nowTime) {
        CommonCommand cmd = new CommonCommand(JuPrizeCmdServiceImpl.class.getName(), "getRewardTims", id, nowTime);
        DeferredResult<JuPrizeResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            JuPrizeResult juPrizeResult = juPrizeCmdService.getRewardTims(cmd.getParameter(), cmd.getParameter());
            return juPrizeResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
