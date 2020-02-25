package fil.car;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Users {
	private HashMap<String, String> users = new HashMap<String, String>();
	
	/**
	* ajouter un user dans la table
	*@param user le nom d'utilisateur
	*@param pass le mot de passe
	 */
	public void addUser(String user,String pass){
		users.put(user, pass);
	}
	
	/**
	* si un user existe dans la table
	*@param user le non d'utilisateur
	@return si l'utilisateur existe ou pas 
	 */
	public boolean contain(String user){
		if(users.containsKey(user))
			return true;
		else 
			return false;
		
	}
	
	/**
	* si la table contien un autre user avec son mot de passe
	*@param user le nom d'utilisateur
	*@param pass le mot de passe
	*@return si le user existe
	 */
	public boolean containPass(String user,String pass){
		if(users.get(user).equals(pass))
			return true;
		else 
			return false;
		
	}
	/**
	* recuperer un mot de passe d'un user
	*@param user le nom d'utilisateur
	*@return la valeur du mot de passe
	 */
	public String getPassword(String user){
			return users.get(user);
	}

	/**
	* remplir la table de users avec des users existant sur le fichier users.txt	 */
	public void SetUsers() {
		String path = System.getProperty("user.dir");
		File sol = new File(path+"/users.txt" ); 
		FileReader fileread =null;
		try {
			fileread = new FileReader(sol);
			BufferedReader lineread= new BufferedReader(fileread);
			String line ;
			while((line=lineread.readLine())!=null) {
				String users[] = line.split("\\s");
				this.users.put(users[0], users[1]);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("le fichier n'a pas ete trouve  "+ sol.toString());
			System.out.println(sol.getAbsolutePath());
		} catch (IOException e) {
			System.out.println("impossoble de lire le contenu de fichier "+ sol.toString());
		}
		
		try {
			fileread.close();
		} catch (IOException e) {
			System.out.println("impossible de fermer le fichier "+sol.toString());
		} catch (NullPointerException e) {
			System.out.println("impossible d'ouvrir le ficher "+ sol.toString());
		}
		
	}
}
