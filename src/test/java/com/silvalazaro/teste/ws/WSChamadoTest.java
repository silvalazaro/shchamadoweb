package com.silvalazaro.teste.ws;

import com.silvalazaro.teste.HttpMetodo;
import com.silvalazaro.teste.ServidorTest;
    import javax.ws.rs.core.Response;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Lazaro Silva
 */
public class WSChamadoTest extends TestCase {

    private String link;
    private String message;
    public WSChamadoTest() {
        link = ServidorTest.link + "chamado/";
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        // limpar registros
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSincronizarDadosComSHOficina() {
        Response resp = ServidorTest.request(link + "sincronizar", HttpMetodo.GET);
        try {
            message = resp.readEntity(String.class);
        } catch (Exception e) {
            message = "";
        }
        assertEquals(message, resp.getStatus(), 200);
        
    }
}
