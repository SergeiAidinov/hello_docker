package hello_docker;

import java.util.concurrent.TimeUnit;

public class HelloDocker {

	public static void main(String[] args) {
		for(;;) {
			System.out.println("Hello from Docker!");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
