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

    public int id;
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
    public static final String objName = "rmi://localhost:1099/Request";


    /* Construtor */
    Cliente(int id) {
        this.id = id;

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
        switch (index) {
            case 0: return ARQUIVO_A;
            case 1: return ARQUIVO_B;
            case 2: return ARQUIVO_C;
            default: return null;
        }
    }

    /* Main */
    public static void main(String[] args) {

        try {
            Cliente cliente1 = new Cliente(CLIENTE_1);
            Cliente cliente2 = new Cliente(CLIENTE_2);
            Cliente cliente3 = new Cliente(CLIENTE_3);
            Cliente cliente;
            Random r = new Random(System.currentTimeMillis()); // gerador de números aleatórios
            int[] contagem = {0, 0, 0}; // contagem de requisições por cliente
            int c, arquivo, op;
            String conteudo; // o que será escrito no arquivo, em caso de leitura
        
            // escolher aleatoriamente um cliente, uma operação e um arquivo
            // roda até todos os clientes terem feito 10 requisições cada
            while (contagem[CLIENTE_1] + contagem[CLIENTE_2] + contagem[CLIENTE_3] < 30) {

                c = r.nextInt(3); // escolhe cliente aleatório

                if (contagem[c] == 10) { // se o cliente sorteado já completou 10, escolher outro
                    continue;
                }

                switch (c) { // setar variável cliente como a classe do cliente escolhido
                    case CLIENTE_1: cliente = cliente1; break;
                    case CLIENTE_2: cliente = cliente2; break;
                    case CLIENTE_3: cliente = cliente3; break;
                    default: cliente = null; break;
                }

                arquivo = r.nextInt(3);
                
                op = r.nextInt(3); /* mudar para 2 para igualar as probabilidades */
                
                switch (op) {
                    case 0:
                    op = ESCRITA;
                    conteudo = "Cliente " + c + " esteve aqui!\n";
                    break;

                    default: op = LEITURA;
                    conteudo = null;
                    break;
                }
                
                System.out.printf("Cliente %d quer fazer uma %s no arquivo %s.%n", c, op == 0 ? "leitura" : "escrita", nomeArquivo(arquivo));

                // chamar método de requisição e incrementar contagem
                String retorno = cliente.obj.requisicao(c, op, arquivo, conteudo);
                System.out.println("Retorno: " + retorno);
                contagem[c]++;

                System.out.printf("Contagem de requisições: %d / %d / %d%n", contagem[0], contagem[1], contagem[2]);
            }
        }

        catch (Exception e) {
            System.err.println("Exceção no Cliente: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void thiago(){
        try {







            Runnable Cliente1Requests() -> {
                Cliente cliente1 = new Cliente(CLIENTE_1);
                Random r = new Random(System.currentTimeMillis()); // gerador de números aleatórios
                int c, arquivo, op;   // o que será escrito no arquivo, em caso de leitura
                String conteudo;
                while (int contagem  <10){

                    arquivo = r.nextInt(3);
                    op = r.nextInt(3); /* mudar para 2 para igualar as probabilidades */

                    switch (op) {
                        case 0:
                            op = ESCRITA;
                            conteudo = "Cliente " + c + " esteve aqui!\n";
                            break;

                        default:
                            op = LEITURA;
                            conteudo = null;
                            break;
                    }
                    System.out.printf("Cliente %d quer fazer uma %s no arquivo %s.%n", 1, op == 0 ? "leitura" : "escrita", nomeArquivo(arquivo));
                    // chamar método de requisição e incrementar contagem
                    String retorno = cliente1.obj.requisicao(1, op, arquivo, conteudo);
                    System.out.println("Retorno: " + retorno);
                    contagem++;

                }
            }
            Runnable Cliente2Requests() -> {
                Cliente cliente2 = new Cliente(CLIENTE_2);
                Random r = new Random(System.currentTimeMillis()); // gerador de números aleatórios
                int arquivo, op;   // o que será escrito no arquivo, em caso de leitura
                String conteudo;
                while (int contagem  <10){

                    arquivo = r.nextInt(3);
                    op = r.nextInt(3); /* mudar para 2 para igualar as probabilidades */

                    switch (op) {
                        case 0:
                            op = ESCRITA;
                            conteudo = "Cliente " + c + " esteve aqui!\n";
                            break;

                        default:
                            op = LEITURA;
                            conteudo = null;
                            break;
                    }
                    System.out.printf("Cliente %d quer fazer uma %s no arquivo %s.%n", 2, op == 0 ? "leitura" : "escrita", nomeArquivo(arquivo));
                    // chamar método de requisição e incrementar contagem
                    String retorno = cliente2.obj.requisicao(2, op, arquivo, conteudo);
                    System.out.println("Retorno: " + retorno);
                    contagem++;

                }
            }
            Runnable Cliente1Requests() -> {
                Cliente cliente3 = new Cliente(CLIENTE_3);
                Random r = new Random(System.currentTimeMillis()); // gerador de números aleatórios
                int c, arquivo, op;   // o que será escrito no arquivo, em caso de leitura
                String conteudo;
                while ( int contagem  <10){
                    cliente = cliente1;
                    arquivo = r.nextInt(3);
                    op = r.nextInt(3); /* mudar para 2 para igualar as probabilidades */

                    switch (op) {
                        case 0:
                            op = ESCRITA;
                            conteudo = "Cliente " + c + " esteve aqui!\n";
                            break;

                        default:
                            op = LEITURA;
                            conteudo = null;
                            break;
                    }
                    System.out.printf("Cliente %d quer fazer uma %s no arquivo %s.%n", 3, op == 0 ? "leitura" : "escrita", nomeArquivo(arquivo));
                    // chamar método de requisição e incrementar contagem
                    String retorno = cliente3.obj.requisicao(3, op, arquivo, conteudo);
                    System.out.println("Retorno: " + retorno);
                    contagem++;

                }
            }

        }

        catch (Exception e) {
            System.err.println("Exceção no Cliente: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }
    }
}