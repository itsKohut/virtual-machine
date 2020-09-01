# virtual-machine-sisop

## TODOS na classe APP do programa

Trabalho da cadeira de Sistemas Operacionais - PUCRS 2020/2

### Instruções para rodar o programa

- Versão do java necessário: `11 no minimo`
- Clonar o projeto em uma IDE de preferência
- Rodar o seguinte comando: `mvn clean install`

### Programas

- `program0` - Sequência fibonacci dos 10 primeiros números
- `program1` - Sequência fibonacci de um valor **x** lido de uma posição da memória
- `program2` - fatoriade um valor **x** lido de uma posição da memória
- `program3` - Bubble sort ordena 10 posições.
### Testes dos programas

- No diretório  `src/test/java/com/trabalho/sisop/` encontrasse a classe `VirtualMachineTest`
- Executar cada teste separadamente, pois devido a problema de concorrência dos mesmos, acabam quebrando.
- A cada execução de teste, é possível verificar o log de toda a execução do programa e as posicões de memória que foram populadas pela execução do programa.

### Observações

- A VM conta com uma memória que é representada por um array de Strings.
- Carrega as instruções do programa no inicio da memória.
- Carrega os dados apartir da posição **[50]** de memória.
- Os programas `program1`, `program2`, `program3` não fazem nenhuma carga de valores na memória para serem executados, como o `program0`,
portando se for executado o programa sem ser pelos testes criados **(que popula a memória para executar cada caso de cada programa)**, será necessário popular os valores na mão.