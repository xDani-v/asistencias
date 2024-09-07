/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Datos.Conexion;
import Entidades.Ausencia;
import java.sql.Connection;
 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author NITRO
 */
public class AusenciaDAO {
     private static final String INSERTAR = "INSERT INTO Ausencias (id_empleado, fecha_inicio, fecha_fin, motivo) VALUES (?, ?, ?, ?)";
    private static final String MODIFICAR = "UPDATE Ausencias SET id_empleado = ?, fecha_inicio = ?, fecha_fin = ?, motivo = ? WHERE id_ausencia = ?";
    private static final String ELIMINAR = "DELETE FROM Ausencias WHERE id_ausencia = ?";
    private static final String BUSCAR = "SELECT * FROM Ausencias WHERE id_ausencia = ?";
    private static final String LISTAR_AUSENCIAS = "SELECT * FROM Ausencias";
    private static final String BASE_QUERY = "SELECT * FROM Ausencias WHERE ";

    private Connection obtenerConexion() throws SQLException {
        Conexion con = new Conexion();
        return con.ObtenerConexion();
    }

    public boolean insertarAusencia(Ausencia ausencia) {
        boolean exito = false;
        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(INSERTAR)) {
            pst.setInt(1, ausencia.getIdEmpleado());
            pst.setDate(2, new java.sql.Date(ausencia.getFechaInicio().getTime())); // Conversión a java.sql.Date
            pst.setDate(3, new java.sql.Date(ausencia.getFechaFin().getTime())); // Conversión a java.sql.Date
            pst.setString(4, ausencia.getMotivo());
            int n = pst.executeUpdate();
            exito = (n != 0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return exito;
    }

    public boolean modificarAusencia(Ausencia ausencia) {
        boolean exito = false;
        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(MODIFICAR)) {
            pst.setInt(1, ausencia.getIdEmpleado());
            pst.setDate(2, new java.sql.Date(ausencia.getFechaInicio().getTime())); // Conversión a java.sql.Date
            pst.setDate(3, new java.sql.Date(ausencia.getFechaFin().getTime())); // Conversión a java.sql.Date
            pst.setString(4, ausencia.getMotivo());
            pst.setInt(5, ausencia.getIdAusencia());
            int n = pst.executeUpdate();
            exito = (n != 0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return exito;
    }

    public boolean eliminarAusencia(int idAusencia) {
        boolean exito = false;
        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(ELIMINAR)) {
            pst.setInt(1, idAusencia);
            int n = pst.executeUpdate();
            exito = (n != 0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return exito;
    }

    public Ausencia buscarAusencia(int idAusencia) {
        Ausencia ausencia = null;
        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(BUSCAR)) {
            pst.setInt(1, idAusencia);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    ausencia = new Ausencia(
                        rs.getInt("id_ausencia"),
                        rs.getInt("id_empleado"),
                        rs.getDate("fecha_inicio"), // ResultSet devuelve java.sql.Date
                        rs.getDate("fecha_fin"),     // ResultSet devuelve java.sql.Date
                        rs.getString("motivo")
                    );
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return ausencia;
    }

  public List<Ausencia> listarAusencias(String filtro) {
    List<Ausencia> lista = new ArrayList<>();
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
            Ausencia ausencia = new Ausencia(
                rs.getInt("id_ausencia"),
                rs.getInt("id_empleado"),
                rs.getDate("fecha_inicio"),
                rs.getDate("fecha_fin"),
                rs.getString("motivo")
            );
            lista.add(ausencia);
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
        return "SELECT * FROM Ausencias";
    } else {
        // Escapar las comillas simples en el filtro para evitar problemas de sintaxis
        String filtroEscapado = filtro.replace("'", "''");

        // Construir la consulta con el filtro escapado
        return "SELECT * FROM Ausencias WHERE " +
               "id_empleado LIKE '%" + filtroEscapado + "%' OR " +
               "fecha_inicio LIKE '%" + filtroEscapado + "%' OR " +
               "fecha_fin LIKE '%" + filtroEscapado + "%' OR " +
               "motivo LIKE '%" + filtroEscapado + "%'";
    }
}

   
     
    public DefaultTableModel visualizarAusencias(List<Ausencia> lista) {
        String[] titulos = {"Id Ausencia", "Id Empleado", "Fecha Inicio", "Fecha Fin", "Motivo"};
        String[] registro = new String[5];
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        for (Ausencia ausencia : lista) {
            registro[0] = String.valueOf(ausencia.getIdAusencia());
            registro[1] = String.valueOf(ausencia.getIdEmpleado());
            registro[2] = ausencia.getFechaInicio().toString(); // java.util.Date a String
            registro[3] = ausencia.getFechaFin().toString();    // java.util.Date a String
            registro[4] = ausencia.getMotivo();
            modelo.addRow(registro);
        }
        return modelo;
    }
}
