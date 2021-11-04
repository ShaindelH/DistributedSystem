package distributed;

import java.io.*;
import java.net.*;


public class Client {
	
	//google what to buffered reader for system.in is for
	public static void main(String[] args) {
		
	
		String job;
	try 	
		(Socket socket = new Socket("127.0.0.1", 8080);
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader reader=  new BufferedReader(new InputStreamReader(socket.getInputStream())); 
        BufferedReader input =  new BufferedReader(new InputStreamReader(System.in));)
			{
		do {
		do {
			System.out.print("Please input a job type: A/B. If you would like to quit, enter Q. ");
			job = input.readLine().toUpperCase();
			
			if(!job.equals("A") && !job.equals("B")  && !job.equals("Q") ) {
				System.out.print("You can only input A/B. Please try again. ");
				job = input.readLine().toUpperCase();
			}	
		}
		while(!job.equals("A") && !job.equals("B")  && !job.equals("Q")) ; 
		
		writer.println(job);
		
		
		}while(job.equals("Q"));
		
		System.out.println("Goodbye...");
	}
	catch(Exception ex) {
		
	}	
		
	}

}