/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

public class crudGeneral extends conexionBaseDatos {
    
    public crudGeneral( Connection _conexion){
        conexion = _conexion;
    }

    @Override
    public boolean ingresar(List<Object> values, String nombre_tabla, String selecion) throws SQLException {
        
        String valores = "";
        int contador = 0;
    
    
        for (int i = 0; i < values.size() ; i++) {
          System.out.println( i );
          if( i == 0){
            valores = "?";
          }else{
            valores = valores + ",?";
          }   
        }
        System.out.println( valores );
        String sql_query = ("INSERT INTO " + nombre_tabla + " (" + selecion + ") VALUES " + "(" + valores + ")" );
    

        PreparedStatement declaracion = conexion.prepareStatement( sql_query );
        System.out.println( declaracion );

        for (int i = 0; i <= values.size() - 1; i++) {
          contador = i + 1;
          declaracion.setObject(contador, values.get( i ));

        }
  
        int filas_agregadas = declaracion.executeUpdate();
        declaracion.close();
        return filas_agregadas > 0;
    }

    @Override
    public boolean borrar(int id_fila, String nombreTabla, String id_pk) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(List<Object> values, String nombre_tabla, String id_pk, String columnas_valores) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DefaultTableModel selecionarLista(String nombreTabla, String selecion) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> Seleccion(int id_fila, String nombreTabla, String id_pk, String seleccion) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
