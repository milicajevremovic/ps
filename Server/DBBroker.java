/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;



import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBBroker {
    Connection konekcija;

    public void ucitajDrajver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void otvoriKonekciju() {
        String url = "jdbc:mysql://localhost:3306/prosoftjun17";
        String username = "root";
        String password = "";

        try {
            konekcija = DriverManager.getConnection(url, username, password);
            konekcija.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void zatvoriKonekciju() {
        try {
            konekcija.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void commit() {
        try {
            konekcija.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rollback() {
        try {
            konekcija.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void sacuvajStavku(StavkaRentiranja s, int rentiranjeID) throws SQLException {
        String upit = "INSERT INTO StavkaRentiranja VALUES (?,?,?,?)";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.setInt(1, rentiranjeID);
        ps.setInt(2, s.getRb());
        ps.setInt(3, s.getOprema().getOpremaID());
        ps.setDouble(4, s.getIznos());
        
        ps.executeUpdate();
    }



    public ArrayList<PomocnaKlasaTabela> vratiPodatkeServer(Proizvodjac p) {
        ArrayList<PomocnaKlasaTabela> lista = new ArrayList<>();
        String upit = "select *, sum(s.Iznos / o.CenaPoDanu) as brojDana, sum(s.Iznos) as prihod from oprema o join proizvodjac p on o.ProizvodjacID = p.ProizvodjacID join stavkarentiranja s on o.OpremaID = s.OpremaID group by o.OpremaID order by prihod desc, brojDana desc";
        if(p != null){
            upit ="select *, sum(s.Iznos / o.CenaPoDanu) as brojDana, sum(s.Iznos) as prihod from oprema o join proizvodjac p on o.ProizvodjacID = p.ProizvodjacID join stavkarentiranja s on o.OpremaID = s.OpremaID where p.ProizvodjacID="+p.getProizvodjacID()+" group by o.OpremaID order by prihod desc, brojDana desc";
        }
        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(upit);
            while (rs.next()) {                
                Proizvodjac proiz = new Proizvodjac(rs.getInt("ProizvodjacID"), rs.getString("p.Naziv"), rs.getString("Adresa"));
                Oprema o = new Oprema(rs.getInt("OpremaID"), rs.getString("o.Naziv"), rs.getDouble("CenaPoDanu"), proiz);
                int brojDana = rs.getInt("brojDana");
                double prihod = rs.getDouble("prihod");
                PomocnaKlasaTabela pom = new PomocnaKlasaTabela(o, brojDana, prihod);
                lista.add(pom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

}

