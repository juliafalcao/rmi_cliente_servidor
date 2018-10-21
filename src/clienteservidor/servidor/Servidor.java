package clienteservidor.servidor;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;

import clienteservidor.servico.InterfaceRequisicao;
import clienteservidor.servico.RemoteRequisicao;

/*
Servidor multithreaded que recebe pedidos, coloca-os numa fila e realiza operações de leitura e escrita em 3 arquivos diferentes.
*/

public class Servidor {

    /* Construtor */
    public Servidor() {
    }


    /* Main */
    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.createRegistry(1099);

            InterfaceRequisicao obj = new RemoteRequisicao();
            registry.rebind("Requisicao", obj);
            System.out.println("Servidor pronto!");
        }

        catch (Exception e) {
            System.err.println("Exceção no servidor: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }
        
    }
}