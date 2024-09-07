/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.sql.Time;

/**
 *
 * @author NITRO
 */
public class Turno {
    private int idTurno;
    private String nombre;
    private Time horaInicio;
    private Time horaFin;

    // Constructor por defecto
    public Turno() {
    }

    // Constructor con parámetros
    public Turno(int idTurno, String nombre, Time horaInicio, Time horaFin) {
        this.idTurno = idTurno;
        this.nombre = nombre;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public String toString() {
        return  nombre + "[" + horaInicio + "-" + horaFin + ']';
    }
    
    
}
