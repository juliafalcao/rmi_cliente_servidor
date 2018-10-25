package clienteservidor.servidor;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.util.ArrayList;

import clienteservidor.servico.*;

/*
Servidor multithreaded que recebe pedidos de leitura e escrita em arquivos e cria uma thread para cada.
*/

public class Servidor {
    public static boolean prioritario; // diz se o servidor é prioritário ou não
    public static Arquivo[] arquivos = {new Arquivo("A.txt"), new Arquivo("B.txt"), new Arquivo("C.txt")};

    /* Constantes */
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
    public Servidor(boolean prioritario) {
        Servidor.prioritario = prioritario;
        /* uso ruim de variável estática mas como só vai ter um Servidor tá tudo bem */
    }

    /* Função para checar se o servidor é prioritário ou não */
    public static boolean temPrioridade() {
        return prioritario;
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
            boolean isPrioritario;

            if (args.length > 0) {
                isPrioritario = args[0].equals("prioritario"); // busca argumento passado no comando java
            } else {
                isPrioritario = false;
            }

            Servidor servidor = new Servidor(isPrioritario);

            /* setup do RMI */
            Registry registry = LocateRegistry.createRegistry(1099); // criação do Registro na porta 1099
            InterfaceRequisicao obj = new RemoteRequisicao(servidor); // criação do objeto remoto
            registry.rebind("Request", obj); // associa o objeto ao nome "Request" no Registro

            System.out.printf("%nServidor (%s) está rodando e pronto para uso.%n", Servidor.prioritario ? "prioritário" : "normal");
        }

        catch (Exception e) {
            System.err.println("Exceção no Servidor: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }
        
    }
}