package distributed;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client1 {

	public static void main(String[] args) {

		System.out.println("Client 1 connected");
		try (Socket socket = new Socket("127.0.0.1", 8080);) {
			
			ArrayList<String> jobsList = new ArrayList<String>();
			Object lock = new Object();
			
			KeyboardReaderThread readFromUser = new KeyboardReaderThread(jobsList, lock);
			WritingThread writeToMasterThread = new WritingThread(socket, jobsList, lock);
			
			readFromUser.start();
			writeToMasterThread.start();
			
			readFromUser.join();
			writeToMasterThread.join();			
			
			ReadingThread readFromMaster = new ReadingThread(socket, jobsList);
			
			readFromMaster.start();
			readFromMaster.join();

			
		} catch (Exception ex) {

		}

	}

}