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

    int op, arq; // índices da operação (0 ou 1) e do arquivo (0, 1 ou 2)
    String nomeArquivo;
    String resposta = null; // resposta da operação para enviar ao cliente
    String conteudo; // o que será escrito no arquivo, null em caso de leitura

    public static Arquivo[] arquivos = {new Arquivo("A.txt"), new Arquivo("B.txt"), new Arquivo("C.txt")};


    /* Construtor que recebe o nome da thread, os identificadores de operação e do arquivo, e uma String de conteúdo se
    a operação for de escrita (caso contrário, null). */
    public ThreadOperacao(String threadName, int operacao, int arquivo, String conteudo) {
        super(threadName); // chama construtor de Thread e passa o nome

        if (op < 0 || op > 1) {
            System.err.println("Exceção no construtor de ThreadOperacao.");
            throw new IllegalArgumentException("O valor de op deve ser 0 (leitura) ou 1 (escrita).");
        }

        if (op == ESCRITA && conteudo == null) {
            System.err.println("Erro em ThreadOperacao: Operação de escrita mas conteúdo nulo. Nada será escrito.");
            conteudo = "";
        }

        this.op = operacao;
        this.arq = arquivo;
        nomeArquivo = nomeArquivo(this.arq);
        this.conteudo = conteudo;
    }

    /* Código executado pela thread após ser iniciada */
    @Override
    public void run() {
        try {
            Arquivo arquivo = arquivos[arq]; // obter Arquivo correspondente a requisição atual

            if (op == 0) { // efetuar leitura
                String conteudoLido = arquivo.leitura(getName()); // faz leitura e sleep
                resposta = "Cliente " + getName().charAt(0) + " leu " + conteudoLido.length() + " caracteres do arquivo " + nomeArquivo + ".";
            }

            else { // efetuar escrita
                boolean retorno = arquivo.escrita(conteudo, getName());
                if (retorno) {
                    resposta = "Cliente " + getName().charAt(0) + " escreveu uma linha no arquivo " + nomeArquivo + ".";
                }
            }
        }

        catch (Exception e) {
            System.err.printf("Exceção na ThreadOperacao %s: %s%n",  getName(), e.toString());
            e.printStackTrace();
            System.exit(0);
        }
    }
 
    /* Função de retorno da thread */
    public String resposta() {
        while (isAlive()) {
            // espera a thread ser finalizada
        }

        return resposta;
    }
}