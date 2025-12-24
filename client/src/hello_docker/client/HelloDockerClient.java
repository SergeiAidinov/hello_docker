package hello_docker.client;

import java.net.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.time.Duration;
import java.util.Objects;
import java.util.Random;

public class HelloDockerClient {

	public static void main(String[] args) throws InterruptedException, IOException {
		HttpClient client = HttpClient.newHttpClient();

		while (true) {
			HttpRequest request = null;
			HttpResponse<String> response = null;
			String id = String.valueOf((int) (Math.random() * 10));
			System.out.println("Request for ID: " + id);
			try {
				request = HttpRequest.newBuilder().uri(URI.create("http://volumes-java-server:8080/" + id))
						.timeout(Duration.ofSeconds(5)).GET().build();
				response = client.send(request, HttpResponse.BodyHandlers.ofString());

			} catch (Exception e) {
				System.out.println("Server did not respond");
				System.out.println(e.getMessage());
			}
			if (Objects.nonNull(response))
				System.out.println("Response: " + response.body());
			Thread.sleep(2000);
		}
	}

}