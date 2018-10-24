package clienteservidor.servico;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.lang.Thread;
import java.util.Random;

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

    /* TODO: MOVER CRIAÇÃO DOS ARQUIVOS PARA DENTRO DO SERVIDOR */
    
    private boolean isFair = false; // TODO: definir com base no servidor ser prioritário ou não (prioritário: not fair, normal: fair)
    /* Quando o lock é "justo", ele tenta respeitar o melhor possível uma ordem de chegada. */

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(isFair);
    /* QUANDO O SERVIDOR NÃO É PRIORITÁRIO O LOCK PRECISA SER FAIR */
    /* ReadWriteLock é um mecanismo de bloqueio do objeto que possui dois locks, um de leitura de um de escrita.
    O lock de leitura pode ser adquirido por múltiplas threads ao mesmo tempo, e o de escrita, só por uma thread
    por vez. Isso permite que várias threads possam ler o arquivo ao mesmo tempo, mas só uma possa escrever. */


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
            // Thread.sleep(SLEEP_MAX); /* TESTE */
            System.out.printf("Thread %s terminou sua operação.%n", threadName);
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
            System.out.printf("Thread %s terminou sua operação.%n", threadName);
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