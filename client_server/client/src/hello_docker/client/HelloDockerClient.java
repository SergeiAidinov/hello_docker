package hello_docker.client;

import java.net.http.*;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Objects;

public class HelloDockerClient {

	public static void main(String[] args) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();

		while (true) {
			HttpRequest request = null;
			HttpResponse<String> response = null;
			try {
				request = HttpRequest.newBuilder().uri(URI.create("http://java-server:8080"))
						.timeout(Duration.ofSeconds(5)).GET().build();
				response = client.send(request, HttpResponse.BodyHandlers.ofString());

			} catch (Exception e) {
				System.out.println("Server did not respond");
			}
			if (Objects.nonNull(response))
				System.out.println("Response: " + response.body());
			Thread.sleep(2000);
		}
	}

}