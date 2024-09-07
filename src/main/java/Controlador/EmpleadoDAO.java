/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Datos.Conexion;
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
public class EmpleadoDAO {
    private static final String BASE_QUERY = "SELECT * FROM Empleados WHERE ";
    private static final String Insertar = "INSERT INTO Empleados (nombre, apellido, correo_electronico, telefono, cedula, password) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String Modificar = "UPDATE Empleados SET nombre = ?, apellido = ?, correo_electronico = ?, telefono = ?, cedula = ?, password = ? WHERE id_empleado = ?";
    private static final String Eliminar = "DELETE FROM Empleados WHERE id_empleado = ?";
    private static final String BUSCAR_EMPLEADO = "SELECT * FROM empleados WHERE id_empleado = ?";

   public List<Empleado> ListarEmpleado(String filtro) {
        List<Empleado> lista = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultado = null;

        try {
            Conexion con = new Conexion();
            connection = con.ObtenerConexion();

            // Construye la consulta con el filtro
            String query = buildQuery(filtro);

            // Ejecuta la consulta
            statement = connection.createStatement();
            resultado = statement.executeQuery(query);

            // Procesa los resultados
            while (resultado.next()) {
                Empleado emp = new Empleado(
                    resultado.getInt("id_empleado"),
                    resultado.getString("nombre"),
                    resultado.getString("apellido"),
                    resultado.getString("correo_electronico"),
                    resultado.getString("telefono"),
                    resultado.getString("cedula"),
                    resultado.getString("password")
                );
                lista.add(emp);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                if (resultado != null) resultado.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        return lista;
    }

    // Método para construir la consulta con el filtro
    private String buildQuery(String filtro) {
        // Aquí construimos una consulta con filtros en los campos. Puedes ajustar esto según tus necesidades.
        return BASE_QUERY + 
               "nombre LIKE '%" + filtro + "%' OR " +
               "apellido LIKE '%" + filtro + "%' OR " +
               "correo_electronico LIKE '%" + filtro + "%' OR " +
               "telefono LIKE '%" + filtro + "%' OR " +
               "cedula LIKE '%" + filtro + "%' OR " +
               "password LIKE '%" + filtro + "%'";
    }

    public DefaultTableModel VisualizarEmpleado(List<Empleado> lista) {
        String[] titulos = {"Id_empleado", "Nombre", "Apellido", "Correo Electrónico", "Teléfono", "Cédula"};
        String[] registro = new String[6];
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        for (Empleado emp : lista) {
            registro[0] = String.valueOf(emp.getIdEmpleado());
            registro[1] = emp.getNombre();
            registro[2] = emp.getApellido();
            registro[3] = emp.getCorreoElectronico();
            registro[4] = emp.getTelefono();
            registro[5] = emp.getCedula();
            modelo.addRow(registro);
        }
        return modelo;
    }

    public boolean InsertarEmpleado(Empleado emp) {
        Conexion con = new Conexion();
        boolean op = false;
        try (Connection connection = con.ObtenerConexion()) {
            PreparedStatement pst = connection.prepareStatement(Insertar);
            pst.setString(1, emp.getNombre());
            pst.setString(2, emp.getApellido());
            pst.setString(3, emp.getCorreoElectronico());
            pst.setString(4, emp.getTelefono());
            pst.setString(5, emp.getCedula());
            pst.setString(6, emp.getPassword());
            int n = pst.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }

    public boolean ModificarEmpleado(Empleado emp) {
        Conexion con = new Conexion();
        boolean op = false;
        try (Connection connection = con.ObtenerConexion()) {
            PreparedStatement pst = connection.prepareStatement(Modificar);
            pst.setString(1, emp.getNombre());
            pst.setString(2, emp.getApellido());
            pst.setString(3, emp.getCorreoElectronico());
            pst.setString(4, emp.getTelefono());
            pst.setString(5, emp.getCedula());
            pst.setString(6, emp.getPassword());
            pst.setInt(7, emp.getIdEmpleado());
            int n = pst.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }

    public boolean EliminarEmpleado(int idEmpleado) {
        Conexion con = new Conexion();
        boolean op = false;
        try (Connection connection = con.ObtenerConexion()) {
            PreparedStatement pst = connection.prepareStatement(Eliminar);
            pst.setInt(1, idEmpleado);
            int n = pst.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }
    
    public boolean Login(String cedula, String password) {
    String query = "SELECT * FROM Empleados WHERE cedula = ? AND password = ?";
    boolean autenticado = false;

    try (Connection connection = new Conexion().ObtenerConexion();
         PreparedStatement pst = connection.prepareStatement(query)) {

        pst.setString(1, cedula);
        pst.setString(2, password);

        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                autenticado = true;
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }

    return autenticado;
    }
    
    public Empleado buscarEmpleado(int idEmpleado) {
        Empleado empleado = null;
        Conexion con = new Conexion();
        try (Connection connection = con.ObtenerConexion();
             PreparedStatement pst = connection.prepareStatement(BUSCAR_EMPLEADO)) {
            pst.setInt(1, idEmpleado);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id_empleado");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo_electronico");
                String telefono = rs.getString("telefono");
                String cedula = rs.getString("cedula");
                String password = rs.getString("password");
                
                empleado = new Empleado(id, nombre, apellido, correo, telefono, cedula, password);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar empleado: " + e.getMessage());
        }
        return empleado;
    }
    
       public void EmpleadosCombo(JComboBox comboBox) {
        List<Empleado> listaEmpleados = listarEmpleados();
        comboBox.removeAllItems(); // Limpia el JComboBox antes de agregar nuevos elementos.

        for (Empleado empleado : listaEmpleados) {
            comboBox.addItem(empleado);
        }
    }
    ///datos combo
    public List<Empleado> listarEmpleados() {
        List<Empleado> lista = new ArrayList<>();
           Conexion con = new Conexion();
        try (Connection connection = con.ObtenerConexion();
             PreparedStatement pst = connection.prepareStatement("SELECT * FROM Empleados");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Empleado empleado = new Empleado(
                    rs.getInt("id_empleado"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo_electronico"),
                    rs.getString("telefono"),
                    rs.getString("cedula"),
                    rs.getString("password")
                );
                lista.add(empleado);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return lista;
    }
}
