/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
 
import Datos.Conexion;
import Entidades.Permiso;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PermisoDAO {
     private static final String BASE_QUERY = "SELECT * FROM Permisos WHERE ";
    private static final String INSERTAR = "INSERT INTO Permisos (id_empleado, fecha, tipo_permiso, comentarios) VALUES (?, ?, ?, ?)";
    private static final String MODIFICAR = "UPDATE Permisos SET id_empleado = ?, fecha = ?, tipo_permiso = ?, comentarios = ? WHERE id_permiso = ?";
    private static final String ELIMINAR = "DELETE FROM Permisos WHERE id_permiso = ?";
    private static final String BUSCAR = "SELECT * FROM Permisos WHERE id_permiso = ?";
    private static final String LISTAR_PERMISOS = "SELECT * FROM Permisos";

    private Connection obtenerConexion() throws SQLException {
        Conexion con = new Conexion();
        return con.ObtenerConexion();
    }

    public boolean insertarPermiso(Permiso permiso) {
        boolean exito = false;
        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(INSERTAR)) {
            pst.setInt(1, permiso.getIdEmpleado());
            pst.setDate(2, new java.sql.Date(permiso.getFecha().getTime())); 
            pst.setString(3, permiso.getTipoPermiso());
            pst.setString(4, permiso.getComentarios());
            int n = pst.executeUpdate();
            exito = (n != 0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return exito;
    }

    public boolean modificarPermiso(Permiso permiso) {
        boolean exito = false;
        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(MODIFICAR)) {
            pst.setInt(1, permiso.getIdEmpleado());
            pst.setDate(2, new java.sql.Date(permiso.getFecha().getTime())); 
            pst.setString(3, permiso.getTipoPermiso());
            pst.setString(4, permiso.getComentarios());
            pst.setInt(5, permiso.getIdPermiso());
            int n = pst.executeUpdate();
            exito = (n != 0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return exito;
    }

    public boolean eliminarPermiso(int idPermiso) {
        boolean exito = false;
        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(ELIMINAR)) {
            pst.setInt(1, idPermiso);
            int n = pst.executeUpdate();
            exito = (n != 0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return exito;
    }

    public Permiso buscarPermiso(int idPermiso) {
        Permiso permiso = null;
        try (Connection connection = obtenerConexion();
             PreparedStatement pst = connection.prepareStatement(BUSCAR)) {
            pst.setInt(1, idPermiso);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    permiso = new Permiso(
                        rs.getInt("id_permiso"),
                        rs.getInt("id_empleado"),
                        rs.getDate("fecha"),
                        rs.getString("tipo_permiso"),
                        rs.getString("comentarios")
                    );
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return permiso;
    }

  public List<Permiso> listarPermisos(String filtro) {
    List<Permiso> lista = new ArrayList<>();
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
            Permiso permiso = new Permiso(
                rs.getInt("id_permiso"),
                rs.getInt("id_empleado"),
                rs.getDate("fecha"),
                rs.getString("tipo_permiso"),
                rs.getString("comentarios")
            );
            lista.add(permiso);
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
        return "SELECT * FROM Permisos";
    } else {
        // Escapar las comillas simples en el filtro para evitar problemas de sintaxis
        String filtroEscapado = filtro.replace("'", "''");

        // Construir la consulta con el filtro escapado
        return "SELECT * FROM Permisos WHERE " +
               "id_empleado LIKE '%" + filtroEscapado + "%' OR " +
               "fecha LIKE '%" + filtroEscapado + "%' OR " +
               "tipo_permiso LIKE '%" + filtroEscapado + "%' OR " +
               "comentarios LIKE '%" + filtroEscapado + "%'";
    }
}

    

    public DefaultTableModel visualizarPermisos(List<Permiso> lista) {
        String[] titulos = {"Id Permiso", "Id Empleado", "Fecha", "Tipo Permiso", "Comentarios"};
        String[] registro = new String[5];
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        for (Permiso permiso : lista) {
            registro[0] = String.valueOf(permiso.getIdPermiso());
            registro[1] = String.valueOf(permiso.getIdEmpleado());
            registro[2] = permiso.getFecha().toString(); // Asegúrate de convertir la fecha a un formato adecuado si es necesario
            registro[3] = permiso.getTipoPermiso();
            registro[4] = permiso.getComentarios();
            modelo.addRow(registro);
        }
        return modelo;
    }
}
