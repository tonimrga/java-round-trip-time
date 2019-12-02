//Klasa iz koje se pokre�e program.
//CATCHER: java TCPPing -c bind_adresa port
//PITCHER: java TCPPing -p hostname port mps size

package tcpping;

public class TCPPing {	
	public static void main(String[] args) {
		
		//Program se pokre�e ako su upisani obavezni argumenti(ip adresa i port)
		//Ovakav na�in pokretanja sa argumentima je malo nespretan jer uvijek moraju biti u istom rasporedu
		//Mogao bi se koristiti nekakav parser, ali zbog jednostavnosti sam pustio ovako
		if(args.length>2) {	
			String mode = args[0];
			String ip_address = args[1];
			int port = Integer.parseInt(args[2]);
			
			//Ovisno o izabranom na�inu izvo�enju programa pokre�e se case koji zadovoljava uvjet
			switch (mode) {
				case "-c":
					//stvaranje catcher objekta i pokretanje listen funkcije
					Catcher catcher = new Catcher();
					catcher.listen(port, ip_address);
					break;
				case "-p":
					//ukoliko su prilo�eni argumenti za mps(default 1) i size(default 300) pokre�u se uvjeti ispod
					int mps = 1;
					if(args.length > 3 && Integer.parseInt(args[3])<=1000) {
						mps = Integer.parseInt(args[3]);
					}
					int size = 300;
					if(args.length > 4 && Integer.parseInt(args[4])<=3000 && Integer.parseInt(args[4])>=50) {
						size = Integer.parseInt(args[4]);
					}
					//stvaranje pitcher objekta i pokretanje funkcije pitch in intervals
					Pitcher pitcher = new Pitcher();
					pitcher.pitchInIntervals(ip_address, port, size, mps);
					//pokretanje funkcije za pra�enje statistike
					Stats.start(mps);
				    break;
			  default:
			    System.out.println("Upisite nacin rada programa: catcher(-c) ili pitcher(-p)");
			}
		}
		else {
			System.out.println("Niste upisali sve argumente! Program se pokrece na sljedeci na�in:");
			System.out.println("CATCHER: java TCPPing -c bind_adresa port");
			System.out.println("PITCHER: java TCPPing -p hostname port mps size");
		}
	}
}
