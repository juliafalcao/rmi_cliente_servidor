compile:
	javac -d bin -cp src src/clienteservidor/servidor/Servidor.java src/clienteservidor/servico/InterfaceRequisicao.java src/clienteservidor/servico/RemoteRequisicao.java src/clienteservidor/cliente/Cliente.java src/clienteservidor/cliente/ThreadCliente.java src/clienteservidor/cliente/Debug.java

servidor:
	java -cp bin clienteservidor.servidor.Servidor

cliente:
	java -cp bin clienteservidor.cliente.Cliente

status:
	java -cp bin clienteservidor.cliente.Debug