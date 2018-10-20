package clienteservidor.servico;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;

import clienteservidor.servico.ThreadOperacao;

/*
Implementação da interface remota.
Contém o método que será chamado pelos clientes. Efetua operações de leitura e escrita nos arquivos.
*/
public class RemoteRequisicao extends UnicastRemoteObject implements InterfaceRequisicao {

    public RemoteRequisicao() throws RemoteException {
        super();
    }

    /* Função que representa uma requisição vindo de algum cliente para executar uma leitura ou escrita em algum dos arquivos.
    O servidor deve colocar a requisição numa fila e tratá-la. */
    public void requisicao(int cliente, int op, int arquivo) throws RemoteException {
        // teste
        System.out.printf("REQUEST: Cliente %d quer fazer %s no arquivo %d.", cliente, op == 0 ? "leitura" : "escrita", arquivo);
        System.out.println();

        ThreadOperacao thread = new ThreadOperacao(op, arquivo);
        thread.setName(cliente + "-" + (op == 0 ? "L" : "E") + "-" + arquivo.charAt(0)); // ex.: "1/E/B" = thread do cliente 1 escrevendo no arquivo B.txt
        thread.start();
        System.out.printf("Iniciando a thread %s.%n", thread.getName());



    }
}