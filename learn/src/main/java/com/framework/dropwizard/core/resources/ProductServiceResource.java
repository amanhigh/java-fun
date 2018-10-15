package com.framework.dropwizard.core.resources;

import com.codahale.metrics.annotation.Timed;
import com.framework.dropwizard.models.MyResponse;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author amanpreet.singh
 * @version 1.0.0
 * @since 01/09/15 5:38 PM.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ProductServiceResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceResource.class);
	private final String name;
	private final String owner;

	public ProductServiceResource(String name, String owner) {

		this.name = name;
		this.owner = owner;
	}

	@GET
	@Timed
	@Path("getTrends")
	public Response getResponse(@QueryParam("category") @NotEmpty String category) {
		LOGGER.info("Serving for Category {}", category);
		final Response response;
		if (!category.equals("aman")) {
			response=Response.status(Response.Status.OK).entity(new MyResponse(category, "Hurray!!")).build();
		}
		else {
			response = Response
					.status(Response.Status.BAD_REQUEST)
					.entity(new MyResponse("Garbar", "Seems like you have lost it")).build();
		}
		return response;
	}
}
