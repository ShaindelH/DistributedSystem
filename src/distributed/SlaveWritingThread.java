package distributed;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SlaveWritingThread extends Thread {
	
	private PrintWriter writer;
	private ArrayList<String> jobs;
	private ArrayList<String> completedJobs;
	private Object lock;
	
	public SlaveWritingThread(Socket socket, ArrayList<String> jobs, ArrayList<String> completedJobs, Object lock) {
		try {
			writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.jobs = jobs;
		this.lock = lock;
		this.completedJobs = completedJobs;

	}
	
	public void run() {
		String jobWithSource;
		String job;
		while (true) {
			try {
			if (!jobs.isEmpty()) {
				jobWithSource = jobs.get(0);
				job=jobWithSource.substring(0,jobWithSource.length()-1);
				if (job.charAt(0) == 'A') {
					sleep(2000);
					System.out.println("Sleeping 2 seconds");
				} else {
					sleep(10000);
					System.out.println("Sleeping 10 seconds");
				}
			
			
			synchronized(lock) {
				completedJobs.add(jobWithSource);
				jobs.remove(0);
			}
			writer.println(jobWithSource);
			System.out.println("Sending job " + job + " to Master");
			}
		
		
			sleep(1000);
		 
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}

}
