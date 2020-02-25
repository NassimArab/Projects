package fil.car;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ProcessPASV {
	private boolean isCon;
	private OutputStreamReaderProxy osrp;
	private int port;
	private ServerSocket passiveMode;
	private boolean isPassive = false;
	private Socket client;
	
	/**
	* processus du mode passive
	*@param isConnec si le client ftp est connecter
	*@param osrp
	*@param client la socket du client tfp
	 */
	public ProcessPASV(boolean isConnec, OutputStreamReaderProxy osrp,Socket client) {
		this.isCon = isConnec;
		this.osrp = osrp;
		this.client = client;
	}
	/**
	* méthode du changement en mode passive
	*@return le message pour le client ftp
	 */
	public String process() {
		
		 try {
				if (this.isCon) {
					this.passiveMode = new ServerSocket(0);				
					
					String ipAdresse = this.client.getLocalAddress().getHostAddress();
					String PartIpAdresse[] = ipAdresse.split("\\.");
					String resultat = PartIpAdresse[0] + "," + PartIpAdresse[1] + "," + PartIpAdresse[2] + "," + PartIpAdresse[3] ;
					this.port = this.passiveMode.getLocalPort();
					resultat +=  "," + (this.port / 256) + "," + (this.port % 256);
					this.isPassive = true;
					this.osrp.write(227);
					return ("227 Mode passif : " + resultat);
								         
			        }else{
			        	this.osrp.write(530);
			        	this.isPassive = false;
			           return("530 Pas connecté.");
			        }
				 } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return ("425 impossible d'ouvrir la connexion de données. \n");
					}	
	}
	/**
	*@return le numéro de port du mode passive
	 */
	public int getPort() {
		return port;
	}
	/**
	* @return la socket du mode passive
	 */
	public ServerSocket getPassiveMode() {
		return passiveMode;
	}
	/**
	*@return si le mode passive est bien activé
	 */
	public boolean isPassive() {
		return isPassive;
	}
}
