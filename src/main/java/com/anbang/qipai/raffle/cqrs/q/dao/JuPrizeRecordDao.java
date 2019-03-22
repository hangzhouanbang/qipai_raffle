package com.anbang.qipai.raffle.cqrs.q.dao;

import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRecord;

public interface JuPrizeRecordDao {
    void save(JuPrizeRecord juPrizeRecord);

    void delete(String id);

    JuPrizeRecord getJuPrizeRecord(String Id);
}
