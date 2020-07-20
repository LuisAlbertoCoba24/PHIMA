/*
    esta clase nos permite consultar los datos, para generar los combos 
    en la interface grafica
*/
package modelo;

import BaseDatos.conexionBaseDatos;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Vector; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class modeloCombosOrden extends conexionBaseDatos{
    
    //variables glovales para almacenar datos de la consulta
    private String nombre = "";
    private String clave = "";
    private int id = 0;
    
    public modeloCombosOrden(){
        
    }//constructor vacio modeloCombosOrden
    
    //realiza la conexion a la base de datos
    public modeloCombosOrden( Connection _conexion ){
        conexion = _conexion;
    }//constructor lleno modeloCombosOrden
    
    //metodos get

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public String getClave() {
        return clave;
    }
    
    
    
    //metodos set

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setClave(String clave) {
        this.clave = clave;
    }
        
    public String toString() {
        return nombre ;
    }


    //consultar estados
    
    public Vector<modeloCombosOrden> modeloEstados (){
        
        //Declaramos un nuevo vector
        Vector<modeloCombosOrden> datos = new Vector<modeloCombosOrden>();
        
        //intanciuamos la clase dentro del metodo
        modeloCombosOrden  valores = null;
        
        //Decramaos el primer y nuevo item
        valores = new modeloCombosOrden();
        valores.setId(0);
        valores.setNombre("---");
        datos.add(valores);//Añadimos al vector
        
        
        try {
      
            //Preparamos los campos de la consulta
            String tabla = "estado";
            String seleccion = "IDESTADO, NOMBRE";

            //armamos la consulta 
            String Sql_query = "SELECT " + seleccion + " FROM " + tabla ;

            //Ejecutamos la consulta
            PreparedStatement declaracion = conexion.prepareStatement( Sql_query );
            ResultSet resultado = declaracion.executeQuery();

            //Recorremos los resltados
            while( resultado.next() ){

              valores = new modeloCombosOrden();
              valores.setId( resultado.getInt( "IDESTADO" ) );
              valores.setNombre( resultado.getString( "NOMBRE" ) );
              datos.add(valores);//Añadimos al vector

            }//while

          } catch (SQLException ex) {
            Logger.getLogger(modeloCombosOrden.class.getName()).log(Level.SEVERE, null, ex);
          }
        
        //regresamos los datos
        return datos;
    }
    
    public Vector<modeloCombosOrden> unidadMedica( int idEstado ){
        
        //Declaramos un nuevo vector
        Vector<modeloCombosOrden> datos = new Vector<modeloCombosOrden>();
        
        //intanciuamos la clase dentro del metodo
        modeloCombosOrden  valores = null;
        
        //Decramaos el primer y nuevo item
        valores = new modeloCombosOrden();
        valores.setId(0);
        valores.setNombre("---");
        datos.add(valores);//Añadimos al vector
        
        
        try {
      
            //Preparamos los campos de la consulta
            String tabla = ("hospital INNER JOIN concentrado_informacion ON "
                    + "( concentrado_informacion.ID_HOSPITAL = hospital.IDHOSPITAL ) "
                    + "WHERE concentrado_informacion.ID_ESTADO LIKE ");
            
            String seleccion = "hospital.IDHOSPITAL, hospital.UNIDAD_MEDICA";

            //armamos la consulta 
            String Sql_query = "SELECT DISTINCT " + seleccion + " FROM " + tabla + idEstado ;
            System.out.println(Sql_query);

            //Ejecutamos la consulta
            PreparedStatement declaracion = conexion.prepareStatement( Sql_query );
            ResultSet resultado = declaracion.executeQuery();

            //Recorremos los resltados
            while( resultado.next() ){

              valores = new modeloCombosOrden();
              valores.setId( resultado.getInt( "hospital.IDHOSPITAL" ) );
              valores.setNombre( resultado.getString( "hospital.UNIDAD_MEDICA" ) );
              datos.add(valores);//Añadimos al vector

            }//while

          } catch (SQLException ex) {
            Logger.getLogger(modeloCombosOrden.class.getName()).log(Level.SEVERE, null, ex);
          }
        
        //regresamos los datos
        return datos;
    }
    
    public Vector<modeloCombosOrden> modeloPiso ( int idHospital){
        
        //Declaramos un nuevo vector
        Vector<modeloCombosOrden> datos = new Vector<modeloCombosOrden>();
        
        //intanciuamos la clase dentro del metodo
        modeloCombosOrden  valores = null;
        
        //Decramaos el primer y nuevo item
        valores = new modeloCombosOrden();
        valores.setId(0);
        valores.setNombre("---");
        datos.add(valores);//Añadimos al vector
        
        
        try {
      
            //Preparamos los campos de la consulta
            String tabla = ("concentrado_informacion INNER JOIN hospital "
                    + "ON ( concentrado_informacion.ID_HOSPITAL = hospital.IDHOSPITAL ) "
                    + "WHERE hospital.IDHOSPITAL LIKE ");
            
            String seleccion = " concentrado_informacion.PISO, concentrado_informacion.CLAVE_PISO ";

            //armamos la consulta 
            String Sql_query = "SELECT DISTINCT " + seleccion + " FROM " + tabla + idHospital;

            //Ejecutamos la consulta
            PreparedStatement declaracion = conexion.prepareStatement( Sql_query );
            ResultSet resultado = declaracion.executeQuery();

            //Recorremos los resltados
            while( resultado.next() ){

              valores = new modeloCombosOrden();
              valores.setClave(resultado.getString("concentrado_informacion.CLAVE_PISO") );
              valores.setNombre( resultado.getString( "concentrado_informacion.PISO" ) );
              datos.add(valores);//Añadimos al vector

            }//while

          } catch (SQLException ex) {
            Logger.getLogger(modeloCombosOrden.class.getName()).log(Level.SEVERE, null, ex);
          }
        
        //regresamos los datos
        return datos;
    }
    
    public Vector<modeloCombosOrden> modeloArea( String PISO, int estado, int hospital ){
        
        //Declaramos un nuevo vector
        Vector<modeloCombosOrden> datos = new Vector<modeloCombosOrden>();
        
        //intanciuamos la clase dentro del metodo
        modeloCombosOrden  valores = null;
        
        //Decramaos el primer y nuevo item
        valores = new modeloCombosOrden();
        valores.setId(0);
        valores.setNombre("---");
        datos.add(valores);//Añadimos al vector
        
        
        try {
      
            //Preparamos los campos de la consulta
            String tabla = "concentrado_informacion";
            String seleccion = "AREA_REQUIRIENTE, CLAVE_AREA";

            //armamos la consulta 
            String Sql_query = ("SELECT DISTINCT " + seleccion + " FROM " + tabla 
                    + " WHERE CLAVE_PISO LIKE '" + PISO
                    + "' AND ID_HOSPITAL LIKE "+ hospital +" AND ID_ESTADO LIKE " +estado );
            System.out.println(Sql_query);
            //Ejecutamos la consulta
            PreparedStatement declaracion = conexion.prepareStatement( Sql_query );
            ResultSet resultado = declaracion.executeQuery();

            //Recorremos los resltados
            while( resultado.next() ){

              valores = new modeloCombosOrden();
              valores.setClave(resultado.getString("CLAVE_AREA" ) );
              valores.setNombre( resultado.getString( "AREA_REQUIRIENTE" ) );
              datos.add(valores);//Añadimos al vector

            }//while

          } catch (SQLException ex) {
            Logger.getLogger(modeloCombosOrden.class.getName()).log(Level.SEVERE, null, ex);
          }
        
        //regresamos los datos
        return datos;
    }
    
    public Vector<modeloCombosOrden> modeloSitioBase ( String claveArea ){
        
        //Declaramos un nuevo vector
        Vector<modeloCombosOrden> datos = new Vector<modeloCombosOrden>();
        
        //intanciuamos la clase dentro del metodo
        modeloCombosOrden  valores = null;
        
        //Decramaos el primer y nuevo item
        valores = new modeloCombosOrden();
        valores.setId(0);
        valores.setNombre("---");
        datos.add(valores);//Añadimos al vector
        
        
        try {
      
            //Preparamos los campos de la consulta
            String tabla = "concentrado_informacion";
            String seleccion = " NUM_SITIO_BASE, CLAVE_SITIO";

            //armamos la consulta 
            String Sql_query = "SELECT DISTINCT " + seleccion + " FROM " + tabla + " WHERE CLAVE_AREA LIKE '" + claveArea + "'" ;

            //Ejecutamos la consulta
            PreparedStatement declaracion = conexion.prepareStatement( Sql_query );
            ResultSet resultado = declaracion.executeQuery();

            //Recorremos los resltados
            while( resultado.next() ){

              valores = new modeloCombosOrden();
              valores.setId( resultado.getInt( "CLAVE_SITIO" ) );
              valores.setNombre( resultado.getString( "NUM_SITIO_BASE" ) );
              datos.add(valores);//Añadimos al vector

            }//while

          } catch (SQLException ex) {
            Logger.getLogger(modeloCombosOrden.class.getName()).log(Level.SEVERE, null, ex);
          }
        
        //regresamos los datos
        return datos;
    }
    
    public Vector<modeloCombosOrden> modeloJefe (  ){
        
        //Declaramos un nuevo vector
        Vector<modeloCombosOrden> datos = new Vector<modeloCombosOrden>();
        
        //intanciuamos la clase dentro del metodo
        modeloCombosOrden  valores = null;
        
        //Decramaos el primer y nuevo item
        valores = new modeloCombosOrden();
        valores.setId(0);
        valores.setNombre("---");
        datos.add(valores);//Añadimos al vector
        
        valores = new modeloCombosOrden();
        valores.setId(1);
        valores.setNombre("Agregar nuevo");
        datos.add(valores);//Añadimos al vector
        
        
        try {
      
            //Preparamos los campos de la consulta
            String tabla = "nombre_jefe";
            String seleccion = "IDNOMBREJEFE, NOMBRE";

            //armamos la consulta 
            String Sql_query = "SELECT " + seleccion + " FROM " + tabla ;

            //Ejecutamos la consulta
            PreparedStatement declaracion = conexion.prepareStatement( Sql_query );
            ResultSet resultado = declaracion.executeQuery();

            //Recorremos los resltados
            while( resultado.next() ){

              valores = new modeloCombosOrden();
              valores.setId( resultado.getInt( "IDNOMBREJEFE" ) );
              valores.setNombre( resultado.getString( "NOMBRE" ) );
              datos.add(valores);//Añadimos al vector

            }//while

          } catch (SQLException ex) {
            Logger.getLogger(modeloCombosOrden.class.getName()).log(Level.SEVERE, null, ex);
          }
        
        //regresamos los datos
        return datos;
    }

    @Override
    public boolean ingresar(List<Object> values, String nombre_tabla, String selecion) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
