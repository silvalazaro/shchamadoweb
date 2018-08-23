package com.silvalazaro.ws;

import com.silvalazaro.modelo.Chamado;
import com.silvalazaro.modelo.ChamadoTipo;
import com.silvalazaro.modelo.Cliente;
import com.silvalazaro.modelo.Modelo;
import com.silvalazaro.modelo.Tecnico;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Representa o recurso de manipulação de Chamado
 *
 * @author Lázaro Silva
 */
@Path("chamado")
public class WSChamado extends Facade<Modelo> {

    public WSChamado() {
        super(Modelo.class);
    }

    @GET
    @Path("sincronizar")
    public Response atualizar() throws Excecao {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException ex) {
            throw new Excecao(Response.Status.CREATED, ex.getMessage());
        }
        try {
            String msAccDB = "/tmp/dados.mdb";
            String dbURL = "jdbc:ucanaccess://" + msAccDB;
            con = DriverManager.getConnection(dbURL, "admin", "!(&&!!)&");
            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM CHAMADO WHERE CODIGO > 3951");
            Chamado chamado;
            Cliente cliente;
            // atualiza chamados
            while (result.next()) {
                chamado = new Chamado();
                cliente = new Cliente();
                chamado.setShCodigo(result.getString("CODIGO"));
                chamado.setShTipo(ChamadoTipo.valueOf(result.getString("TIPO")));
                chamado.setTelefone(result.getString("FONES"));
                chamado.setEmail(result.getString("EMAIL"));
                chamado.setTecnico(result.getString("PARA"));
                chamado.setPrioridade(result.getInt("PRIORIDADE"));
                chamado.setRealizado(result.getInt("REALIZADO"));
                chamado.setData(result.getDate("DIA_CHAMADO"));
                chamado.setDescricao("OBS");
                chamado.setEndereco(result.getString("ENDER_CLI"));
                cliente.setShCodigo(result.getString("COD_CLIENTE"));
                cliente.setNome(result.getString("NOME_AVULSO"));
                chamado.setCliente(cliente);
            }
            // atualiza clientes
            result = statement.executeQuery("SELECT * FROM CHAMADO WHERE CLIENTES > 3951");
        } catch (SQLException sqlex) {
            throw new Excecao(Response.Status.CREATED, sqlex.getMessage());
        }
        return Response.ok("sucesso", MediaType.TEXT_PLAIN).build();
    }
}
