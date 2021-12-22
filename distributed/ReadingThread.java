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
		readingList = new ArrayList<>();
		this.lock = new Object();

		try {

			reader = new BufferedReader(new InputStreamReader(System.in));

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		} catch (IOException e) {
		}

	}

	@Override
	public void run() {	
		try {
			while(true){
				String readingIn;
				//readingIn = reader.readLine();
				while ((readingIn=reader.readLine()) != null) {
					System.out.println("Read job: " + readingIn);

					synchronized (lock) {

						readingList.add(readingIn);
					}

					System.out.println("Added job to list");
				}
				
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
