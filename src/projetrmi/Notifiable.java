package projetrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;

public interface Notifiable extends Remote {
    public void notifier(String nomEnchere, int Prix, String meneurActuel) throws RemoteException;
    public String getNom() throws RemoteException;
    public void notifyNameTaken() throws RemoteException;
    public void notifierFin(String nomEnchere, int Prix, String meneurActuel) throws RemoteException;
    public void alertEnchereFermee(String enchere) throws RemoteException;
}
