package com.example.mibarrio.logica;

import java.io.Serializable;
import java.util.Date;

public class Comentario implements Serializable {

    private int id;
    private int idDenuncia;
    private String autor;
    private Date fecha;
    private String comentario;

    public Comentario(){}
    public Comentario(int id, int idDenuncia, String autor, Date fecha, String comentario) {
        this.id = id;
        this.idDenuncia = idDenuncia;
        this.autor = autor;
        this.fecha = fecha;
        this.comentario = comentario;
    }

    public int getId() { return id; }
    public int getIdDenuncia() { return idDenuncia; }
    public String getAutor() { return autor; }
    public Date getFecha() { return fecha; }
    public String getComentario() { return comentario; }

    public void setId(int id) { this.id = id;}
    public void setIdDenuncia(int idDenuncia) { this.idDenuncia = idDenuncia; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
