

import java.net.Socket;

public class GameTest {
	 public static boolean sendMessage(String ipAddress,int port){
	        try {
	            Socket socket=new Socket(ipAddress,port);
	            
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	    }
	 
	public static void main(String[] args) {
		boolean f = sendMessage("120.55.34.22", 80);
		System.out.println(f);
	    }
}
