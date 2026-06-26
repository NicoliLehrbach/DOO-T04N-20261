# Aula 13 - Kotlin

## Parallel Programming (Programação Paralela)

### Timestamp do vídeo que menciona o conceito:

03:20

### O que é? Pra que serve? Como é normalmente utilizado?

Programação paralela é a capacidade de executar múltiplas tarefas simultaneamente, aproveitando melhor os recursos do processador. No Kotlin, isso pode ser feito por meio de threads, coroutines e outras ferramentas que permitem executar operações de forma concorrente.

Essa abordagem é muito utilizada em aplicações que precisam realizar várias atividades ao mesmo tempo, como processamento de dados, requisições de rede, tarefas em segundo plano e aplicações com interfaces gráficas que não podem ficar bloqueadas durante operações demoradas.

A programação paralela ajuda a melhorar o desempenho, a responsividade e a eficiência das aplicações.

### Exemplo de código em Kotlin

```kotlin
fun main() {
    val thread1 = Thread {
        println("Executando tarefa 1")
    }

    val thread2 = Thread {
        println("Executando tarefa 2")
    }

    thread1.start()
    thread2.start()
}
```

Nesse exemplo, duas tarefas são executadas em threads diferentes, permitindo que ocorram de forma paralela ou concorrente, dependendo do ambiente de execução.

---

## Elvis Operator (?:)

### Timestamp do vídeo que menciona o conceito:

00:55

### O que é? Pra que serve? Como é normalmente utilizado?

O Elvis Operator (`?:`) é um operador utilizado para tratar valores nulos de forma simples e segura. Ele verifica se uma expressão possui valor; caso seja nula, retorna um valor alternativo definido pelo programador.

Esse recurso é amplamente utilizado em Kotlin porque a linguagem possui suporte nativo à segurança contra valores nulos (*null safety*), reduzindo a ocorrência de erros como `NullPointerException`.

O operador Elvis torna o código mais limpo, legível e fácil de manter quando é necessário definir valores padrão.

### Exemplo de código em Kotlin

```kotlin
fun main() {
    val nome: String? = null

    val nomeExibicao = nome ?: "Usuário"

    println(nomeExibicao)
}
```

Nesse exemplo, a variável `nome` possui valor `null`. O operador Elvis retorna `"Usuário"` como valor padrão, evitando problemas relacionados ao uso de valores nulos.
