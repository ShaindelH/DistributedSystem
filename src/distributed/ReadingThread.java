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
		String jobWithSource;
		
		while (true) {
			System.out.println("In Reading thread loop");
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			String readingIn = null;
			try {
				jobWithSource = reader.readLine();
				readingIn = jobWithSource.substring(0, jobWithSource.length() - 1);

				if (readingIn == null || readingIn.isEmpty() || readingIn.isBlank()) {
					continue;
				} else {
					synchronized (lock) {
						System.out.println(readingIn + " received from " + message);
						readingList.add(jobWithSource);
					}
				}
				if (message.equals("Master")) {
					System.out.println("Job " + readingIn + " completed");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
