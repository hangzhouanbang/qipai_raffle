package com.anbang.qipai.raffle.plan.dao;

import com.anbang.qipai.raffle.plan.bean.PrizeCheck;

/**
 * @Description:
 */
public interface PrizeCheckDao {
    void save(PrizeCheck prizeCheck);

    PrizeCheck getPrizeCheck(String id);
}
