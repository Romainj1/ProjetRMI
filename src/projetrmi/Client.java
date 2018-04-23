package projetrmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.*;
import java.rmi.RemoteException;

public class Client extends UnicastRemoteObject implements Notifiable {
    private static String nom;
    private static Connection con;
    public Client() throws RemoteException {}
    private static Client clientMoi;
    public static void main(String args[]) {

        System.out.println("*** CLIENT PROJET RMI ASI 4 ***");
        System.out.println("*** Romain JACQUIER/ Gaetan BAERT ***");
        String machine = "localhost";
        int port = 1099;
        System.out.println("Entrez votre pseudo");
        Scanner sc = new Scanner(System.in);
        nom = sc.nextLine();
       
        
        try {
            Registry registry = LocateRegistry.getRegistry(machine, port);
            con = (Connection)registry.lookup("Connection");
            
            clientMoi = new Client();
            con.addClient(clientMoi,nom);
            while(true){
                List<String> encheres = afficherEncheres();
                System.out.println("Que voulez-vous faire ? (tapez le numéro de l'enchère pour y acceder), 0 pour ne rien faire");

                int choix = sc.nextInt();
                if (choix!=0){
                    Enchere enchereChoisie = (Enchere)registry.lookup(encheres.get(choix-1));
                    System.out.println("Vous avez selectionné :"+ enchereChoisie.getNom()); 
                    System.out.println("Nom   | Prix   | Temps  ");
                    System.out.println(enchereChoisie.getNom()+"   | "+enchereChoisie.getPrix()+"  | "+enchereChoisie.getTemps());
                    System.out.println("voulez-vous enchérir (oui/non) ? si non, vous retournerez à la liste des enchères.");
                    String chenchere = sc.nextLine();
                    chenchere = sc.nextLine();
                    switch(chenchere){
                        case "oui" :
                            System.out.println("quel prix voulez-vous mettre ?");
                            int prix = sc.nextInt();
                            enchereChoisie.encherir(clientMoi,prix);
                            System.out.println("Votre Enchere a bin été prise en compte!");
                            break;
                        case "non" :
                            System.out.println("On reviens à la liste des Encheres");
                        default : break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Client exception: " +e);
        }
    }
    public void notifier(String nomEnchere, int Prix, String meneurActuel) throws RemoteException{
        System.out.println("Nouvelle Notification ! :");
        System.out.println("Enchere : "+nomEnchere);
        System.out.println("Nouveau Prix :"+Prix);
        System.out.println("Meneur actuel :"+meneurActuel);
        afficherEncheres();
    }

    public void notifierFin(String nomEnchere, int Prix, String meneurActuel) throws RemoteException{
        System.out.println("Nouvelle Notification ! :");
        System.out.println("L'ENCHERE : "+nomEnchere+" EST TERMINEE");
        System.out.println("Prix atteint :"+Prix);
        System.out.println("Heureux Gagnant :"+meneurActuel);
        afficherEncheres();
    }
    public String getNom() throws RemoteException{
        return this.nom;
    }
    private static List<String> afficherEncheres(){
        int compteEncheres=1;
        try{
            List<String> encheres = con.demanderEncheres();
            System.out.println("---- Encheres : ------");
            for (String enchere :encheres){
                System.out.print(compteEncheres+" : "+enchere);
                compteEncheres++;
                System.out.println(" ");
            }
            return encheres;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;

    }
    public void notifyNameTaken() throws RemoteException{
        System.out.println("Name is already taken !!!!");
        System.out.println("Entrez de nouveau votre pseudo !");
        Scanner sc = new Scanner(System.in);
        nom = sc.nextLine();
        try{
            con.addClient(clientMoi,nom);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void alertEnchereFermee(String enchere) throws RemoteException{
        System.out.println("Attention, Vous ne pouvez polus surencherir !");
        System.out.println("L'enchere : "+enchere+" est fermée");
    }


}