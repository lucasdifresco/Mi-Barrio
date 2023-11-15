package com.example.mibarrio.logica;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;

public class Usuario implements Serializable {
    @SerializedName("id")
    private long id;
    @SerializedName("usuario")
    private String usuario;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("apellido")
    private String apellido;
    @SerializedName("idrol")
    private long idrol;
    @SerializedName("strRol")
    private String strRol;
    //@SerializedName("fecha")
    //private Date fecha;
    @SerializedName("estado")
    private Boolean estado;
    @SerializedName("password")
    private String password;

    public Usuario(){}
    public Usuario(long id)
    {
        this.id = id;
    }
    public Usuario(String usuario, String pass, Boolean isVecino) {
        this.usuario = usuario;
        this.password = pass;
        if(isVecino) { idrol = 1; }
        else { idrol = 2; }
    }
    public Usuario(String usuario, String nombre, String apellido, String pass) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = pass;
    }

    public long getId() { return id; }
    public String getUsuario() { return usuario; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public long getIdrol() { return idrol; }
    public String getStrRol() { return strRol; }
    //public Date getFecha() { return fecha; }
    public Boolean getEstado() { return estado; }
    public String getPassword() { return password; }

    public void setId(long id) { this.id = id; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setIdrol(long idrol) { this.idrol = idrol; }
    public void setStrRol(String strRol) { this.strRol = strRol; }
    //public void setFecha(Date fecha) { this.fecha = fecha; }
    public void setEstado(Boolean estado) { this.estado = estado; }
    public void setPassword(String password) { this.password = password; }
}
