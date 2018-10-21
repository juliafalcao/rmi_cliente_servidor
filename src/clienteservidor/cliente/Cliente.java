package clienteservidor.cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Random;
import java.lang.Thread;

import clienteservidor.servico.*;
import clienteservidor.servidor.*;
import clienteservidor.cliente.ThreadCliente;


/*
Classe principal que vai gerar os 3 clientes e suas requisições de leitura e escrita nos arquivos.
*/
public class Cliente {

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
	Cliente(int id) {
		this.id = id;

		try {
			obj = (InterfaceRequisicao) Naming.lookup(objName);
		}

		catch (Exception e) {
			System.err.println("Exceção no Cliente - obtenção do obj: " + e.toString());
			e.printStackTrace();
			System.exit(0);
		}
	}

	/* Função auxiliar que recebe um índice de arquivo [0-2] e retorna seu nome */
	public static String nomeArquivo(int index) {
		switch (index) {
			case 0: return ARQUIVO_A;
			case 1: return ARQUIVO_B;
			case 2: return ARQUIVO_C;
			default: return null;
		}
	}

	/* Nova main */
	public static void main(String[] args) {
		try {
			// criar threads dos 3 clientes e inicializá-las

			ThreadCliente[] threadsClientes = {null, null, null};

			for (int c = 0; c < 3; c++) {
				ThreadCliente threadCliente = new ThreadCliente(new Cliente(c));
				threadsClientes[c] = threadCliente;
				threadCliente.start();
			}
		}

		catch (Exception e) {
			System.err.println("Exceção no Cliente: " + e.toString());
			e.printStackTrace();
			System.exit(0);
		}
	}
}