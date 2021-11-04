package distributed;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Master {

	public static void main(String[] args) {

		try (ServerSocket serverSocket = new ServerSocket(8080);
				Socket socket = serverSocket.accept();
				PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

			while (reader.readLine() != null) {

				String job = reader.readLine();
				divideJobs(job);
			}

		} catch (Exception ex) {

		}

	}

	public static void divideJobs(String job) {
		final int OPTIMAL = 2;
		final int NONOPTIMAL = 10;
		int aCounter = 0;
		int bCounter = 0;
		
		if (job.equals("A")) {
			if (aCounter + OPTIMAL < bCounter + NONOPTIMAL) {
				//send to a
			}
			else {
				//send to b
			}
		}
		else {
			if (bCounter + OPTIMAL < aCounter + NONOPTIMAL) {
				//SEND TO B
			}
			else {
				//send to a
			}
		}
	}
}