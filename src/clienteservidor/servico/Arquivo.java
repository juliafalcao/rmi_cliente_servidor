package clienteservidor.servico;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.lang.Thread;
import java.util.Random;

import clienteservidor.servidor.Servidor;

/*
Classe que encapsula um dos arquivos do servidor, nos quais serão feitas operações de leitura e escrita
requisitadas pelos clientes.
*/
public class Arquivo {
    public String nomeArquivo;
    private String caminho = "src/clienteservidor/arquivos/";
    private File file;
    Random r = new Random(System.currentTimeMillis()); // gerador de números aleatórios

    public int SLEEP_MIN = 5000;
    public int SLEEP_MAX = 12000;

    private boolean isFair = !(Servidor.temPrioridade());
    /* Quando o lock é "justo", ele tenta respeitar o melhor possível uma ordem de chegada.
    No servidor normal (não-prioritário), é isso que queremos.
    No servidor prioritário, queremos um lock "injusto", assim a prioridade das threads definirá a ordem de acesso aos arquivos.
    */

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(/*isFair*/ true);
    /* ReadWriteLock é um mecanismo de bloqueio do objeto que possui dois locks: o readLock pode ser adquirido por múltiplas
    threads ao mesmo tempo desde que ninguém esteja lendo o arquivo, ou seja, possua o writeLock, que só pode ser adquirido
    por uma thread por vez, desde que ninguém esteja lendo ou escrevendo no arquivo. */


    /* Construtor */
    public Arquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.file = new File(caminho + nomeArquivo);

        if (!file.canRead() || !file.canWrite()) { // checar acesso e permissões
            System.err.println("Arquivo não está acessível para leitura ou para escrita.");
            System.exit(0);
        }
    }

    /* Método que adquire um lock de leitura, efetua a leitura do arquivo completo e retorna o conteúdo lido. */
    public String leitura(String nomeThread) {
        try {
            readWriteLock.readLock().lock();
            String conteudo = "";
            Scanner reader = new Scanner(file);
            System.out.printf("Thread %s está lendo o arquivo %s...%n", nomeThread, nomeArquivo);

            while (reader.hasNext()) {
                conteudo += reader.nextLine();
            }

            reader.close();

            Thread.sleep(SLEEP_MIN + r.nextInt(SLEEP_MAX)); // pausa thread para a "leitura" demorar um pouco
            System.out.printf("Thread %s terminou sua operação.%n", nomeThread);
            return conteudo;
        }

        catch (Exception e) {
            System.err.println("Exceção na escrita do arquivo: " + e.toString());
            e.printStackTrace();
        }

        finally {
            readWriteLock.readLock().unlock();
        }

        return null;
    }

    /* Método que adquire o lock de escrita, efetua escrita do conteudo no arquivo, e retorna um booleano
    que diz se a escrita foi feita com sucesso ou não. */
    public boolean escrita(String conteudo, String nomeThread) {
        try {
            readWriteLock.writeLock().lock();
            System.out.printf("Thread %s bloqueou o arquivo %s para escrita.%n", nomeThread, nomeArquivo);
            PrintWriter writer = new PrintWriter(new FileOutputStream(file, true));
            System.out.printf("Thread %s está escrevendo no arquivo %s...%n", nomeThread, nomeArquivo);
            writer.println(conteudo);
            writer.close();
            Thread.sleep(SLEEP_MIN + r.nextInt(SLEEP_MAX));
            System.out.printf("Thread %s terminou sua operação.%n", nomeThread);
        }

        catch (Exception e) {
            System.err.println("Exceção na escrita do arquivo: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }

        finally {
            readWriteLock.writeLock().unlock();
            System.out.printf("Arquivo %s foi desbloqueado.%n", nomeArquivo);
        }

        return true;
    }



}