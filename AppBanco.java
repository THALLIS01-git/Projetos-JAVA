import java.util.Random;
import java.util.Scanner;

public class AppBanco {
    public static void main(String[] args) throws Exception {
        Random random=new Random();
        try(Scanner scanner=new Scanner(System.in)){
            System.out.println("Voce possui uma conta em THSBANK? s/n");
            String perguntaDeentrada=scanner.nextLine();
            if(perguntaDeentrada.equalsIgnoreCase("n")){
                Thread.sleep(2000);
                System.out.println("Voce deseja criar uma conta? s/n");
                Thread.sleep(2000);
                String criarConta=scanner.nextLine();
                if(criarConta.equalsIgnoreCase("s")){
                    Thread.sleep(2000);
                    System.out.println("Ok, para começarmos digite seu nome");
                    String nome=scanner.nextLine();
                    System.out.println("OK "+nome+", qual sua idade?");
                    int idade=Integer.parseInt(scanner.nextLine());
                    if(idade<18){
                        Thread.sleep(2000);
                        System.out.println("Voce abrira uma conta para kides");
                        Thread.sleep(2000);
                        System.out.println("Qual seu CPF?");
                        int cpf=Integer.parseInt(scanner.nextLine());
                        Thread.sleep(2000);
                        System.out.println("Ok conseguimos validar o CPF: "+cpf);
                        System.out.println("Precisamos que voce coloque o e-mail de seu responsavel para continuarmos");
                        String emailResponsavel=scanner.nextLine();
                        System.out.println("Ok acabamos de enviar um codigo para " +emailResponsavel);
                        int codigoResponsavel = random.nextInt(9000)+1345;
                        Thread.sleep(2000);
                        System.out.println("Codigo recebido: "+codigoResponsavel);
                        Thread.sleep(2000);
                        System.out.println("Coloque o codigo que seu responsavel recebeu");
                        int codigoResponsavelchegou=Integer.parseInt(scanner.nextLine());
                        if (codigoResponsavel==codigoResponsavelchegou){
                            Thread.sleep(2000);
                            System.out.println("Ok agora vamos criar uma senha para sua conta, a senha deve ser em numeros");
                            int senhaContakides=Integer.parseInt(scanner.nextLine());
                            Thread.sleep(2000);
                            System.out.println("Ok "+nome+" sua senha: "+senhaContakides+" esta salva. Para finalizarmos qual seu e-mail?");
                            String emailKide=scanner.nextLine();
                            Thread.sleep(2000);
                            System.out.println("O e-mail:"+emailKide+" foi salvo com sucesso!");
                            Thread.sleep(2000);
                            System.out.println("Agora voce pode sair e entrar novamente e colocar seus dados para acessar sua conta!");                
                        }
                        else {System.out.println("Codigo não corresponde!");}
                    }
                    else if(idade>=18){
                        System.out.println("Qual seu CPF?");
                        int cpfMaior=Integer.parseInt(scanner.nextLine());
                        Thread.sleep(2000);
                        System.out.println("Ok conseguimos validar o CPF: "+cpfMaior);
                        Thread.sleep(2000);
                        System.out.println("Qual seu e-mail?");
                        String emailMaior=scanner.nextLine();
                        Thread.sleep(1000);
                        System.out.println("Acabamos de enviar um codigo em seu e-mail: "+emailMaior);
                        int codigoMaior = random.nextInt(9000)+1345;
                        Thread.sleep(1000);
                        System.out.println("Codigo recebido: "+codigoMaior);
                        Thread.sleep(1000);
                        System.out.println("Coloque o codigo que voce recebeu");
                        int codigoMaiorchegou=Integer.parseInt(scanner.nextLine());
                        if (codigoMaior==codigoMaiorchegou) {
                            System.out.println("Ok, voce precissa criar uma senha em numeros para sua conta digite ela");
                            int senhaMaior=Integer.parseInt(scanner.nextLine());
                            Thread.sleep(1000);
                            System.out.println("Ok "+nome+" sua senha: "+senhaMaior+" esta salva");
                            Thread.sleep(1000);
                            System.out.println("Agora voce pode sair e entrar novamente e colocar seus dados e acessar sua conta!");
                        }
                        else{System.out.println("Codigo não corresponde!");}
                    }
                }
                else if(criarConta.equalsIgnoreCase("n")){System.out.println("Ok até mais!");}
            }
            else if(perguntaDeentrada.equalsIgnoreCase("s")){
                System.out.println("Ok digite seu CPF");
                Thread.sleep(2000);
                long cpfLogado=Integer.parseInt(scanner.nextLine());
                Thread.sleep(2000);
                System.out.println("Ok agora sua senha");
                int senhaLogado=Integer.parseInt(scanner.nextLine());
                Thread.sleep(2000);
                System.out.println("Entando...");
                Thread.sleep(2000);
                System.out.println("Seja bem vindo(a)");
                Thread.sleep(2000);
                System.out.println("Aqui esta as opções que voce pode acessar: Saldo-0, Pix-1, Extrato-2 , Investimento-3 , Cartões-4, Minha Conta-5");
                String[]opçõesBanco={"Saldo-0","Pix-1","Extrato-2","Investimento-3","Cartões-4","Minha Conta-5"};
                int escolhaOpçõesBanco=Integer.parseInt(scanner.nextLine());
                switch (opçõesBanco[escolhaOpçõesBanco]) {
                    case"Saldo-0":
                        System.out.println("Saldo em conta: R$ 5989,00");
                        break;
                    case"Pix-1":
                        System.out.println("Voce deseja fazer um pix? s/n");
                        String pix=scanner.nextLine();
                        if(pix.equalsIgnoreCase("s")){
                            Thread.sleep(2000);
                            System.out.println("Digite a chave");
                            String chavePix=scanner.nextLine();
                            Thread.sleep(2000);
                            System.out.println("Digite o valor em numeros");
                            int valorPix=Integer.parseInt(scanner.nextLine());
                            Thread.sleep(2000);
                            System.out.println("Pix enviado para: "+chavePix+" Fulano(a) no valor de $"+valorPix);
                            break;
                        }
                        else if(pix.equalsIgnoreCase("n")){System.out.println("Ok até mais!");}
                        break;
                    case"Extrato-2":
                        System.out.println("------------------------------------------");
                        System.out.println("|           EXTRATO BANCÁRIO             |");
                        System.out.println("------------------------------------------");
                        System.out.println(" Data: 30/04/2026   Hora: 10:30");
                        System.out.println("------------------------------------------");
                        System.out.println(" DESCRIÇÃO                VALOR (R$)      ");
                        System.out.println(" Saldo Anterior           1.500,00        ");
                        System.out.println(" Depósito (+)               500,00        ");
                        System.out.println(" Saque (-)                  200,00        ");
                        System.out.println("------------------------------------------");
                        System.out.println(" SALDO ATUAL:             R$ 5.989,00     ");
                        System.out.println("------------------------------------------");
                        System.out.println("       Obrigado por usar nosso banco!     ");
                        System.out.println("------------------------------------------");
                        break;
                    case"Investimento-3":
                        System.out.println("====================================================");
                        System.out.println("          CARTEIRA DE INVESTIMENTOS  ");
                        System.out.println("====================================================");
                        System.out.println(" ATIVO          | APLICADO    | RENDIMENTO | TOTAL  ");
                        System.out.println("----------------------------------------------------");
                        System.out.println(" Tesouro Selic  | R$ 1000,00  | R$ 12,50   | R$ 1012,50");
                        System.out.println(" CDB 110% CDI   | R$ 500,00   | R$ 8,20    | R$ 508,20 ");
                        System.out.println(" Ações (VALE3)  | R$ 300,00   | R$ -5,00   | R$ 295,00 ");
                        System.out.println("----------------------------------------------------");
                        System.out.println(" PATRIMÔNIO TOTAL:                  R$ 1.815,70");
                        System.out.println(" RENTABILIDADE MENSAL:              + 0,85%    ");
                        System.out.println("====================================================");
                        break;
                    case"Cartões-4":
                        System.out.println("====================================================");
                        System.out.println("                EXTRATO DE CARTÃO                   ");
                        System.out.println("====================================================");
                        System.out.println(" Cartão Final: **** **** **** ");
                        System.out.println(" Status: LIBERADO / BLACK");
                        System.out.println("----------------------------------------------------");
                        System.out.println(" DATA   | ESTABELECIMENTO            | VALOR (R$)   ");
                        System.out.println("----------------------------------------------------");
                        System.out.println(" 25/04  | Amazon Prime               | R$ 14,90    ");
                        System.out.println(" 26/04  | Restaurante Sabor          | R$ 85,00    ");
                        System.out.println(" 28/04  | Posto de Gasolina          | R$ 150,00   ");
                        System.out.println(" 30/04  | Cinema Multiplex           | R$ 42,00    ");
                        System.out.println("----------------------------------------------------");
                        System.out.println(" TOTAL DA FATURA:                    R$ 291,90    ");
                        System.out.println(" VENCIMENTO:                         10/05/2026   ");
                        System.out.println("====================================================");
                        break;
                    case"Minha Conta-5":
                        System.out.println("####################################################");
                        System.out.println("#                PERFIL DO USUÁRIO                 #");
                        System.out.println("####################################################");
                        System.out.println(   "CPF: "+cpfLogado+"    | Data de criação de conta:" );
                        System.out.println(   "Senha" +senhaLogado+" | 15/04/2026");
                        break;
                    default:
                        System.out.println("Nemnhum intem selecionado");
                        break;
                }
            }
        }
    }   
}
