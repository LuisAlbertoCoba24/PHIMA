/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import javax.swing.text.MaskFormatter;
import utilidades.notificaciones;
import utilidades.configuracionXml;
import BaseDatos.*;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import modelo.modeloCombosOrden;
/**
 *
 * @author luisc
 */
public class Ordenes_servicio extends javax.swing.JFrame {
    int idestado = 0, idHospital = 0, claveSitio = 0, idJefe = 0;
    String clavePiso = "";
    String unidadMEdica = "";
    String claveArea = "";
    /*Objetos notificaciones*/
    notificaciones noti = new notificaciones();
    /*Conexion a la base de datos */
    configuracionXml conf = new configuracionXml();
    crudGeneral con = new crudGeneral(conf.getConexion().getConexion() );
    //Objeto de combo
     modeloCombosOrden modelo = new modeloCombosOrden( conf.getConexion().getConexion() );
     //Fecha del sistema
     Date fechaAcutal = new Date();
     
     /*Folios*/
     int folioOrden = 0, FolioCartel = 0;
    
    /**
     * Creates new form Ordenes_servicio 
     */
    public Ordenes_servicio() {
        initComponents();
        
        /*this.setUndecorated(true);*/
        /*Combo box*/
        turnos(); 
        reabasto();
        tipoInsumo();
        Tamanio();
        tituloDecarteles();
        llenarComboEstado();
        llenarComboEstadoCar();
        /////////configuraciones y acciones//////////////
        Material_impreso();
        ConfiguracionFrame();
        
    }//Ordenes_servicio
    
    
    /**Configuracion de la pantalla*/
    private void ConfiguracionFrame(){
        this.setLocationRelativeTo(null);
    }//ConfiguracionFrame
    
    /*Llenado de combos*/
    
    /*Turnos*/
    
    private void turnos(){
        /*Primer */
        cbxTurno.addItem("---");
        cbxTurno.addItem("Matutino");
        cbxTurno.addItem("Vespertino");
        cbxTurno.addItem("Nocturno");
        
        /*Segunda*/
        
        cbxTurnoForm.addItem("---");
        cbxTurnoForm.addItem("Matutino");
        cbxTurnoForm.addItem("Vespertino");
        cbxTurnoForm.addItem("Nocturno");
        
    }//turnos
    
    /*Reabasto*/
    
    private void reabasto(){
        cbxReabasto.addItem("---");
        cbxReabasto.addItem("SI");
        cbxReabasto.addItem("ROBO");
    }//reabasto
    
    /*Tipo de insumo*/
    
    private void tipoInsumo(){
        cbxTipoInsumo.addItem("---");
        cbxTipoInsumo.addItem("CLORHEXIDINA");
        cbxTipoInsumo.addItem("SOLUCION BASE ALCOHOL");
    }//tipoInsumo
    
    /*Material impreso*/
    
    private void Material_impreso(){
        cbxMaterialImpreso.addItem("---");
        cbxMaterialImpreso.addItem("INSTALACION");
        cbxMaterialImpreso.addItem("REABASTO");
        cbxMaterialImpreso.addItem("REUBICACION");
    }//Material_impreso
    
    /*Material impreso*/
    
    private void Tamanio(){
        cbxTamanioForm.addItem("---");
        cbxTamanioForm.addItem("TABLOIDE");
        cbxTamanioForm.addItem("DOBLE CARTA");
        cbxTamanioForm.addItem("CARTA");
    }//Tamanio
    
    /*Titulos de carteles*/
    
    private void tituloDecarteles(){
        cbxTituloCartel.addItem("---");
        cbxTituloCartel.addItem("COMO LAVARSE LAS MANOS");
        cbxTituloCartel.addItem("COMO DESINFECTARSE LAS MANOS");
        cbxTituloCartel.addItem("5 MOMENTOS PARA HIGIENE DE MANOS");
        cbxTituloCartel.addItem("HIGIENE DE MANOS ¿CUANDO Y COMO?");
    }//tituloDecarteles
    
    
    /*Combos enlasados a la base de datos*/
    
    /*Acciones de combos de estado*/
    private void llenarComboEstado(){
        DefaultComboBoxModel modeloComboEstado = new DefaultComboBoxModel( modelo.modeloEstados());
        cbxEstado.setModel(modeloComboEstado);
    }// llenarComboEstado
    
