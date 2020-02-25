package fil.car;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ProcessUser {
	
	private Users users ;
	private OutputStreamReaderProxy osrp;
	private String UserCon;
	private String message;
	/**
	*Processus de verification de user name
	*@param message le username saisie par le client
	*@param osrp
	*@param user la table des utilisateurs
	 */
	public ProcessUser(String message, OutputStreamReaderProxy osrp,Users user) {
		this.osrp = osrp;
		this.message = message;
		this.users = user;
		this.UserCon = "";
		
	}

	public String process() {
		try {
			if (users.contain(message)) {
				osrp.write("331");
				this.UserCon = message;
				return (331 + " Nom d'utilisateur OK , Attente de mot de passe : ");
			}else {
				osrp.write("501");
				this.UserCon = "";
				return (501 + " Nom d'utilisateur incorrect");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.UserCon = "";
			return (530 + " Pas connecté. ");
		}
	}

	public String getUserCon() {
		return UserCon;
	}

	public void setUserCon(String userCon) {
		UserCon = userCon;
	}
	
}
