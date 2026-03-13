package classesAtividade;
public class CalculadoraVendas {
	public double resposta;
	public double desconto;
    public ResultadoVenda calcularVendasResposta(int valor01, double valor02) {
    	
        double resposta = (valor01 * valor02);
        
        double desconto = 0;

        if(valor01 >= 10) {
        	desconto = (resposta * 0.10);
        	
        	resposta = resposta - (resposta * 0.10);            
        }
     return new ResultadoVenda(resposta, desconto);
    }

    public class ResultadoVenda {
        public double resposta;
        
        public double desconto;

        public ResultadoVenda(double resposta, double desconto) {
            this.resposta = resposta;
            
            this.desconto = desconto;
        }
    }
    }
