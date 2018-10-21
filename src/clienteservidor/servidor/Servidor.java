package clienteservidor.servidor;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;

import clienteservidor.servico.*;

/*
Servidor multithreaded que recebe pedidos de leitura e escrita em arquivos e cria uma thread para cada.
*/

public class Servidor {

    public boolean prioritario; // diz se o servidor é prioritário ou não

    /* Construtor */
    public Servidor(boolean servidorPrioritario) {
        prioritario = servidorPrioritario;
    }

    /* Função para checar se o servidor é prioritário ou não */
    public boolean temPrioridade() {
        return prioritario;
    }


    /* Main */
    public static void main(String[] args) {

        try {
            Servidor servidor = new Servidor(false); /* mudar boolean para demonstrar ambos os servidores */
            Registry registry = LocateRegistry.createRegistry(1099); // criação do Registro RMI
            InterfaceRequisicao obj = new RemoteRequisicao(servidor); // criação do objeto remoto
            registry.rebind("Request", obj);
            System.out.println("Servidor está rodando e pronto para uso.");
        }

        catch (Exception e) {
            System.err.println("Exceção no Servidor: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }
        
    }
}