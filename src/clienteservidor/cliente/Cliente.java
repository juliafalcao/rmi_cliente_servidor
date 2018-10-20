package clienteservidor.cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.util.Random;

import clienteservidor.servico.*;
import clienteservidor.servidor.*;


/* Classe principal que vai gerar os 3 clientes e suas requisições de leitura e escrita nos arquivos. */

public class Cliente {

    private InterfaceRequisicao obj; // a interface do objeto remoto

    /* constantes */
    public static final String ARQUIVO_A = "A.txt";
    public static final String ARQUIVO_B = "B.txt";
    public static final String ARQUIVO_C = "C.txt";
    public static final int LEITURA = 0;
    public static final int ESCRITA = 1;
    public static final int CLIENTE_1 = 1;
    public static final int CLIENTE_2 = 2;
    public static final int CLIENTE_3 = 3;

    /* construtor */
    Cliente() {
        try {
            obj = (InterfaceRequisicao) Naming.lookup("rmi://localhost:2020/Requisicao");
        }

        catch (Exception e) {
            System.err.println("Exceção no Cliente: " + e.toString());
            e.printStackTrace();
        }
    }

    /* main */
    public static void main(String[] args) {
        
        // String host = (args.length < 1) ? null : args[0];

        try {            
            Cliente cliente1 = new Cliente();
            Cliente cliente2 = new Cliente();
            Cliente cliente3 = new Cliente();
            int[] contagem = {0, 0, 0};

            Random r = new Random();
            Cliente cliente;
            int c, arquivo, op;

            // escolher aleatoriamente um cliente, uma operação e um arquivo
            // roda até todos os clientes terem feito 10 requisições cada
            while (contagem[0] <= 10 && contagem[1] <= 10 && contagem[2] <= 10) {
                c = r.nextInt(3); // escolhe cliente aleatório

                if (contagem[c] >= 10)
                    continue;

                switch (c) {
                    case 0: cliente = cliente1; break;
                    case 1: cliente = cliente2; break;
                    case 2: cliente = cliente3; break;
                    default: cliente = null; break;
                }
                
                op = r.nextInt(3);
                switch (op) {
                    case 0: op = ESCRITA; break;
                    default: op = LEITURA; break; // para leitura ser mais provável que escrita
                }

                arquivo = r.nextInt(3);

                // enviar requisição e incrementar contagem
                cliente.obj.requisicao(c, op, arquivo);
                contagem[c]++;
                cliente.obj.printFila();
            }

        }

        catch (Exception e) {
            System.err.println("Exceção no Cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}