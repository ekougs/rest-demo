package com.zenika.restdemo.resources;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
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
    if (isMongoUp()) {
      return Response.ok().entity("Up and running").build();
    }
    return Response.serverError().entity("Up and running but mongo is down").build();
  }

  // Usage of deprecated methods but no other way to check if mongo is up
  private static boolean isMongoUp() {
    try {
      Mongo mongo = new Mongo("api-shop-mongo");
      mongo.isLocked();
      return true;
    } catch (MongoException e) {
      return false;
    }
  }
}
