import java.util.Scanner;

public class SistemaValor {
  public static void main(String[] args) throws Exception {
    double saldoConta = 1000.00;
    System.out.println("Voce deseja: Pagamento-1 | Receber-2");
    Thread.sleep(2000);
    System.out.println("Saldo em conta: " + saldoConta);
    Scanner scanner = new Scanner(System.in);
    String CredDebi = scanner.nextLine();
    if (CredDebi.equalsIgnoreCase("1")) {
      System.out.println("Ok selecione uma das opções abaixo");
      Thread.sleep(2000);
      System.out.println("PIX - 1 | CARTÃO - 2 | BOLETO - 3 |");
      int opçPagamento = Integer.parseInt(scanner.nextLine());
      switch (opçPagamento) {
        case 1:
          System.out.println("Digite a chave:");
          String chavePix = scanner.nextLine();
          System.out.println("Para: " + chavePix + " Digite o valor:");
          double valorPix = Double.parseDouble(scanner.nextLine());
          while (valorPix <= 0 || valorPix > saldoConta) {
            if (valorPix <= 0) {
              System.out.println(
                  "Erro: O valor do PIX deve ser maior que zero!");
            } else {
              System.out.println(
                  "Erro: Saldo insuficiente! Seu saldo atual é: " + saldoConta);
            }
            System.out.print("Digite um valor válido: ");
            valorPix = Double.parseDouble(scanner.nextLine());
          }
          System.out.println("processando PIX...");
          Thread.sleep(4500);
          System.out.println("Pix enviado com sucesso!");
          saldoConta = saldoConta - valorPix;
          Thread.sleep(2000);
          System.out.println("Saldo em conta: " + saldoConta);
          break;
        case 2:
          System.out.println("Digite o numero do cartão:");
          String numeCart = scanner.nextLine();
          System.out.println("Para: " + numeCart + " Digite o valor:");
          double valorCart = Double.parseDouble(scanner.nextLine());
          while (valorCart <= 0 || valorCart > saldoConta) {
            if (valorCart <= 0) {
              System.out.println(
                  "Erro: O valor do pagamento deve ser maior que zero!");
            } else {
              System.out.println(
                  "Erro: Saldo insuficiente! Seu saldo atual é: " + saldoConta);
            }
            System.out.print("Digite um valor válido: ");
            valorCart = Double.parseDouble(scanner.nextLine());
          }
          System.out.println("processando Pagamento...");
          Thread.sleep(4500);
          System.out.println("Pagamento enviado com sucesso!");
          saldoConta = saldoConta - valorCart;
          Thread.sleep(2000);
          System.out.println("Saldo em conta: " + saldoConta);
          break;
        case 3:
          System.out.println("Digite o numero do boleto:");
          String numeBolet = scanner.nextLine();
          System.out.println("Para: " + numeBolet + " Digite o valor:");
          double valorbolet = Double.parseDouble(scanner.nextLine());
          while (valorbolet <= 0 || valorbolet > saldoConta) {
            if (valorbolet <= 0) {
              System.out.println(
                  "Erro: O valor do boleto deve ser maior que zero!");
            } else {
              System.out.println(
                  "Erro: Saldo insuficiente! Seu saldo atual é: " + saldoConta);
            }
            System.out.print("Digite um valor válido: ");
            valorbolet = Double.parseDouble(scanner.nextLine());
          }
          System.out.println("processando Pagamento...");
          Thread.sleep(4500);
          System.out.println("Pagamento enviado com sucesso!");
          saldoConta = saldoConta - valorbolet;
          Thread.sleep(2000);
          System.out.println("Saldo em conta: " + saldoConta);
          break;

        default:
          System.out.println("Ok até mais!");
          break;
      }
    } else if (CredDebi.equalsIgnoreCase("2")) {
      System.out.println("Ok digite o meio de deposito para sua conta:");
      Thread.sleep(2000);
      System.out.println("PIX - 1 | CARTÃO - 2 | BOLETO - 3 |");
      int opçDeposito = Integer.parseInt(scanner.nextLine());
      switch (opçDeposito) {
        case 1:
          System.out.println("Ok digite o valor:");
          System.out.print("Digite o valor do pix: ");
          double valorPIXd = Double.parseDouble(scanner.nextLine());
          while (valorPIXd <= 0) {
            System.out.println("Erro: O valor do PIX deve ser maior que zero!");
            System.out.print("Por favor, digite um valor válido: ");
            valorPIXd = Double.parseDouble(scanner.nextLine());
          }
          saldoConta = saldoConta + valorPIXd;
          System.out.println(
              "PIX realizado com sucesso! Novo saldo: " + saldoConta);
          break;
        case 3:
          System.out.println("Ok digite o valor:");
          System.out.print("Digite o valor do boleto: ");
          double valorBOLETd = Double.parseDouble(scanner.nextLine());
          while (valorBOLETd <= 0) {
            System.out.println(
                "Erro: O valor do BOLETO deve ser maior que zero!");
            System.out.print("Por favor, digite um valor válido: ");
            valorBOLETd = Double.parseDouble(scanner.nextLine());
          }
          saldoConta = saldoConta + valorBOLETd;
          System.out.println(
              "Boleto realizado com sucesso! Novo saldo: " + saldoConta);
          break;
        case 2:
          System.out.println("Ok digite o valor:");
          System.out.print("Digite o valor do catão: ");
          double valorCARTd = Double.parseDouble(scanner.nextLine());
          while (valorCARTd <= 0) {
            System.out.println(
                "Erro: O valor do CARTÂO deve ser maior que zero!");
            System.out.print("Por favor, digite um valor válido: ");
            valorCARTd = Double.parseDouble(scanner.nextLine());
          }
          saldoConta = saldoConta + valorCARTd;
          System.out.println(
              "Pagamento via boleto realizado com sucesso! Novo saldo: "
              + saldoConta);
          break;
        default:
          System.out.println("Ok até mais!");
          break;
      }
    }
  }
}