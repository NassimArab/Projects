package fil.car;

import java.io.File;
import java.io.IOException;

public class ProcessCWD {
	private String path;
	private OutputStreamReaderProxy osrp;
	private boolean isCoonected;
	private String dir;
	/**
	* changer le repertoire distant de travail
	* @param dir le repertoire ou accéder
	* @param path le chemain actuel
	* @param ospr
	* @param isConn si le client ftp est connecté
	 */
	public ProcessCWD(String dir, String path, OutputStreamReaderProxy osrp, boolean isConn) {
		this.path = path;
		this.isCoonected = isConn;
		this.osrp = osrp;
		this.dir = dir;
	}
	
	/**
	* methode process qui traite la commande CWD
	* @return message d'affichage pour le client ftp
	 */
	public String process() {
		 try {
			 String pathTest = this.path;
		if (this.isCoonected) {
			if(dir.startsWith("/")) {
				pathTest = path;
			}else {
				pathTest += "/" + dir;
			}
			 File file=new File(pathTest);
	            if (file.isDirectory()) {
	                this.path= pathTest;
	                this.osrp.write(250);
	                return ("250 Action sur le fichier exécutée avec succès. ");
	            }else {
	            	this.osrp.write(550);
	                return ("550	Requete non executee : Fichier indisponible (ex., fichier introuvable, pas d'acces)." );
	            }
	            
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
	* @return le path actuel
	 */
	public String getPath() {
		return path;
	}
	/**
	* @param path changer le path 
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	* @return is connceted
	 */
	public boolean isCoonected() {
		return isCoonected;
	}
	public void setCoonected(boolean isCoonected) {
		this.isCoonected = isCoonected;
	}
}
