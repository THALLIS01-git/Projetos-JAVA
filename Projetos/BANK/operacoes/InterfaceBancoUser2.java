package BANK.operacoes;

import java.util.Random;
import java.util.Scanner;

public class InterfaceBancoUser2 {
    public static void exibirMenuPrincipal(User usuario2) throws Exception {
        double saldoGeralDoBanco = 10000.0;
        double limiteChequeEspecial = 1000.00;
        
        System.out.println("=================================");
        System.out.println("     BEM-VINDO AO BANK!   ");
        System.out.println("================================="); 
        Thread.sleep(2000);     
        System.out.println("1 - PAGAMENTOS E RECEBIMENTOS"); 
        System.out.println("2 - INFORMAÇÕES DO USUÁRIO"); 
        System.out.println("3 - SALDO EM CONTA"); 
        Thread.sleep(1200);
        System.out.print("Escolha uma opção: ");
        
        Scanner scanner = new Scanner(System.in);
        String opcaoUser = scanner.nextLine();
        
        if(opcaoUser.equals("1")){
            saldoGeralDoBanco = BANK.operacoes.ServicoPagamento.ejecutarSistema(saldoGeralDoBanco);
        }
        else if(opcaoUser.equals("2")){
            System.out.println("--- INFORMAÇÕES DO USUÁRIO ---"); 
            Thread.sleep(1200);
            System.out.println("Nome do titular: " + usuario2.getNome());
            Thread.sleep(1200);
            System.out.println("CPF: " + usuario2.getCpf());
            Thread.sleep(1200);
            System.out.println("Endereço completo: " + usuario2.getEndereco() + " " + usuario2.getNumeroEndereco()); 
            Thread.sleep(1200);
            System.out.println("E-mail: " + usuario2.getEmail());
            Thread.sleep(1200);
            System.out.println("Senha: " + usuario2.getSenhaConta());
        }
        else if(opcaoUser.equals("3")){
            Random random = new Random();
            int contaAleatoria = 10000 + random.nextInt(90000);
            int digitoAleatorio = random.nextInt(10);
            String numeroConta = "000" + contaAleatoria + "-" + digitoAleatorio;

            System.out.println("====================================================");
            System.out.println("                 BANK - TELA DE SALDO                ");
            System.out.println("====================================================");
            System.out.println();
            System.out.println(" Cliente: " + usuario2.getNome());
            System.out.println(" Conta Corrente: " + numeroConta);
            System.out.println();
            System.out.println("----------------------------------------------------");
            System.out.println(" SALDO DISPONÍVEL:         R$ " + String.format("%.2f", saldoGeralDoBanco)); 
            System.out.println(" LIMITE CHEQUE ESPECIAL:   R$ " + String.format("%.2f", limiteChequeEspecial));
            System.out.println("----------------------------------------------------");
            System.out.println(" SALDO TOTAL DISPONÍVEL:   R$ " + String.format("%.2f", (saldoGeralDoBanco + limiteChequeEspecial)));
            System.out.println("----------------------------------------------------");
            System.out.println();
            System.out.println("====================================================");
            System.out.println("        SISTEMA DESENVOLVIDO COM SUCESSO BY  ");
            System.out.println("                    THALLIS.V");
            System.out.println("====================================================");
            System.out.println();
        }
        else {
            System.out.println("Opção inválida! Tente novamente.");
            Thread.sleep(1500);
            InterfaceBancoUser2.exibirMenuPrincipal(usuario2); 
        }
        
    }
}
