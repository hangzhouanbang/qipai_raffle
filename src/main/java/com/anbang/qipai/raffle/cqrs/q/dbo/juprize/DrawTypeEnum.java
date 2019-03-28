package com.anbang.qipai.raffle.cqrs.q.dbo.juprize;

public enum DrawTypeEnum {
    first, general;


    public static DrawTypeEnum valueOf(int ordinal){
        return DrawTypeEnum.values()[ordinal];
    }
}
