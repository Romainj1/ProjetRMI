package projetrmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.io.Serializable;
import java.util.*;
import java.rmi.RemoteException;

public class EnchereImpl implements Enchere {
    public static List<EnchereImpl> listeEncheres = new ArrayList<EnchereImpl>();
    public static List<String> listeNomEncheres = new ArrayList<String>();
    private int temps;
    private Produit produit;
    private ETAT etat;
    private List<Notifiable> lesInterresses;
    private Notifiable meneur;
    public EnchereImpl(int temps, Produit produit ){
        lesInterresses = new ArrayList<Notifiable>();
        if (!this.listeNomEncheres.contains(produit.getNom())){
            this.etat=ETAT.ENCOURS;
            this.temps = temps;
            this.produit = produit;
            this.listeEncheres.add(this);
            this.listeNomEncheres.add(produit.getNom());
            System.out.println(" Enchere créée");
        }
    }

    private void ajouterEncherisseur(Notifiable client){
        this.lesInterresses.add(client);
    }

    public void encherir(Notifiable client, int prix) throws RemoteException{
        if (this.etat == ETAT.CLOS){
            for (Notifiable unClient : lesInterresses){
                unClient.alertEnchereFermee(this.getNom());
            }
        }else{
            this.temps=temps -1;
            if (temps<0){
                if (this.etat == ETAT.ENCOURS){
                   for (Notifiable unClient : lesInterresses){
                        unClient.notifierFin(this.getNom(),this.getPrix(),this.getMeneur().getNom());
                    }
                }
                this.etat=ETAT.CLOS;
            }
            if (this.etat==ETAT.ENCOURS){
            //rajouter condition etat
                if (!this.lesInterresses.contains(client)){
                    this.ajouterEncherisseur(client);
                }
                if (produit.getPrix() < prix){
                    this.meneur = client;
                    produit.setPrix(prix);
                    for (Notifiable unClient : lesInterresses){
                        unClient.notifier(this.getNom(),this.getPrix(),this.getMeneur().getNom());
                    }
                }
            }
        }
    }

    public int getPrix(){
        return produit.getPrix();
    }
    public Produit getProduit(){
        return this.produit;
    }
    public ETAT getEtat(){
        return this.etat;
    }
    public String getNom(){
        return this.produit.getNom();
    }

    public Notifiable getMeneur(){
        return this.meneur;
    }
    public int getTemps(){
        return this.temps;
    }


    public void fermerEnchere(){
        this.etat=ETAT.CLOS;
    }



}
