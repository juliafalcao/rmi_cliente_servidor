package clienteservidor.servidor;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import clienteservidor.operacao.InterfaceOperacao;
import clienteservidor.operacao.Operacao;

/*
Servidor multithreaded que recebe pedidos, coloca-os numa fila e realiza operações de leitura e escrita em 3 arquivos diferentes.
*/

class Servidor {

    public Servidor() {
        // construtor
    }


    public static void main(String[] args) {
        
        try {
            Servidor servidor = new Servidor();

            Operacao stub = (Operacao) UnicastRemoteObject.exportObject(servidor, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Operacao", stub);
        }

        catch (Exception e) {
            System.err.println("Exceção no servidor: " + e.toString());
            e.printStackTrace();
        }
        
    }
}