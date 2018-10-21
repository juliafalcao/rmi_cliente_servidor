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

    int op, arquivo; // índices da operação (0 ou 1) e do arquivo (0, 1 ou 2)
    String nomeArquivo;
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

        this.op = operacao;
        this.arquivo = arquivo;
        nomeArquivo = nomeArquivo(this.arquivo);
    }

    /* Código executado pela thread após ser iniciada */
    public void run() {
        try {
            File file = new File("src\\clienteservidor\\arquivos\\" + nomeArquivo);

            if (!file.canRead() || !file.canWrite()) { // checar permissões
                System.err.println("Arquivo não está acessível para leitura ou para escrita.");
                throw new IOException();
            }

            // checar se o arquivo não está bloqueado para escrita, e se estiver, esperar um tempo e checar novamente
            while (locks[arquivo]) {
                System.out.printf("Thread %s não pode ler o arquivo %s pois ele está bloqueado.%n", getName(), nomeArquivo);
                Thread.sleep(SLEEP_MIN);
            }


            if (op == 0) { // efetuar leitura
                String conteudo = "";
                
                Scanner in = new Scanner(file);
                while (in.hasNext()) {
                    conteudo += in.nextLine();
                }

                System.out.printf("Thread %s está lendo o arquivo %s...%n", getName(), nomeArquivo);
                Thread.sleep(SLEEP_MIN + r.nextInt(SLEEP_MAX)); // sleep para a thread não ser rápida demais
                in.close();
            }

            else { // efetuar escrita
                if (locks[arquivo]) {
                    System.err.println("ERRO: permitiu chegar na escrita mas o lock está ativado.");
                    System.exit(0);
                }

                locks[arquivo] = true; // bloqueia arquivo para escrita
                System.out.printf("Arquivo %s foi bloqueado.%n", nomeArquivo);

                PrintWriter writer = new PrintWriter(new FileOutputStream(file, true));
                writer.println("O cliente " + getName().charAt(0) + " esteve aqui!"); // escreve no arquivo
                System.out.printf("Thread %s está escrevendo no arquivo %s...%n", getName(), nomeArquivo);
                Thread.sleep(SLEEP_MIN + r.nextInt(SLEEP_MAX)); // sleep por tempo aleatório
                writer.close();

                locks[arquivo] = false;
                System.out.printf("Arquivo %s foi desbloqueado.%n", nomeArquivo);
            }

            System.out.printf("Thread %s terminou sua operação.%n", getName());
        }

        catch (Exception e) {
            System.err.printf("Exceção na ThreadOperacao %s: %s%n",  getName(), e.toString());
            e.printStackTrace();
            System.exit(0);
        }
    }
}