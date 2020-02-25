package fil.car;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ProcessPORT {

	private boolean isCon;
	private OutputStreamReaderProxy osrp;
	private int port;
	private ServerSocket passiveMode;
	private Socket client;
	private boolean isPassive = true;
	private String ipPort;
	private String Adresse;
	
	/**
	* processus du mode active
	*@param IpPort l'ensemble adress ip et numero de port du client 
	*@param osrp
	*@param isConnec si le client est connecté
	 */
	public ProcessPORT(String IpPort, OutputStreamReaderProxy osrp, boolean isConnec) {
		this.isCon = isConnec;
		this.osrp = osrp;
		this.ipPort = IpPort;

	}

	/**
	* méthode de traitement de la commande PORT
	*@return le message pour le client ftp
	 */
	public String process() {
		
		 try {
				if (this.isCon) {
					String ipPort[] = this.ipPort.split("\\,");
					this.Adresse = ipPort[0] + "." + ipPort[1] + "." + ipPort[2] + "." + ipPort[3];
					this.port = Integer.parseInt(ipPort[4]) *256 + Integer.parseInt(ipPort[5]);
					this.osrp.write(200);
					this.isPassive = false;
					return (" 200 Adresse : " + this.Adresse + " \n Port : " + this.port);
								         
			        }else{
			        	this.osrp.write(530);
			           return("530 Pas connecté.");
			        }
				 } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return ("425 impossible d'ouvrir la connexion de données. \n");
					}	
	}
	/**
	*@return le mode actuel
	 */
	public boolean isPassive() {
		return isPassive;
	}
	/**
	*@return le numéro de port
	 */
	public int getPort() {
		return this.port;
	}
	/**
	*@return l'adresse ip 
	 */
	public String getAdresse() {
		return Adresse;
	}

}
