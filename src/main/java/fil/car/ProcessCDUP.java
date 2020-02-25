package fil.car;

import java.io.File;
import java.io.IOException;

public class ProcessCDUP {
	private String path;
	private OutputStreamReaderProxy osrp;
	private boolean isCoonected;
	private String dir;
	private String racine;

	/**
	* changer le répertoire de travail distant 
	* @param racine le chemain racine
	* @param path le chemin courrant
	* @param osrp
	* @param isConn si le client ftp est connecté
	 */
	public ProcessCDUP(String racine, String path, OutputStreamReaderProxy osrp, boolean isConn) {
		this.path = path;
		this.isCoonected = isConn;
		this.osrp = osrp;
		this.racine = racine;
	}
	
	/**
	* méthode process permet de traiter la commande cdup
	* @return le message à afficher au client ftp
	 */
	public String process() {
	 try {
		if (this.isCoonected) {
			
			
			if (this.path.equals(this.racine)) {
				this.osrp.write(250);
                return "250 Vous etes a la racine";
            }else {
                String cdup="";
                String partPath[] = this.path.split("/");
                for (int i = 1; i < partPath.length-1; i++) {
                    cdup +="/"+ partPath[i];
                }
                this.path=cdup;
                this.osrp.write(250);
                return ("250 Action sur le fichier exécutée avec succès. ");
            }
	     }else{
	        	this.osrp.write(530);
	           return("530 Pas connecté.");
	       }
	 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "425 impossible d'ouvrir la connexion de données. \n";
			}
	}
	
	/**
	*@return le chemain actuel
	 */
	public String getPath() {
		return path;
	}
	/**
	*@param path changer le repertoire
	 */
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isCoonected() {
		return isCoonected;
	}
	public void setCoonected(boolean isCoonected) {
		this.isCoonected = isCoonected;
	}
}
