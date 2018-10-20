package clienteservidor.operacao;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceOperacao extends Remote {
    
    /* essa vai ser a função que manda o servidor fazer a leitura ou escrita em algum dos arquivos */
    void funcaoOperacao() throws RemoteException;
}