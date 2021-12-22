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
		System.out.println("Master connected");

		ArrayList<String> slaveAJobs = new ArrayList<String>();
		ArrayList<String> slaveBJobs = new ArrayList<String>();

		ArrayList<String> completedJobs = new ArrayList<String>();

		try (ServerSocket serverSocketClient1 = new ServerSocket(8080);
				Socket socketClient1 = serverSocketClient1.accept();
				//ServerSocket serverSocketClient2 = new ServerSocket(9085);
				//Socket socketClient2 = serverSocketClient2.accept();
				//ServerSocket slaveASocket = new ServerSocket(9090);
				//Socket slaveA = slaveASocket.accept();
				//ServerSocket slaveBSocket = new ServerSocket(8085);
				//Socket slaveB = slaveBSocket.accept();
				) {
			
			
			Object lockA = new Object();
			Object lockB = new Object(); 

			MasterReadingThread readFromClient1 = new MasterReadingThread(socketClient1, lockA, lockB, slaveAJobs, slaveBJobs);
			//MasterReadingThread readFromClient2 = new MasterReadingThread(socketClient2, lockA, lockB, slaveAJobs, slaveBJobs);
			
			readFromClient1.start();
		
			//readFromClient2.start();
			
			//WritingThread writingThreadA = new WritingThread(slaveA, slaveAJobs);
			//WritingThread writingThreadB = new WritingThread(slaveB, slaveBJobs);

			//writingThreadA.start();
			//writingThreadB.start();

			//ReadingThread readingThreadA = new ReadingThread(slaveA, completedJobs);
			//ReadingThread readingThreadB = new ReadingThread(slaveB, completedJobs);

			// START READING THREADS
			//readingThreadA.start();
			//readingThreadB.start();
			
			//WritingThread writeToClient1 = new WritingThread(completedJobs);


		} catch (Exception ex) {

		}

	}

}