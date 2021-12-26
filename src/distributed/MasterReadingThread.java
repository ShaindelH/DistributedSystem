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

	public MasterReadingThread(Socket socket, Object lockA, Object lockB, ArrayList<String> slaveAJobs,
			ArrayList<String> slaveBJobs) {
		this.lockA = lockA;
		this.lockB = lockB;
		this.slaveAJobs = slaveAJobs;
		this.slaveBJobs = slaveBJobs;

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
			

			try {
				sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			String job = null;
			try {
				job = reader.readLine();
				if (job == null) {
					job = "Empty";
				}
				else
					System.out.println(job + " received");
			}
			catch (Exception ex) {
				
				ex.printStackTrace();
				System.out.println("IN exception");

			}
			System.out.println("JOb " + job);
			if (job == "Empty") {
				continue;
			}
			if (job.charAt(0) == 'A') {
				if (aCounter + OPTIMAL < bCounter + NONOPTIMAL) {
					synchronized (lockA) {
						slaveAJobs.add(job);
						System.out.println("Master sending job " + job + " to Slave A");
					}
				} else {
					synchronized (lockB) {
						slaveBJobs.add(job);
						System.out.println("Master sending job " + job + " to Slave B");
					}
				}
			} else if (job.charAt(0) == 'B') {
				if (bCounter + OPTIMAL < aCounter + NONOPTIMAL) {
					synchronized (lockB) {
						slaveBJobs.add(job);
						System.out.println("Master sending job " + job + " to Slave B");
					}
				} else {
					synchronized (lockA) {
						slaveAJobs.add(job);
						System.out.println("Master sending job " + job + " to Slave A");
					}
				}
			}

			System.out.println("Didnt receive job");

		}

	}
}
