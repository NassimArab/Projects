package fil.car;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import junit.framework.Assert;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

public class UserTest {
	String res;
	OutputStreamReaderProxy osrp;
	ProcessUser procUser;
	ProcessPass procPass;
	ProcessQuit processQuit;
	private Users users ;
	FtpRequest ftpr;
	private Socket socket;
	private ProcessLIST processLIST;
	private ProcessPWD processPWD;
	private ProcessCDUP processCDUP;
	private ProcessCWD processCWD;
	private ProcessPASV processPASV;
	private ServerSocket serverSocket;
	@Before
	public void setUP(){
		this.users = Mockito.mock(Users.class,"mockUser");
		this.osrp = Mockito.mock(OutputStreamReaderProxy.class,"mockeOSUser");
		this.procUser = new ProcessUser("dddd", osrp,users);
		this.procPass = new ProcessPass("dddd", osrp,users,"user");
		this.socket = new Socket();
		this.serverSocket = Mockito.mock(ServerSocket.class,"mockserver");
		
		
		
	}
	
    @Test
	public void shouldAnswerWithSuccess() throws IOException{
    	Mockito.when(users.contain(anyString())).thenReturn(true);
    	Mockito.when(users.containPass(anyString(),anyString())).thenReturn(true);
    	procUser.process();
    	
    	Mockito.verify(osrp).write("331");
    	procPass.process();
    	Mockito.verify(osrp).write("230");
   }
    
    @Test
  	public void shouldAnswerWithFailureUser() throws IOException{
      	Mockito.when(users.contain(anyString())).thenReturn(false);
      	Mockito.when(users.containPass(anyString(),anyString())).thenReturn(true);
      	procUser.process();
     	
      	Mockito.verify(osrp).write("501");
      	procPass.process();
      	Mockito.verify(osrp).write("230");
     }
      
    @Test
  	public void shouldAnswerWithFailurePassword() throws IOException{
      	Mockito.when(users.contain(anyString())).thenReturn(true);
      	Mockito.when(users.containPass(anyString(),anyString())).thenReturn(false);
      	procUser.process();
     	
      	Mockito.verify(osrp).write("331");
      	procPass.process();
      	Mockito.verify(osrp).write("530");
     }
      
    @Test
  	public void shouldAnswerWithFailureUserPassword() throws IOException{
      	Mockito.when(users.contain(anyString())).thenReturn(false);
      	Mockito.when(users.containPass(anyString(),anyString())).thenReturn(false);
      	procUser.process();
     	
      	Mockito.verify(osrp).write("501");
      	procPass.process();
      	Mockito.verify(osrp).write("530");
     }
    
    @Before
	public void initQuit(){
		this.users = Mockito.mock(Users.class,"mockUser");
		this.osrp = Mockito.mock(OutputStreamReaderProxy.class,"mockeOSUser");
		this.socket =  Mockito.mock(Socket.class,"socketClient");
		this.processQuit = new ProcessQuit(this.socket, osrp, true);
		
	}
    @Test
  	public void shouldAnswerWithSuccessQuit() throws IOException{
    	assertEquals(processQuit.process(), "221 Deconnexion ...");
     
     }
    
    @Test
  	public void shouldAnswerWithFailureQuit() throws IOException{
    	this.processQuit = new ProcessQuit(this.socket, osrp, false);
    	assertEquals(processQuit.process(), "530 Pas connecté. ");
     
     }
    
  
    
    @Test
  	public void shouldAnswerWithFailureLIST() throws IOException{
    	String path = System.getProperty("user.dir") + "123";
    	this.processLIST = new ProcessLIST(true,this.serverSocket,path, osrp, true,1030,"127.0.0.1");
    	this.processLIST.process();
    	Mockito.verify(osrp).write("550");
     
     }
      
      
    /*
     * Pour le cas de Failure, la commande va echouer ssi le client n'est pas connecté,
     * donc le teste est le même à celui de QUITFailure.
     * */
    @Test
  	public void shouldAnswerWithSuccessPWD() throws IOException{
    	String path = System.getProperty("user.dir");
    	this.processPWD = new ProcessPWD(path, osrp, true);
    	this.processPWD.process();
    	Mockito.verify(osrp).write(257);
     
     }
    
    /*
     * Pour le cas de Failure, la commande va echouer ssi le client n'est pas connecté,
     * donc le teste est le même à celui de QUITFailure.
     * */
    @Test
  	public void shouldAnswerWithSuccessCDUP() throws IOException{
    	String path = System.getProperty("user.dir");
    	this.processCDUP = new ProcessCDUP(path,path, osrp, true);
    	this.processCDUP.process();
    	Mockito.verify(osrp).write(250);
     
     }
    

    @Test
  	public void shouldAnswerWithSuccessCWD() throws IOException{
    	String path = System.getProperty("user.dir");
    	this.processCWD = new ProcessCWD("bin",path, osrp, true);
    	this.processCWD.process();
    	Mockito.verify(osrp).write(250);
     
     }
    
    @Test
  	public void shouldAnswerWithFailureCWD() throws IOException{
    	String path = System.getProperty("user.dir");
    	this.processCWD = new ProcessCWD("123",path, osrp, true);
    	this.processCWD.process();
    	Mockito.verify(osrp).write(550);
     
     }
    
    /*
     * Pour le cas de Failure, la commande va echouer ssi le client n'est pas connecté,
     * donc le teste est le même à celui de QUITFailure.
     * */
    @Test
  	public void shouldAnswerWithSuccessPASV() throws IOException{
    	this.processPASV = new ProcessPASV(true, osrp, this.socket);
    	this.processPASV.process();
    	Mockito.verify(osrp).write(227);
     
     }
   

    
}
