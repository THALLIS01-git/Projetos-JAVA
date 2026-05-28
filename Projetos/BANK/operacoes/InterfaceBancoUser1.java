package BANK.operacoes;

import java.util.Random;
import java.util.Scanner;

public class InterfaceBancoUser1 {
    public static void exibirMenuPrincipal(User usuario1)throws Exception{
        double saldoGeralDoBanco=10000.0;
        double limiteChequeEspecial = 1000.00;
        System.out.println("=================================");
        System.out.println("    BEM-VINDO AO BANK!   ");
        System.out.println("================================="); 
        Thread.sleep(2000);     
        System.out.println("1 - PAGAMENTOS E RECEBIMENTOS"); 
        System.out.println("2 - INFORMAÇÕES DO USUARIO"); 
        System.out.println("3 - SALDO EM CONTA"); 
        Thread.sleep(1200);
        System.out.print("Escolha uma opção: ");
        Scanner scanner=new Scanner(System.in);
        String opçãoUser=scanner.nextLine();
        if(opçãoUser.equals("1")){
            saldoGeralDoBanco = BANK.operacoes.ServicoPagamento.executarSistema(saldoGeralDoBanco);
        }
        else if(opçãoUser.equals("2")){
        System.out.println("--- INFORMAÇÕES DO USUARIO ---");
        Thread.sleep(1200);
        System.out.println("Nome do titular: "+usuario1.getNome());
        Thread.sleep(1200);
        System.out.println("CPF: "+usuario1.getCpf());
        Thread.sleep(1200);
        System.out.println("Endereço completo: "+ usuario1.getEndereco()+" "+usuario1.getNumeroEndereco()); 
        Thread.sleep(1200);
        System.out.println("E-mail: "+usuario1.getEmail());
        Thread.sleep(1200);
        System.out.println("Senha: "+usuario1.getSenhaConta());
        }
        else{
            Random random = new Random();
            int contaAleatoria = 10000 + random.nextInt(90000);
            int digitoAleatorio = random.nextInt(10);
            String numeroConta = "000" + contaAleatoria + "-" + digitoAleatorio;

            System.out.println("====================================================");
            System.out.println("                BANK - TELA DE SALDO                ");
            System.out.println("====================================================");
            System.out.println();
            System.out.println(" Cliente: " + usuario1.getNome());
            System.out.println(" Conta Corrente: " + numeroConta);
            System.out.println();
            System.out.println("----------------------------------------------------");
            System.out.println(" SDO. DISPONÍVEL:          R$ " + String.format("%.2f", saldoGeralDoBanco));
            System.out.println(" LIMITE CHEQUE ESPECIAL:   R$ " + String.format("%.2f", limiteChequeEspecial));
            System.out.println("----------------------------------------------------");
            System.out.println(" SALDO TOTAL DISPONÍVEL:   R$ " + String.format("%.2f", saldoGeralDoBanco));
            System.out.println("----------------------------------------------------");
            System.out.println();
            System.out.println("====================================================");
            System.out.println("        SISTEMA DESENVOLVIDO COM SUCESSO BY  ");
            System.out.println("                    THALLIS.V");
            System.out.println("====================================================");
            System.out.println();
            System.out.print(" Digite a opção desejada: ");
            InterfaceBancoUser1.exibirMenuPrincipal(usuario1);
        }
        
    }
}
