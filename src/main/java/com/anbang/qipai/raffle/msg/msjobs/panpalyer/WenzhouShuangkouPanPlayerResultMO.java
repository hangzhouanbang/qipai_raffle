package com.anbang.qipai.raffle.msg.msjobs.panpalyer;

public class WenzhouShuangkouPanPlayerResultMO {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private boolean chaodi;
	private WenzhouShuangkouMingcifenVO mingcifen;
	private int xianshubeishu;
	private int gongxianfen;
	private int score;// 一盘结算分
	private int totalScore;// 总分

	public WenzhouShuangkouPanPlayerResultMO() {

	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public boolean isChaodi() {
		return chaodi;
	}

	public void setChaodi(boolean chaodi) {
		this.chaodi = chaodi;
	}

	public WenzhouShuangkouMingcifenVO getMingcifen() {
		return mingcifen;
	}

	public void setMingcifen(WenzhouShuangkouMingcifenVO mingcifen) {
		this.mingcifen = mingcifen;
	}

	public int getXianshubeishu() {
		return xianshubeishu;
	}

	public void setXianshubeishu(int xianshubeishu) {
		this.xianshubeishu = xianshubeishu;
	}

	public int getGongxianfen() {
		return gongxianfen;
	}

	public void setGongxianfen(int gongxianfen) {
		this.gongxianfen = gongxianfen;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

}
