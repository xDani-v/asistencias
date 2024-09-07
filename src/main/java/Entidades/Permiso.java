/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.util.Date;

/**
 *
 * @author NITRO
 */
public class Permiso {
    private int idPermiso;
    private int idEmpleado;
    private Date fecha;
    private String tipoPermiso;
    private String comentarios;

    public Permiso(){}
    
    public Permiso(int idPermiso, int idEmpleado, Date fecha, String tipoPermiso, String comentarios) {
        this.idPermiso = idPermiso;
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
        this.tipoPermiso = tipoPermiso;
        this.comentarios = comentarios;
    }

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Permiso{" + "idPermiso=" + idPermiso + ", idEmpleado=" + idEmpleado + ", fecha=" + fecha + ", tipoPermiso=" + tipoPermiso + ", comentarios=" + comentarios + '}';
    }
    
    
}
