package cas.jee.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
public class MyResource {

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "Hello World";
	}

	@GET
	@Path("/helloto{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHelloTo(@PathParam("id") String name) {
		return "Hello " + name;
	}

}
