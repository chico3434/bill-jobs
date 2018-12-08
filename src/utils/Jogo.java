/*
 * Há algumas linhas de código comentadas abaixo para que eu me lembre que eu sou um idiota
 * E para que eu pare de tentar de fazer as coisas da maneira mais difícil e desnecessária
 * ~Francisco Rubens 27/11/2018 23:39
 * */
package utils;

import com.google.gson.Gson;
import exceptions.DinheiroInsuficiente;
import exceptions.ProdutoExistente;
import exceptions.VerbaInsuficiente;
import objeto.*;

import java.io.*;
import java.lang.reflect.Type;

public class Jogo {
	
	// precisa estar dentro de empresario para ser salvo pelo gson
	// Pode ser um dado interessante para balancear melhor o jogo no futuro
    private static int tempo = 0;

    private static String relatorioMensal;
    
    private static Empresario empresario;

    public static void carregarEmpresario(String nome) throws FileNotFoundException{
        Gson gson = new Gson();

        BufferedReader br;
        if (System.getProperty("os.name").equals("Linux") || System.getProperty("os.name").equals("Darwin")) {
             br = new BufferedReader(new FileReader("saves/" + nome + ".json"));
        } else {
             br = new BufferedReader(new FileReader("saves\\" + nome + ".json"));
        }

        empresario = gson.fromJson(br, (Type) Empresario.class);

    }