    private void seleccionComboEstado(){
        modeloCombosOrden modelo = (modeloCombosOrden) cbxEstado.getSelectedItem();
        int idEstado = modelo.getId();
        idestado = modelo.getId();
        llenarComboUnidad( idEstado);
    }//seleccionComboEstado
    
    /*Acciones de combos de Unidad medica*/
    private void llenarComboUnidad(int _idestado){
        DefaultComboBoxModel modeloComboUnidad = new DefaultComboBoxModel( modelo.unidadMedica( _idestado ));
        cbxUnidadMedica.setModel(modeloComboUnidad);
    }// llenarComboUnidad
    
    private void seleccionComboUnidad(){
        modeloCombosOrden modelo = (modeloCombosOrden) cbxUnidadMedica.getSelectedItem();
        int idUnidad = modelo.getId();
        idHospital = modelo.getId();
        unidadMEdica = modelo.getNombre();
        folioOrden += 1;
        String folioDeSolicitud = unidadMEdica + "-000" + String.valueOf(folioOrden);
        textFolioServicio.setText(folioDeSolicitud);
        llenarComboPiso(idUnidad);
    }//seleccionComboUnidad
    
    /*Acciones de combos de Piso*/
    private void llenarComboPiso(int _idHospital){
        DefaultComboBoxModel modeloComboPiso = new DefaultComboBoxModel( modelo.modeloPiso(_idHospital ));
        cbxPiso.setModel(modeloComboPiso);
    }// llenarComboPiso
    
    private void seleccionComboPiso(){
        modeloCombosOrden modelo = (modeloCombosOrden) cbxPiso.getSelectedItem();
        clavePiso = modelo.getClave();
        llenarComboArea(clavePiso);
    }//seleccionComboPiso
    
    /*Acciones de combos de Area*/
    private void llenarComboArea(String clavePiso){
        DefaultComboBoxModel modeloComboArea = new DefaultComboBoxModel( modelo.modeloArea(clavePiso,idestado ,idHospital ));
        cbxAreaRequi.setModel(modeloComboArea);
    }// llenarComboArea
    
    private void seleccionComboArea(){
        modeloCombosOrden modelo = (modeloCombosOrden) cbxAreaRequi.getSelectedItem();
        claveArea = modelo.getClave();
        llenarComboSitio(claveArea);
    }//seleccionComboArea
    
    /*Acciones de combos de sitio*/
    private void llenarComboSitio(String area){
        DefaultComboBoxModel modeloComboArea = new DefaultComboBoxModel( modelo.modeloSitioBase(area));
        cbxSitioBase.setModel(modeloComboArea);
    }// llenarComboSitio
    
    private void seleccionComboSitio(){
        modeloCombosOrden modelo = (modeloCombosOrden) cbxSitioBase.getSelectedItem();
        claveSitio = modelo.getId();
       llenarComboJefe();
    }//seleccionComboSitio
    
    /*Acciones de combos de Jefe*/
    private void llenarComboJefe(){
        DefaultComboBoxModel modeloComboJefe = new DefaultComboBoxModel( modelo.modeloJefe());
        cbxJefe.setModel(modeloComboJefe);
    }// llenarComboJefe
    
    private void seleccionComboJefe(){
        modeloCombosOrden modelo = (modeloCombosOrden) cbxJefe.getSelectedItem();
        idJefe = modelo.getId();
        
        if ( idJefe == 1 ){
          pantallaEmergente.setVisible(true);
          pantallaEmergente.setBounds(0, 0, 400, 250);
          pantallaEmergente.setResizable(false);
          pantallaEmergente.setLocationRelativeTo(null);
          
        }
    }//seleccionComboJefe
    
    //////////////////////////Combos carteles///////////////////////////////////
    
     /*Acciones de combos de estado*/
    private void llenarComboEstadoCar(){
        DefaultComboBoxModel modeloComboEstado = new DefaultComboBoxModel( modelo.modeloEstados());
        cbxEstadoFormato.setModel(modeloComboEstado);
    }// llenarComboEstado
    
    private void seleccionComboEstadoCar(){
        idestado = 0;
        modeloCombosOrden modelo = (modeloCombosOrden) cbxEstadoFormato.getSelectedItem();
        int idEstado = modelo.getId();
        idestado = modelo.getId();
        llenarComboUnidadCar( idEstado);
    }//seleccionComboEstado
    
    /*Acciones de combos de Unidad medica*/
    private void llenarComboUnidadCar(int _idestado){
        DefaultComboBoxModel modeloComboUnidad = new DefaultComboBoxModel( modelo.unidadMedica( _idestado ));
        cbxUnidadMEdicaForm.setModel(modeloComboUnidad);
    }// llenarComboUnidad
    
