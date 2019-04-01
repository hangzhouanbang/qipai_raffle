package com.anbang.qipai.raffle.cqrs.q.dbo.juprize;

import java.util.Objects;

/**
 * @Description:
 */
public class JuPrizeAccount {
    private String id;  // 玩家id
    private int rewardTimes;   //已获得的奖励次数
    private int calTimes;   //用于计算的奖励次数，最大五次
    private int dayTimes;   //今天总胜利次数
    private int totalTimes;   //总胜利次数
    private long updateTime;    //最后更新时间

    public JuPrizeAccount() {
    }

    public JuPrizeAccount(String id, int calTimes, Long nowTime) {
        this.id = id;
        this.calTimes = calTimes;
        this.updateTime = nowTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRewardTimes() {
        return rewardTimes;
    }

    public void setRewardTimes(int rewardTimes) {
        this.rewardTimes = rewardTimes;
    }

    public int getCalTimes() {
        return calTimes;
    }

    public void setCalTimes(int calTimes) {
        this.calTimes = calTimes;
    }

    public int getDayTimes() {
        return dayTimes;
    }

    public void setDayTimes(int dayTimes) {
        this.dayTimes = dayTimes;
    }

    public int getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(int totalTimes) {
        this.totalTimes = totalTimes;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JuPrizeAccount that = (JuPrizeAccount) o;
        return rewardTimes == that.rewardTimes &&
                calTimes == that.calTimes &&
                dayTimes == that.dayTimes &&
                totalTimes == that.totalTimes &&
                updateTime == that.updateTime &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rewardTimes, calTimes, dayTimes, totalTimes, updateTime);
    }
}
