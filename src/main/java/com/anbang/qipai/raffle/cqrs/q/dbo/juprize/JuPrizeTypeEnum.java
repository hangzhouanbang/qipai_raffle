package com.anbang.qipai.raffle.cqrs.q.dbo.juprize;

public enum JuPrizeTypeEnum {
    HONGBAODIAN;

    public static JuPrizeTypeEnum valueOf(int ordinal) {
        return JuPrizeTypeEnum.values()[ordinal];
    }
}
