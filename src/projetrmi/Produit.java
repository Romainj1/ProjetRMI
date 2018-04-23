package projetrmi;

import java.io.Serializable;

public class Produit implements Serializable{
    private int prix;
    private String nom;

    public Produit(){
        this.prix=0;
        this.nom="NA";
    }
    public Produit(String nom){
        this.prix=0;
        this.nom=nom;
    }
    public Produit(int prix,String nom){
        this.prix=prix;
        this.nom=nom;
    }
    public int getPrix(){
        return this.prix;
    }
    public String getNom(){
        return this.nom;
    }
    public void setPrix(int prix){
        this.prix=prix;
    }
}
