package com.zenika.restdemo.resources;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * User: sennen
 * Date: 13/05/15
 * Time: 13:59
 */
@Component
@Path("/health")
public class HealthResource {
  @GET
  @Produces({"text/plain;charset=utf-8"})
  public Response health() {
    return Response.ok().entity("Up and running").build();
  }
}
