package distributed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ReadingThread extends Thread {
	private BufferedReader reader;
	private ArrayList<String> readingList;
	public Object lock;
	private String message;

	public ReadingThread(Socket socket, ArrayList<String> readingList, Object lock, String message) {
		this.readingList = readingList;
		this.lock = lock;
		this.message = message;

		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		while (true) {
			System.out.println("In Reading thread loop");
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			String readingIn = null;
			try {

				readingIn = reader.readLine();

				if (readingIn == null || readingIn.isEmpty() || readingIn.isBlank()) {
					continue;
				} else {
					synchronized (lock) {
						System.out.println(readingIn + " received from " + message);
						readingList.add(readingIn);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
