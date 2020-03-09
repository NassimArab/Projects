package fil.car;

import java.io.File;
import java.io.IOException;

public class ProcessRNFR {
	private static boolean isConnec;
	private OutputStreamReaderProxy osrp;
	private String file;
	private String path;
	private String pth;

	/**
	*Processus de suppression de fichier par le client
	* @param  dir le nom du repertoire
	* @param path le repertoire actuel
	* @param osrp 
	* @param isConn si le client ftp est connecté
	 */
	public ProcessRNFR(String file, String path ,OutputStreamReaderProxy osrp, boolean isCoon) {
		this.isConnec = isCoon;
		this.file = file;
		this.osrp =  osrp;	
		this.path = path;
	}
	/**
	* méthode de traitemet de la commande DELE
	* @return le message pour le client ftp
	 */
	public String process() {
		
		try {
			if(this.isConnec == true) {
				System.out.println(this.path);
				String pathA = this.path + "/" + file;
				File file = new File(pathA);
				System.out.println(pathA);
				if (file.exists() && file.isFile()) {
					
					osrp.write("350");
                    return (350 + " renomer le fichier ...");
	              /* File file2 = new File(this.getPath() + "/" + file);
	                if (file.renameTo(file2)) {
	                	osrp.write("350");
	                    return (350 + " le fichier est bien renomé");
	                }else {
	                	osrp.write("550");
	                    return(550 + " impossible de ronomer le fichier ");
	                }*/

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
	
		return this.path + "/" + file;
		
	}
}
