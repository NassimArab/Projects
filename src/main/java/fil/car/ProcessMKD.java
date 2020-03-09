package fil.car;


import java.io.File;
import java.io.IOException;

public class ProcessMKD {

	private OutputStreamReaderProxy osrp;
	private String dirname;
	private String path;
	private boolean isConnected;

	public ProcessMKD(String dirName, OutputStreamReaderProxy osrp,String path, boolean isConn) {
		this.osrp = osrp;
		this.path = path;
		this.dirname = dirName;
		this.isConnected = isConn;
	}
	
	/**
	* méthode de traitement de la commande
	*@return le message pour le client ftp
	 */
	public String process() {
		//this.port = this.modePassive.getLocalPort();
		//this.isPassive = true;
		try {
			   if(this.isConnected) {
	        	
		    		File Dir = new File(this.path + "/" + this.dirname);
		    		if(Dir.mkdir()) {
		    			
		    			osrp.write("250");
		    			return (250 + "Dossier crée avec succes");
						
			        }else {
			        	
			        	osrp.write("550");
			        	return (550 + " erreur pendant la creation de dossier");
			        }
		        }else {
				
					osrp.write("530");
				
		        	return (530 + " Pas connecté.");
						
		        }
		    	
			
		} catch (IOException e) {
			e.printStackTrace();
			return "425 impossible d'ouvrir la connexion de données. \n";
		}	
		}
}
