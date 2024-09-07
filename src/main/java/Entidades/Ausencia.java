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
public class Ausencia {
    private int idAusencia;
    private int idEmpleado;
    private Date fechaInicio;
    private Date fechaFin;
    private String motivo;

    public Ausencia() {
    }

    public Ausencia(int idAusencia, int idEmpleado, Date fechaInicio, Date fechaFin, String motivo) {
        this.idAusencia = idAusencia;
        this.idEmpleado = idEmpleado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.motivo = motivo;
    }

    public int getIdAusencia() {
        return idAusencia;
    }

    public void setIdAusencia(int idAusencia) {
        this.idAusencia = idAusencia;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "Ausencia{" + "idAusencia=" + idAusencia + ", idEmpleado=" + idEmpleado + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", motivo=" + motivo + '}';
    }
    
    
}
