package org.tyniest.shortner;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.jaxrs.RestResponseBuilderImpl;
import org.tyniest.shortner.store.Store;

import io.smallrye.mutiny.Uni;

@Path("/g")
public class RedirectorController {

  private final Store<String> store;

  public RedirectorController(final Store<String> store) {
    this.store = store;
  }

  @GET
  @Path("/{key}")
  public Uni<RestResponse<Void>> getKey(@PathParam("key") final String key) {
    return store.get(key)
        .map(k -> {
          if (k != null) {
            return RestResponseBuilderImpl.<Void>create(301).location(URI.create(k)).build(); // want 301
          } else {
            return RestResponse.notFound();
          }
        });
  }
}