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
	        String response = "";

	        try (Connection connection = DriverManager.getConnection(url, user, password)) {

	            if ("GET".equalsIgnoreCase(method)) {
	                String path = httpExchange.getRequestURI().getPath();

	                if ("/count".equals(path)) {
	                    response = getCount(connection);
	                } else if (path.length() > 1) {
	                    int id = Integer.parseInt(path.substring(1));
	                    response = getMessageById(connection, id);
	                } else {
	                    response = "Invalid GET request";
	                }

	            } else if ("POST".equalsIgnoreCase(method)) {
	                InputStream is = httpExchange.getRequestBody();
	                String body = new String(is.readAllBytes());
	                int id = insertMessage(connection, body);
	                response = "Inserted message with id = " + id;
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

	    private String getMessageById(Connection connection, int id) throws Exception {
	        try (CallableStatement stmt = connection.prepareCall("SELECT content FROM messages WHERE id = ?")) {
	            stmt.setInt(1, id);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) return rs.getString("content");
	        }
	        return "Hello from Docker HTTP at " + LocalDateTime.now();
	    }

	    private String getCount(Connection connection) throws Exception {
	        try (Statement stmt = connection.createStatement()) {
	            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS cnt FROM messages");
	            if (rs.next()) return String.valueOf(rs.getInt("cnt"));
	        }
	        return "0";
	    }

	    private int insertMessage(Connection connection, String content) throws Exception {
	        try (CallableStatement stmt = connection.prepareCall(
	                "INSERT INTO messages(content) VALUES(?) RETURNING id")) {
	            stmt.setString(1, content);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) return rs.getInt("id");
	        }
	        return -1;
	    }
	}

}
