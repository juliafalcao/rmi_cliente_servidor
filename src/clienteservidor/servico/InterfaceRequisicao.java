package clienteservidor.servico;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/*
Interface remota do serviço.
Descreve o objeto remoto que representa um pedido enviado por um cliente para fazer leitura ou escrita num arquivo.
*/
public interface InterfaceRequisicao extends Remote {

    ArrayList<Thread> listaThreads = null;

    public RemoteRequisicao(Servidor servidor) throws RemoteException;

    public void requisicao(int cliente, int op, int arquivo) throws RemoteException;

    public void printStatus() throws RemoteException;

}