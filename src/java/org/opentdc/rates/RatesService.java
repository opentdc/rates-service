package org.opentdc.rates;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.opentdc.exception.DuplicateException;
import org.opentdc.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CXFNonSpringJaxrsServlet (defined in web.xml) uses Singleton as a default
 * scope for service classes specified by a jaxrs.serviceClasses servlet
 * parameter.
 * 
 * @author Bruno Kaiser
 *
 */
@Path("/api/rate")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RatesService {
	private static StorageProvider sp = null;

	// instance variables
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Invoked for each service invocation (Constructor)
	 */
	public RatesService(@Context ServletContext context) {
		logger.info("> RatesService()");
		if (sp == null) {
			sp = StorageFactory.getStorageProvider(context);
		}
		logger.info("RatesService() initialized");
	}

	/******************************** company *****************************************/
	@GET
	@Path("/")
	public List<RatesModel> list() {
		return sp.list();
	}

	@POST
	@Path("/")
	public RatesModel create(RatesModel rate) throws DuplicateException {
		return sp.create(rate);
	}

	@GET
	@Path("/{id}")
	public RatesModel read(@PathParam("id") String id) throws NotFoundException {
		return sp.read(id);
	}

	@PUT
	@Path("/")
	public RatesModel update(RatesModel rate) throws NotFoundException {
		return sp.update(rate);
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") String id) throws NotFoundException {
		sp.delete(id);
	}

	@GET
	@Path("/count")
	public int count() {
		return sp.count();
	}

}
