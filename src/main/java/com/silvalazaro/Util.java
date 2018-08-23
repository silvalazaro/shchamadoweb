package com.silvalazaro;

import com.silvalazaro.ws.Excecao;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.ws.rs.core.Response;

/**
 * Contém métodos e parâmetros auxiliares, úteis no programa.
 *
 * @author Lázaro Silva
 */
public class Util {

    public static final DateTimeFormatter DATE_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate data(String valor) {
        return LocalDate.parse(valor, DATE_BR);
    }

    public static Number parseNumber(String tipo, String valor) throws Excecao {
        Number numero = null;
        switch (tipo) {
            case "int":
                numero = new Integer(valor);
                break;
            case "bigdecimal":
                BigDecimal bigDecimal = new BigDecimal(valor);
                bigDecimal.setScale(2);
                numero = bigDecimal;
                break;
            default:
                throw new Excecao(Response.Status.BAD_REQUEST, "Tipo numérico inválido.");
        }
        return numero;
    }
}
