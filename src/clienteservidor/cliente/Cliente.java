package clienteservidor.cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import clienteservidor.operacao.*;


public class Cliente {
    private Cliente() {
        // construtor
    }

    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];

        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Operacao stub = (Operacao) registry.lookup("Operacao");
            // String response = stub.funcaoOperacao;
        }

        catch (Exception e) {
            Syste.err.println("Exceção no Cliente: " + e.toString());
            e.printStackTrace();
        }
    }


}