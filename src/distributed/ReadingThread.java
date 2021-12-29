package distributed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ReadingThread extends Thread {
	private BufferedReader reader;
	private ArrayList<String> readingList;
	public static Object lock;

	public ReadingThread(Socket socket, ArrayList<String> readingList) {
		this.readingList = readingList;
		this.lock = new Object();

		try {

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		} catch (IOException e) {
		}
		

	}

	@Override
	public void run() {	
		
			while(true){
				System.out.println("In Reading thread");
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				String readingIn = null;
				try {
					
				readingIn = reader.readLine();
				System.out.println("Reading in: " + readingIn);
				
				if (readingIn == null || readingIn.isEmpty() || readingIn.isBlank()) {
					continue;
				}
				else
					System.out.println(readingIn + " received");
				
				synchronized (lock) {
						System.out.println("Reading is adding to list");
						System.out.println("Reading in: " + readingIn);
						readingList.add(readingIn);
				}

					System.out.println("Added job to list");
				
				
				
			 
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
	}
		
	
}
