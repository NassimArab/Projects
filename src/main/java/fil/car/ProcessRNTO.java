package fil.car;

import java.io.File;
import java.io.IOException;

public class ProcessRNTO {

	private static boolean isConnec;
	private OutputStreamReaderProxy osrp;
	private String file;
	private String path;
	private String amodifier;

	/**
	*Processus de suppression de fichier par le client
	* @param  dir le nom du repertoire
	* @param path le repertoire actuel
	* @param osrp 
	* @param isConn si le client ftp est connecté
	 */
	public ProcessRNTO(String file, String ARenomer, String path ,OutputStreamReaderProxy osrp, boolean isCoon) {
		this.isConnec = isCoon;
		this.file = file;
		this.osrp =  osrp;	
		this.path = path;
		this.amodifier = ARenomer;
	}
	/**
	* méthode de traitemet de la commande DELE
	* @return le message pour le client ftp
	 */
	public String process() {
		
		try {
			if(this.isConnec == true) {
				
				System.out.println("------");
				System.out.println(this.amodifier);
				String pathA = this.path + "/" + file;
				System.out.println(pathA);
				File file = new File(this.amodifier);
				File file2 = new File(pathA);
                if (file.renameTo(file2)) {
                	osrp.write("250");
                    return (250 + "fichier est bien renome ");
                }else {
                	osrp.write("550");
                    return(550 + " impossible de renomer le fichier ");
                }

	            
					
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
	
		return this.path;
		
	}
}
