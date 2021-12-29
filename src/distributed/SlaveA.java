package distributed;

import java.net.Socket;
import java.util.ArrayList;

public class SlaveA {

	public static void main(String[] args) {
		System.out.println("Slave A connected");
		ArrayList<String> jobs = new ArrayList<String>();
		ArrayList<String> completedJobs = new ArrayList<String>();
		Object lock = new Object();

		try (Socket socket = new Socket("127.0.0.1", 9090);) {

			ReadingThread readFromMaster = new ReadingThread(socket, jobs);
			//WritingThread writeToMaster = new WritingThread(socket, completedJobs, lock);
			
			readFromMaster.start();
			//writeToMaster.start();
			
			readFromMaster.join();
			//writeToMaster.join();
			
			String job = jobs.get(0);
			if (job.charAt(0) == 'A') {
				Thread.sleep(2000);
				System.out.println("Sleeping 2 seconds");
			} else {
				Thread.sleep(10000);
				System.out.println("Sleeping 10 seconds");
			}
			
		
			System.out.println("Sending job " + job + " to Master");
			completedJobs.add(job);
			jobs.remove(0);

		} catch (Exception ex) {

		}
	}
}
