/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Datos.Conexion;
import Entidades.Asistencia;
import Entidades.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author NITRO
 */
public class asistenciaDAO {
    private static final String INSERTAR = "INSERT INTO Asistencia (id_empleado, id_turno, fecha, hora_entrada, hora_salida, comentarios) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String MODIFICAR = "UPDATE Asistencia SET id_empleado = ?, id_turno = ?, fecha = ?, hora_entrada = ?, hora_salida = ?, comentarios = ? WHERE id_asistencia = ?";
    private static final String ELIMINAR = "DELETE FROM Asistencia WHERE id_asistencia = ?";
    private static final String BUSCAR_ASISTENCIA = "SELECT * FROM Asistencia WHERE id_asistencia = ?";
    private static final String BASE_QUERY = "SELECT * FROM Asistencia";

    private Connection obtenerConexion() throws SQLException {
        Conexion con = new Conexion();
        return con.ObtenerConexion();
    }

    public List<Asistencia> listarAsistencias(String filtro) {
    List<Asistencia> lista = new ArrayList<>();
    Connection connection = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
        Conexion con = new Conexion();
        connection = con.ObtenerConexion();

        // Construye la consulta con el filtro
        String query = buildQuery(filtro);

        // Prepara la consulta
        pst = connection.prepareStatement(query);

        // Ejecuta la consulta
        rs = pst.executeQuery();

        // Procesa los resultados
        while (rs.next()) {
            Asistencia asistencia = new Asistencia(
                rs.getInt("id_asistencia"),
                rs.getInt("id_empleado"),
                rs.getInt("id_turno"),
                rs.getDate("fecha"),
                rs.getTime("hora_entrada"),
                rs.getTime("hora_salida"),
                rs.getString("comentarios")
            );
            lista.add(asistencia);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    return lista;
}

    private String buildQuery(String filtro) {
    // Verifica si el filtro está vacío o es nulo
    if (filtro == null || filtro.trim().isEmpty()) {
        // Si no hay filtro, selecciona todos los registros
        return "SELECT * FROM Asistencia";
    } else {
        // Escapar las comillas simples en el filtro para evitar problemas de sintaxis
        String filtroEscapado = filtro.replace("'", "''");

        // Construir la consulta con el filtro escapado
        return "SELECT * FROM Asistencia WHERE " +
               "id_empleado LIKE '%" + filtroEscapado + "%' OR " +
               "id_turno LIKE '%" + filtroEscapado + "%' OR " +
               "fecha LIKE '%" + filtroEscapado + "%' OR " +
               "hora_entrada LIKE '%" + filtroEscapado + "%' OR " +
               "hora_salida LIKE '%" + filtroEscapado + "%' OR " +
               "comentarios LIKE '%" + filtroEscapado + "%'";
    }
}

    public DefaultTableModel visualizarAsistencias(List<Asistencia> lista) {
        String[] titulos = {"ID Asistencia", "ID Empleado", "ID Turno", "Fecha", "Hora Entrada", "Hora Salida", "Comentarios"};
        String[] registro = new String[7];
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        for (Asistencia asis : lista) {
            registro[0] = String.valueOf(asis.getIdAsistencia());
            registro[1] = String.valueOf(asis.getIdEmpleado());
            registro[2] = String.valueOf(asis.getIdTurno());
            registro[3] = asis.getFecha().toString();
            registro[4] = asis.getHoraEntrada().toString();
            registro[5] = (asis.getHoraSalida() != null) ? asis.getHoraSalida().toString() : "";
            registro[6] = asis.getComentarios();
            modelo.addRow(registro);
        }
        return modelo;
    }

    public boolean insertarAsistencia(Asistencia asistencia) {
        boolean exito = false;
        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(INSERTAR)) {

            pst.setInt(1, asistencia.getIdEmpleado());
            pst.setInt(2, asistencia.getIdTurno());
            pst.setDate(3, new java.sql.Date(asistencia.getFecha().getTime()));
            pst.setTime(4, asistencia.getHoraEntrada());
            pst.setTime(5, asistencia.getHoraSalida());
            pst.setString(6, asistencia.getComentarios());
            int n = pst.executeUpdate();
            exito = (n != 0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return exito;
    }

    public boolean modificarAsistencia(Asistencia asistencia) {
        boolean exito = false;
        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(MODIFICAR)) {

            pst.setInt(1, asistencia.getIdEmpleado());
            pst.setInt(2, asistencia.getIdTurno());
            pst.setDate(3, new java.sql.Date(asistencia.getFecha().getTime()));
            pst.setTime(4, asistencia.getHoraEntrada());
            pst.setTime(5, asistencia.getHoraSalida());
            pst.setString(6, asistencia.getComentarios());
            pst.setInt(7, asistencia.getIdAsistencia());
            int n = pst.executeUpdate();
            exito = (n != 0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return exito;
    }

    public boolean eliminarAsistencia(int idAsistencia) {
        boolean exito = false;
        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(ELIMINAR)) {

            pst.setInt(1, idAsistencia);
            int n = pst.executeUpdate();
            exito = (n != 0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return exito;
    }

    public Asistencia buscarAsistencia(int idAsistencia) {
        Asistencia asistencia = null;
        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(BUSCAR_ASISTENCIA)) {

            pst.setInt(1, idAsistencia);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    asistencia = new Asistencia(
                        rs.getInt("id_asistencia"),
                        rs.getInt("id_empleado"),
                        rs.getInt("id_turno"),
                        rs.getDate("fecha"),
                        rs.getTime("hora_entrada"),
                        rs.getTime("hora_salida"),
                        rs.getString("comentarios")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar asistencia: " + e.getMessage());
        }
        return asistencia;
    }
    
   
}
