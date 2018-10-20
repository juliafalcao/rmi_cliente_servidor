package clienteservidor.servico;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/*
Interface remota do serviço.
Descreve a implementação do objeto remoto que representa um pedido enviado por um cliente para fazer leitura ou escrita num arquivo.
*/
public interface InterfaceRequisicao extends Remote {

    public void requisicao(int cliente, int op, String arquivo) throws RemoteException;

    public void printFila() throws RemoteException;
}