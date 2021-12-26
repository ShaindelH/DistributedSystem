package distributed;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client2 {

	public static void main(String[] args) {

		System.out.println("Client 2 connected");
		try (Socket socket = new Socket("127.0.0.1", 9085);) {
			
			ArrayList<String> jobsList = new ArrayList<String>();
			Object lock = new Object();
			
			KeyboardReaderThread readFromUser = new KeyboardReaderThread(jobsList, lock);
			ReadingThread readFromMaster = new ReadingThread(socket, jobsList);
			WritingThread writeToMasterThread = new WritingThread(socket,jobsList, lock);
			
			readFromUser.start();
			writeToMasterThread.start();
			readFromMaster.start();
			
			readFromUser.join();
			writeToMasterThread.join();
			readFromMaster.join();
			
			
		} catch (Exception ex) {

		}

	}

}