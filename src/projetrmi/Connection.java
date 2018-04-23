package projetrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;
import java.util.List;

public interface Connection extends Remote {
    public List<String> demanderEncheres() throws RemoteException;
    public void addClient(Notifiable client, String nom) throws RemoteException;
}
