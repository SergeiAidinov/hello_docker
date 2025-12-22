package hello_docker.server;

import java.io.IOException;

public class HelloDockerServer {

	public static void main(String[] args) throws ClassNotFoundException {
		try {
			ServerWorkFlow.instance().run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}