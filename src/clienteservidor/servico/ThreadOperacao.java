package clienteservidor.servico;

import java.lang.Thread;
import java.util.MissingFormatArgumentException;
import java.util.Random;
import java.lang.IllegalArgumentException;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;

import clienteservidor.servidor.Servidor;
import static clienteservidor.cliente.Cliente.*;

/*
Classe que representa uma thread, sendo que cada thread é criada para efetuar uma operação de leitura ou escrita em um arquivo,
conforme requisitado por algum cliente.
*/
public class ThreadOperacao extends Thread {

    protected static boolean[] locks = {false, false, false};
    /* Múltiplas threads podem ler um arquivo ao mesmo tempo, mas somente um pode escrever por vez.
    Nessa lista locks, os booleanos indicam se cada arquivo está bloqueado para escrita ou não.
    A lista é estática, então é compartilhada entre todas as instâncias da classe.
    */

    int op, arq; // índices da operação (0 ou 1) e do arquivo (0, 1 ou 2)
    String nome_arquivo;
    Random r = new Random(System.currentTimeMillis());

    int SLEEP_MIN = 5000;
    int SLEEP_MAX = 15000;


    /* Construtor que recebe os índices da operação e do arquivo, e o nome da thread. */
    public ThreadOperacao(int operacao, int arquivo, String threadName) {
        super(threadName); // chama construtor de Thread e passa o nome

        if (op < 0 || op > 1) {
            System.err.println("Exceção no construtor de ThreadOperacao.");
            throw new IllegalArgumentException("O valor de op deve ser 0 (leitura) ou 1 (escrita).");
        }

        op = operacao;
        arq = arquivo;
        nome_arquivo = nomeArquivo(arq);
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
                System.out.printf("Thread %s ainda não pode ler o arquivo %s pois ele está bloqueado para escrita.%n", getName(), nome_arquivo);
                Thread.sleep(SLEEP_MIN);
            }


            if (op == 0) { // efetuar leitura
                String trash;
                
                Scanner in = new Scanner(file);
                while (in.hasNext()) {
                    trash = in.nextLine();
                }

                System.out.printf("Thread %s está lendo o arquivo %s.%n", getName(), nome_arquivo);
                Thread.sleep(SLEEP_MIN + r.nextInt(SLEEP_MAX)); // sleep para a thread não ser rápida demais
                in.close();
            }

            else { // efetuar escrita
                if (locks[arq]) {
                    System.err.println("DEBUG: entrou na escrita quando estava com lock. ERRADO");
                }

                locks[arq] = true; // bloqueia arquivo para escrita
                System.out.printf("Arquivo %s foi bloqueado para escrita.%n", nome_arquivo);

                PrintWriter writer = new PrintWriter(new FileOutputStream(file, true));
                writer.println(r.nextInt(1000)); // escreve número aleatório entre 0 e 1000 no arquivo
                System.out.printf("Thread %s está escrevendo no arquivo %s.%n", getName(), nome_arquivo);
                Thread.sleep(SLEEP_MIN + r.nextInt(SLEEP_MAX));
                writer.close();
                locks[arq] = false;
                System.out.printf("Arquivo %s foi desbloqueado.%n", nome_arquivo);
            }
        }

        catch (Exception e) {
            System.err.printf("Exceção na ThreadOperacao %s: %s%n",  getName(), e.toString());
            e.printStackTrace();
            System.exit(0);
        }
    }
}