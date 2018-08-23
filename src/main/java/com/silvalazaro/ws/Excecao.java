package com.silvalazaro.ws;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Lázaro Silva
 */
public class Excecao extends Exception {

    private Status status;
    
    public Excecao(Status status, String message) {
        super(message);
    }

    Response getReponse() {
        return Response.status(status).entity("").build();
    }
    
}
