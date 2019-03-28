package com.anbang.qipai.raffle.cqrs.c.service.impl;

import com.anbang.qipai.raffle.cqrs.c.domain.juprize.JuPrizeManager;
import com.anbang.qipai.raffle.cqrs.c.domain.juprize.JuPrizeResult;
import com.anbang.qipai.raffle.cqrs.c.domain.juprize.NoFindJuPrizeException;
import com.anbang.qipai.raffle.cqrs.c.domain.juprize.NoRewardTimesException;
import com.anbang.qipai.raffle.cqrs.c.service.JuPrizeCmdService;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRelease;
import com.anbang.qipai.raffle.plan.bean.Game;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JuPrizeCmdServiceImpl extends CmdServiceBase implements JuPrizeCmdService {
    @Override
    public void release(ArrayList<JuPrizeRelease> juPrizeReleases) {
        JuPrizeManager juPrizeManager = singletonEntityRepository.getEntity(JuPrizeManager.class);
        juPrizeManager.release(juPrizeReleases);
    }

    public JuPrizeResult raffle(String id, Game game) throws NoFindJuPrizeException, NoRewardTimesException {
        JuPrizeManager juPrizeManager = singletonEntityRepository.getEntity(JuPrizeManager.class);
        return juPrizeManager.raffle(id, game);
    }

    public JuPrizeResult updateCalTimes(String id) {
        JuPrizeManager juPrizeManager = singletonEntityRepository.getEntity(JuPrizeManager.class);
        return juPrizeManager.updateCalTimes(id);
    }

    public JuPrizeResult getRewardTims(String id) {
        JuPrizeManager juPrizeManager = singletonEntityRepository.getEntity(JuPrizeManager.class);
        return juPrizeManager.getRewardTims(id);
    }
}
