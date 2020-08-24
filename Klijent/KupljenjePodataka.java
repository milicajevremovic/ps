/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klijent;

/**
 *
 * @author Milica
 */
public class KupljenjePodataka {
    
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.SACUVAJ);
        kz.setParametar(r);
        
        KomunikacijaSaServerom.getInstanca().posaljiZahtev(kz);
        
        ServerskiOdgovor so = KomunikacijaSaServerom.getInstanca().primiOdgovor();
        
        JOptionPane.showMessageDialog(this, so.getPoruka());
        
    }                                          

    private void btnIzbirisActionPerformed(java.awt.event.ActionEvent evt) {                                           
       ModelTabele mt = (ModelTabele) tabelaStavki.getModel();
       int red = tabelaStavki.getSelectedRow();
       if(red != -1){
           mt.obrisiStavku(red);
           srediStavke();
       }
    }
}
