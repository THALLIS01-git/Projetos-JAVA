package BANK.login;

import java.util.Scanner;

import BANK.operacoes.CarregamentoBarra;
import BANK.operacoes.InterfaceBancoUser1;
import BANK.operacoes.InterfaceBancoUser2;
import BANK.operacoes.ServicoPagamento;
import BANK.operacoes.User;

public class Main {
    public static void main(String[] args) throws Exception{
        double saldoGeralDoBanco=10000.0;
        System.out.println("Seja bem vindo ao BANK");
        Thread.sleep(2400);
        System.out.println("Voce possui uma conta? s/n");
        Scanner scanner=new Scanner(System.in);
        String enterConta=scanner.nextLine();
        if(enterConta.equals("n")){ 
            User usuario1=new User();
            System.out.println("Qual seu nome inteiro?");
            String nomeUsuario1=scanner.nextLine();
            usuario1.setNome(nomeUsuario1);
            System.out.println("Ok "+usuario1.getNome()+" Qual seu CPF? (Digite sem espaços)");
            long cpfUsuario1 = scanner.nextLong();
            usuario1.setCpf(cpfUsuario1);
            scanner.nextLine();
            System.out.println(usuario1.getCpf());
            System.out.println("Qual seu e-mail?");
            String emailUsuario1=scanner.nextLine();
            usuario1.setEmail(emailUsuario1);
            System.out.println("Qual seu endereço (Apenas nome de rua e bairo)");
            String enderecoUsuario1=scanner.nextLine();
            usuario1.setEndereco(enderecoUsuario1);
            System.out.println("Endereço: "+usuario1.getEndereco()+", qual o numero da residencia:");
            int numeroEnderecoUsuario1=Integer.parseInt(scanner.nextLine());
            usuario1.setNumeroEndereco(numeroEnderecoUsuario1);
            System.out.println("Endereço completo: "+ usuario1.getEndereco()+" "+usuario1.getNumeroEndereco());
            System.out.println("Digite a senha para esta conta:");
            int senhaUsuario1=Integer.parseInt(scanner.nextLine());
            usuario1.setSenhaConta(senhaUsuario1);
            System.out.println("Login concluido entando!...");
            Thread.sleep(3000);
            CarregamentoBarra.barraCarregamento();
            InterfaceBancoUser1.exibirMenuPrincipal(usuario1);
        }
        else{
            User usuario2=new User();
            System.out.println("Ok digite o CPF:");
            long cpfUsuario2=scanner.nextLong();
            usuario2.setCpf(cpfUsuario2);
            scanner.nextLine();
            System.out.println("Digite a senha:");
            int senhaUsuario2=Integer.parseInt(scanner.nextLine());
            usuario2.setSenhaConta(senhaUsuario2);
            System.out.println("Validando informações...");
             System.out.println("Login concluido entando!...");
            Thread.sleep(3000);
            CarregamentoBarra.barraCarregamento();
            InterfaceBancoUser2.exibirMenuPrincipal(usuario2);
        } 
    }
}