    private void seleccionComboUnidadCar(){
        idHospital = 0;
        modeloCombosOrden modelo = (modeloCombosOrden) cbxUnidadMEdicaForm.getSelectedItem();
        int idUnidadCar = modelo.getId();
        idHospital = modelo.getId();
        unidadMEdica = modelo.getNombre();
        FolioCartel += 1;
        String folioDeSolicitud = unidadMEdica + "-000" + String.valueOf(FolioCartel);
        textFolioCartel.setText(folioDeSolicitud);
        llenarComboPisoCar(idUnidadCar);
    }//seleccionComboUnidad
    
    /*Acciones de combos de Piso*/
    private void llenarComboPisoCar(int _idHospital){
        DefaultComboBoxModel modeloComboPiso = new DefaultComboBoxModel( modelo.modeloPiso(_idHospital ));
        cbxPisoForm.setModel(modeloComboPiso);
    }// llenarComboPiso
    
    private void seleccionComboPisoCar(){
        clavePiso = "";
        modeloCombosOrden modelo = (modeloCombosOrden) cbxPisoForm.getSelectedItem();
        clavePiso = modelo.getClave();
        llenarComboAreaCar(clavePiso);
    }//seleccionComboPiso
    
    /*Acciones de combos de Area*/
    private void llenarComboAreaCar(String clavePiso){
        DefaultComboBoxModel modeloComboArea = new DefaultComboBoxModel( modelo.modeloArea(clavePiso,idestado ,idHospital ));
        cbxAreaRequiriente.setModel(modeloComboArea);
    }// llenarComboArea
    
    private void seleccionComboAreaCar(){
        claveArea = "";
        modeloCombosOrden modelo = (modeloCombosOrden) cbxAreaRequiriente.getSelectedItem();
        claveArea = modelo.getClave();
        llenarComboSitioCar(claveArea);
    }//seleccionComboArea
    
    /*Acciones de combos de sitio*/
    private void llenarComboSitioCar(String area){
        DefaultComboBoxModel modeloComboArea = new DefaultComboBoxModel( modelo.modeloSitioBase(area));
        cbxSitioBaseForm.setModel(modeloComboArea);
    }// llenarComboSitio
    
    private void seleccionComboSitioCar(){
        claveSitio = 0;
        modeloCombosOrden modelo = (modeloCombosOrden) cbxSitioBaseForm.getSelectedItem();
        claveSitio = modelo.getId();
       llenarComboJefe();
    }//seleccionComboSitio
    
    ///acciones de pantalla emergente///
    private void limpiarCamposJefe(){
        textNombre.setText("");
        textApellidoPaterno.setText("");
        textApelliudoMaterno.setText("");
    }//limpiarCamposJefe
    
    ////Agregar nuevo jefe//
    private void guardarInformacionJefe(){
        
        String tabla = "nombre_jefe";
        String campos = "NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, ESTADO,FECHA_REGISTRO";
        
        String nombre = textNombre.getText();
        String apellidoP = textApellidoPaterno.getText();
        String apellidoM = textApelliudoMaterno.getText();
        
        
        try {
            /*Validar datos*/
            if( ( !nombre.equals("") ) && ( !apellidoP.equals("") ) &&  ( !apellidoM.equals("")) ){
                
                //Creamos una lista
                List<Object> datos = new ArrayList<Object>();
                datos.add( nombre );
                datos.add( apellidoP );
                datos.add( apellidoM );
                datos.add( 1 );
                datos.add( "2020-07-11" );

                
                boolean resultado = con.ingresar(datos, tabla, campos);
                
                if(resultado){
                    llenarComboJefe();
                    pantallaEmergente.setVisible(false);
                    noti.mensajeCorrecto("Datos registrados con exito");
                }else{
                    noti.mensajeError("Se genero un conflicto al momento de guardar los datos");
                }
                
            }else{
                noti.mensjeInformacion("Compruebe que todos los campos esten llenos");
            }
        } catch (Exception e) {
            noti.mensajeError("Ocurrio un problema con la comunicacion a la base de datos");
        }
    }//guardarInformacionJefe
    
    
    /**********Funciones de pantalla uno **************************************/
    private void limpiarPantallaUno(){
        /*combos*/
        cbxEstado.setSelectedIndex(0);
        cbxUnidadMedica.setSelectedIndex(0);
        cbxPiso.setSelectedIndex(0);
        cbxAreaRequi.setSelectedIndex(0);
        cbxSitioBase.setSelectedIndex(0);
        cbxJefe.setSelectedIndex(0);
        cbxTurno.setSelectedIndex(0);
        cbxReabasto.setSelectedIndex(0);
        cbxTipoInsumo.setSelectedIndex(0);
        /*Cajas de texto*/
        textFolioServicio.setText("");
        textFolioBotella.setText("");
        formTextFecha.setText("");
        textAreaObs.setText("");
        spinBotellaSurtir.setValue(0);
    }
    
