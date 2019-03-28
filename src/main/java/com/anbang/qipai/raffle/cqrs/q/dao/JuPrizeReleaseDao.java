package com.anbang.qipai.raffle.cqrs.q.dao;

import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRelease;
import com.anbang.qipai.raffle.plan.bean.Game;

import java.util.List;

/**
 * @Description:
 */
public interface JuPrizeReleaseDao {

    void save(JuPrizeRelease juPrizeRelease);

    void delete(Game game);

    JuPrizeRelease getJuPrizeRelease(Game game);

    List<JuPrizeRelease> listJuPrize();
}
