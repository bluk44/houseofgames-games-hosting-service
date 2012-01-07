package chat;

public class PlayerObject {
	private int playerId;
	private String playerName;
	
	public PlayerObject() {
		super();
	}
	public PlayerObject(int playerId, String playerName) {
		super();
		this.playerId = playerId;
		this.playerName = playerName;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
}
