package BANK.operacoes;

public class CarregamentoBarra {

    public static void barraCarregamento() throws InterruptedException {
        int totalBlocos = 20; 
        for (int i = 0; i <= totalBlocos; i++) {
            StringBuilder barra = new StringBuilder("[");
            int percentual = (i * 100) / totalBlocos;
            for (int j = 0; j < i; j++) {
                barra.append("█");
            }
            for (int j = i; j < totalBlocos; j++) {
                barra.append("░");
            }
            barra.append("] ").append(percentual).append("%");
            System.out.print("\rConectando ao BANK: " + barra);
            Thread.sleep(150); 
        }
        System.out.println("\n Entrada estabelecida!");
    }
}
