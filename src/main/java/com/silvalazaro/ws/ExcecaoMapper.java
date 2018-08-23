package com.silvalazaro.ws;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Classe que centraliza o tratamento de exeções do Web Service
 *
 * @author Lázaro Silva
 */
@Provider
public class ExcecaoMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        ObjectNode json = new ObjectNode(JsonNodeFactory.instance);
        json.put("erro", e.getMessage());
        System.err.println(e.getMessage());
        try {
            return ((Excecao) e).getReponse();
        } catch (Exception ex) {
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
    }

}
