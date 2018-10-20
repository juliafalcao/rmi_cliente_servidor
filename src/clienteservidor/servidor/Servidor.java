package clienteservidor.servidor;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;

// import clienteservidor.operacao.Operacao;
import clienteservidor.operacao.InterfaceOperacao;
import clienteservidor.operacao.RemoteOperacao;

/*
Servidor multithreaded que recebe pedidos, coloca-os numa fila e realiza operações de leitura e escrita em 3 arquivos diferentes.
*/

public class Servidor {

    /* constantes */
    public static final String ARQUIVO_A = "A.txt";
    public static final String ARQUIVO_B = "B.txt";
    public static final String ARQUIVO_C = "C.txt";
    public static final int LEITURA = 0;
    public static final int ESCRITA = 1;
    public static final int CLIENTE_1 = 1;
    public static final int CLIENTE_2 = 2;
    public static final int CLIENTE_3 = 3;


    /* construtor */
    public Servidor() {
    }


    /* main */
    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.createRegistry(2020);
            // Servidor servidor = new Servidor();
            InterfaceOperacao stub = new RemoteOperacao();
            registry.rebind("Operacao", stub);
            System.out.println("Servidor pronto!");
        }

        catch (Exception e) {
            System.err.println("Exceção no servidor: " + e.toString());
            e.printStackTrace();
        }
        
    }
}