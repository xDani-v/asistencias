/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Datos.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NITRO
 */
public class ConsultaDAO {
     private static final String CONSULTA_ASISTENCIA = 
        "SELECT E.nombre, E.apellido, A.fecha, A.hora_entrada, A.hora_salida, T.nombre AS turno, A.comentarios " +
        "FROM Asistencia A " +
        "JOIN Empleados E ON A.id_empleado = E.id_empleado " +
        "JOIN Turnos T ON A.id_turno = T.id_turno " +
        "ORDER BY E.apellido, A.fecha";
     
       private static final String CONSULTA_CANTIDAD_ASISTENCIAS =
        "SELECT T.nombre AS turno, COUNT(A.id_asistencia) AS cantidad_asistencias " +
        "FROM Turnos T " +
        "LEFT JOIN Asistencia A ON T.id_turno = A.id_turno " +
        "GROUP BY T.id_turno " +
        "ORDER BY cantidad_asistencias DESC";
       
         private static final String CONSULTA_HORAS_TRABAJADAS =
        "SELECT E.nombre, " +
        "       E.apellido, " +
        "       SUM(TIMESTAMPDIFF(MINUTE, A.hora_entrada, A.hora_salida)) / 60 AS total_horas_trabajadas " +
        "FROM Asistencia A " +
        "JOIN Empleados E ON A.id_empleado = E.id_empleado " +
        "WHERE A.hora_salida IS NOT NULL " + // Asegura que solo se cuentan las entradas y salidas completas
        "GROUP BY E.id_empleado " +
        "ORDER BY total_horas_trabajadas DESC";

    private Connection obtenerConexion() throws SQLException {
        Conexion con = new Conexion();
        return con.ObtenerConexion();
    }

    public DefaultTableModel obtenerAsistenciaTabla() {
        String[] titulos = {"Nombre", "Apellido", "Fecha", "Hora Entrada", "Hora Salida", "Turno", "Comentarios"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(CONSULTA_ASISTENCIA);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                Date fecha = rs.getDate("fecha");
                Time horaEntrada = rs.getTime("hora_entrada");
                Time horaSalida = rs.getTime("hora_salida");
                String turno = rs.getString("turno");
                String comentarios = rs.getString("comentarios");

                String[] registro = {
                    nombre,
                    apellido,
                    fecha.toString(),
                    (horaEntrada != null) ? horaEntrada.toString() : "",
                    (horaSalida != null) ? horaSalida.toString() : "",
                    turno,
                    comentarios
                };

                modelo.addRow(registro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return modelo;
    }
    
    public DefaultTableModel obtenerCantidadAsistenciasTabla() {
        String[] titulos = {"Turno", "Cantidad Asistencias"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(CONSULTA_CANTIDAD_ASISTENCIAS);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String turno = rs.getString("turno");
                int cantidadAsistencias = rs.getInt("cantidad_asistencias");

                String[] registro = {
                    turno,
                    String.valueOf(cantidadAsistencias)
                };

                modelo.addRow(registro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return modelo;
    }
    
     public DefaultTableModel obtenerHorasTrabajadasTabla() {
        String[] titulos = {"Nombre", "Apellido", "Total Horas Trabajadas"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(CONSULTA_HORAS_TRABAJADAS);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                double totalHorasTrabajadas = rs.getDouble("total_horas_trabajadas");

                String[] registro = {
                    nombre,
                    apellido,
                    String.format("%.2f", totalHorasTrabajadas) // Formato con 2 decimales
                };

                modelo.addRow(registro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return modelo;
    }
}
