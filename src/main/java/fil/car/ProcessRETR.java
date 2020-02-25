package fil.car;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ProcessRETR {
	private boolean isPassive;
	private OutputStreamReaderProxy osrp;
	private ServerSocket modePassive;
	private String filename;
	private String path;
	private int port;
	private Socket data;
	private String adresse;
	private DataOutputStream out;
	
	/**
	* Pour prendre un fichier du repertoire distant et le deposer dans le repertoire local
	*@param isPassive si le mode est passive
	*@param modePassive la socket du mode passive
	*@param osrp
	*@param filename le nom du fichier
	*@param port le numero de port
	*@param Adresse l"adress ip du client ftp
	*@param path le répertoire actuel
	 */
	public ProcessRETR(boolean isPassive, ServerSocket modePassive, OutputStreamReaderProxy osrp, String filename,int port, String Adresse,String path) {
		this.isPassive = isPassive;
		this.osrp = osrp;
		this.modePassive = modePassive;
		this.filename = filename;
		this.port = port;
		this.adresse = adresse;
		this.path = path;
	}
	

	/**
	* méthode de traitement de la commande
	*@return le message pour client ftp
	 */
	public String process() {
		//this.port = this.modePassive.getLocalPort();
		//this.isPassive = true;
		try {
			if(this.isPassive){
				System.out.println("attente accepte");
				this.data = this.modePassive.accept();
				System.out.println("accepte");
			}else {		
				System.out.println(InetAddress.getByName(this.adresse) + "   " + this.port);
				this.data = new Socket(InetAddress.getByName(this.adresse),this.port);
			}
			
			this.out = new DataOutputStream(this.data.getOutputStream());
			FileInputStream file;
			int nb;
            file = new FileInputStream(this.path + "/" + filename);
            while( (nb = file.read()) != -1){
                this.out.writeByte(nb);
            }
            file.close();
			this.data.close();
			this.osrp.write(226);
			return ("226 Transfert terminé avec succès, fermeture de la connexion. ");
			
			
		} catch (IOException e) {
			e.printStackTrace();
			return "425 impossible d'ouvrir la connexion de données. \n";
		}	
		}

}
