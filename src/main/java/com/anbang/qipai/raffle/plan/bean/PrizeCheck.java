package com.anbang.qipai.raffle.plan.bean;

import java.util.List;

/**
 * @Description:
 */
public class PrizeCheck {
    private String id;
    private List<String> playerIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(List<String> playerIds) {
        this.playerIds = playerIds;
    }
}
