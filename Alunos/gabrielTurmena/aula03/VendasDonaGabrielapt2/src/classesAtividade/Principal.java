package classesAtividade;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class Principal {
public static void main(String[] args) {
	CalculadoraVendas calc = new CalculadoraVendas();
	List<Double> resultados = new ArrayList<>();
	
	List<Integer> quantidades = new ArrayList<>();
	
	List<Double> descontos = new ArrayList<>();
	
	Scanner sc = new Scanner(System.in);
	int resposta = 0;
	while(resposta!= 4) {
		System.out.println(" Seja bem vindo a Venda da Dona Gabriela!");
		
		System.out.println("PARA:");
		
		System.out.println("CALCULAR PREÇO TOTAL - 1");
		
		System.out.println("CALCULAR TROCO DE VENDA - 2");
		
	    System.out.println("HISTÓRICO DE VENDAS - 3 ");
	    
	    System.out.println("Sair - 4 ");
	    resposta = sc.nextInt();
	    sc.nextLine();
	switch (resposta) {
		case 1:
			System.out.println("Digite o número de unidades vendidas");
			
			int unidades = sc.nextInt();
			
			sc.nextLine();
			
			quantidades.add(unidades);
			
			System.out.println("Digite o valor das unidades");
			
			double valor = sc.nextDouble();
			
			sc.nextLine();
			
			CalculadoraVendas.ResultadoVenda resultado = calc.calcularVendasResposta(unidades, valor);
			
			resultados.add(resultado.resposta);
			
			descontos.add(resultado.desconto);
			
			System.out.println("COMPRA CONCLUÍDA! O VALOR A SER PAGO É: ");
			System.out.println(resultado.resposta);
			break;
		case 2:
			System.out.println("Digite o valor pago");
			
			double ValorPago = sc.nextDouble();
			
			sc.nextLine();
			
			System.out.println("Digite o valor do Produto");
			
			double ValorProduto = sc.nextDouble();
			
			sc.nextLine();
			
			new Troco(ValorPago, ValorProduto);
			break;
		case 3:
			System.out.println("NÚMERO DE VENDAS EM VALOLR: ");
			System.out.println(resultados);
			
			System.out.println("NÚMERO DE VENDAS EM QUANTIDADE: ");
			System.out.println(quantidades);
			
			System.out.println("VALOR DOS DESCONTOS");
			System.out.println(descontos);
			break;
		case 4:
			System.out.println("Até mais!");
			break;
		default:
			System.out.println("Opção inválida!"); 
    		break;

			}
		}
	sc.close();
	}
}
