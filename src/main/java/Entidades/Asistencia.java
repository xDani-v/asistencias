/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author NITRO
 */
public class Asistencia {
     private int idAsistencia;
    private int idEmpleado;
    private int idTurno;
    private Date fecha;
    private Time horaEntrada;
    private Time horaSalida;
    private String comentarios;

    public Asistencia() {
    }

    public Asistencia(int idAsistencia, int idEmpleado, int idTurno, Date fecha, Time horaEntrada, Time horaSalida, String comentarios) {
        this.idAsistencia = idAsistencia;
        this.idEmpleado = idEmpleado;
        this.idTurno = idTurno;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.comentarios = comentarios;
    }

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Time horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Time getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Asistencia{" + "idAsistencia=" + idAsistencia + ", idEmpleado=" + idEmpleado + ", idTurno=" + idTurno + ", fecha=" + fecha + ", horaEntrada=" + horaEntrada + ", horaSalida=" + horaSalida + ", comentarios=" + comentarios + '}';
    }
    
    
}
