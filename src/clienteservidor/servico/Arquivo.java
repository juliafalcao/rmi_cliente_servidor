package clienteservidor.servico;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.lang.Thread;
import java.util.Random;


public class Arquivo {
    public String nomeArquivo;
    private String caminho = "src\\clienteservidor\\arquivos\\";
    private File file;
    Random r = new Random(System.currentTimeMillis()); // gerador de números aleatórios
    public int SLEEP_MIN = 5000;
    public int SLEEP_MAX = 12000;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    /* O arquivo pode ser lido por mais de uma thread ao mesmo tempo, mas só uma pode escrever de cada vez. */


    /* Construtor */
    public Arquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.file = new File(caminho + nomeArquivo);

        if (!file.canRead() || !file.canWrite()) { // checar permissões
            System.err.println("Arquivo não está acessível para leitura ou para escrita.");
            System.exit(0);
        }
    }

    /* Método que adquire um lock de leitura, efetua leitura, coloca a thread em sleep por um tempo aleatório
    e retorna o conteúdo lido. */
    public String leitura(String threadName) {
        String conteudo = "";

        try {
            readWriteLock.readLock().lock();

            Scanner in = new Scanner(file);
            System.out.printf("Thread %s está lendo o arquivo %s...%n", threadName, nomeArquivo);

            while (in.hasNext()) {
                conteudo += in.nextLine();
            }

            in.close();
            Thread.sleep(SLEEP_MIN + r.nextInt(SLEEP_MAX)); // sleep para a thread não ser rápida demais
        }

        catch (Exception e) {
            System.err.println("Exceção na escrita do arquivo: " + e.toString());
            e.printStackTrace();
        }

        finally {
            readWriteLock.readLock().unlock();
        }

        return conteudo;
    }

    /* Método que adquire o lock de escrita, efetua escrita do conteudo no arquiv, coloca a thread em sleep por um tempo aleatório
    e retorna um booleano dizendo se a escrita foi feita com sucesso ou não. */
    public boolean escrita(String conteudo, String threadName) {
        try {
            readWriteLock.writeLock().lock();
            System.out.printf("Arquivo %s foi bloqueado para escrita pela thread %s.%n", nomeArquivo, threadName);
            PrintWriter writer = new PrintWriter(new FileOutputStream(file, true));
            System.out.printf("Thread %s está escrevendo no arquivo %s...%n", threadName, nomeArquivo);
            writer.println(conteudo);
            writer.close();
            Thread.sleep(SLEEP_MIN + r.nextInt(SLEEP_MAX));
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