package tcpping;

import java.io.*;
import java.net.*;

public class Catcher {
	
	//varijable socketa i i/o koje su potrebne za komunikaciju
	private Socket socket;
	private ServerSocket server;
	private DataInputStream input;
	private DataOutputStream output;

	//funkcija listen koja pokreæe server socket na priloženoj adresi i portu
    public void listen(int port, String bind_address) {
        try {
        	//otvaranje socketa
			server = new ServerSocket(port, 100, InetAddress.getByName(bind_address));
			System.out.println("Slusam pingove na adresi: " + bind_address + " | Port: " + port);
			//infinite while petlja kako bi socket mogao prihvacati neogranicen broj zahtjeva
            while(true) {
            	socket = server.accept();
            	//prihvacanje inputa i timestamp vremena kada poruka dode
	            input = new DataInputStream(socket.getInputStream());
            	long timeB = System.currentTimeMillis();
	            
            	//èitanje rednog broja i velièine poruke
	            int messageNumber = input.readInt();
	            int size = input.readInt();
	            
	            //generiranje byte arraya sa velièinom istom kao u poruci
				byte[] message = new byte[size];
				
				//slanje outputa redom: redni broj, timestamp b, poruka
	            output = new DataOutputStream(socket.getOutputStream());
	            output.writeInt(messageNumber);
	            output.writeLong(timeB);
				output.write(message);
            }
        }
        catch(IOException i) {
            System.out.println(i);
        }
    }
}
