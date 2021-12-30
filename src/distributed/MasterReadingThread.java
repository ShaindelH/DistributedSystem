package distributed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class MasterReadingThread extends Thread {

	private BufferedReader reader;
	private Object lockA;
	private Object lockB;
	private ArrayList<String> slaveAJobs;
	private ArrayList<String> slaveBJobs;
	private String message;

	public MasterReadingThread(Socket socket, Object lockA, Object lockB, ArrayList<String> slaveAJobs,
			ArrayList<String> slaveBJobs, String message) {
		this.lockA = lockA;
		this.lockB = lockB;
		this.slaveAJobs = slaveAJobs;
		this.slaveBJobs = slaveBJobs;
		this.message=message;

		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		} catch (IOException e) {
			System.out.println("Error occurred");
			e.printStackTrace();
		}
	}

	public void run() {

		System.out.println("\nMaster reading started");
		final int OPTIMAL = 2;
		final int NONOPTIMAL = 10;
		int aCounter = 0;
		int bCounter = 0;

		
		while (true) {
			String jobWithSource=null;
			System.out.println("In Master reading loop");
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			String job = null;
			try {
				jobWithSource = reader.readLine();
				job=jobWithSource.substring(0,jobWithSource.length()-1);
				if (job == null || job.isEmpty() || job.isBlank()) {
					
					continue;
				}
				else
					System.out.println(job + " received from " + message);
			}
			catch (Exception ex) {
				
				ex.printStackTrace();

			}
			System.out.println("Job " + job);
			
			if (job.charAt(0) == 'A') {
				if (aCounter + OPTIMAL < bCounter + NONOPTIMAL) {
					synchronized (lockA) {
						slaveAJobs.add(jobWithSource);
					}
				} else {
					synchronized (lockB) {
						slaveBJobs.add(job);
					}
				}
			} else if (job.charAt(0) == 'B') {
				if (bCounter + OPTIMAL < aCounter + NONOPTIMAL) {
					synchronized (lockB) {
						slaveBJobs.add(job);
					}
				} else {
					synchronized (lockA) {
						slaveAJobs.add(job);
					}
				}
			}
		}
	}
}
