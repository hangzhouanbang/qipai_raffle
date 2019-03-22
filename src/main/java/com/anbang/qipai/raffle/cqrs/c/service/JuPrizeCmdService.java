package com.anbang.qipai.raffle.cqrs.c.service;

import com.anbang.qipai.raffle.cqrs.c.domain.juprize.JuPrizeResult;
import com.anbang.qipai.raffle.cqrs.c.domain.juprize.NoFindJuPrizeException;
import com.anbang.qipai.raffle.cqrs.c.domain.juprize.NoRewardTimesException;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRelease;
import com.anbang.qipai.raffle.plan.bean.Game;

import java.util.List;

public interface JuPrizeCmdService {
    void release(List<JuPrizeRelease> juPrizeReleases);

    JuPrizeResult raffle(String id, Game game) throws NoFindJuPrizeException, NoRewardTimesException;

    JuPrizeResult updateCalTimes(String id);

    JuPrizeResult getRewardTims(String id);
}
