package com.silvalazaro.teste;

import com.silvalazaro.teste.ws.WSChamadoTest;
import com.silvalazaro.ws.WS;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.plugins.server.sun.http.HttpContextBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Prepara o teste das class
 *
 * @author Lázaro Silva
 */
@RunWith(Suite.class)
@SuiteClasses({WSChamadoTest.class})
public class ServidorTest {

    public static HttpServer servidor;
    public static final String link = "http://localhost:8081/ws/";
    public static Client client = ResteasyClientBuilder.newBuilder().build();
    public static WebTarget target;

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.err.println("Inicializa");
        servidor = HttpServer.create(new InetSocketAddress(8081), 10);
        HttpContextBuilder contextBuilder = new HttpContextBuilder();
        contextBuilder.getDeployment().setApplication(new WS());
        contextBuilder.setPath("ws");
        HttpContext context = contextBuilder.bind(servidor);
        servidor.start();

    }

    @AfterClass
    public static void tearDownClass() {
        System.err.println("Finaliza");
        servidor.stop(0);
    }

    /**
     * Realiza uma requisição HTTP
     *
     * @param link Endereço da requisição
     * @param metodo Tipo do metodo
     * @return
     */
    public static Response request(String link, HttpMetodo metodo) {
        Response response = null;
        target = client.target(link);
        switch (metodo) {
            case GET:
                response = target.request().get();
                break;
            case DELETE:
                response = target.request().delete();
                break;
            case OPTIONS:
                response = target.request().options();
                break;

        }
        return response;
    }

    /**
     * Realiza uma requisição HTTP - POST
     *
     * @param link
     * @param entity
     * @return
     */
    public static Response requestPOST(String link, Entity entity) {
        target = client.target(link);
        return target.request().post(entity);
    }

    /**
     * Realiza uma requisição HTTP - PUT
     *
     * @param link Endereço da requisição
     * @param entity
     * @return
     */
    public static Response requestPUT(String link, Entity entity) {
        target = client.target(link);
        return target.request()
                .accept(MediaType.APPLICATION_JSON)
                .put(entity);
    }

}
