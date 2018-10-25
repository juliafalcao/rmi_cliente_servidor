package clienteservidor.cliente;

import java.rmi.RemoteException;
import java.util.Random;
import java.lang.Thread;

import clienteservidor.servico.*;
import clienteservidor.servidor.*;
import static clienteservidor.servidor.Servidor.*;

/*
Thread que, para um cliente passado no construtor, gera requisições aleatórias e faz chamadas ao servidor.
*/
class ThreadCliente extends Thread {
    public Cliente cliente = null;
    
    Random r = new Random(System.currentTimeMillis()); // gerador de números aleatórios

    /* Construtor */
    public ThreadCliente(Cliente cliente) {
        super();

        if (cliente != null) {
            setName("threadCliente" + cliente.id);
            this.cliente = cliente;
        }   
    }    

    public void run() {
        int arquivo, op;
        String conteudo; // o que será escrito no arquivo, null em caso de leitura

        for (int i = 0; i < 10; i++) { // gerar 10 requisições
            arquivo = r.nextInt(3); // escolher arquivo aleatório

            op = r.nextInt(2);
            switch (op) {
                case 0:
                    op = ESCRITA;
                    conteudo = "Cliente " + cliente.id + " esteve aqui!";
                    break;

                default:
                    op = LEITURA;
                    conteudo = null;
                    break;
            }
            
            System.out.printf("Cliente %d quer fazer uma %s no arquivo %s.%n", cliente.id, op == 0 ? "leitura" : "escrita", nomeArquivo(arquivo));
            
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