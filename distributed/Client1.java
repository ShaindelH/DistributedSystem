package distributed;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client1 {

	public static void main(String[] args) {

		System.out.println("Client 1 connected");
		try (Socket socket = new Socket("127.0.0.1", 8080);) {
			
			ArrayList<String> jobsList = new ArrayList<String>();
			Object readingLOCK = new Object();
			
			KeyboardReaderThread readFromUser = new KeyboardReaderThread(jobsList, readingLOCK);
			WritingThread writeToMasterThread = new WritingThread(socket, jobsList, readingLOCK);
			
			readFromUser.start();
			writeToMasterThread.start();
			
			readFromUser.join();
			writeToMasterThread.join();
			
			
			//readFromMaster.start();
			
			//ReadingThread readFromMaster = new ReadingThread(socket, jobsList);

			
		} catch (Exception ex) {

		}

	}

}