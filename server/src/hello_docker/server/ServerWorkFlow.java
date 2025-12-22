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
	private final static String url = "jdbc:postgresql://postgres:5432/hello_db";
	private final static String user = "user";
	private final static String password = "password";

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
			String result = null;
			if (method.equals("GET")) {
				String path = httpExchange.getRequestURI().getPath();
				String idStr = path.substring(1); // "1"
				int id = Integer.parseInt(idStr);
				result = handleGetRequest(id);
				System.out.println(result);
			}
			String response = Objects.nonNull(result) ? result : "Hello from Docker HTTP at " + LocalDateTime.now();
			httpExchange.sendResponseHeaders(200, response.getBytes().length);
			OutputStream os = httpExchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}

		private String handleGetRequest(int id) {
			try (Connection connection = DriverManager.getConnection(url, user, password)) {
				CallableStatement callableStatement = connection.prepareCall("select * from messages where id = ?");
				callableStatement.setLong(1, id);
				ResultSet rs = callableStatement.executeQuery();
				if (rs.next()) return rs.getString("content");

			} catch (Exception e) {
				e.printStackTrace();

			}
			return null;
		}

	}
}
