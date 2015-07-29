package com.zenika.restdemo.resources;

import com.zenika.restdemo.dao.CommandsRepository;
import com.zenika.restdemo.dto.Hyperlink;
import com.zenika.restdemo.dto.SimpleCommand;
import com.zenika.restdemo.model.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: sennen
 * Date: 20/07/15
 * Time: 16:45
 */
@Component
@Path("/commands")
public class CommandsResource {
  private static final int MAX_SIZE = 100;
  @Context
  private UriInfo uriInfo;

  @Autowired
  private CommandsRepository repository;

  @GET
  public Response getCommands(@QueryParam("from") Integer from, @QueryParam("size") Integer size) {
    from = from == null ? 0 : from;
    size = size == null ? 20 : size;

    if (size > MAX_SIZE) {
      return Response.status(Response.Status.BAD_REQUEST)
                     .entity(String.format("Maximum request size is %d", MAX_SIZE))
                     .header(HttpHeaders.CONTENT_TYPE, mimeTypeWithCharsetUTF8(MimeTypeUtils.TEXT_PLAIN_VALUE))
                     .build();
    }

    List<Command> commands = repository.getCommands(from, size);
    // Have to do it here otherwise we're out of request scope and exception is thrown
    UriBuilder originalUriBuilder = getUriBuilder();

    List<SimpleCommand> simpleCommands =
      commands.parallelStream()
              .map((command) -> {
                Hyperlink commandDetailsLink = buildCommandDetailsLink(command, originalUriBuilder);
                return new SimpleCommand(command.getReference(), commandDetailsLink);
              })
              .collect(Collectors.toList());

    return Response.ok(simpleCommands, mimeTypeWithCharsetUTF8(MimeTypeUtils.APPLICATION_JSON_VALUE))
                   .header(HttpHeaders.CONTENT_RANGE,
                           getContentRange(from, commands.size(), repository.count()))
                   .build();
  }

  private static String getContentRange(int firstIndex, int contentSize, long wholeCollectionSize) {
    return String.format("%d-%d/%d", firstIndex, firstIndex + contentSize - 1, wholeCollectionSize);
  }

  private static String mimeTypeWithCharsetUTF8(String mimeType) {
    return String.format("%s;charset=utf-8", mimeType);
  }

  private Hyperlink buildCommandDetailsLink(Command command, UriBuilder uriBuilder) {
    UriBuilder localUriBuilder = uriBuilder.clone();
    String commandDetailsLink = localUriBuilder.replacePath("commands")
                                               .replaceQuery("")
                                               .path(command.getReference())
                                               .build()
                                               .toString();
    return new Hyperlink(commandDetailsLink, "command");
  }

  private UriBuilder getUriBuilder() {
    return uriInfo.getRequestUriBuilder().clone();
  }
}
