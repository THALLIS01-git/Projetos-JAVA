package BANK.operacoes;

import java.util.Scanner;

public class ServicoPagamento {

    public static double executarSistema(double saldoConta) throws Exception {
        System.out.println("\n--- ÁREA DE PAGAMENTOS E RECEBIMENTOS ---");
        System.out.println("Escolha uma das opções");
        Thread.sleep(2500);
        System.out.println("Pagar - 1 | Receber - 2");
        Scanner scanner=new Scanner(System.in);
            String opcaoUser=scanner.nextLine();
            if (opcaoUser.equalsIgnoreCase("1")) {
                System.out.println("Escolha uma das opções");
                System.out.println("[Pix - 1 | Cartão - 2 | Boleto - 3]");
                String opcaoUserPAG = scanner.nextLine();
                if (opcaoUserPAG.equalsIgnoreCase("1")) {
                    System.out.println("Meio de pagamento PIX, digite o valor");
                    double valorPAG = Double.parseDouble(scanner.nextLine());                   
                    if (Sistema_PAG_DEP_Fucoes.somarPAG(saldoConta, valorPAG)) {
                        saldoConta = saldoConta - valorPAG;
                    }
                    System.out.println("Saldo atual na conta: R$ " + saldoConta);
                } 
                else if (opcaoUserPAG.equalsIgnoreCase("2")) {
                    System.out.println("Meio de pagamento CARTÃO, digite o valor");
                    double valorPAG = Double.parseDouble(scanner.nextLine());           
                    if (Sistema_PAG_DEP_Fucoes.somarPAG(saldoConta, valorPAG)) {
                        saldoConta = saldoConta - valorPAG;
                    }
                    System.out.println("Saldo atual na conta: R$ " + saldoConta);
                }
                else if (opcaoUserPAG.equalsIgnoreCase("3")) {
                    System.out.println("Meio de pagamento BOLETO, digite o valor");
                    double valorPAG = Double.parseDouble(scanner.nextLine());
                    if (Sistema_PAG_DEP_Fucoes.somarPAG(saldoConta, valorPAG)) {
                        saldoConta = saldoConta - valorPAG;
                    }
                    System.out.println("Saldo atual na conta: R$ " + saldoConta);
                }
            } 
            else if (opcaoUser.equalsIgnoreCase("2")) {
                System.out.println("Digite o valor para deposito:");
                double valorDEP = Double.parseDouble(scanner.nextLine());
                if (Sistema_PAG_DEP_Fucoes.somarDEP(valorDEP)) {
                    saldoConta = saldoConta + valorDEP;
                }
                System.out.println("Saldo atual na conta: R$ " + saldoConta);
            }
        return saldoConta; 
    }
}