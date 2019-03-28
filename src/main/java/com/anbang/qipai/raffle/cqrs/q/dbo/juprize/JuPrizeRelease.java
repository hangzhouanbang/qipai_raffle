package com.anbang.qipai.raffle.cqrs.q.dbo.juprize;

import com.anbang.qipai.raffle.plan.bean.Game;
import com.highto.framework.nio.ByteBufferAble;
import com.highto.framework.nio.ByteBufferSerializer;
import org.springframework.data.annotation.Id;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 对局奖励发布
 */
public class JuPrizeRelease implements ByteBufferAble {
    @Id
    private Game game;
    private boolean release;
    private long creatTime;

    private List<JuPrize> firstJuPrizes;
    private List<JuPrize> generalJuPrizes;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isRelease() {
        return release;
    }

    public void setRelease(boolean release) {
        this.release = release;
    }

    public long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }

    public List<JuPrize> getFirstJuPrizes() {
        return firstJuPrizes;
    }

    public void setFirstJuPrizes(List<JuPrize> firstJuPrizes) {
        this.firstJuPrizes = firstJuPrizes;
    }

    public List<JuPrize> getGeneralJuPrizes() {
        return generalJuPrizes;
    }

    public void setGeneralJuPrizes(List<JuPrize> generalJuPrizes) {
        this.generalJuPrizes = generalJuPrizes;
    }

    @Override
    public void toByteBuffer(ByteBuffer bb) throws Throwable {
        bb.put((byte) game.ordinal());
        ByteBufferSerializer.booleanToByteBuffer(release, bb);
        bb.putLong(creatTime);
        ByteBufferSerializer.listToByteBuffer(new ArrayList<>(firstJuPrizes), bb);

        ByteBufferSerializer.listToByteBuffer(new ArrayList<>(generalJuPrizes), bb);
    }

    @Override
    public void fillByByteBuffer(ByteBuffer bb) throws Throwable {
        game = Game.valueOf(Byte.toUnsignedInt(bb.get()));
        release = ByteBufferSerializer.byteBufferToBoolean(bb);
        creatTime = bb.getLong();
        firstJuPrizes = new ArrayList<>();
        ByteBufferSerializer.byteBufferToList(bb).forEach((o) -> firstJuPrizes.add((JuPrize) o));
        generalJuPrizes = new ArrayList<>();
        ByteBufferSerializer.byteBufferToList(bb).forEach((o) -> generalJuPrizes.add((JuPrize) o));
    }
}
