package fil.car;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ProcessSTOR {
	private boolean isPassive;
	private OutputStreamReaderProxy osrp;
	private ServerSocket modePassive;
	private String filename;
	private String path;
	private int port;
	private Socket data;
	private String adresse;
	private DataInputStream in;
	
	/**
	* Processus Pour prendre un fichier du repertoire local et le deposer dans le repertoire distant
	*@param isPassive si le mode est en passive
	*@param modePassive la socket du mode passive
	*@param osrp
	*@param filename le nom du ficher a sauvegardé
	*@param port le numéro de poet
	*@param Adresse l'adress ip de client ftm
	*@param path le repertoire actuel

	 */
	public ProcessSTOR(boolean isPassive, ServerSocket modePassive, OutputStreamReaderProxy osrp, String filename,int port, String Adresse,String path) {
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
	*@return le message pour le client ftp
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
			
			this.in = new DataInputStream(this.data.getInputStream());
			FileOutputStream file;
			int nb;
            file = new FileOutputStream(this.path + "/" + filename);
            while( (nb = this.in.read()) != -1){
                file.write(nb);
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
