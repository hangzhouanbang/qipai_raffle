package com.anbang.qipai.raffle.cqrs.c.domain.juprize;

import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrize;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeAccount;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRelease;

/**
 * @Description:
 */
public class JuPrizeResult {
    private JuPrize juPrize;
    private JuPrizeAccount juPrizeAccount;
    private JuPrizeRelease juPrizeRelease;

    public JuPrizeResult(JuPrize juPrize, JuPrizeAccount juPrizeAccount,JuPrizeRelease juPrizeRelease) {
        this.juPrize = juPrize;
        this.juPrizeAccount = juPrizeAccount;
        this.juPrizeRelease = juPrizeRelease;
    }

    public JuPrizeResult(JuPrizeAccount juPrizeAccount) {
        this.juPrizeAccount = juPrizeAccount;
    }

    public JuPrizeResult() {
    }

    public JuPrize getJuPrize() {
        return juPrize;
    }

    public void setJuPrize(JuPrize juPrize) {
        this.juPrize = juPrize;
    }

    public JuPrizeAccount getJuPrizeAccount() {
        return juPrizeAccount;
    }

    public void setJuPrizeAccount(JuPrizeAccount juPrizeAccount) {
        this.juPrizeAccount = juPrizeAccount;
    }

    public JuPrizeRelease getJuPrizeRelease() {
        return juPrizeRelease;
    }

    public void setJuPrizeRelease(JuPrizeRelease juPrizeRelease) {
        this.juPrizeRelease = juPrizeRelease;
    }
}
