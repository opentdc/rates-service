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

// import io.swagger.annotations.*;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.opentdc.service.GenericService;
import org.opentdc.service.exception.DuplicateException;
import org.opentdc.service.exception.InternalServerErrorException;
import org.opentdc.service.exception.NotFoundException;
import org.opentdc.service.exception.ValidationException;

@Path("/api/rate")
// @Api(value = "/api/rate", description = "Operations about rates")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RatesService extends GenericService<ServiceProvider> {
	
	private static ServiceProvider sp = null;

	private static final Logger logger = Logger.getLogger(RatesService.class.getName());

	/**
	 * Invoked for each service invocation (Constructor)
	 * @throws ReflectiveOperationException
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

	/******************************** rate *****************************************/
	@GET
	@Path("/")
//	@ApiOperation(value = "Return a list of all rates")
	public List<RateModel> list(
		@DefaultValue(DEFAULT_QUERY) @QueryParam("query") String query,
		@DefaultValue(DEFAULT_QUERY_TYPE) @QueryParam("queryType") String queryType,
		@DefaultValue(DEFAULT_POSITION) @QueryParam("position") int position,
		@DefaultValue(DEFAULT_SIZE) @QueryParam("size") int size
	) {
		return sp.list(query, queryType, position, size);
	}

	@POST
	@Path("/")
//	@ApiOperation(value = "Create a new rate")
//	@ApiResponses(value = { 
//			@ApiResponse(code = 400, message = "Invalid ID supplied or mandatory field missing (BAD_REQUEST)"),
//			@ApiResponse(code = 409, message = "An object with the same id exists already (CONFLICT)") })
	public RateModel create(
			@Context HttpServletRequest request,
			RateModel rate) 
		throws DuplicateException, ValidationException {
		return sp.create(request, rate);
	}

	@GET
	@Path("/{id}")
//	@ApiOperation(value = "Find a rate by id", response = RatesModel.class)

	public RateModel read(
		@PathParam("id") String id
	) throws NotFoundException {
		return sp.read(id);
	}

	@PUT
	@Path("/{id}")
	public RateModel update(
		@Context HttpServletRequest request,
		@PathParam("id") String id,
		RateModel rate
	) throws NotFoundException, ValidationException {
		return sp.update(request, id, rate);
	}

	@DELETE
	@Path("/{id}")
	public void delete(
			@PathParam("id") String id) 
			throws NotFoundException, InternalServerErrorException {
		sp.delete(id);
	}
}