    /*Guardar datos*/
    
    private void guardarInformacionOrden(){
        /**Bloque de accion de guarado*/
        
        /*Campos de la tabla*/
        String tabla = "registro_orden_servicios";
        String campos = ("FOLIO_ORDEN_SERVICIO, FOLIO_BOTELLA, "
                + "REABASTO, FECHA_CAMBIO, TIPO_INSUMO, TURNO, NO_FOLLETOS_SURTIR, "
                + "ID_ESTADO, UNIDAD_MEDICA, PISO, AREA_REQUIRIENTE, SITIO_BASE, "
                + "OBSERVACIONES, FECHA_REGISTRO, ESTADO");
        
        /*Datos a guardar*/
        String folioSolicitud = textFolioServicio.getText();
        String folioDeBotella = textFolioBotella.getText();
        String reabasto = cbxReabasto.getSelectedItem().toString();
        String fechaDeCambio = formTextFecha.getText();
        String tipoInsumo = cbxTipoInsumo.getSelectedItem().toString();
        String turno = cbxTurno.getSelectedItem().toString();
        int noBotellas = (int) spinBotellaSurtir.getValue();
        int idEstado = idestado;
        int _idHospital = idHospital;
        String _clavePiso = clavePiso;
        String _claveArea = claveArea;
        int _claveSitio = claveSitio;
        
        String observaciones = textAreaObs.getText();
                
        if( observaciones.equals("") ){
            
            observaciones = "NA";
        }else{
            
        }
        
        try {
            /*Validar datos*/
            if( (!folioSolicitud.equals("")) && (!folioDeBotella.equals("")) ){
                
                 //Creamos una lista
                List<Object> datos = new ArrayList<Object>();
                datos.add( folioSolicitud );
                datos.add( folioDeBotella );
                datos.add( reabasto );
                datos.add( fechaDeCambio );
                datos.add( tipoInsumo );
                datos.add( turno );
                datos.add( noBotellas );
                datos.add( idEstado );
                datos.add( _idHospital );
                datos.add( _clavePiso );
                datos.add( _claveArea );
                datos.add( _claveSitio );
                datos.add( observaciones );
                datos.add( "2020-07-11" );
                datos.add( 1 );
                
                boolean resultado = con.ingresar(datos, tabla, campos);
                
                if( resultado ){
                    noti.mensajeCorrecto("Datos registrados con exito");
                    limpiarPantallaUno();
                }else{
                    noti.mensajeError("Se genero un conflicto al momento de guardar los datos");
                }
                
            }else{
                noti.mensjeInformacion("Compruebe que todos los campos esten llenos");
            }
        } catch (Exception e) {
            noti.mensajeError("Ocurrio un problema con la comunicacion a la base de datos");
        }
    }
    
    
    ////////////////////////////////Funciones de página 2///////////////////////
    
    private void limpiarPantallaDos(){
        /*combos*/
        cbxEstadoFormato.setSelectedIndex(0);
        cbxUnidadMEdicaForm.setSelectedIndex(0);
        cbxPisoForm.setSelectedIndex(0);
        cbxAreaRequiriente.setSelectedIndex(0);
        cbxSitioBaseForm.setSelectedIndex(0);
        cbxMaterialImpreso.setSelectedIndex(0);
        cbxTamanioForm.setSelectedIndex(0);
        cbxTituloCartel.setSelectedIndex(0);
        cbxTurnoForm.setSelectedIndex(0);
        /*Cajas de texto*/
        textFolioCartel.setText("");
        textFormatoFechaFormcar.setText("");
        textAreaObservacioneForm.setText("");
        SpinnerNoCarteles.setValue(0);
    }
    
    /**Guardar datos*/
    
