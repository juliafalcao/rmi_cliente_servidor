package clienteservidor.cliente;

import java.rmi.RemoteException;
import java.util.Random;
import java.lang.Thread;

import clienteservidor.servico.*;
import clienteservidor.servidor.*;
import static clienteservidor.cliente.Cliente.*;

/*
Versão modificada de ThreadCliente para demonstrar o servidor prioritário.
*/
class ThreadClienteTeste extends Thread {
    public Cliente cliente = null;
    
    Random r = new Random(System.currentTimeMillis()); // gerador de números aleatórios

    /* Construtor */
    public ThreadClienteTeste(Cliente cliente) {
        super();

        if (cliente != null) {
            setName("threadCliente" + cliente.id);
            this.cliente = cliente;
        }   
    }    

    public void run() {
        int arquivo, op;
        String conteudo = "teste"; // o que será escrito no arquivo, null em caso de leitura

        for (int i = 0; i < 1; i++) {
            arquivo = 0;

            switch (cliente.id) {
                case 1: op = 1; break; // cliente 1 vai querer escrever
                default: op = 0; break; // todos os outros clientes vão querer ler
            }
            
            System.out.printf("Cliente %d quer fazer uma %s no arquivo %s.%n", cliente.id, op == 0 ? "leitura" : "escrita", Servidor.nomeArquivo(arquivo));
            
            // chamar método de requisição e incrementar contagem
            try {
                String retorno = cliente.obj.requisicao(cliente.id, op, arquivo, conteudo);
                System.out.println(retorno);
            }

            catch (RemoteException e) {
                System.err.println("RemoteException numa ThreadCliente.");
                e.printStackTrace();
            }
        }
    }
};