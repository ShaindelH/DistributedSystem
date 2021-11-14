package distributed;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Master {

	public static void main(String[] args) {

		ArrayList<String> jobsList = new ArrayList<String>();
		
		ArrayList<String> slaveAJobs = new ArrayList<String>();
		ArrayList<String> slaveBJobs = new ArrayList<String>();
		
		ArrayList<String> completedJobs = new ArrayList<String>();

		Object readingLOCK = new Object();
		Object readingLOCK_BOTH = new Object();

		try (ServerSocket serverSocket = new ServerSocket(8080);
				Socket socket = serverSocket.accept();
				PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

			ReadingThread clientThread = new ReadingThread(socket, jobsList, readingLOCK);
			clientThread.start();

			// CHANGE SOCKETS IN WRITING AND READING:)
			
			WritingThread writingThreadA = new WritingThread(socket, slaveAJobs);
			WritingThread writingThreadB = new WritingThread(socket, slaveBJobs);

			writingThreadA.start();
			writingThreadB.start();

			ReadingThread readingThreadA = new ReadingThread(socket, completedJobs, readingLOCK_BOTH);
			ReadingThread readingThreadB = new ReadingThread(socket, completedJobs, readingLOCK_BOTH);
			
			//START READING THREADS
			readingThreadA.start();
			readingThreadB.start();
			
			while (true) {
				divideJobs(socket, jobsList, readingLOCK, slaveAJobs, slaveBJobs);
			}

			

		} catch (Exception ex) {

		}

	}

	public static void divideJobs(Socket socket, ArrayList<String> jobsList, Object readingLOCK,
			ArrayList<String> slaveAJobs, ArrayList<String> slaveBJobs) {
		final int OPTIMAL = 2;
		final int NONOPTIMAL = 10;
		int aCounter = 0;
		int bCounter = 0;

		String job = jobsList.get(0);

		if (job.equals("A")) {
			if (aCounter + OPTIMAL < bCounter + NONOPTIMAL) {
				slaveAJobs.add(job);
			} else {
				slaveBJobs.add(job);
			}
		} else {
			if (bCounter + OPTIMAL < aCounter + NONOPTIMAL) {
				slaveBJobs.add(job);
			} else {
				slaveAJobs.add(job);
			}
		}
		
		jobsList.remove(0);
	}
}