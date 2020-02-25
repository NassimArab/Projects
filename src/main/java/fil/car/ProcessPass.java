package fil.car;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ProcessPass {
	private OutputStreamReaderProxy osrp;
	private String message;
	private Users users ;
	private String ClientUser ;
	private boolean session;
	
	/**
	* processus de verification du mot de passe
	* @param message le contenu du message ou le mot de passe 
	* @param osrp
	* @param user la table des utilisateur
	* @param userName le nom de l'utilisateur
	 */
	public ProcessPass(String message, OutputStreamReaderProxy osrp, Users user,String userName) {
		this.osrp = osrp;
		this.message = message;
		this.users = user;
		this.ClientUser = userName;
		this.session = false;
	}
	
	/**
	* méthode du processus de verification de mot de passe
	* @return le message pour le client ftp
	 */
	public String process() {
		try {
			if(this.ClientUser.isEmpty())
        		return(332 + " Besoin d'un compte pour la connexion. ");
			else if (users.containPass(this.ClientUser, this.message)) {
				osrp.write("230");
				this.session = true;
				return (230 + " L'utilisateur s'est connecté, continuez. ");
				
			}else {
				osrp.write("530");
				return (530 + " Pas connecté. ");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (530 + " Pas connecté. ");
		}
	}
	/**
	*@return si le client est bien valide 
	 */
	public boolean isSession() {
		return session;
	}

	public void setSession(boolean session) {
		this.session = session;
	}
	
		
}
