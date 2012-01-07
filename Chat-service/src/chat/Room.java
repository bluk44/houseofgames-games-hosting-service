package chat;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Room {
	protected String name;
	protected Map<Integer, PlayerObject> players = new HashMap<Integer, PlayerObject>();
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock r = rwl.readLock();
	private final Lock w = rwl.writeLock();
	public Room(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<PlayerObject> getPlayers() {
		r.lock();
		try {
			return players.values();
		} finally {
			r.unlock();
		}
	}

	public void setPlayers(Map<Integer, PlayerObject> players) {
		w.lock();
		try {
			this.players = players;
		} finally {
			w.unlock();
		}
	}

	public void addPlayer(Integer playerid, PlayerObject player) {
		w.lock();
		try{
		players.put(playerid, player);
		} finally{
			w.unlock();
		}
	}

	public void removePlayer(int playerId) {
		w.lock();
		try{
		players.remove(playerId);
		} finally{
			w.unlock();
		}
	}
	 
}
