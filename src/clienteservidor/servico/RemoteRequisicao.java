package clienteservidor.servico;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;


/*
Implementação da interface remota.
Contém o método que será chamado pelos clientes.
*/ 
public class RemoteRequisicao extends UnicastRemoteObject implements InterfaceRequisicao {

    ArrayList<Integer> testeFila;

    public RemoteRequisicao() throws RemoteException {
        super();
        testeFila = new ArrayList<Integer>();
    }

    /* Função que representa uma requisição vindo de algum cliente para executar uma leitura ou escrita em algum dos arquivos.
    O servidor deve colocar a requisição numa fila e tratá-la.
    */
    public void requisicao(int cliente, int op, String arquivo) throws RemoteException {
        // teste
        System.out.printf("REQUEST: Cliente %d quer fazer %s no arquivo %s.", cliente, op == 0 ? "leitura" : "escrita", arquivo);
        testeFila.add(cliente);
        System.out.println();
    }

    public void printFila() throws RemoteException {
        System.out.println("TESTE FILA:");
        for (int i = 0; i < testeFila.size(); i++)
            System.out.println(testeFila.get(i));
    }

}