package com.restpdf.javaclases.bdclases;

import java.sql.*;
import java.util.ArrayList;

public class BDForms {
    Connection c;
    PreparedStatement ps;
    String sql;
    private final static String url="jdbc:mysql://sql11.freemysqlhosting.net/sql11652348",  user="id22000619_sql11652348", pwd="rtJrPKLmpc_1";


    public Connection getC() {        return c;    }
    public void setC(Connection c) {        this.c = c;    }

    public BDForms() throws SQLException, ClassNotFoundException {
        c = DriverManager.getConnection(url, user, pwd);
    }

    public void setCarpeta(String Carpeta) throws SQLException {

        sql = "UPDATE CredencialesApp SET ValorCampo=? WHERE NombreCampo=?";

        ps = c.prepareStatement(sql);
        ps.setString(1,Carpeta);
        ps.setString(2,"SaveFolder");
        ps.executeUpdate();

    }

    public void setNuevoPDF (FormularioPDF NewPDF) throws SQLException {

        sql = "INSERT INTO CamposFormularios (NombreCampo, NombreFormulario, Pagina, PosicionX, PosicionY, Largo, Ancho) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
        try {
            ps = c.prepareStatement(sql);

            ArrayList<CampoF> fields = NewPDF.getFormfields();

            for (CampoF campoF : fields) {
                CampoF field = new CampoF(campoF);
                ps.setString(1, field.getNameField());
                ps.setString(2, field.getNameFatherForm());
                ps.setInt(3, field.getPage());
                ps.setInt(4, field.getPosX());
                ps.setInt(5, field.getPosY());
                ps.setInt(6, field.getHeight());
                ps.setInt(7, field.getWidth());
                ps.addBatch();
            }
            ps.executeBatch();
        }
        finally {
            cerrar();
        }
    }

    public void setNuevoCampoPDF (String NombrePDF) throws SQLException {

        sql = "UPDATE CredencialesApp SET ValorCampo=? WHERE NombreCampo=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1,NombrePDF);
        ps.setString(2,"SaveFolder");
        ps.executeUpdate();
    }

    public String getCarpeta() throws SQLException {

        String Carpeta="";
        sql = "select * from CredencialesApp where Campo=?";

        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1,"SaveFolder");
        ResultSet myRs= ps.executeQuery();

        while (myRs.next()) {
            Carpeta = myRs.getString("ValorCampo");
        }
        cerrar();

        return Carpeta;

    }
    public void cerrar() {
        try {
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println("pasados close");
    }
}
