package com.anbang.qipai.raffle.cqrs.q.dao;

import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeAccount;

import java.util.List;

public interface JuPrizeAccountDao {

    void save(JuPrizeAccount juPrizeAccount);

    void delete(String id);

    JuPrizeAccount getJuPrizeAccount(String Id);

    List<JuPrizeAccount> listJuPrizeAccount();
}
