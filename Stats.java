package tcpping;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.text.SimpleDateFormat;

public class Stats {
	
	//staticne varijable koje sluze za praæenje statistike
	static int numOfMessages = 0;     //broj poslanih poruka
	static double rtt = 0;			  //suma svih vremena od A->B->A
	static double aToB = 0;			  //suma svih vremena od A->B
	static double bToA = 0;			  //suma svih vremena od B->A
	static double maxTime = 0;		  //maksimalno vrijeme - najsporije vrijeme
	
	//funkcija start koja ispisuje statistièke detalje svake sekunde
	public static void start(int mps) {		
		Timer t = new Timer();
		t.schedule(new TimerTask() {
		    @Override
		    public void run() {		    

		    	String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
			    System.out.println(
			    		timeStamp + " Ukupno poruka: " + numOfMessages + " | Brzina: " + mps + "msgs/s |" + " Najsporije(max) vrijeme: " + maxTime + " ms" +
			    		"\nProsjek(A->B->A): " + String.format("%.2f", countAvg(rtt)) + " ms | Prosjek(A->B): " + String.format("%.2f", countAvg(aToB)) + " ms | Prosjek(B->A): " + String.format("%.2f", countAvg(bToA)) + " ms"					       
			    );
			    System.out.println("--------------------------------------------------------------------------------------");
		   }		    
		}, 1000, 1000);
	}
	
	//funkcija koja raèuna prosjeène vrijednosti
	public static double countAvg(double sum) {
		return sum / numOfMessages;
	}
	
}
