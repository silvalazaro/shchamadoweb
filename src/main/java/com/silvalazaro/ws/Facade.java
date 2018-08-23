package com.silvalazaro.ws;

import com.silvalazaro.Util;
import com.silvalazaro.modelo.Modelo;
import com.silvalazaro.modelo.busca.Busca;
import com.silvalazaro.modelo.busca.Filtro;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Representa uma fachada de um recurso do Web Service
 *
 * @author Lazaro Silva
 * @param <T>
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class Facade<T extends Modelo> {

    protected Class<T> classe;
    private static final EntityManagerFactory EMF = null;
    private final EntityManager EM = null;
    protected Connection con = null;
    protected Statement statement = null;
    protected ResultSet result = null;

    public Facade(Class<T> classe) {
        this.classe = classe;
//        EM = EMF.createEntityManager();
    }

    /**
     * Persiste um objeto no banco de dados
     *
     * @param modelo Objeto que extenda Modelo
     * @return Response
     */
    public T salvar(T modelo) {
        EM.getTransaction().begin();
        EM.persist(modelo);
        EM.getTransaction().commit();
        return modelo;
    }

    /**
     * Busca uma entidade
     *
     * @param id Id da entidade
     * @return
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response selecionar(@PathParam("id") String id) {
        CriteriaBuilder cb = EM.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(classe);
        Root root = query.from(classe);
        query.where(cb.equal(root.get("id"), id));
        T modelo = (T) EM.createQuery(query).getSingleResult();
        return Response.ok().entity(modelo).build();
    }

    /**
     * Salva uma entidade
     *
     * @param modelo
     * @return
     */
    @POST
    public Response criar(T modelo) {
        modelo = salvar(modelo);
        return Response.status(Response.Status.CREATED).entity(modelo).build();
    }

    /**
     * Salva uma lista de entidades
     *
     * @param lista
     * @return
     */
    @POST
    public Response criar(List<T> lista) {
        EM.getTransaction().begin();
        lista.forEach(modelo -> {
            EM.persist(modelo);
        });
        EM.getTransaction().commit();
        return Response.status(Response.Status.CREATED).entity(lista).build();
    }

    /**
     * Remove um objeto do banco de dados
     *
     * @param id Chave primária do objeto
     * @return
     */
    @DELETE
    @Path("{id}")
    public Response remover(@PathParam("id") String id) {
        EM.getTransaction().begin();
        CriteriaBuilder cb = EM.getCriteriaBuilder();
        CriteriaDelete del = cb.createCriteriaDelete(classe);
        Root root = del.from(classe);
        del.where(cb.equal(root.get("id"), id));
        EM.createQuery(del).executeUpdate();
        EM.getTransaction().commit();
        return Response.ok().build();
    }

    /**
     * Método que remove todos os objetos
     *
     * @return
     */
    @DELETE
    public Response remover() {
        EM.getTransaction().begin();
        CriteriaBuilder cb = EM.getCriteriaBuilder();
        CriteriaDelete del = cb.createCriteriaDelete(classe);
        Root root = del.from(classe);
        Query query = EM.createQuery(del);
        query.executeUpdate();
        EM.getTransaction().commit();
        return Response.ok().build();
    }

    /**
     * Método que realiza busca de objetos
     *
     * @param busca Objeto que contem os parametros da busca
     * @return
     * @throws com.silvalazaro.ws.Excecao
     */
    @POST
    @Path("busca")
    public Response buscar(Busca busca) throws Excecao {
        CriteriaBuilder cb = EM.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(classe);
        Root root = query.from(classe);
        if (busca != null) {
            if (busca.getFiltro() != null) {
                Predicate where = filtroToPredicate(busca.getFiltro(), cb, root);
                query.where(where);
            }
        }
        List lista = EM.createQuery(query).getResultList();
        return Response.ok().entity(lista).build();
    }

    /**
     * Gera um Predicate a partir de um Filtro
     *
     * @param filtro
     * @param cb
     * @param root
     * @return
     * @throws Excecao
     */
    private Predicate filtroToPredicate(Filtro filtro, CriteriaBuilder cb, Root root) throws Excecao {
        Predicate conjunto;
        Predicate temp = null;
        Predicate and = null;
        Predicate or = null;
        // obtem AND
        if (filtro.getE() != null && filtro.getE().size() > 0) {
            Predicate[] es = new Predicate[filtro.getE().size()];
            for (int i = 0; i < filtro.getE().size(); i++) {
                es[i] = filtroToPredicate(filtro.getE().get(i), cb, root);
            }
            and = cb.and(es);
        }
        // obtem OR
        if (filtro.getOu() != null && filtro.getOu().size() > 0) {
            Predicate[] os = new Predicate[filtro.getE().size()];
            for (int i = 0; i < filtro.getOu().size(); i++) {
                os[i] = filtroToPredicate(filtro.getE().get(i), cb, root);
            }
            and = cb.or(os);
        }
        switch (filtro.getOperador()) {
            case "eq":
                temp = cb.equal(root.get(filtro.getPropriedade()), filtro.getValor());
                break;
            case "lt":
                if (filtro.getTipo().equals("date")) {
                    temp = cb.lessThan(root.get(filtro.getPropriedade()), Util.data(filtro.getValor()));
                } else {
                    temp = cb.lt(root.get(filtro.getPropriedade()), Util.parseNumber(filtro.getTipo(), filtro.getValor()));
                }
                break;
            case "le":
                if (filtro.getTipo().equals("date")) {
                    temp = cb.lessThanOrEqualTo(root.get(filtro.getPropriedade()), Util.data(filtro.getValor()));
                } else {
                    temp = cb.le(root.get(filtro.getPropriedade()), Util.parseNumber(filtro.getTipo(), filtro.getValor()));
                }
                break;
            case "gt":
                if (filtro.getTipo().equals("date")) {
                    temp = cb.greaterThan(root.get(filtro.getPropriedade()), Util.data(filtro.getValor()));
                } else {
                    temp = cb.gt(root.get(filtro.getPropriedade()), Util.parseNumber(filtro.getTipo(), filtro.getValor()));
                }
                break;
            case "ge":
                if (filtro.getTipo().equals("date")) {
                    temp = cb.lessThanOrEqualTo(root.get(filtro.getPropriedade()), Util.data(filtro.getValor()));
                } else {
                    temp = cb.ge(root.get(filtro.getPropriedade()), Util.parseNumber(filtro.getTipo(), filtro.getValor()));
                }
                break;
            case "%":
                temp = cb.like(root.get(filtro.getPropriedade()), filtro.getValor());
                break;
            default:
                throw new Excecao(Response.Status.BAD_REQUEST, "Operador condicional inválido");
        }
        if (and != null) {
            conjunto = cb.and(temp, and);
            if (or != null) {
                conjunto = cb.or(conjunto, or);
            }
        } else if (or != null) {
            conjunto = cb.or(temp, or);
        } else {
            conjunto = temp;
        }
        return conjunto;
    }

    /**
     * Atualiza uma entidade
     *
     * @param modelo
     * @return
     */
    @PUT
    public Response atualizar(T modelo) {
        EM.getTransaction().begin();
        modelo = EM.merge(modelo);
        EM.getTransaction().commit();
        return Response.ok().entity(modelo).build();
    }

    /**
     * Atualiza uma lista de entidades
     *
     * @param lista
     * @return
     */
    @PUT
    @Path("lista")
    public Response atualizar(List<T> lista) {
        return Response.ok().entity(lista).build();
    }

}
