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
	private String message;
	
	public SlaveWritingThread(Socket socket, ArrayList<String> jobs, ArrayList<String> completedJobs, Object lock, String message) {
		try {
			writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.jobs = jobs;
		this.lock = lock;
		this.completedJobs = completedJobs;
		this.message = message;
	}
	
	public void run() {
		String jobWithSource;
		String job;
		while (true) {
			try {
			if (!jobs.isEmpty()) {
				jobWithSource = jobs.get(0);
				job=jobWithSource.substring(0,jobWithSource.length()-1);
				if ((job.charAt(0) == 'A' && message.equals("Slave A")) || (job.charAt(0) == 'B' && message.equals("Slave B"))) {
					System.out.println("Sleeping 2 seconds");
					sleep(2000);
				} else if((job.charAt(0) == 'A' && message.equals("Slave B")) || (job.charAt(0) == 'B' && message.equals("Slave A"))){
					System.out.println("Sleeping 10 seconds");
					sleep(10000);
				} else {
					try {
						sleep(100);
					} catch(InterruptedException e) {
						
					}
				}
			
			
			synchronized(lock) {
				completedJobs.add(jobWithSource);
				jobs.remove(0);
			}
			writer.println(jobWithSource);
			System.out.println("Sending job " + job + " to Master");
			}
		
		 
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}

}
