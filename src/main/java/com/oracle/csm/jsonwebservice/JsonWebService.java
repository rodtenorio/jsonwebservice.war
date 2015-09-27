package com.oracle.csm.jsonwebservice;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.oracle.csm.soapjsonproxy.model.Pessoa;

@Path("/JsonWebService")
public class JsonWebService {

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "I'm working! " + new Date().toString();
    }

    @POST
    @Path("doTask")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response doTask(final Pessoa pessoa) {
        pessoa.setNome("Mudei pra JSON =)");

        final ObjectMapper om = new ObjectMapper();
        String result;
        try {
            result = om.writeValueAsString(pessoa);
        } catch (final JsonGenerationException e) {
            throw new RuntimeException(e);
        } catch (final JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return Response.status(201).entity(result).build();
    }

    @POST
    @Path("doStringTask")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doTask(final String pessoa) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(pessoa);
            jsonObject.put("servidor", "Recebi isto.");
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
        return Response.status(201).entity(jsonObject.toString()).build();
    }
}
