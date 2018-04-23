package projetrmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Scanner;
import java.rmi.RemoteException;
import java.rmi.AlreadyBoundException;
public class Serveur {

    private static int actionAdminServeur=0;
    private static int port  = 1099;
    private ConnectionImpl connections;
    
    public static void main(String args[]) {
        System.out.println("*** SERVEUR PROJET RMI ASI 4 ***");
        System.out.println("*** Romain JACQUIER/ Gaetan BAERT ***");
        if(args.length==1){
            port = Integer.parseInt(args[0]);
        }
        try {
            ConnectionImpl connections = new ConnectionImpl();
            Connection skeleton = (Connection)UnicastRemoteObject.exportObject(connections, 0);
            Registry registry = LocateRegistry.getRegistry(port);
            //connection with clients
            if(!Arrays.asList(registry.list()).contains("Connection")){
                registry.bind("Connection", skeleton);
            }
            else{
                registry.rebind("Connection", skeleton);
            }
            while (true){
                actionAdminServeur = ihmDemanderUtlisiateur();
                switch(actionAdminServeur){
                    case 0 :creerEnchere();break;
                    case 1 :afficheListeEncherisseurs();break;
                    case 2 :afficheListeEncheres();break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static int ihmDemanderUtlisiateur(){
        System.out.println("Creer une Enchère : 0");
        System.out.println("Afficher la liste des encherisseurs : 1");
        System.out.println("Afficher la liste des enchères: 2");
        Scanner sc2 = new Scanner(System.in);
        return sc2.nextInt();
    }

    private static void afficheListeEncherisseurs(){
        System.out.println(" --------- LISTE DES ENCHERISSEURS -----------");
        for (String connections : ConnectionImpl.clientsNotifiables){
            System.out.print(connections+"; ");
        }
        System.out.println(" ");
        System.out.println(" --------- FIN LISTE DES ENCHERES -----------");
    }   

    private static void afficheListeEncheres(){
        try {
            System.out.println(" --------- LISTE DES ENCHERES -----------");
            for (EnchereImpl enchere : EnchereImpl.listeEncheres){
                System.out.print(enchere.getNom()+"; ");
            }
            System.out.println(" ");
            System.out.println(" --------- FIN LISTE DES ENCHERES -----------");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void creerEnchere(){

        Produit produit = creerUnProduit();
        Scanner sc = new Scanner(System.in);
        System.out.println("    Indiquer la durée max de l'enchère :");
        int prix = sc.nextInt();
        
        EnchereImpl nouvelleEnchere = new EnchereImpl(prix,produit);
        try {
            Registry registry = LocateRegistry.getRegistry(port);
            Enchere enchere = (Enchere)UnicastRemoteObject.exportObject(nouvelleEnchere, 0);
            if(!Arrays.asList(registry.list()).contains("Premier objet")){
                registry.bind(produit.getNom(), enchere );
            }
            else{
                registry.rebind(produit.getNom(), enchere);
            }
        }catch (RemoteException e){
            System.out.println(e.getMessage());
        }catch (AlreadyBoundException e2){
            System.out.println(e2.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());

        }

    }

    private static Produit creerUnProduit(){
        System.out.println("------ Création de Produit --------");
        Scanner sc2 = new Scanner(System.in);
        System.out.println("    Indiquer le nom :");
        String nom = sc2.nextLine();
        System.out.println("    Indiquer le prix :");
        int prix = sc2.nextInt();
        return new Produit(prix,nom);
    }
}
