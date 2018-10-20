package clienteservidor.operacao;

import java.rmi.RemoteException;

public class Operacao implements InterfaceOpeacao {

    public void funcaoOperacao() throws RemoteException {
        System.out.println("rodando função operação");
    }
}