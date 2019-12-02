package tcpping;

import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class Pitcher {
	
	//varijable socketa i i/o koje su potrebne za komunikaciju
	private Socket socket; 
    private DataInputStream input; 
    private DataOutputStream output; 
	
    /* funkcija pitch in intervals koja poziva funkciju pitch u intervalima.
       Interval se raèuna ovisno o tome koju je vrijednost korisnik prosljedio za mps. Dijeli se 1000ms sa mps.  */
	public void pitchInIntervals(String ip_address, int port, int size, int mps) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
		    @Override
		    public void run() {		    	
		    	pitch(ip_address, port, size);
		    }
		}, 0, 1000/mps);
	}
	
	//funkcija pitch koja šalje poruku prema drugom raèunalu
	public void pitch(String ip_address, int port, int size) {
		try {
			//poèetak praæenja vremena
			long startTime = System.currentTimeMillis();
			
			//otvaranje socketa
			socket = new Socket(ip_address, port);
	        
			//byte array velièine koju je korisnik upisau u argumente
	        byte[] message = new byte[size];
	        
	        //otvaranje output streama
	        output = new DataOutputStream(socket.getOutputStream());
	        //inkrementacija totalno broja poruka. Koristi se i za redne brojeve.
	        Stats.numOfMessages++;
	        //slanje outputa redom: redni broj, velièina i poruka(byte array)
	        output.writeInt(Stats.numOfMessages);
	        output.writeInt(size);
		    output.write(message);		        
	        
		    //dohvaæanje inputa i završetak praæenja vremena
		    input = new DataInputStream(socket.getInputStream());
		    long endTime = System.currentTimeMillis();
		    int messageNumber = input.readInt();	    
		    long bTime = input.readLong();
		    
		    //provjera za redne brojeve
		    if(messageNumber != Stats.numOfMessages) {
		    	System.out.println("Redni brojevi se ne podudaraju. Poruka se izgubila.");
		    }
		    else {
		    	//provjera za najsporije vrijeme(max time)
		    	if((endTime-startTime)>Stats.maxTime) {
		    		Stats.maxTime = endTime-startTime;
		    	}
		    	//dodavanje rezultata vremena u staticne varijable u klasi Stats
			    Stats.aToB += (bTime-startTime);
			    Stats.bToA += (endTime-bTime);	        
			    Stats.rtt += (endTime-startTime);			    
		    }
		    input.close();
		    output.close();
		    socket.close();
	     } 
	     catch(UnknownHostException u) { 
	    	 System.out.println(u);
	     } 
	     catch(IOException i) { 
	         System.out.println(i); 
	     } 
	}	
}

