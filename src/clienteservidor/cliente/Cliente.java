package clienteservidor.cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Random;
import java.lang.Thread;
import java.util.ArrayList;

import clienteservidor.cliente.*;
import clienteservidor.servico.*;
import clienteservidor.servidor.*;
import static clienteservidor.servidor.Servidor.*;

/*
Classe principal que vai gerar os 3 clientes e suas requisições de leitura e escrita nos arquivos.
*/
public class Cliente {

	public int id;
	protected InterfaceRequisicao obj; // a interface do objeto remoto

	/* Construtor */
	Cliente(int id) {
		this.id = id;

		try {
			obj = (InterfaceRequisicao) Naming.lookup(objName); // busca objeto a partir do nome no Registry
		}

		catch (Exception e) {
			System.err.println("Exceção no Cliente - obtenção do objeto remoto: " + e.toString());
			e.printStackTrace();
			System.exit(0);
		}
	}

	/* Nova main */
	public static void main(String[] args) {
		try {
			/* criar threads dos 3 clientes e inicializá-las */
			ArrayList<ThreadCliente> threadsClientes = new ArrayList<ThreadCliente>();

			for (int c = 0; c < 3; c++) {
				ThreadCliente threadCliente = new ThreadCliente(new Cliente(c));
				threadsClientes.add(threadCliente);
			}

			for (int c = 0; c < 3; c++) {// separado do acima para iniciar as threads com menor intervalo de tempo
				threadsClientes.get(c).start();
			}
			
		}

		catch (Exception e) {
			System.err.println("Exceção no Cliente: " + e.toString());
			e.printStackTrace();
			System.exit(0);
		}
	}
}