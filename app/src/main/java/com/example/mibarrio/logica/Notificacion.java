package com.example.mibarrio.logica;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Notificacion implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("idusuario")
    private int idUsuario;
    @SerializedName("iddestino")
    private int idDestino;
    @SerializedName("tipo")
    private int tipo;
    @SerializedName("mensaje")
    private String mensaje;

    public Notificacion(){}
    public Notificacion(int idUsuario, int idDestino, int tipo, String mensaje) {
        this.idUsuario = idUsuario;
        this.idDestino = idDestino;
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    public int getId() { return id; }
    public int getIdUsuario() { return idUsuario; }
    public int getIdDestino() { return idDestino; }
    public int getTipo() { return tipo; }
    public String getMensaje() { return mensaje; }

    public void setId(int id) { this.id = id; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public void setIdDestino(int idDestino) { this.idDestino = idDestino; }
    public void setTipo(int tipo) { this.tipo = tipo; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

}
