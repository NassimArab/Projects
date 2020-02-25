package fil.car;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class ProcessRMD {
	private static boolean isConnec;
	private OutputStreamReaderProxy osrp;
	private String dir;
	private String path;
	private String pth;

	/**
	*Processus de suppression de repertoire par le client
	* @param  dir le nom du repertoire
	* @param path le repertoire actuel
	* @param osrp 
	* @param isConn si le client ftp est connecté
	 */
	public ProcessRMD(String dir, String path ,OutputStreamReaderProxy osrp, boolean isCoon) {
		this.isConnec = isCoon;
		this.dir = dir;
		this.osrp =  osrp;	
		this.path = path;
	}
	/**
	* méthode de traitemet de la commande RMD
	* @return le message pour le client ftp
	 */
	public String process() {
		
		try {
			if(this.isConnec == true) {
			/*	String pathTest = this.path;
				System.out.println(this.path);
				if(dir.startsWith("/")) {
					pathTest = path;
				}else {
					pathTest += "/" + dir;
				}*/
				File file = new File(this.path);
				if (file.exists() && file.isDirectory()) {
	                if (file.delete()) {
	                	osrp.write("250");
	                    return (250 + " Commande succes");
	                }else {
	                	osrp.write("550");
	                    return(550 + " Erreur du suppression ");
	                }

	            }else{
	            	osrp.write("550");
	            	return(550 + " Erreur d'acces ");}
					
	          }else {
				return (530 + " Pas connecté. ");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (530 + " Pas connecté. ");
		} //Deconnexion | Canal de contrôle ferme par le service
	
	}
	
	
	String getPath() {
		String tab[] = this.path.split("\\/");
		String p = "";
		for(int i = 0; i< tab.length-2; i++) {
			p+= tab[i] + "/";
		}
		p+=tab[tab.length-2];
		return p;
		
	}
}
