package distributed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ReadingThread extends Thread {
	private BufferedReader reader;
	private ArrayList<String> readingList;
	private Object lock;

	public ReadingThread(Socket socket, ArrayList<String> readingList, Object lock) {
		readingList = new ArrayList<>();
		this.lock = lock;

		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
		}

	}

	@Override
	public void run() {
		String readingIn = null;

		try {
			do {

				readingIn = reader.readLine();

				synchronized (lock) {
					readingList.add(readingIn);
				}

			} while (reader.readLine() != null);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
