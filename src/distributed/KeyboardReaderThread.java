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
	private String message;

	public KeyboardReaderThread(ArrayList<String> jobsList, Object readingLOCK, String message) {
		input = new BufferedReader(new InputStreamReader(System.in));
		this.jobsList = jobsList;
		this.readingLOCK = readingLOCK;
		this.message=message;

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
					System.out.println();
				} while (!job.equals("A") && !job.equals("B"));

				synchronized (readingLOCK) {
					if (job.equals("A")) {
						jobsList.add(job + aJobCount + message);
						aJobCount++;
					} else {
						jobsList.add(job + bJobCount + message);
						bJobCount++;
					}

				}
				
				sleep(100);

			} while (!job.equals("Q"));
			
		} catch (IOException e) {
		}
		catch(InterruptedException e) {
			
		}

		System.out.println("Goodbye...");
	}

}