    private void guardarInformacionCarteles(){
        
         /**Bloque de accion de guarado*/
        
        /*Campos de la tabla*/
        String tabla = "registro_carteles";
        String campos = ("NO_FORMATO, FOLIO_CARTEL, MATERIAL_IMPRESO, "
                + "TITULO_CARTEL, TAMANIO, CARTELES_SURTIR, "
                + "FECHA_CAPTURA, TURNO, ID_ESTADO, UNIDAD_MEDICA, "
                + "PISO, AREA_REQUIRIENTE, SITIO_BASE, OBSERVACIONES, "
                + "ESTADO, FECHA_REGISTRO");
        
        /*Datos a guardar*/
        int noFormato = Integer.valueOf(textFormato.getText() );
        String folioCartel = textFolioCartel.getText();
        String material = cbxMaterialImpreso.getSelectedItem().toString();
        String tituloDeCartel = cbxTituloCartel.getSelectedItem().toString();
        String tamanio = cbxTamanioForm.getSelectedItem().toString();
        int numeroCarteles = (int) SpinnerNoCarteles.getValue();
        String _fechaCaptura = textFormatoFechaFormcar.getText();
        String turno = cbxTurnoForm.getSelectedItem().toString();
        int _idEstado = idestado;
        int _idHospital = idHospital;
        String _clavePiso = clavePiso;
        String _claveArea = claveArea;
        int _claveSitio = claveSitio;
        
        String observaciones = textAreaObservacioneForm.getText();
                
        if( observaciones.equals("") ){
            
            observaciones = "NA";
        }else{
            
        }
        
        try {
            /*Validar datos*/
            if( (!folioCartel.equals(""))){
                
                 //Creamos una lista
                List<Object> datos = new ArrayList<Object>();
                datos.add( noFormato );
                datos.add( folioCartel );
                datos.add( material );
                datos.add( tituloDeCartel );
                datos.add( tamanio );
                datos.add( numeroCarteles );
                datos.add( _fechaCaptura );
                datos.add( turno );
                datos.add(_idEstado );
                datos.add( _idHospital );
                datos.add( _clavePiso );
                datos.add( _claveArea );
                datos.add( _claveSitio );
                datos.add( observaciones );
                datos.add( 1 );
                datos.add( "2020-07-11" );
                
                
                boolean resultado = con.ingresar(datos, tabla, campos);
                
                if( resultado ){
                    noti.mensajeCorrecto("Datos registrados con exito");
                    limpiarPantallaDos();
                }else{
                    noti.mensajeError("Se genero un conflicto al momento de guardar los datos");
                }
                
            }else{
                noti.mensjeInformacion("Compruebe que todos los campos esten llenos");
            }
        } catch (Exception e) {
            noti.mensajeError("Ocurrio un problema con la comunicacion a la base de datos");
            System.out.println( e );
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pantallaEmergente = new javax.swing.JFrame();
        jPanel7 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        textNombre = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        textApellidoPaterno = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        textApelliudoMaterno = new javax.swing.JTextField();
        btnGuardarMedico = new javax.swing.JButton();
        btncancelarGuardado = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbxEstadoFormato = new javax.swing.JComboBox<>();
        cbxUnidadMEdicaForm = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cbxPisoForm = new javax.swing.JComboBox<>();
        cbxAreaRequiriente = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cbxSitioBaseForm = new javax.swing.JComboBox<>();
        textFolioCartel = new javax.swing.JTextField();
        textFormato = new javax.swing.JTextField();
        btnCarteles = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        cbxMaterialImpreso = new javax.swing.JComboBox<>();
        cbxTamanioForm = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cbxTituloCartel = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        textFormatoFechaFormcar = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        cbxTurnoForm = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        SpinnerNoCarteles = new javax.swing.JSpinner();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        textAreaObservacioneForm = new javax.swing.JTextArea();
        jLabel31 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox<>();
        cbxUnidadMedica = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbxPiso = new javax.swing.JComboBox<>();
        cbxAreaRequi = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbxSitioBase = new javax.swing.JComboBox<>();
        textFolioServicio = new javax.swing.JTextField();
        textFolioBotella = new javax.swing.JTextField();
        BtnGuardarOrden = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        cbxJefe = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        formTextFecha = new javax.swing.JFormattedTextField();
        jLabel25 = new javax.swing.JLabel();
        cbxReabasto = new javax.swing.JComboBox<>();
        cbxTipoInsumo = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        cbxTurno = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaObs = new javax.swing.JTextArea();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        spinBotellaSurtir = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        btnLimpiarOrden1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo medico"));

        jLabel32.setText("Nombre :");

        jLabel33.setText("Apellido paterno:");

        textApellidoPaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textApellidoPaternoActionPerformed(evt);
            }
        });

        jLabel34.setText("Apellido materno:");

        btnGuardarMedico.setBackground(new java.awt.Color(0, 102, 255));
        btnGuardarMedico.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnGuardarMedico.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarMedico.setText("Guardar");
        btnGuardarMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarMedicoActionPerformed(evt);
            }
        });

        btncancelarGuardado.setBackground(new java.awt.Color(153, 153, 153));
        btncancelarGuardado.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btncancelarGuardado.setForeground(new java.awt.Color(255, 255, 255));
        btncancelarGuardado.setText("Cancelar");
        btncancelarGuardado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarGuardadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textApellidoPaterno))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textApelliudoMaterno))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnGuardarMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btncancelarGuardado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(textNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(textApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(textApelliudoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarMedico)
                    .addComponent(btncancelarGuardado))
                .addContainerGap())
        );

        javax.swing.GroupLayout pantallaEmergenteLayout = new javax.swing.GroupLayout(pantallaEmergente.getContentPane());
        pantallaEmergente.getContentPane().setLayout(pantallaEmergenteLayout);
        pantallaEmergenteLayout.setHorizontalGroup(
            pantallaEmergenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pantallaEmergenteLayout.setVerticalGroup(
            pantallaEmergenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusable(false);
        setResizable(false);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(51, 51, 51));
        jPanel6.setPreferredSize(new java.awt.Dimension(845, 1));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Estado:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Unidad Medica:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Area requiriente :");

        cbxEstadoFormato.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbxEstadoFormato.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEstadoFormatoItemStateChanged(evt);
            }
        });

        cbxUnidadMEdicaForm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxUnidadMEdicaFormItemStateChanged(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Piso :");

        cbxPisoForm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPisoFormItemStateChanged(evt);
            }
        });

        cbxAreaRequiriente.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxAreaRequirienteItemStateChanged(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Sitio # de base :");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Folio de calrtel :");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("No. Formato :");

        cbxSitioBaseForm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSitioBaseFormItemStateChanged(evt);
            }
        });

        btnCarteles.setBackground(new java.awt.Color(51, 102, 255));
        btnCarteles.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCarteles.setForeground(new java.awt.Color(255, 255, 255));
        btnCarteles.setText("Guardar");
        btnCarteles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartelesActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(153, 153, 153));
        btnLimpiar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Material impreso :");

        cbxMaterialImpreso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cbxTamanioForm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbxTamanioForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTamanioFormActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Tamaño :");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Titulo de cartel :");

        cbxTituloCartel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Fecha :");

        textFormatoFechaFormcar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        textFormatoFechaFormcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFormatoFechaFormcarActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Turno :");

        cbxTurnoForm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("No. Carteles a surtir :");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setText("Observaciones :");

        textAreaObservacioneForm.setColumns(20);
        textAreaObservacioneForm.setRows(5);
        jScrollPane3.setViewportView(textAreaObservacioneForm);

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Formato:\"AAAA/MM/DD\"");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel35.setText("Recorrido");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 991, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbxTituloCartel, javax.swing.GroupLayout.Alignment.TRAILING, 0, 267, Short.MAX_VALUE)
                                    .addComponent(cbxTamanioForm, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbxMaterialImpreso, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnCarteles, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel21)
                                    .addGap(115, 115, 115)
                                    .addComponent(SpinnerNoCarteles, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(115, 115, 115)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20))
                                .addGap(52, 52, 52)))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxTurnoForm, 0, 279, Short.MAX_VALUE)
                            .addComponent(textFormatoFechaFormcar, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                            .addComponent(jScrollPane3)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(cbxAreaRequiriente, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel35)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(26, 26, 26)
                                .addComponent(cbxEstadoFormato, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbxPisoForm, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbxUnidadMEdicaForm, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(115, 115, 115)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textFormato, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(38, 38, 38)
                                .addComponent(textFolioCartel))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(33, 33, 33)
                                .addComponent(cbxSitioBaseForm, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(63, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(150, 150, 150))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbxEstadoFormato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(cbxSitioBaseForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFolioCartel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxUnidadMEdicaForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel13))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxPisoForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel14)
                    .addComponent(textFormato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cbxAreaRequiriente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cbxMaterialImpreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(textFormatoFechaFormcar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(cbxTurnoForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxTamanioForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(cbxTituloCartel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(SpinnerNoCarteles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCarteles)
                    .addComponent(btnLimpiar))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("FORMATO DE CARTELES", jPanel3);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setPreferredSize(new java.awt.Dimension(845, 1));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Estado:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Unidad Medica:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Area requiriente :");

        cbxEstado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbxEstado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEstadoItemStateChanged(evt);
            }
        });

        cbxUnidadMedica.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbxUnidadMedica.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxUnidadMedicaItemStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Piso :");

        cbxPiso.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPisoItemStateChanged(evt);
            }
        });

        cbxAreaRequi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxAreaRequiItemStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Sitio # de base :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Folio orden de servicio :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Folio de botella");

        cbxSitioBase.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSitioBaseItemStateChanged(evt);
            }
        });

        BtnGuardarOrden.setBackground(new java.awt.Color(51, 102, 255));
        BtnGuardarOrden.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BtnGuardarOrden.setForeground(new java.awt.Color(255, 255, 255));
        BtnGuardarOrden.setText("Guardar");
        BtnGuardarOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarOrdenActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("Nombre de jefe de area :");

        cbxJefe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxJefeItemStateChanged(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setText("Recorrido");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("Fecha de captura :");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("Reabasto:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setText("Tipo de insumo:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setText("Turno :");

        cbxTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTurnoActionPerformed(evt);
            }
        });

        textAreaObs.setColumns(20);
        textAreaObs.setRows(5);
        jScrollPane2.setViewportView(textAreaObs);

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setText("Observaciones :");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setText("No. Botellas a surtir :");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Formato:\"AAAA/MM/DD\"");

        btnLimpiarOrden1.setBackground(new java.awt.Color(153, 153, 153));
        btnLimpiarOrden1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLimpiarOrden1.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiarOrden1.setText("Limpiar");
        btnLimpiarOrden1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarOrden1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 991, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel29))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbxUnidadMedica, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbxPiso, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxAreaRequi, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(100, 100, 100)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textFolioServicio, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                            .addComponent(cbxSitioBase, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textFolioBotella)
                            .addComponent(cbxJefe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(66, 66, 66))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(spinBotellaSurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxReabasto, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(formTextFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxTipoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(104, 104, 104)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(BtnGuardarOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLimpiarOrden1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(387, 387, 387))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(cbxSitioBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxUnidadMedica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(textFolioServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(textFolioBotella, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cbxPiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxJefe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(cbxAreaRequi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(formTextFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27)
                            .addComponent(cbxTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel17)
                                        .addGap(24, 24, 24)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel25)
                                            .addComponent(cbxReabasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addComponent(jLabel28)))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel26)
                                    .addComponent(cbxTipoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(spinBotellaSurtir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(216, 216, 216))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLimpiarOrden1)
                            .addComponent(BtnGuardarOrden))
                        .addGap(152, 152, 152))))
        );

        jTabbedPane1.addTab("ORDENES DE SERVICIO", jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, Short.MAX_VALUE)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Ordenes de servicio");

        jMenuBar1.setAutoscrolls(true);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/img_logo02.jpg"))); // NOI18N
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 55, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTurnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxTurnoActionPerformed

    private void BtnGuardarOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarOrdenActionPerformed
        // TODO add your handling code here:
        guardarInformacionOrden();
    }//GEN-LAST:event_BtnGuardarOrdenActionPerformed

    private void btnCartelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartelesActionPerformed
        // TODO add your handling code here:
        guardarInformacionCarteles();
    }//GEN-LAST:event_btnCartelesActionPerformed

    private void cbxEstadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEstadoItemStateChanged
        // TODO add your handling code here:
        seleccionComboEstado();
    }//GEN-LAST:event_cbxEstadoItemStateChanged

    private void cbxUnidadMedicaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxUnidadMedicaItemStateChanged
        // TODO add your handling code here:
        seleccionComboUnidad();
    }//GEN-LAST:event_cbxUnidadMedicaItemStateChanged

    private void cbxPisoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPisoItemStateChanged
        // TODO add your handling code here:
        seleccionComboPiso();
    }//GEN-LAST:event_cbxPisoItemStateChanged

    private void cbxAreaRequiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxAreaRequiItemStateChanged
        // TODO add your handling code here:
        seleccionComboArea();
    }//GEN-LAST:event_cbxAreaRequiItemStateChanged

    private void cbxSitioBaseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSitioBaseItemStateChanged
        // TODO add your handling code here:
        seleccionComboSitio();
    }//GEN-LAST:event_cbxSitioBaseItemStateChanged

    private void cbxJefeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxJefeItemStateChanged
        // TODO add your handling code here:
        seleccionComboJefe();
    }//GEN-LAST:event_cbxJefeItemStateChanged

    private void textApellidoPaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textApellidoPaternoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textApellidoPaternoActionPerformed

    private void btnGuardarMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarMedicoActionPerformed
        // TODO add your handling code here:
        guardarInformacionJefe();
    }//GEN-LAST:event_btnGuardarMedicoActionPerformed

    private void btncancelarGuardadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarGuardadoActionPerformed
        // TODO add your handling code here:
        limpiarCamposJefe();
    }//GEN-LAST:event_btncancelarGuardadoActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiarPantallaDos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void cbxEstadoFormatoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEstadoFormatoItemStateChanged
        // TODO add your handling code here:
        seleccionComboEstadoCar();
    }//GEN-LAST:event_cbxEstadoFormatoItemStateChanged

    private void cbxUnidadMEdicaFormItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxUnidadMEdicaFormItemStateChanged
        // TODO add your handling code here:
        seleccionComboUnidadCar();
    }//GEN-LAST:event_cbxUnidadMEdicaFormItemStateChanged

    private void cbxPisoFormItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPisoFormItemStateChanged
        // TODO add your handling code here:
        seleccionComboPisoCar();
    }//GEN-LAST:event_cbxPisoFormItemStateChanged

    private void cbxAreaRequirienteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxAreaRequirienteItemStateChanged
        // TODO add your handling code here:
        seleccionComboAreaCar();
    }//GEN-LAST:event_cbxAreaRequirienteItemStateChanged

    private void cbxSitioBaseFormItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSitioBaseFormItemStateChanged
        // TODO add your handling code here:
        seleccionComboSitioCar();
    }//GEN-LAST:event_cbxSitioBaseFormItemStateChanged

    private void cbxTamanioFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTamanioFormActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxTamanioFormActionPerformed

    private void textFormatoFechaFormcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFormatoFechaFormcarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFormatoFechaFormcarActionPerformed

    private void btnLimpiarOrden1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarOrden1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarOrden1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ordenes_servicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ordenes_servicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ordenes_servicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ordenes_servicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ordenes_servicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnGuardarOrden;
    private javax.swing.JSpinner SpinnerNoCarteles;
    private javax.swing.JButton btnCarteles;
    private javax.swing.JButton btnGuardarMedico;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnLimpiarOrden1;
    private javax.swing.JButton btncancelarGuardado;
    private javax.swing.JComboBox<String> cbxAreaRequi;
    private javax.swing.JComboBox<String> cbxAreaRequiriente;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<String> cbxEstadoFormato;
    private javax.swing.JComboBox<String> cbxJefe;
    private javax.swing.JComboBox<String> cbxMaterialImpreso;
    private javax.swing.JComboBox<String> cbxPiso;
    private javax.swing.JComboBox<String> cbxPisoForm;
    private javax.swing.JComboBox<String> cbxReabasto;
    private javax.swing.JComboBox<String> cbxSitioBase;
    private javax.swing.JComboBox<String> cbxSitioBaseForm;
    private javax.swing.JComboBox<String> cbxTamanioForm;
    private javax.swing.JComboBox<String> cbxTipoInsumo;
    private javax.swing.JComboBox<String> cbxTituloCartel;
    private javax.swing.JComboBox<String> cbxTurno;
    private javax.swing.JComboBox<String> cbxTurnoForm;
    private javax.swing.JComboBox<String> cbxUnidadMEdicaForm;
    private javax.swing.JComboBox<String> cbxUnidadMedica;
    private javax.swing.JFormattedTextField formTextFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JFrame pantallaEmergente;
    private javax.swing.JSpinner spinBotellaSurtir;
    private javax.swing.JTextField textApellidoPaterno;
    private javax.swing.JTextField textApelliudoMaterno;
    private javax.swing.JTextArea textAreaObs;
    private javax.swing.JTextArea textAreaObservacioneForm;
    private javax.swing.JTextField textFolioBotella;
    private javax.swing.JTextField textFolioCartel;
    private javax.swing.JTextField textFolioServicio;
    private javax.swing.JTextField textFormato;
    private javax.swing.JFormattedTextField textFormatoFechaFormcar;
    private javax.swing.JTextField textNombre;
    // End of variables declaration//GEN-END:variables
}
