package clienteservidor.cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;

import clienteservidor.operacao.*;


public class Cliente {

    private InterfaceOperacao obj; // a interface do objeto remoto

    /* construtor */
    Cliente() {
        try {
            obj = (InterfaceOperacao) Naming.lookup("rmi://localhost:2020/Operacao");
        }

        catch (Exception e) {
            System.err.println("Exceção no Cliente: " + e.toString());
            e.printStackTrace();
        }
    }

    /* main */
    public static void main(String[] args) {
        
        // String host = (args.length < 1) ? null : args[0];

        try {
            Cliente c1 = new Cliente();

            /* teste */
            c1.obj.operacao(1, 0, "A.txt");

        }

        catch (Exception e) {
            System.err.println("Exceção no Cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}