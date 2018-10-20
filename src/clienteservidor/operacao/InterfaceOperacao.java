package clienteservidor.operacao;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
Interface remota do serviço a ser requisitado pelos clientes para o servidor.
*/
public interface InterfaceOperacao extends Remote {

    /* essa vai ser a função que manda o servidor fazer a leitura ou escrita em algum dos arquivos */
    public void operacao(int cliente, int op, String arquivo) throws RemoteException;
}