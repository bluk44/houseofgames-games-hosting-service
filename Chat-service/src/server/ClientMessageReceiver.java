package server;

import java.io.BufferedReader;

/**
 * Klasa ClientMessageReceiver odpowiada za odbieranie komunikatow od 
 * poszczególnych klientów i jest uruchamiana w osobnych wątkach
 * 
 * @author Łukasz Budkowski
 *
 */
public class ClientMessageReceiver implements Runnable {
	
	private BufferedReader reader;
	private ClientHandler handler;
	//private 
	public ClientMessageReceiver(BufferedReader reader){
		this.reader = reader;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
