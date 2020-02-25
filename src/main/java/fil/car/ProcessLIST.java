package fil.car;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ProcessLIST {
	private String path;
	private String Contenu;
	private boolean connexion;
	private OutputStreamReaderProxy osrp;
	private ServerSocket modePassive;
	private boolean isPassive;
	private Socket data;
	private int port;
	private String adresse;
	private DataOutputStream out;
	
	/**
	* afficher la liste des fichiers et des répertoire
	* @param isPassive si le mode passive
	* @param modePassive socket en mode passive
	* @param path le repertoire actuel
	* @param osrp
	* @param isConnect si leclient ftp est conncté
	* @param port le numéro de port
	* @param Adresse ladresse ip du client 
	 */
	public ProcessLIST(boolean isPassive, ServerSocket modePassive, String path, OutputStreamReaderProxy osrp, boolean isConnect,int port, String Adresse) {
		this.path = path;
		this.Contenu = "";
		this.connexion = isConnect;
		this.osrp = osrp;
		this.isPassive = isPassive;
		this.modePassive = modePassive;
		this.port = port;
		this.adresse = adresse;
		
		
	}
	/**
	*@return le message pour le client ftp
	 */
	public String process() {
		
		try {
        if(this.connexion) {
        	
        	if(this.isPassive){
				this.data = this.modePassive.accept();
			}else {		
				System.out.println(InetAddress.getByName(this.adresse) + "   " + this.port);
				this.data = new Socket(InetAddress.getByName(this.adresse),this.port);
			}
        	
    		File file = new File(path);
    		if(file.isDirectory()) {
    			
    			this.Contenu = "150 État du repertoire correct. Sur le point d'ouvrir la connexion de données. \n";
    			
					osrp.write("150");
				this.out = new DataOutputStream(this.data.getOutputStream());
	            File[] files = file.listFiles();
	            String c = "";
	            if(files!=null) {
	            	System.out.println("not null");
		            for (File file2 : files) {
		            	
		            	if(!file2.isHidden()) {
		            		if(file2.isDirectory()) {
			            		c =  "+/,m"+file2.lastModified()/1000+",\011"+file2.getName()+"\015\012";
			            		
			            	}else if(file2.isFile()) {
			            		c =	"+s" + file2.length()+",m"+file2.lastModified()/1000+",\011"+file2.getName()+"\015\012";
			            		
			            	}
		            	}
		            	this.out.writeBytes(c  + "\r\n");
		            			}
		            System.out.println("226");
		            return (226 + " ");
	            }else {
	            	
	            	osrp.write("550");
				
	            	this.Contenu = "550 Le répertoire est vide";
	            }
        }else {
        	
        	osrp.write("550");
		
           this.Contenu = ("550 Le répertoire n'existe pas. \n");
        }
        }else {
		
			osrp.write("530");
		
        	this.Contenu =  "530 Pas connecté.";
				
        }
    	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		this.Contenu= "425 impossible d'ouvrir la connexion de données. \n";
		//osrp.write("530");
        throw new RuntimeException(e);

	}
        
        return Contenu;
	}
        
}
