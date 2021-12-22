package distributed;

import java.net.Socket;
import java.util.ArrayList;
import java.io.*;

public class WritingThread extends Thread {
	private PrintWriter writer;
	private ArrayList<String> jobs;
	private Object lock;

	public WritingThread(Socket socket, ArrayList<String> jobs, Object lock) {
		try {
			writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.jobs = jobs;
		this.lock = lock;
	}

	@Override
	public void run() {
		System.out.println("Running");
		String job;
		while (true) {
			System.out.println("In Writing. Size: " + jobs.size());
			if (jobs.size() > 0) {
				System.out.println(jobs.toString());

				synchronized (lock) {
					job = jobs.get(0);
					jobs.remove(job);
				}
				writer.println(job);
				System.out.println("Sent job " + job);
				
				}
			
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
