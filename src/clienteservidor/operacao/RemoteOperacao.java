package clienteservidor.operacao;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;


/*
Implementação da interface remota.
*/ 
public class RemoteOperacao extends UnicastRemoteObject implements InterfaceOperacao {

    public RemoteOperacao() throws RemoteException {
        super();
    }

    /* Função principal que representa um pedido vindo de algum cliente para
    realizar uma leitura ou escrita em algum arquivo. */
    public void operacao(int cliente, int op, String arquivo) throws RemoteException {
        // teste
        System.out.println("rodando função operação");
        System.out.println("cliente: " + cliente);
        System.out.println("operação: " + op);
        System.out.println("arquivo: " + arquivo);
    }

}