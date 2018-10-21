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

    public boolean prioritario; // diz se o servidor é prioritário ou não

    /* Construtor */
    public Servidor(boolean servidorPrioritario) {
        prioritario = servidorPrioritario;
    }


    /* Main */
    public static void main(String[] args) {

        try {
            Servidor servidor = new Servidor(False); /* mudar boolean para demonstrar ambos os servidores */
            Registry registry = LocateRegistry.createRegistry(1099); // criação do Registro RMI
            InterfaceRequisicao obj = new RemoteRequisicao(servidor); // criação do objeto remoto
            registry.rebind("Requisicao", obj);
            System.out.println("Servidor está rodando e pronto para uso.");
        }

        catch (Exception e) {
            System.err.println("Exceção no Servidor: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }
        
    }
}