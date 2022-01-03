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
	protected static int aCounter = 0;
	protected static int bCounter = 0;

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
		
		while (true) {
			String jobWithSource = null;			
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
						aCounter++;
					}
				} else {
					synchronized (lockB) {
						slaveBJobs.add(jobWithSource);
						bCounter++;
					}
				}
			} else if (job.charAt(0) == 'B') {
				if (bCounter + OPTIMAL < aCounter + NONOPTIMAL) {
					synchronized (lockB) {
						slaveBJobs.add(jobWithSource);
						bCounter++;
					}
				} else {
					synchronized (lockA) {
						slaveAJobs.add(jobWithSource);
						aCounter++;
					}
				}
			}
			try {
				sleep(100);
			} catch(InterruptedException e) {
				
			}
		}
		
	}
}
