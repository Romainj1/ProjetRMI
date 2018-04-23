package projetrmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.io.Serializable;
import java.util.*;
import java.rmi.RemoteException;

public class ConnectionImpl implements Connection{
    private String nom;
    private int port = 1099;
    public static List<String> clientsNotifiables = new ArrayList<String>();
    public ConnectionImpl(){
    }

    public List<String> demanderEncheres() throws RemoteException {

        List<String> listeEncheres = new ArrayList<String>();
        for (EnchereImpl enchere : EnchereImpl.listeEncheres){
            listeEncheres.add(enchere.getNom());
        }
        return listeEncheres;
    }

    public void addClient(Notifiable client,String nom) throws RemoteException {
        if (clientsNotifiables.contains(nom)){
            client.notifyNameTaken();
        }else{
            this.clientsNotifiables.add(nom);
        }
        
    }

}
