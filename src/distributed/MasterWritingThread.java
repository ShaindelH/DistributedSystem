package distributed;

import java.net.Socket;
import java.util.ArrayList;
import java.io.*;

public class MasterWritingThread extends Thread {
	private PrintWriter writerClient1;
	private PrintWriter writerClient2;
	private ArrayList<String> jobs;
	private Object lock;
	private String message;

	public MasterWritingThread(Socket socketClient1,  Socket socketClient2, ArrayList<String> jobs, Object lock, String message) {
		try {
			writerClient1 = new PrintWriter(socketClient1.getOutputStream(), true);
			writerClient2 = new PrintWriter(socketClient2.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.jobs = jobs;
		this.lock = lock;
		this.message = message;
	}

	@Override
	public void run() {
		System.out.println("Master Writing Thread Running");
		String jobWithSource;
		String job;
		while (true) {
			
			job = null;
			if (jobs.size() > 0) {
				synchronized (lock) {

					
					jobWithSource = jobs.get(0);
					job=jobWithSource.substring(0,jobWithSource.length()-1);
					jobs.remove(jobWithSource);
				}
				if(jobWithSource.substring(jobWithSource.length()-1).equals("1")){
					writerClient1.println(jobWithSource);
				}
				else {
					writerClient2.println(jobWithSource);
				}
				
				System.out.println("Sent job " + job + " to " + message);
			
			}

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}