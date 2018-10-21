package clienteservidor.servico;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;

import clienteservidor.servico.ThreadOperacao;
import static clienteservidor.cliente.Cliente.*; // constantes declaradas em Cliente
import clienteservidor.servidor.Servidor;

/*
Implementação da interface remota.
Contém os métodos que serão chamados pelos clientes para efetuar operações nos arquivos.
*/
public class RemoteRequisicao extends UnicastRemoteObject implements InterfaceRequisicao {

    public ArrayList<Thread> listaThreads = new ArrayList<Thread>(); // lista de todas as threads criadas
    Servidor servidor = null;

    /* Construtor */
    public RemoteRequisicao(Servidor servidor) throws RemoteException {
        super();
    }

    /* Função que representa uma requisição feita por algum cliente para executar uma leitura ou escrita em algum dos arquivos.
    O servidor cria uma thread para a operação. */
    public void requisicao(int cliente, int op, int arquivo) throws RemoteException {
        String nomeArquivo = nomeArquivo(arquivo);

        System.out.printf("Nova requisição chega ao servidor: Cliente %d quer fazer %s no arquivo %s.%n", cliente, (op == 0 ? "leitura" : "escrita"), nomeArquivo.charAt(0));

        try {
            String threadName = new String(cliente + "-" + (op == 0 ? "L" : "E") + "-" + nomeArquivo.charAt(0));
            // ex.: "1/E/B" = thread do cliente 1 escrevendo no arquivo B.txt
            ThreadOperacao thread = new ThreadOperacao(op, arquivo, threadName);
            listaThreads.add(thread);
            thread.start();
            System.out.printf("Iniciando a thread %s.%n", thread.getName());
        }

        catch (Exception e) {
            System.err.println("Exceção no RemoteRequisicao, método requisicao: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void printStatus() throws RemoteException {
        System.out.printf("%n-- MULTITHREADING STATUS%n");
        Thread atual;

        for (int i = 0; i < listaThreads.size(); i++) {
            atual = listaThreads.get(i);

            System.out.printf("%s: %s / %s%n", atual.getName(), (atual.isAlive() ? "alive" : "dead"), (atual.isInterrupted() ? "interrupted" : "not interrupted"));
        }

        System.out.println("--");
    }
}