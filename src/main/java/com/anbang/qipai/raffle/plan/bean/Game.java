package com.anbang.qipai.raffle.plan.bean;

/**
 * 我们平台提供的所有游戏名称的一个枚举
 * 
 * @author Neo
 *
 */
public enum Game {
	ruianMajiang, wenzhouMajiang, fangpaoMajiang, dianpaoMajiang, wenzhouShuangkou, doudizhu, paodekuai, chayuanShuangkou;

	public static Game valueOf(int ordinal) {
		return Game.values()[ordinal];
	}
}
