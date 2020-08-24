/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Kontroler {
    private static Kontroler instanca;
    DBBroker db;

    private Kontroler() {
        db = new DBBroker();
    }

    public static Kontroler getInstanca() {
        if(instanca == null)
            instanca = new Kontroler();
        return instanca;
    }

    public ArrayList<Oprema> dajMiOpremu() {
        db.ucitajDrajver();
        db.otvoriKonekciju();
        ArrayList<Oprema> lista = db.vratiOpremu();
        db.zatvoriKonekciju();
        
        return lista;
    }

    public ServerskiOdgovor sacuvajRentiranje(Rentiranje r) {
        ServerskiOdgovor so = new ServerskiOdgovor();
        db.ucitajDrajver();
        db.otvoriKonekciju();
        try {
            int rentiranjeID = db.vratiID();
            r.setRentiranjeID(rentiranjeID);
            db.sacuvajRentiranje(r);
            for (StavkaRentiranja s : r.getLista()) {
                db.sacuvajStavku(s,r.getRentiranjeID());
            }
            db.commit();
            so.setPoruka("Uspesno sacuvano sve");
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            db.rollback();
            so.setPoruka("zajebao si nesto "+ex.getMessage());
        }
        
        db.zatvoriKonekciju();
        
        return so;
    }



