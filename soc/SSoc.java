import java.net.*;

public class SSoc{
	
	public static void main(String args[]){
		System.out.println("SSoc Start");
		try{
			ServerSocket ss = new ServerSocket(4333);
			ss.accept();
			ss.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
