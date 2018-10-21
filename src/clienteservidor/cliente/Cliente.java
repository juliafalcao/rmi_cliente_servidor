package clienteservidor.cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.util.Random;

import clienteservidor.servico.*;
import clienteservidor.servidor.*;


/*
Classe principal que vai gerar os 3 clientes e suas requisições de leitura e escrita nos arquivos.
*/
public class Cliente {

    private InterfaceRequisicao obj; // a interface do objeto remoto

    /* constantes */
    public static final String ARQUIVO_A = "A.txt";
    public static final String ARQUIVO_B = "B.txt";
    public static final String ARQUIVO_C = "C.txt";
    public static final int LEITURA = 0;
    public static final int ESCRITA = 1;
    public static final int CLIENTE_1 = 0;
    public static final int CLIENTE_2 = 1;
    public static final int CLIENTE_3 = 2;
    public static final String objName = "rmi://localhost:1099/Requisicao";


    /* Construtor */
    Cliente() {
        try {
            obj = (InterfaceRequisicao) Naming.lookup(objName);
        }

        catch (Exception e) {
            System.err.println("Exceção no Cliente - obtenção do obj: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }
    }


    /* Função auxiliar que recebe um índice de arquivo [0-2] e retorna seu nome */
    public static String nomeArquivo(int index) {
        String nome;

        switch (index) {
            case 0: nome = ARQUIVO_A; break;
            case 1: nome = ARQUIVO_B; break;
            case 2: nome = ARQUIVO_C; break;
            default: nome = null; break;
        }

        return nome;
    }


    /* Main */
    public static void main(String[] args) {

        try {
            Cliente cliente1 = new Cliente();
            Cliente cliente2 = new Cliente();
            Cliente cliente3 = new Cliente();
            Cliente cliente;
            Random r = new Random(); // gerador de números aleatórios
            int[] contagem = {0, 0, 0};
            int c, arquivo, op;

            // escolher aleatoriamente um cliente, uma operação e um arquivo
            // roda até todos os clientes terem feito 10 requisições cada
            while (contagem[0] <= 10 && contagem[1] <= 10 && contagem[2] <= 10) {
                c = r.nextInt(3); // escolhe cliente aleatório

                if (contagem[c] >= 10)
                    continue;

                switch (c) { // setar variável cliente como a classe do cliente escolhido
                    case CLIENTE_1: cliente = cliente1; break;
                    case CLIENTE_2: cliente = cliente2; break;
                    case CLIENTE_3: cliente = cliente3; break;
                    default: cliente = null; break;
                }
                
                op = r.nextInt(3);
                switch (op) {
                    case 0: op = ESCRITA; break;
                    default: op = LEITURA; break;
                    // se cair 0 será escrita e se cair 1 ou 2 será leitura, assim a leitura é mais provável
                }

                arquivo = r.nextInt(3);

                // chamar método de requisição e incrementar contagem
                cliente.obj.requisicao(c, op, arquivo);
                contagem[c]++;
            }
        }

        catch (Exception e) {
            System.err.println("Exceção no Cliente: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }
    }
}