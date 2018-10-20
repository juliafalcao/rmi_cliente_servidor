package clienteservidor.servico;

import java.lang.Thread;
import java.util.Random;
import java.lang.IllegalArgumentException;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/*
Classe que representa uma thread, sendo que cada thread é uma operação de leitura ou escrita em um arquivo,
conforme requisitado por algum cliente.
*/
public class ThreadOperacao extends Thread {

    public static boolean[] locks = {False, False, False}; // booleanos que indicam se cada arquivo está bloqueado para escrita ou não
    /* Múltiplas threads podem ler um arquivo ao mesmo tempo, mas somente um pode escrever por vez. */

    int op, arq;
    String nome_arquivo;
    Random r = new Random();

    int SLEEP_MIN = 100;
    int SLEEP_MAX = 400;


    /* Construtor que recebe os índices da operação e do arquivo. */
    public ThreadOperacao(int operacao, int arquivo) {
        if (op < 0 || op > 1) {
            System.err.println("Exceção em ThreadOperacao");
            throw new IllegalArgumentException("O valor de op deve ser 0 (leitura) ou 1 (escrita).");
        }

        op = operacao;
        arq = arquivo;

        switch (arq) {
            case 0: nome_arquivo = ARQUIVO_A; break;
            case 1: nome_arquivo = ARQUIVO_B; break;
            case 2: nome_arquivo = ARQUIVO_C; break;
            default: nome_arquivo = null; break;
        }
    }

    /* Código executado pela thread após ser iniciada. */
    public void run() {
        try {
            File file = new File("src\\clienteservidor\\arquivos\\" + nome_arquivo);

            if (!file.canRead() || !file.canWrite()) { // checar permissões
                System.err.println("Arquivo não está acessível para leitura ou para escrita.");
                throw new IOException();
            }

            // checar se o arquivo não está bloqueado para escrita, e se estiver, esperar um tempo e checar novamente
            while (locks[arq]) {
                System.out.printf("Thread %s ainda não pode ler o arquivo %s pois ele está bloqueado para escrita.%s", getName(), nome_arquivo);
                Thread.sleep(SLEEP_MIN);
            }


            if (op == 0) { // efetuar leitura
                String trash;
                
                Scanner in = new Scanner(file);
                while (in.hasNext()) {
                    trash = in.nextLine();
                }

                System.out.printf("Thread %s está lendo o arquivo %s.%n", getName(), nome_arquivo());
                Thread.sleep(SLEEP_MIN + r.nextInt(SLEEP_MAX)); // sleep para a thread não ser rápida demais
                in.close();
            }

            else { // efetuar escrita
                if (locks[arq]) {
                    System.err.println("DEBUG: entrou na escrita quando estava com lock. ERRADO");
                }

                locks[arq] = True; // bloqueia arquivo para escrita
                System.out.println("Arquivo %s foi bloqueado para escrita.%n", nome_arquivo);

                PrintWriter writer = new PrintWriter(file);
                writer.println(r.nextInt(1000)); // escreve número aleatório entre 0 e 1000 no arquivo
                System.out.println("Thread %s está escrevendo no arquivo %s.%n", getName(), nome_arquivo);
                Thread.sleep(SLEEP_MIN + r.nextInt(SLEEP_MAX));
                writer.close();
                locks[arq] = False;
                System.out.println("Arquivo %s foi desbloqueado.%n", nome_arquivo);
            }
        }

        catch (Exception e) {
            System.err.printf("Exceção na ThreadOperacao %s: %s%n",  getName(), e.toString());
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        
    }
}