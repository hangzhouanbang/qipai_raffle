package com.anbang.qipai.raffle.cqrs.q.dbo.juprize;


/**
 * @Description:
 */
public class JuPrizeRecord {
    private String id;
    private long sendTime;
    private JuPrize juPrize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public JuPrize getJuPrize() {
        return juPrize;
    }

    public void setJuPrize(JuPrize juPrize) {
        this.juPrize = juPrize;
    }
}
