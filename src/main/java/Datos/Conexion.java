/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author NITRO
 */
public class Conexion {
   private static Connection conexion = null; 
    private static final String usr = "root";
    private static final String pswd = "root";
    private static final String url = "jdbc:mysql://localhost:3306/control_asistencia";
    public Conexion() {
    }
    public Connection ObtenerConexion() throws SQLException
    {
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection(url, usr, pswd); 
        System.out.println("conexion exitosa");
    }
    catch(ClassNotFoundException | SQLException e)
    {
        throw new RuntimeException(e);
    }
        return conexion;
    }
}
