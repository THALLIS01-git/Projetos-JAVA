package BANK.operacoes;

public class User {
    private String Nome;
    private long Cpf;
    private String Endereco;
    private int NumeroEndereco;
    private int SenhaConta;
    private String Email;

    public void setNome(String Nome){
        if(Nome.matches("[a-zA-ZÀ-ú\\s]+")){
        this.Nome=Nome.toUpperCase();
        }
        else{
        System.out.println("ERRO: Nome inválido (não use números ou símbolos).");
        this.Nome="ERRO_Nome invalido";
        }
    }
    public String getNome(){
        return this.Nome;
    }

    public void setCpf(long Cpf) {
        this.Cpf = Cpf;
    }
    public long getCpf() {
        System.out.println("Conseguimos validar o CPF!:");
        return this.Cpf;
    }

    public void setEndereco(String Endereco){
        if(Endereco.matches("[a-zA-ZÀ-ú\\s]+")){
        this.Endereco=Endereco.toUpperCase();
        }
        else{
        System.out.println("ERRO: Endereço inválido (não use números ou símbolos).");
        this.Endereco="ERRO_Endereço invalido";
        }
    }
    public String getEndereco(){
        return this.Endereco;
    }

    public void setNumeroEndereco(int NumeroEndereco){
        this.NumeroEndereco=NumeroEndereco;
    }
    public int getNumeroEndereco(){
        return this.NumeroEndereco;
    }
    
    public void setSenhaConta(int SenhaConta){
        this.SenhaConta=SenhaConta;
    }
    public int getSenhaConta(){
        return this.SenhaConta;
    }

    public void setEmail(String Email){
        if(Email.contains("@")){
        this.Email=Email.toLowerCase();
        }
        else{
        System.out.println("ERRO: Seu endereço não contem @");
        this.Email="ERRO_Endereço invalido";
        }
    }
    public String getEmail(){
        return this.Email;
    }
}
