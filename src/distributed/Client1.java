package distributed;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client1 {

	public static void main(String[] args) {

		System.out.println("Client 1 connected");
		try (Socket socket = new Socket("127.0.0.1", 8080);) {
			
			ArrayList<String> jobsList = new ArrayList<String>();
			ArrayList<String> completedJobs = new ArrayList<>();
			Object lock = new Object();
			
			KeyboardReaderThread readFromUser = new KeyboardReaderThread(jobsList, lock,"1");
			WritingThread writeToMasterThread = new WritingThread(socket, jobsList, lock, "Master");
			ReadingThread readFromMaster = new ReadingThread(socket, completedJobs, lock, "Master");
			
			readFromUser.start();
			writeToMasterThread.start();
			readFromMaster.start();
			
			readFromUser.join();
			writeToMasterThread.join();			
			readFromMaster.join();
			
		} catch (Exception ex) {
			System.out.println("Error occurred. Please try again.");
			System.exit(0);
		}

	}

}