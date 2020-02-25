package fil.car;

import java.io.IOException;
import java.net.Socket;

public class ProcessQuit {
	private static boolean isConnec;
	private static Socket socketClient;
	private OutputStreamReaderProxy osrp;
	private boolean enligne = true;
	public boolean isEnligne() {
		return enligne;
	}

	public void setEnligne(boolean enligne) {
		this.enligne = enligne;
	}
	

	/**
	* processus pour quiter la conncexion 
	*@param clientS la socket client ftp
	*@param osrp
	*@param isConn si le client ftp est connecté
	 */
	public ProcessQuit(Socket clientS ,OutputStreamReaderProxy osrp, boolean isCoon) {
		this.isConnec = isCoon;
		this.socketClient = clientS;
		this.osrp =  osrp;
		
	}
	/**
	* méthode de traitement de la requete
	* @return le messahe pour client ftp
	 */
	public String process() {

			try {
				if(this.isConnec == true) {
					osrp.write("221");
					this.isConnec = false;
					this.enligne = false;
					this.socketClient.close();
					return (221 + " Deconnexion ...");
				}else {
					return (530 + " Pas connecté. ");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return (530 + " Pas connecté. ");
			} //Deconnexion | Canal de contrôle ferme par le service
		
			
		
	}
}
