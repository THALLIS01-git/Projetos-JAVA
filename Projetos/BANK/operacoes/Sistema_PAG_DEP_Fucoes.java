package BANK.operacoes;

public class Sistema_PAG_DEP_Fucoes {

    public static boolean somarPAG(double saldoConta, double valorPAG) throws Exception {
        if(saldoConta >= valorPAG) {
            System.out.println("Processando pagamento...");
            Thread.sleep(3500);
            System.out.println("Pagamento aprovado!");
            return true; 
        }else{
            System.out.println("ERRO_Saldo insuficiente");
            return false;
        }
    }    
    public static boolean somarDEP(double valorDEP) throws Exception{
        if(valorDEP>0){
            System.out.println("Processando deposito...");
            Thread.sleep(3500);
            System.out.println("Deposito aprovado!");
            return true;
        }else{
            System.out.println("ERRO_Valor de deposito insuficiente");
            return false;
        }
    }
    
}
