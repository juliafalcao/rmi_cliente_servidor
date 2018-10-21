package clienteservidor.cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;

import clienteservidor.servico.*;
import clienteservidor.servidor.*;


/*
Classe de debug que funciona como cliente.
Serve para fazer chamada ao método da interface remota que mostra o status de todas as threads criadas.
*/
public class Debug {

    private InterfaceRequisicao obj; // a interface do objeto remoto
    public static final String objName = "rmi://localhost:1099/Request";


    /* Construtor */
    Debug() {
        try {
            obj = (InterfaceRequisicao) Naming.lookup(objName);
        }

        catch (Exception e) {
            System.err.println("Exceção no Debug - obtenção do obj: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }
    }

    /* Main */
    public static void main(String[] args) {

        try {
            Debug debug = new Debug();
            
            debug.obj.printStatus(); // printar status de todas as threads
        }

        catch (Exception e) {
            System.err.println("Exceção no Debug: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }
    }
}