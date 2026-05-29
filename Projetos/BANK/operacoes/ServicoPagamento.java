package BANK.operacoes;

import java.util.Scanner;

public class ServicoPagamento {

    public static double ejecutarSistema(double saldoConta) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n--- ÁREA DE PAGAMENTOS E RECEBIMENTOS ---");
        System.out.println("Escolha uma das opções:");
        Thread.sleep(2500);
        System.out.println("Pagar - 1 | Receber - 2");
        System.out.print("Sua opção: ");
        
        String opcaoUser = scanner.nextLine();
        
        if (opcaoUser.equalsIgnoreCase("1")) {
            System.out.println("\nEscolha o meio de pagamento:");
            System.out.println("[Pix - 1 | Cartão - 2 | Boleto - 3]");
            System.out.print("Sua opção: ");
            String opcaoUserPAG = scanner.nextLine();
            
            if (!opcaoUserPAG.equals("1") && !opcaoUserPAG.equals("2") && !opcaoUserPAG.equals("3")) {
                System.out.println("Opção de pagamento inválida!");
                return saldoConta;
            }

            if (opcaoUserPAG.equalsIgnoreCase("1")) {
                System.out.println("Meio de pagamento: Pix. Digite o valor:");
            } else if (opcaoUserPAG.equalsIgnoreCase("2")) {
                System.out.println("Meio de pagamento: Cartão. Digite o valor:");
            } else {
                System.out.println("Meio de pagamento: Boleto. Digite o valor:");
            }
            
            double valorPAG = Double.parseDouble(scanner.nextLine());                   
            
            if (Sistema_PAG_DEP_Fucoes.somarPAG(saldoConta, valorPAG)) {
                saldoConta = saldoConta - valorPAG;
                System.out.println("Pagamento realizado com sucesso!");
            } else {
                System.out.println("Não foi possível realizar o pagamento. Verifique seu saldo ou o valor digitado.");
            }
            
            System.out.println("Saldo atual na conta: R$ " + String.format("%.2f", saldoConta));
            
        } 
        else if (opcaoUser.equalsIgnoreCase("2")) {
            System.out.println("Digite o valor para depósito:");
            double valorDEP = Double.parseDouble(scanner.nextLine());
            
            if (Sistema_PAG_DEP_Fucoes.somarDEP(valorDEP)) {
                saldoConta = saldoConta + valorDEP;
                System.out.println("Depósito realizado com sucesso!");
            } else {
                System.out.println("Falha ao realizar o depósito. Valor inválido.");
            }
            
            System.out.println("Saldo atual na conta: R$ " + String.format("%.2f", saldoConta));
        }
        else {
            System.out.println("Opção inválida! Retornando ao menu...");
        }
        
        return saldoConta; 
    }
}