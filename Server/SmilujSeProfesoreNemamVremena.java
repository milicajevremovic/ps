/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author Milica
 */
public class SmilujSeProfesoreNemamVremena {
    
    
    
    
//    long diff = datumDo.getTime() - datumOd.getTime();
//        long dani = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//    
    private void srediKombo() {
        ArrayList<Proizvodjac> lista = Kontroler.getInstanca().dajProizvodjace();
        
        cmbProizvodjaci.removeAllItems();
        
        for (Proizvodjac proizvodjac : lista) {
            cmbProizvodjaci.addItem(proizvodjac);
        }
    }

    public void srediTabelu() {
        Proizvodjac p = null;
        
        if(cbFilter.isSelected()){
            p = (Proizvodjac) cmbProizvodjaci.getSelectedItem();
        }
        ArrayList<PomocnaKlasaTabela> lista = Kontroler.getInstanca().vratiListuZaServer(p);
        ModelTabeleServer mts = (ModelTabeleServer) tabelaServer.getModel();
        mts.setLista(lista);

    }
    
//    public static void main(String[] args) {
//        ThreadServer threadServer=new ThreadServer();
//        threadServer.start();
//        Scanner scanner=new Scanner(System.in);
//        System.out.println("[FON] chat server");
//        while(true){
//            try {
//                System.out.print("Poruka za aktivne korisnike: ");
//                String text=scanner.nextLine();
//                Poruka poruka=new Poruka(0, "server", "", text, new Date());
//                threadServer.dodajPoruke(poruka);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
}
