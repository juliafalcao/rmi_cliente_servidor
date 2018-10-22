package clienteservidor.servico;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;

import clienteservidor.servico.ThreadOperacao;
import clienteservidor.cliente.Cliente;
import static clienteservidor.cliente.Cliente.*; // constantes declaradas em Cliente
import clienteservidor.servidor.Servidor;

/*
Implementação da interface remota.
Contém os métodos que serão chamados pelos clientes para efetuar operações nos arquivos.
*/
public class RemoteRequisicao extends UnicastRemoteObject implements InterfaceRequisicao {

    public ArrayList<ThreadOperacao> listaThreads = new ArrayList<ThreadOperacao>(); // lista de todas as threads criadas
    Servidor servidor = null;

    /* Construtor */
    public RemoteRequisicao(Servidor servidor) throws RemoteException {
        super();
        this.servidor = servidor;
    }

    /* Função que representa uma requisição feita por algum cliente para executar uma leitura ou escrita em algum dos arquivos.
    O servidor cria uma thread para a operação.
    Parâmetros:
        cliente: identificador [0-3] do cliente que fez a requisição
        op: 0 (leitura) ou 1 (escrita)
        arquivo: identificador [0-3] do arquivo no qual será feita a operação
        conteudo: o que será escrito no arquivo, ou null em caso de leitura
    */
    public String requisicao(int cliente, int op, int arquivo, String conteudo) throws RemoteException {
        String nomeArquivo = nomeArquivo(arquivo);
        System.out.printf("Nova requisição: Cliente %d quer fazer %s no arquivo %s.%n", cliente, (op == 0 ? "leitura" : "escrita"), nomeArquivo);

        try {
            String threadName = new String(cliente + "-" + (op == 0 ? "L" : "E") + "-" + nomeArquivo.charAt(0));
            // ex.: "1-E-B" = thread do cliente 1 escrevendo no arquivo B.txt
            ThreadOperacao thread = new ThreadOperacao(threadName, op, arquivo, conteudo);
            
            if (servidor.temPrioridade()) {
                if (op == LEITURA) {
                    thread.setPriority(10); /* em construção */
                }
            }

            listaThreads.add(thread);
            System.out.printf("Iniciando a thread %s.%n", thread.getName());
            thread.start();

            // obter resposta após a thread ser finalizada
            return thread.resposta();
        }

        catch (Exception e) {
            System.err.println("Exceção no RemoteRequisicao, método requisicao: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }

        return null;
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