    public static void salvar(){
        Gson gson = new Gson();
        String json = gson.toJson(empresario);

        try {
            // Criar pasta
            if (System.getProperty("os.name").equals("Linux") || System.getProperty("os.name").equals("Darwin")) {
                String path = System.getProperty("user.dir");
                System.out.println(path);
                File file = new File(path + "/saves");
                file.mkdir();


                FileWriter writer = new FileWriter("saves/" + empresario.getNome() + ".json");
                writer.write(json);
                writer.close();
            } else {
                File file = new File("saves");
                file.mkdir();


                FileWriter writer = new FileWriter("saves\\" + empresario.getNome() + ".json");
                writer.write(json);
                writer.close();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static void criarEmpresario(String nome){
        empresario = new Empresario(nome);
    }

    public static void criarEmpresa(String nome, double verba) throws DinheiroInsuficiente {
        empresario.setEmpresa(new Empresa(nome));
        empresario.tranferirDinheiro(verba);
    }

    public static Empresario getEmpresario() {
        return empresario;
    }

    public static void setEmpresario(Empresario empresario) {
        Jogo.empresario = empresario;
    }

    public static String getDinheiro(){
        return ("R$ " + String.format("%.2f", empresario.getDinheiro()));
    }

    public static String getVerba(){
        return ("R$ " + String.format("%.2f", empresario.getEmpresa().getVerba()));
    }

    public static void comprarServidor(double preco, int capacidade) throws VerbaInsuficiente {
        empresario.getEmpresa().comprarServidor(preco, capacidade);
    }

    public static void comprarFabrica(double preco, int potencial, int nivel) throws VerbaInsuficiente {
        empresario.getEmpresa().comprarFabrica(preco, potencial, nivel);
    }

    public static void comprarGalpao(double preco, int capacidade) throws VerbaInsuficiente {
        empresario.getEmpresa().comprarGalpao(preco, capacidade);
    }

    public static void criarSoftware(String nome, String versao, String descricao, double custo, double preco) throws VerbaInsuficiente, ProdutoExistente {
        empresario.getEmpresa().criarSoftware(nome, versao, descricao, custo, preco);
    }

    public static void criarHardware(String nome, String versao, String descricao, double custo, double preco) throws ProdutoExistente {
        empresario.getEmpresa().criarHardware(nome, versao, descricao, custo, preco);
    }

    public static void atualizarProduto(Produto p, String descricao, double custo, double preco) throws VerbaInsuficiente {
        empresario.getEmpresa().atualizarProduto(p, descricao, custo, preco);
    }

    public static void atualizarSoftware(Produto p, String versao, String descricao, double custo, double preco) throws VerbaInsuficiente {
        empresario.getEmpresa().atualizarSoftware(p, versao, descricao, custo, preco);
    }
    
    // Hospedar Software em um slot de servidor
    public static void hospedarSoftware(Servidor servidor, String softwareId) throws Exception {
        if (empresario.getEmpresa().getSoftwares().contains(new Software(softwareId, ""))) {
            Software s = empresario.getEmpresa().getSoftwares().get(empresario.getEmpresa().getSoftwares().indexOf(new Software(softwareId, "")));
                /*
                 * O jeito abaixo de se fazer é mais complicado do que deveria, pode ser mais simples e por isso está comentado
                 * Como esse método recebe servidor como referencia alterá-lo irá alterar o que mandou a referencia
                 * O que faz ser bem mais simples do que a que está comentada
                 *
                 * empresario.getEmpresa().getServidores().get(empresario.getEmpresa().getServidores().indexOf(servidor)).hospedarSoftware(s);
                 *
                 */

                servidor.hospedarSoftware(s);
        } else {
            throw new ProdutoExistente("Software não existe!");
        }
    }
    
    // Hospedar Software em todos os slots do servidor
    public static void hospedarSoftwareAll(Servidor servidor, String softwareId) throws Exception {
        if (empresario.getEmpresa().getSoftwares().contains(new Software(softwareId, ""))) {
        	Software s = empresario.getEmpresa().getSoftwares().get(empresario.getEmpresa().getSoftwares().indexOf(new Software(softwareId, "")));

                /*
                 * O jeito abaixo de se fazer é mais complicado do que deveria, pode ser mais simples e por isso está comentado
                 * Como esse método recebe servidor como referencia alterá-lo irá alterar o que mandou a referencia
                 * O que faz ser bem mais simples do que a que está comentada
                 *
                 * empresario.getEmpresa().getServidores().get(empresario.getEmpresa().getServidores().indexOf(servidor)).hospedarSoftwareEmTodo(s);
                 *
                 */

                servidor.hospedarSoftwareEmTodo(s);
        } else {
            throw new ProdutoExistente("Software não existe!");
        }
    }
    
    // Trocar um Software por outro em um slot de Servidor
    public static void trocarSoftware(Servidor servidor, String softwareId, Software software) throws Exception {
        if (empresario.getEmpresa().getSoftwares().contains(new Software(softwareId, ""))) {
            Software s = empresario.getEmpresa().getSoftwares().get(empresario.getEmpresa().getSoftwares().indexOf(new Software(softwareId, "")));
                /*
                 * O jeito abaixo de se fazer é mais complicado do que deveria, pode ser mais simples e por isso está comentado
                 * Como esse método recebe servidor como referencia alterá-lo irá alterar o que mandou a referencia
                 * O que faz ser bem mais simples do que a que está comentada
                 *
                 * empresario.getEmpresa().getServidores().get(empresario.getEmpresa().getServidores().indexOf(servidor)).trocarSoftware(s, software);
                 *
                 */
                servidor.trocarSoftware(s, software);
        } else {
            throw new ProdutoExistente("Software não existe!");
        }
    }
    
    // Alocar um Hardware em uma Fabrica para que ele possa ser produzido ao passar o tempo
    public static void alocarHardwareEmFabrica(Fabrica fabrica, String hardwareId) throws Exception {
        if (empresario.getEmpresa().getHardwares().contains(new Hardware(hardwareId, ""))){
            Hardware h = empresario.getEmpresa().getHardwares().get(empresario.getEmpresa().getHardwares().indexOf(new Hardware(hardwareId, "")));
            fabrica.setProduto(h);
        } else {
            throw new ProdutoExistente("Hardware não existe!");
        }
    }

    public static void passarTempo(){
        relatorioMensal = "";
        tempo++;

        // Vender Softwares
        empresario.getEmpresa().venderSoftwares();

        // Fabricar e estocar hardwares
        empresario.getEmpresa().produzirHardware();
        
        // Vender Hardwares
        empresario.getEmpresa().venderHardware();
        
        // Tempo precisa estar na classe Empresario (para poder ser salvo) para poder ser mostrado na janela
        System.out.println("Tempo: " + tempo);
    }
    
    // Método criado para que outras classes possam adicionar informações no relatório mensal
    public static void addDado(String str){
        relatorioMensal += str + "\n";
    }
    
    public static String getRelatorio(){
        return relatorioMensal;
    }

    public static int getTempo(){
        return tempo;
    }
}
