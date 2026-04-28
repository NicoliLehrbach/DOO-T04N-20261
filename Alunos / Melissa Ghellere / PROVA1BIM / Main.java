import java.time.LocalDate;
import java.util.Scanner;

public class Main { // interface do sistema
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();
        Hospede[] hospedes = new Hospede[10];
        Quarto[] quartos = new Quarto[10];
        int contH = 0, contQ = 0;

        int op = 0;
        while (op != 7) {
            System.out.println("\n1.Cadastrar Hóspede 2.Cadastrar Quarto 3.Reservar 4.Check-out 5.Listar Ativas 6.Demo 7.Sair");
            op = sc.nextInt(); sc.nextLine();

            switch (op) {
                case 1:
                    System.out.print("Nome: "); String n = sc.nextLine();
                    hospedes[contH++] = new Hospede(n, "000", "000");
                    break;
                case 2:
                    System.out.print("1.Simples 2.Luxo: "); int t = sc.nextInt();
                    System.out.print("Número: "); int num = sc.nextInt();
                    System.out.print("Valor Diária: "); double v = sc.nextDouble();
                    if (t == 1) quartos[contQ++] = new QuartoSimples(num, v, true);
                    else quartos[contQ++] = new QuartoLuxo(num, v, true);
                    break;
                case 3:
                    System.out.print("ID Hóspede (0 a "+(contH-1)+"): "); int ih = sc.nextInt();
                    System.out.print("ID Quarto (0 a "+(contQ-1)+"): "); int iq = sc.nextInt();
                    hotel.adicionarReserva(new Reserva(hospedes[ih], quartos[iq], LocalDate.now(), LocalDate.now().plusDays(2)));
                    break;
                case 4:
                    System.out.print("ID Reserva (0 a "+(hotel.getTotalReservas()-1)+"): ");
                    hotel.getReservas()[sc.nextInt()].realizarCheckout();
                    break;
                case 5: hotel.listarReservasAtivas(); break;
                case 6: demonstracao(hotel); break;
            }
        }
    }

    public static void demonstracao(Hotel h) {
        Hospede h1 = new Hospede("Melissa GABi", "123", "999");
        Hospede h2 = new Hospede("João Silva", "456", "888");
        QuartoSimples qs = new QuartoSimples(101, 150.0, true);
        QuartoLuxo ql = new QuartoLuxo(201, 400.0, true);
        
        Reserva r1 = new Reserva(h1, qs, LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 5));
        Reserva r2 = new Reserva(h2, ql, LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 15));
        
        r1.realizarCheckout(); // Uma realizada
        h.adicionarReserva(r1);
        h.adicionarReserva(r2); // Uma ativa
        
        System.out.println("\n--- DEMONSTRAÇÃO: LISTANDO ATIVAS ---");
        h.listarReservasAtivas();
    }
}
