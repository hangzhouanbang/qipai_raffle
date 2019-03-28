package com.anbang.qipai.raffle.cqrs.q.service;

import com.anbang.qipai.raffle.cqrs.c.domain.juprize.JuPrizeResult;
import com.anbang.qipai.raffle.cqrs.q.dao.JuPrizeRecordDao;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeAccount;
import com.anbang.qipai.raffle.cqrs.q.dao.JuPrizeAccountDao;
import com.anbang.qipai.raffle.cqrs.q.dao.JuPrizeReleaseDao;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRecord;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRelease;
import com.anbang.qipai.raffle.msg.service.PrizeSendMsgService;
import com.anbang.qipai.raffle.plan.bean.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @Description: 局抽奖业务
 */
@Service
public class JuPrizeBusinessService {
    @Autowired
    private JuPrizeAccountDao juPrizeAccountDao;

    @Autowired
    private JuPrizeReleaseDao juPrizeReleaseDao;

    @Autowired
    private JuPrizeRecordDao juPrizeRecordDao;

    public JuPrizeAccount getJuPrizeAccount(String id){
        return juPrizeAccountDao.getJuPrizeAccount(id);
    }

    public void saveJuPrizeAccount(JuPrizeAccount juPrizeAccount) {
        juPrizeAccountDao.save(juPrizeAccount);
    }

    public JuPrizeRecord saveRaffle(JuPrizeResult juPrizeResult) {
        juPrizeAccountDao.save(juPrizeResult.getJuPrizeAccount());
        juPrizeReleaseDao.save(juPrizeResult.getJuPrizeRelease());

        JuPrizeRecord juPrizeRecord = new JuPrizeRecord();
        juPrizeRecord.setJuPrize(juPrizeResult.getJuPrize());
        juPrizeRecord.setPlayerId(juPrizeResult.getJuPrizeAccount().getId());
        juPrizeRecord.setSendTime(System.currentTimeMillis());
        juPrizeRecordDao.save(juPrizeRecord);

        return juPrizeRecord;
    }

    public JuPrizeRelease getJuPrizeReleaseById(Game game) {
        return juPrizeReleaseDao.getJuPrizeRelease(game);
    }

}
