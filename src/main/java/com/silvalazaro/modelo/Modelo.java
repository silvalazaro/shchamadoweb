package com.silvalazaro.modelo;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Classe que representa o Modelo
 *
 * @author Lazaro
 */
@MappedSuperclass
public abstract class Modelo implements Serializable {

    @Id
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
