package projetrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;

public interface Enchere extends Remote {
    public enum ETAT {ENCOURS,CLOS}
    public void encherir(Notifiable encherisseur, int prix) throws RemoteException;
    public int getPrix() throws RemoteException;
    public Produit getProduit() throws RemoteException;
    public ETAT getEtat() throws RemoteException;
    public String getNom() throws RemoteException;
    public int getTemps()throws RemoteException;
    // public Encherisseur getMeneur() throws RemoteException;
  // public String sayHello(String nom) throws RemoteException;
  // public int operation(String operation) throws RemoteException;
}
