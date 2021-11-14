package distributed;

import java.net.Socket;
import java.util.ArrayList;
import java.io.*;

public class WritingThread extends Thread{
	private PrintWriter writer;
	private ArrayList<String> jobs;
	
	
	public WritingThread(Socket socket, ArrayList<String> jobs) {
		try {
			writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.jobs = jobs;
		
	}
	
	@Override
	public void run() {
		
	}
}
