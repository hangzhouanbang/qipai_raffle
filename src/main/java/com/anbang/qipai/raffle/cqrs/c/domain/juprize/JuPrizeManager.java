package com.anbang.qipai.raffle.cqrs.c.domain.juprize;

import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrize;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeAccount;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRelease;
import com.anbang.qipai.raffle.plan.bean.Game;

import java.util.*;

public class JuPrizeManager {
    private static final int TOTAL_PRIZEPROB = 10000;   // 总奖励概率的和
    private static final long DAY_MESC = 24 * 60 * 60 * 1000;

    private Map<Game, JuPrizeRelease> releaseMap = new HashMap();
    private Map<String, JuPrizeAccount> juPrizeAccountMap = new HashMap<>();

    Random rand = new Random();


    // 发布奖励概率
    public void release(ArrayList<JuPrizeRelease> juPrizeReleases) {
        for (JuPrizeRelease list : juPrizeReleases) {
            releaseMap.put(list.getGame(), list);
        }
    }

    // 抽奖
    public JuPrizeResult raffle(String id, Game game) throws NoFindJuPrizeException, NoRewardTimesException {
        JuPrizeRelease juPrizeRelease = releaseMap.get(game);
        if (juPrizeRelease == null || !juPrizeRelease.isRelease()) {
            throw new NoFindJuPrizeException();
        }

        JuPrizeAccount account = juPrizeAccountMap.get(id);
        if (account == null) {
            juPrizeAccountMap.put(id, new JuPrizeAccount(id, 5));
        }

        if (account.getRewardTimes() == 0) {
            throw new NoRewardTimesException();
        }

        JuPrize juPrize;

        // 每日首抽
        if (account.getDayTimes() == 0) {
            juPrize = toRaffle(juPrizeRelease.getFirstJuPrizes());

        } else {
            juPrize = toRaffle(juPrizeRelease.getGeneralJuPrizes());
        }

        // 扣除
        account.setRewardTimes(account.getRewardTimes() - 1);
        account.setDayTimes(account.getDayTimes() + 1);
        account.setTotalTimes(account.getTotalTimes() + 1);
        account.setUpdateTime(System.currentTimeMillis());
        return new JuPrizeResult(juPrize, account, juPrizeRelease);
    }

    /**
     * 执行抽奖，超额时再次抽，再次抽时概率会出现波动
     */
    private JuPrize toRaffle(List<JuPrize> juPrizes) {
        // 本次抽奖的随机数
        int totalPrizeprob = juPrizes.stream().mapToInt(JuPrize::getPrizeProb).sum();
        int random = rand.nextInt(totalPrizeprob) + 1;

        for (JuPrize list : juPrizes) {
            random = random - list.getPrizeProb();
            if (random < 0) {
                int storeNum = list.getStoreNum() - list.getSingleNum(); //修改库存
                if (list.isOverstep()) { //可以超额
                    list.setStoreNum(storeNum);
                    return list;
                }

                if (storeNum < 0) { //不可超额且奖池不够
//                    list.setStoreNum(storeNum);
                    List<JuPrize> tempJuPrizes = new ArrayList<>(juPrizes);
                    tempJuPrizes.remove(list);
                    return toRaffle(juPrizes);
                } else {
                    list.setStoreNum(storeNum);
                    return list;
                }
            }
        }
        return null;
    }

    public JuPrizeResult updateCalTimes(String id) {
        JuPrizeAccount account = juPrizeAccountMap.get(id);
        if (account == null) {
            juPrizeAccountMap.put(id, new JuPrizeAccount(id, 5));
        }
        account.setCalTimes(account.getCalTimes() - 1);
        account.setUpdateTime(System.currentTimeMillis());
        if (account.getCalTimes() == 0) {
            account.setCalTimes(5);
            account.setRewardTimes(account.getRewardTimes() + 1);
        }

        return new JuPrizeResult(account);
    }

    public JuPrizeResult getRewardTims(String id) {
        JuPrizeAccount account = juPrizeAccountMap.get(id);

        if (account == null) {
            account = new JuPrizeAccount(id, 5);
            juPrizeAccountMap.put(id, account);
        } else {
            long nowTime = System.currentTimeMillis();
            if (nowTime / DAY_MESC != account.getUpdateTime() / DAY_MESC) {
                account.setCalTimes(5);
                account.setDayTimes(0);
                account.setRewardTimes(0);
            }
        }

        return new JuPrizeResult(account);
    }

}
