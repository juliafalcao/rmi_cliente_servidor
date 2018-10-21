package clienteservidor.servico;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;

import clienteservidor.servico.ThreadOperacao;
import static clienteservidor.cliente.Cliente.*; // constantes declaradas em Cliente

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
        System.out.printf("REQUEST: Cliente %d quer fazer %s no arquivo %d.%n", cliente, (op == 0 ? "leitura" : "escrita"), arquivo);
        
        String nome_arquivo = nomeArquivo(arquivo);

        try {
            String threadName = new String(cliente + "-" + (op == 0 ? "L" : "E") + "-" + nome_arquivo.charAt(0));
            // ex.: "1/E/B" = thread do cliente 1 escrevendo no arquivo B.txt
            ThreadOperacao thread = new ThreadOperacao(op, arquivo, threadName);
            thread.start();
            System.out.printf("Iniciando a thread %s.%n", thread.getName());
        }

        catch (Exception e) {
            System.err.println("Exceção no método requisicao: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }



    }
}