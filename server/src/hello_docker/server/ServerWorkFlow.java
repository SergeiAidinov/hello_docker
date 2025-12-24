package hello_docker.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.Objects;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ServerWorkFlow {

	private static ServerWorkFlow instance = null;
	private static final String url = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/hello_db");
    private static final String user = System.getenv().getOrDefault("DB_USER", "user");
    private static final String password = System.getenv().getOrDefault("DB_PASSWORD", "password");

	private ServerWorkFlow() {

	}

	public static ServerWorkFlow instance() {
		if (Objects.isNull(instance))
			instance = new ServerWorkFlow();
		return instance;
	}

	public void run() throws IOException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		server.createContext("/", new MyHandler());
		server.setExecutor(null);
		System.out.println("Server started on port 8080");
		server.start();

	}

	static class MyHandler implements HttpHandler {

	    @Override
	    public void handle(HttpExchange httpExchange) throws IOException {
	        String method = httpExchange.getRequestMethod();
	        System.out.println(method);
	        String response;

	        try {
	            if ("GET".equalsIgnoreCase(method)) {
	                String path = httpExchange.getRequestURI().getPath();
	                String idStr = path.substring(1); // Получаем "1" из "/1"
	                int id = Integer.parseInt(idStr);
	                response = handleGetRequest(id);

	            } else if ("POST".equalsIgnoreCase(method)) {
	                InputStream is = httpExchange.getRequestBody();
	                String message = new String(is.readAllBytes());
	                response = handlePostRequest(message);

	            } else {
	                response = "Unsupported method";
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response = "Error: " + e.getMessage();
	        }

	        httpExchange.sendResponseHeaders(200, response.getBytes().length);
	        try (OutputStream os = httpExchange.getResponseBody()) {
	            os.write(response.getBytes());
	        }
	    }

	    /** Получение сообщения по id */
	    private String handleGetRequest(int id) {
	        try (Connection connection = DriverManager.getConnection(url, user, password);
	             CallableStatement stmt = connection.prepareCall("SELECT * FROM messages WHERE id = ?")) {
	            stmt.setLong(1, id);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                return rs.getString("content");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "Message not found";
	    }

	    /** Сохранение сообщения в базу */
	    private String handlePostRequest(String message) {
	        try (Connection connection = DriverManager.getConnection(url, user, password);
	             java.sql.PreparedStatement stmt = connection.prepareStatement(
	                     "INSERT INTO messages (content) VALUES (?) RETURNING id")) {
	            stmt.setString(1, message);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                int id = rs.getInt("id");
	                return "Message saved with id: " + id;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Failed to save message: " + e.getMessage();
	        }
	        return "Failed to save message";
	    }
	}
}
