/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Datos.Conexion;
import Entidades.Turno;
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
public class TurnoDAO {
     private static final String BASE_QUERY = "SELECT * FROM Turnos WHERE ";
    private static final String INSERTAR = "INSERT INTO Turnos (nombre, hora_inicio, hora_fin) VALUES (?, ?, ?)";
    private static final String MODIFICAR = "UPDATE Turnos SET nombre = ?, hora_inicio = ?, hora_fin = ? WHERE id_turno = ?";
    private static final String ELIMINAR = "DELETE FROM Turnos WHERE id_turno = ?";
    private static final String BUSCAR_TURNO = "SELECT * FROM Turnos WHERE id_turno = ?";

    public List<Turno> ListarTurno(String filtro) {
        List<Turno> lista = new ArrayList<>();
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
                Turno turno = new Turno(
                    resultado.getInt("id_turno"),
                    resultado.getString("nombre"),
                    resultado.getTime("hora_inicio"),
                    resultado.getTime("hora_fin")
                );
                lista.add(turno);
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
               "nombre LIKE '%" + filtro + "%'";
    }

    public DefaultTableModel VisualizarTurno(List<Turno> lista) {
        String[] titulos = {"Id_turno", "Nombre", "Hora Inicio", "Hora Fin"};
        String[] registro = new String[4];
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        for (Turno turno : lista) {
            registro[0] = String.valueOf(turno.getIdTurno());
            registro[1] = turno.getNombre();
            registro[2] = turno.getHoraInicio().toString();
            registro[3] = turno.getHoraFin().toString();
            modelo.addRow(registro);
        }
        return modelo;
    }

    public boolean InsertarTurno(Turno turno) {
        boolean op = false;
        try (Connection connection = new Conexion().ObtenerConexion();
             PreparedStatement pst = connection.prepareStatement(INSERTAR)) {
            pst.setString(1, turno.getNombre());
            pst.setTime(2, turno.getHoraInicio());
            pst.setTime(3, turno.getHoraFin());
            int n = pst.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }

    public boolean ModificarTurno(Turno turno) {
        boolean op = false;
        try (Connection connection = new Conexion().ObtenerConexion();
             PreparedStatement pst = connection.prepareStatement(MODIFICAR)) {
            pst.setString(1, turno.getNombre());
            pst.setTime(2, turno.getHoraInicio());
            pst.setTime(3, turno.getHoraFin());
            pst.setInt(4, turno.getIdTurno());
            int n = pst.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }

    public boolean EliminarTurno(int idTurno) {
        boolean op = false;
        try (Connection connection = new Conexion().ObtenerConexion();
             PreparedStatement pst = connection.prepareStatement(ELIMINAR)) {
            pst.setInt(1, idTurno);
            int n = pst.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }

    public Turno buscarTurno(int idTurno) {
        Turno turno = null;
        try (Connection connection = new Conexion().ObtenerConexion();
             PreparedStatement pst = connection.prepareStatement(BUSCAR_TURNO)) {
            pst.setInt(1, idTurno);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                turno = new Turno(
                    rs.getInt("id_turno"),
                    rs.getString("nombre"),
                    rs.getTime("hora_inicio"),
                    rs.getTime("hora_fin")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar turno: " + e.getMessage());
        }
        return turno;
    }
    
         public void TurnoCombo(JComboBox comboBox) {
        List<Turno> listaEmpleados = ListarTurno("");
        comboBox.removeAllItems(); // Limpia el JComboBox antes de agregar nuevos elementos.

        for (Turno empleado : listaEmpleados) {
            comboBox.addItem(empleado);
        }
    }
}
