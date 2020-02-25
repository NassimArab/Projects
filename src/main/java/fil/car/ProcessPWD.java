package fil.car;

import java.io.IOException;

public class ProcessPWD {
	private String path;
	private OutputStreamReaderProxy osrp;
	private boolean isCoonected;

	/**
	* processus de traitement de répertoire actuel
	*@param path le répertoire actuel
	*@param osrp
	*@param isConn si le client ftp est connecté
	 */
	public ProcessPWD(String path, OutputStreamReaderProxy osrp, boolean isConn) {
		this.path = path;
		this.isCoonected = isConn;
		this.osrp = osrp;
	}
	
	/**
	* @return le repertoire actuel pour le client ftp
	 */
	public String process() {
		 try {
			if (this.isCoonected) {
				
				this.osrp.write(257);
				this.path = path;
		        return ("257 " + this.path );
		         
		   }else{
	        	this.osrp.write(530);
	        	this.path = "";
	           return("530 Pas connecté.");
	        }
		 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "425 impossible d'ouvrir la connexion de données. \n";
			}
	}
	/**
	*@return le répértoire actuel
	 */
	public String getPath() {
		return path;
	}
	/**
	* changer le réperoire
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	* @return si le client est connecté
	 */
	public boolean isCoonected() {
		return isCoonected;
	}
	public void setCoonected(boolean isCoonected) {
		this.isCoonected = isCoonected;
	}
}
