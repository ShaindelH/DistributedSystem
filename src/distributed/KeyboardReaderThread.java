package distributed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class KeyboardReaderThread extends Thread {

	private ArrayList<String> jobsList;
	private BufferedReader input;
	private Object readingLOCK;
	protected static int aJobCount = 1;
	protected static int bJobCount = 1;

	public KeyboardReaderThread(ArrayList<String> jobsList, Object readingLOCK) {
		input = new BufferedReader(new InputStreamReader(System.in));
		this.jobsList = jobsList;
		this.readingLOCK = readingLOCK;

	}

	public void run() {
		String job;

		try {
			do {
				do {
					System.out.print("Please input job types A/B ");
					job = input.readLine().toUpperCase();

					if (!job.equals("A") && !job.equals("B") && !job.equals("Q")) {
						System.out.print("You can only input A/B. Please try again. ");
						job = input.readLine().toUpperCase();
					}
				} while (!job.equals("A") && !job.equals("B"));

				synchronized (readingLOCK) {
					if (job.equals("A")) {
						jobsList.add(job + aJobCount);
						System.out.println("Added job to list");
						System.out.println("Size " + jobsList.size());
						aJobCount++;
					} else {
						jobsList.add(job + bJobCount);
						System.out.println("Added job to list");
						System.out.println("Size " + jobsList.size());
						bJobCount++;
					}

				}
				
				try {
					sleep(1000);
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}

			} while (!job.equals("Q"));
		} catch (IOException e) {
		}

		System.out.println("Goodbye...");
	}

}
