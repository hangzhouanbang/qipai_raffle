package com.anbang.qipai.raffle.cqrs.q.dao;

import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRelease;

import java.util.List;

/**
 * @Description:
 */
public interface JuPrizeReleaseDao {

    void save(JuPrizeRelease juPrizeRelease);

    void delete(String id);

    JuPrizeRelease getJuPrizeRelease(String game);

    List<JuPrizeRelease> listJuPrize();
}
