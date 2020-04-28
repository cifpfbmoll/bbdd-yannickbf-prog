/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica8;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yann
 */
public class Practica8 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Connection con = establecerConexion();
            Scanner lector = new Scanner(System.in);
            boolean seguirMostrandoMenu = true;
            while(seguirMostrandoMenu){
                System.out.println("Dime una opcion:");
                System.out.println("1-Consulta");
                System.out.println("2-Actualizacion");
                System.out.println("3-Insercion");
                System.out.println("4-Transacciones");
                System.out.println("5-Salir");
                int opcion = lector.nextInt();
                switch(opcion){
                    case 1:
                            menuConsulta(con);
                        break;  
                    case 2:
                        updateTabla(con);
                        break;
                    case 4:
                        transacciones(con);
                        break;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getSQLState());
            System.out.println(ex.getMessage());
            Logger.getLogger(Practica8.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void transacciones(Connection con) throws SQLException {
        Scanner lector = new Scanner(System.in);
        System.out.println("1-Actualizacion simple");
        System.out.println("2-Transaccion_1");
        System.out.println("3-Transaccion_2");
        System.out.println("Dime una opcion");
        int opcion = lector.nextInt();
        switch(opcion){
            case 1:
                actualizacionSimple(con);
                break;
            case 2:
                transaccion_1(con);
                break;
        }
    }

    public static void transaccion_1(Connection con) throws SQLException{
        //¿Se actualiza la tabla si falla la primera, segunda o tercera sentencia?
        //¿Y si se ejecuta correctamente las tres primeras sentencias que forman parte de la transacción y falla la última qué ocurre?
        //¿Qué ocurre si dejas el autocommit a false y ejecutas el apartado b y luego el a?
        /*con.setAutoCommit(false);
        String actualitzaPrecioCerveza = "update serves set price=? where bar =? and beer=?";
        PreparedStatement preciosCervezas = con.prepareStatement(actualitzaPrecioCerveza);
        preciosCervezas.setDouble(1,2.70);
        preciosCervezas.setString(2,"Down Under Pub");
        preciosCervezas.setString(3,"Amstel");
        int n = preciosCervezas.executeUpdate (); // Ejecuta la sentencia con los valores // suministrados.
        Statement st = con.createStatement ();
        int añadirCerveza2 = st.executeUpdate("update serves set price=2.30 where bar ='Down Under Pub' and beer='Budweiser'");
        int añadirCerveza3 = st.executeUpdate("update serves set price=2.80 where bar ='Down Under Pub' and beer='Corona'");
        con.commit();
        int añadirCerveza4 = st.executeUpdate("update serves set price=2.45 where bar ='James Joyce Pub' and beer='Amstel'");
        //con.setAutoCommit(true);
        preciosCervezas.close();
        st.close();*/
        /*
        try{
            con.setAutoCommit(false);
            String actualitzaPrecioCerveza = "update serves set price=? where bar =? and beer=?";
            PreparedStatement preciosCervezas = con.prepareStatement(actualitzaPrecioCerveza);
            preciosCervezas.setDouble(1,2.70);
            preciosCervezas.setString(2,"Down Under Pub");
            preciosCervezas.setString(3,"Amstel");
            int n = preciosCervezas.executeUpdate (); // Ejecuta la sentencia con los valores // suministrados.
            Statement st = con.createStatement ();
            int añadirCerveza2 = st.executeUpdate("update serves set price=2.30 where bar ='Down Under Pub' and beer='Budweiser'");
            int añadirCerveza3 = st.executeUpdate("update serves set price=2.80 where bar ='Down Under Pub' and beer='Corona'");
            con.commit();
            int añadirCerveza4 = st.executeUpdate("update serves set price=2.45 where bar ='James Joyce Pub' and beer='Amstel'");
            preciosCervezas.close();
            st.close();
        }
        catch(SQLException ex){
            System.out.println("SQLSTATE " + ex.getSQLState() + "SQLMESSAGE" +      ex.getMessage());
            System.out.println("Hago rollback");
            con.rollback();    
        }
        finally{
            con.setAutoCommit(true);
        }
        */
        try{
            con.setAutoCommit(false);
            String actualitzaPrecioCerveza = "update serves set price=? where bar =? and beer=?";
            PreparedStatement preciosCervezas = con.prepareStatement(actualitzaPrecioCerveza);
            preciosCervezas.setDouble(1,2.70);
            preciosCervezas.setString(2,"Down Under Pub");
            preciosCervezas.setString(3,"Amstel");
            int n;
            n = preciosCervezas.executeUpdate (); // Ejecuta la sentencia con los valores // suministrados.
            preciosCervezas.setDouble(1,2.70);
            preciosCervezas.setString(2,"Down Under Pub");
            preciosCervezas.setString(3,"Budweiser");
            n = preciosCervezas.executeUpdate (); // Ejecuta la sentencia con los valores // suministrados.
            preciosCervezas.setDouble(1,2.70);
            preciosCervezas.setString(2,"Down Under Pub");
            preciosCervezas.setString(3,"Corona");
            n = preciosCervezas.executeUpdate (); // Ejecuta la sentencia con los valores // suministrados.
            con.commit();
            preciosCervezas.setDouble(1,2.70);
            preciosCervezas.setString(2,"James Joyce Pub");
            preciosCervezas.setString(3,"Amstel");
            n = preciosCervezas.executeUpdate (); // Ejecuta la sentencia con los valores // suministrados.
        }
        catch(SQLException ex){
            System.out.println("SQLSTATE " + ex.getSQLState() + "SQLMESSAGE" +      ex.getMessage());
            System.out.println("Hago rollback");
            con.rollback();    
        }
        finally{
            con.setAutoCommit(true);
        }
    }

    public static void actualizacionSimple(Connection con) throws SQLException {
        //¿Se actualiza la tabla si falla la primera sentencia? 
        //Si, se actualiza con la segunda sentencia, esto es por el autocommit
        //¿Y si falla la segunda se actualiza la primera?
        //Si, tambien, tambien por el autocommit
        Statement st = con.createStatement ();
        int añadirCerveza2 = st.executeUpdate("update serves set price=2.50 where bar ='Down Under Pub' and beer='Budweiser'");
        int añadirCerveza3 = st.executeUpdate("update serves set price=2.50 where bar ='Down Under Pub' and beer='Corona'");
        st.close();
        /*Scanner lector = new Scanner(System.in);
        System.out.println("Cambiar precio de cerveza en bar1");
        System.out.println("Dime que en que bar quieres cambiar la cerveza");
        String bar = lector.nextLine();
        System.out.println("Dime de que cerveza quieres cambiar el precio");
        String cerveza = lector.next();
        System.out.println("Dime a que precio quieres actualizar el precio de la cerveza");
        double precio = lector.nextDouble();
        String actualitzaPrecioCerveza = "update serves set price=? where bar =? and beer=?";
        PreparedStatement preciosCervezas = con.prepareStatement(actualitzaPrecioCerveza);
        preciosCervezas.setDouble(1,precio);
        preciosCervezas.setString(2,bar);
        preciosCervezas.setString(3,cerveza);
        int n = preciosCervezas.executeUpdate (); // Ejecuta la sentencia con los valores // suministrados.
        System.out.println("Cambiar precio de cerveza en bar2");  
        System.out.println("Dime que en que bar quieres cambiar la cerveza");
        String bar2 = lector.nextLine();
        System.out.println("Dime de que cerveza quieres cambiar el precio");
        String cerveza2 = lector.next();
        System.out.println("Dime a que precio quieres actualizar el precio de la cerveza");
        double precio2 = lector.nextDouble();      
        String actualitzaPrecioCerveza2 = "update serves set price=? where bar =? and beer=?";
        PreparedStatement preciosCervezas2 = con.prepareStatement(actualitzaPrecioCerveza2);
        preciosCervezas2.setDouble(1,precio);
        preciosCervezas2.setString(2,bar);
        preciosCervezas2.setString(3,cerveza);
        int j = preciosCervezas2.executeUpdate (); // Ejecuta la sentencia con los valores // suministrados.*/
    }
    
    public static void updateTabla(Connection con) throws SQLException {
        Scanner lector = new Scanner(System.in);
        System.out.println("Cambiar precio de cerveza en bar");
        System.out.println("Dime que en que bar quieres cambiar la cerveza");
        String bar = lector.nextLine();
        System.out.println("Dime de que cerveza quieres cambiar el precio");
        String cerveza = lector.next();
        System.out.println("Dime a que precio quieres actualizar el precio de la cerveza");
        double precio = lector.nextDouble();
        String actualitzaPrecioCerveza = "update serves set price=? where bar =? and beer=?";
        PreparedStatement preciosCervezas = con.prepareStatement(actualitzaPrecioCerveza);
        preciosCervezas.setDouble(1,precio);
        preciosCervezas.setString(2,bar);
        preciosCervezas.setString(3,cerveza);
        int n = preciosCervezas.executeUpdate (); // Ejecuta la sentencia con los valores // suministrados.
    }

    public static void menuConsulta(Connection con) throws SQLException {
        Scanner lector = new Scanner(System.in);
        System.out.println("1-Consultar precio de una cerveza en los diferentes bares");
        System.out.println("2-Consultar precio de una cerveza en un bar concreto");
        System.out.println("Dime una opcion");
        int opcion = lector.nextInt();
        switch(opcion){
            case 1:
                consultarPrecioCerveza();
                break;
            case 2:
                consultarPrecioCervezaBar(con);
                break;
        }        
    }

    public static void consultarPrecioCervezaBar(Connection con) throws SQLException {
        Scanner lector = new Scanner(System.in);
        System.out.println("Dime el bar");
        String bar = lector.nextLine();
        System.out.println("Dime la cerveza");
        String cerveza = lector.next();
        PreparedStatement statement = con.prepareStatement("select * from serves where bar = ? and beer = ?"); 
        statement.setString(1, bar); 
        statement.setString(2, cerveza); 
        ResultSet results = statement.executeQuery();
        
        BufferedWriter bw = null;
        FileWriter fw = null;
        String data = "----\nSe ha consultado el precio de una cerveza determinada en un bar determinado. Bar: "+bar+". Cerveza: "+cerveza+"\n";
        
        try {
            results.next ();
            String nombreBar = results.getString ("bar");
            String nombreBeer = results.getString ("beer");
            String precio = results.getString ("price");
            System.out.println("La cerveza: "+nombreBeer+" vale "+precio+"€ en el bar: "+nombreBar);
            data += "La cerveza: "+nombreBeer+" vale "+precio+"€ en el bar: "+nombreBar+"\n----";
            
            File file = new File("logderesultadosbusquedacerveza.txt");
            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(data);
            System.out.println("información agregada!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                            //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        if (results!= null) results.close (); //cierra el objeto ResultSet llamado rs 
        if (statement!= null) statement.close ();//cierra el objeto Statement llamado st
        if (con!= null) con.close (); //cierra el objeto Connection llamado con*/
    }

    public static void consultarPrecioCerveza() throws SQLException {
        Scanner lector = new Scanner(System.in);
        System.out.println("Dime la marca");
        String marca = lector.next();
        Connection con = establecerConexion();
        Statement st = con.createStatement ();
        ResultSet rs = st.executeQuery ("Select * from serves where beer = '" + marca + "'");
        
        BufferedWriter bw = null;
        FileWriter fw = null;
        String data = "\n----\nSe ha consultado el precio de una cerveza en todos los bares. Cerveza: "+marca+"\n";
        
        try{
            while (rs.next ()) {
                String nombreBar = rs.getString ("bar");
                String nombreBeer = rs.getString ("beer");
                String precio = rs.getString ("price");
                System.out.println("La cerveza: "+nombreBeer+" vale "+precio+"€ en el bar: "+nombreBar);
                data += "La cerveza: "+nombreBeer+" vale "+precio+"€ en el bar: "+nombreBar+"\n";
            }
            data += "----";
            File file = new File("logderesultadosbusquedacerveza.txt");
            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(data);
            System.out.println("información agregada!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                            //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        if (rs!= null) rs.close (); //cierra el objeto ResultSet llamado rs 
        if (st!= null) st.close ();//cierra el objeto Statement llamado st
        if (con!= null) con.close (); //cierra el objeto Connection llamado con
        
    }
    public static Connection establecerConexion() throws SQLException{  
        
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/beer", "root", "");  
    }
}
