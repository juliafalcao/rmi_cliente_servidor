package clienteservidor.cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Random;
import java.lang.Thread;
import java.util.ArrayList;

import clienteservidor.servico.*;
import clienteservidor.servidor.*;
import clienteservidor.cliente.ThreadClienteTeste;


/*
Classe de teste para ver o funcionamento do servidor prioritário.
*/
public class ClienteTeste {

	public int id;
	protected InterfaceRequisicao obj; // a interface do objeto remoto

	/* constantes */
	public static final String ARQUIVO_A = "A.txt";
	public static final String ARQUIVO_B = "B.txt";
	public static final String ARQUIVO_C = "C.txt";
	public static final int LEITURA = 0;
	public static final int ESCRITA = 1;
	public static final int CLIENTE_1 = 0;
	public static final int CLIENTE_2 = 1;
	public static final int CLIENTE_3 = 2;
	public static final String objName = "rmi://localhost:1099/Request";


	/* Construtor */
	ClienteTeste(int id) {
		this.id = id;

		try {
			obj = (InterfaceRequisicao) Naming.lookup(objName);
		}

		catch (Exception e) {
			System.err.println("Exceção no ClienteTeste - obtenção do obj: " + e.toString());
			e.printStackTrace();
			System.exit(0);
		}
	}

	/* Nova main */
	public static void main(String[] args) {
		try {
			ArrayList<ThreadClienteTeste> threadsClientes = new ArrayList<ThreadClienteTeste>();

			int n = 10;

			for (int c = 0; c < n; c++) {
				ThreadClienteTeste threadCliente = new ThreadClienteTeste(new Cliente(c));
				threadsClientes.add(threadCliente);
			}

			for (int c = 0; c < n; c++) { // separado do acima para iniciar as threads com menor intervalo de tempo
				threadsClientes.get(c).start();
			}
		}
		

		catch (Exception e) {
			System.err.println("Exceção no ClienteTeste: " + e.toString());
			e.printStackTrace();
			System.exit(0);
		}
	}
}