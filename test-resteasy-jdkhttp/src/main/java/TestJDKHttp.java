import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.server.sun.http.HttpContextBuilder;
import org.jboss.resteasy.spi.ResteasyDeployment;

import com.sun.net.httpserver.HttpServer;

//com.sun.net.httpserver.HttpServer
public class TestJDKHttp {

	public static void main(String[] args) throws IOException, InterruptedException {
		HttpServer httpServer = HttpServer.create(new InetSocketAddress(8001), 10);
		
		HttpContextBuilder contextBuilder = new HttpContextBuilder();
		ResteasyDeployment resteasyDeployment=contextBuilder.getDeployment();
		
		//System.out.println("http://localhost:8001/test");
		//resteasyDeployment.getActualResourceClasses().add(Resource.class);
		
		//nepouzije se @ApplicationPath("/base")
		System.out.println("http://localhost:8001/test");
		resteasyDeployment.setApplication(new MyApp());

		
		contextBuilder.bind(httpServer);
		httpServer.start();
		
		//server pobezi minutu, pak to ukoncm
		Thread.sleep(60000);
		
		contextBuilder.cleanup();
		httpServer.stop(0);

	}

	@Path("/test")
	public static class Resource {
		@GET
		@Produces("text/plain")
		public String get() {
			System.out.println("test");
			return "hello world";
		}
	}
	
	@ApplicationPath("/base")
	public static class MyApp extends Application {
		@Override
		public Set<Class<?>> getClasses() {
			HashSet<Class<?>> classes = new HashSet<Class<?>>();
			classes.add(Resource.class);
			return classes;
		}
	}

	
}
