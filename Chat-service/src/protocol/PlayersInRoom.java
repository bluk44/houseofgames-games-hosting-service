package protocol;

import java.util.Collection;

import chat.PlayerObject;


public class PlayersInRoom extends ChatMessage {
	protected  Collection<PlayerObject> players;
	
	public PlayersInRoom(Collection<PlayerObject> players){
		this.players = players;
	}
	
	public Collection<PlayerObject> getPlayers(){
		return players;
	}
}
