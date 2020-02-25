package fil.car;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FtpRequest implements Runnable{

	/**
	* @author Nassim ARAB
	*/
	private static Socket socket;
	private ServerSocket serverSocket;
	private boolean isPassive; 
	private BufferedReader in;
	OutputStreamReaderProxy osrp;
	private PrintWriter Out;
	private Users users ;
	private String userConnec;
	private static boolean isConnected = false;
	private String PatchActu;
	private static String Racine;
	private boolean Enligne = true;
	private int port;
	private String Adresse;
	private BufferedReader inClient;
	private PrintWriter outClient;
	    public FtpRequest(Socket socket) throws IOException {
	    	this.users = new Users();
	    	this.socket = socket;
	    	this.userConnec = "";
			this.users.SetUsers();
			this.osrp = new OutputStreamReaderProxy(socket.getOutputStream());
	    	
	    	
	    	this.PatchActu = System.getProperty("user.dir");
	    	this.Racine = System.getProperty("user.dir");
	    	this.isPassive = false;
	    	
	    	
	    	 try {
	    		this.in = new BufferedReader(new InputStreamReader(
	 	                socket.getInputStream()));
	 	    	this.Out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
	 	    	this.Out.println(220 + " Service ready, plz login");
	         } catch (IOException e) {
	             throw new RuntimeException(e);
	         }
	        
		}
	    /**
		*Methode run s'execute à chaque connexion dun client
		 */
	    @Override
	    public void run() {
	        while (this.Enligne == true) 
	        {
	            	try {
	            		//this.Out.println(this.PatchActu);
	            		//separateRequest(in.readLine());
						processRequest();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        }
	    }

	  
	/**
	* processus qui permet de gerer les commande en fonction du besions du client ftp
	 */
	void processRequest() throws IOException{
		String request = in.readLine();
		
		if(/*(!request.isEmpty())&&*/(!(request==null))){
			System.out.println(request);
			String resReq[] = request.split("\\s");
			switch(resReq[0]) 
	        { 
	            case "USER": 
	            	ProcessUser p = new ProcessUser(resReq[1], this.osrp,users);
	            	String m = p.process();
	            	System.out.println(m);
	            	this.Out.println(m);
	            	this.userConnec = p.getUserCon();
	            	break;
	            case "PASS":
	            	ProcessPass pass = new ProcessPass(resReq[1], this.osrp,users,this.userConnec);
	            	this.Out.println(pass.process());
	            	this.isConnected = pass.isSession();
	            	break;
	            case "QUIT":     
	            	ProcessQuit pQuit = new ProcessQuit(this.socket, this.osrp, this.isConnected);
	            	this.Out.println( pQuit.process());
	            	this.Enligne = pQuit.isEnligne();
	            	break;
	            case "LIST": 
	            	System.out.println("LIST " + this.PatchActu);
	            	ProcessLIST pLIST = new ProcessLIST(this.isPassive,this.serverSocket, this.PatchActu, this.osrp, this.isConnected,this.port,this.Adresse);
	            	String msg = pLIST.process();
	            	this.Out.println( msg);
	            	break;
	            case "PWD":
	            	
	            	ProcessPWD pPWD = new ProcessPWD(this.PatchActu, this.osrp, this.isConnected);
	            	String PWDres = pPWD.process();
	            	this.Out.println(PWDres);
	              	break;
	            case "CWD":
	            	
	            	ProcessCWD pCWD = new ProcessCWD(resReq[1],this.PatchActu, this.osrp, this.isConnected);
	            	this.Out.println(pCWD.process());
	            	this.PatchActu = pCWD.getPath();
	            	System.out.println("CWD" + this.PatchActu);
	              	break;
	            case "CDUP":
	            	ProcessCDUP pCDUP = new ProcessCDUP(this.Racine,this.PatchActu, this.osrp, this.isConnected);
	            	this.Out.println(pCDUP.process());
	            	this.PatchActu = pCDUP.getPath();
	              	break;
	            case "PASV":
	            	ProcessPASV pPASV = new ProcessPASV(this.isConnected,this.osrp,this.socket);
	            	this.Out.println(pPASV.process());
	            	this.isPassive = pPASV.isPassive();
	            	this.serverSocket = pPASV.getPassiveMode();
	            	this.port = pPASV.getPort();
	              	break;
	            case "PORT":
	            	ProcessPORT pPORT = new ProcessPORT(resReq[1],this.osrp,this.isConnected);
	            	this.Out.println(pPORT.process());
	            	this.isPassive = pPORT.isPassive();
	            	this.port = pPORT.getPort();
	            	this.Adresse = pPORT.getAdresse();
	              	break;
	            case "RETR":
	            	ProcessRETR pRETR= new ProcessRETR(this.isPassive,this.serverSocket,this.osrp,resReq[1],this.port,this.Adresse,this.PatchActu);
	            	this.Out.println(pRETR.process());
	              	break;
	            case "STOR":
	            	ProcessSTOR pSTOR= new ProcessSTOR(this.isPassive,this.serverSocket,this.osrp,resReq[1],this.port,this.Adresse,this.PatchActu);
	            	this.Out.println(pSTOR.process());
	              	break;
	            case "RMD":
	            	System.out.println("bef" + this.PatchActu);
	            	ProcessRMD pRMD = new ProcessRMD(resReq[1],this.PatchActu, this.osrp, this.isConnected);
	            	System.out.println("aft" + this.PatchActu);
	            	this.PatchActu = pRMD.getPath();
	            	this.Out.println(pRMD.process());
	              	break;
	            case "TYPE":
	            	this.Out.println(200 + "   ");
	              	break;
	              	
	            default: 
	            	this.Out.println(502 + " default");
	        }
		}else {
			//this.Out.println("Entrez une commande : ");
		}
	}


}
