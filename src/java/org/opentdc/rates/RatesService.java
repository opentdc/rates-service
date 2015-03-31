/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Arbalo AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.opentdc.rates;

import java.util.List;
import java.util.logging.Logger;

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

import org.opentdc.service.GenericService;
import org.opentdc.service.exception.DuplicateException;
import org.opentdc.service.exception.NotFoundException;

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
public class RatesService extends GenericService<ServiceProvider> {
	
	private static ServiceProvider sp = null;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * Invoked for each service invocation (Constructor)
	 */
	public RatesService(
		@Context ServletContext context
	) throws ReflectiveOperationException{
		logger.info("> RatesService()");
		if (sp == null) {
			sp = this.getServiceProvider(RatesService.class, context);
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
