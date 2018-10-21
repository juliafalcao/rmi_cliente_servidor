# Aplicação Cliente-Servidor com RMI
Trabalho 2 da disciplina Sistemas Distribuídos (2018.2). Sistema cliente-servidor em Java onde 3 clientes efetuam operações de leitura e escrita em 3 arquivos diferentes. Comunicação entre servidor e cliente feita com RMI.

## Arquivos
* `Servidor.java`: servidor que recebe requisições de leitura ou escrita em arquivos e cria threads para cada operação.
* `Cliente.java`: cliente que faz requisições de leitura ou escrita em arquivos. O método `main` gera 10 requisições aleatórias para cada um dos 3 clientes, para demonstração.
* `InterfaceRequisicao.java`: interface do objeto remoto.
* `RemoteRequisicao.java`: implementação de InterfaceRequisicao; é o objeto remoto criado pelo servidor, para o qual o RMI permite que os clientes façam chamadas de métodos.
* `ThreadOperacao.java`: thread criada para efetuar uma operação de leitura ou escrita em um arquivo.
* `Debug.java`: classe especial que funciona como cliente e serve para chamar um método no objeto remoto que imprime o status de todas as threads criadas, para verificar se elas foram finalizadas ou estão interrompidas.


## Scripts
* `compile.bat`: Compila todos os arquivos necessários e gera os binários na pasta `bin`.
* `servidor.bat`: Inicia o servidor.
* `cliente.bat`: (Para rodar em uma linha de comando diferente do servidor.) Cria 3 clientes e gera 10 requisições de operações aleatórias para cada.
* `status.bat`: Chama função que pede ao servidor para imprimir o status de todas as threads criadas.
* `clearfiles.bat`: Limpa o conteúdo dos arquivos A.txt, B.txt e C.txt.
