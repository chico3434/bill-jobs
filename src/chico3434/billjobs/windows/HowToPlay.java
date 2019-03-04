package chico3434.billjobs.windows;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

class HowToPlay extends Stage{
	
	private String howToPlay = "O jogo Bill Jobs: Money Machine tem como objetivo a acumulação máxima de capital provindos de vendas de produtos tecnológicos.\n" +
			"\n" + 
			"Todo produto tem:\n" + 
			"-Nome: Nome escolhido por você ao seu produto.\n" + 
			"\n" + 
			"-Versão: Para que você tenha dois produtos com mesmo nome existe a versão, que diferencia dois ou mais produtos de mesmo nome. OBS: É impossível dois produtos terem o mesmo nome e versão, pois fica impossível de diferenciá-los.\n" + 
			"\n" + 
			"-Id: O id do produto é a junção do nome com a versão, serve como o identificador do produto, e por isso não é possível dois produtos com o mesmo id. OBS: Apesar de ser gerado automáticamente, o uso do id é necessário para alocar produtos em infraestruturas (Falarei mais sobre depois).\n" + 
			"\n" + 
			"-Descrição: Um textinho em que você pode descrever o seu produto.\n" + 
			"\n" + 
			"-Preço: O preço é o quanto de verba sua empresa ganha na venda por unidade de um produto.\n" + 
			"\n" + 
			"-Custo: O custo é quanto um produto custa às suas reservas de dinheiro.\n" + 
			"\n" + 
			"-Tipo: O tipo de produto determina qual(is) infraestrutura(s) ele precisa para ser vendido e também modifica como funciona o custo dele (Falarei mais depois)\n" + 
			"\n" + 
			"É possível vender dois tipos de produtos:\n" + 
			"-Software: São os programas de computador, jogos, apps, etc.\n" + 
			"Possui como infraestrutura o Server.\n" +
			"O custo dele é debitado uma única vez, que é na hora da criação do software.\n" + 
			"\n" + 
			"-Hardware: São qualquer tipo de produto físico, como computadores, componentes eletrônicos, etc.\n" + 
			"Possui como infraestruturas a Fábrica e o Galpão.\n" + 
			"O custo dele é debitado a cada unidade produzida, não é debitada nenhuma quantia na criação de um hardware.\n" + 
			"\n" + 
			"O jogo possui infraestruturas que te possibilitam efetuar as vendas dos seus produtos, sendo eles:\n" + 
			"-Server: Server é uma estrutura exclusiva de softwares, que possui um número de espaço de armazenamento.\n" +
			"Em cada espaço é possível alocar um software, é possível alocar o mesmo software em mais de um (quantos você quiser) espaço de armazenamento de um mesmo servidor.\n" + 
			"Um software só é vendido se estiver alocado em pelo menos um espaço de armazenamento de servidor, quanto mais espaços ele ocupar, mais ele será vendido, pois quanto maior espaço de armazenamento de um software, melhor é a sua qualidade.\n" + 
			"\n" + 
			"-Fábrica: A fábrica é um estrutura exclusiva de hardwares, que possui a capacidade de manufacture somente um tipo de hardware.\n" +
			"A fábrica possui níveis, que são o quão complexo os hardwares podem ser para ela manufacture, a complexidade do hardware é maior, quando seu custo for maior.\n" +
			"Uma fábrica de nível 1, produz hardwares com custo de até R$100,00. Se for de nível 2, produz hardwares com custo de R$200,00, e assim por diante.\n" + 
			"As fábricas possuem um potencial, que é o quanto ela consegue manufacture funcionando em 100%. Em um mês podem ser produzidos 100%, 75%, 50%, 25%, 0% do potencial. E isto depende primeiramente de condições aleatórias e segundamente de condições financeiras, se sua empresa não tiver verba para manufacture algum produto ele não será produzido.\n" +
			"\n" + 
			"-Galpões: Os galpões são estruturas onde são armazenados os hardwares e vendidos.\n" + 
			"Ao ser produzido em uma fábrica um hardware é automaticamente levado a um galpão, e depois de armazenado é possível ser vendido.\n" + 
			"Os galpões possuem capacidade, se um galpão lotar as fábricas tentaram armazenar os produtos em outro galpão, porém se todos os galpões lotarem a produção não armazenada é perdida e as fábricas param com suas atividades.\n" + 
			"\n" + 
			"O jogo possui uma interface que te possibilite buy infraestruturas, criar produtos, usar as infraestruturas ver os detalhes de cada infraestrutura ou produto e passar o tempo.\n" +
			"\n" + 
			"É possível buy uma infraestrutura clicando no botão Buy que fica ao lado das infraestruturas.\n" +
			"\n" + 
			"É possível usar uma infraestrutura clicando no botão Usar que fica ao lado da infraestrutura.\n" + 
			"Para servidores o Usar serve para alocar um ou mais softwares nele.\n" + 
			"Para fábricas o Usar serve para alocar um hardware nela.\n" + 
			"Para galpões o Usar serve para ver os produtos armazenados nele e removê-los dele.\n" + 
			"\n" + 
			"É possível criar um produto clicando no botão Criar ao lado de Produtos e preenchendo a screen que se abre com os dados do produto.\n" +
			"\n" + 
			"É possível ver os detalhes clicando no id de qualquer elemento na parte \"Principal\" da screen. \n" +
			"Se quiser ver detalhes do servidor Servidor1, clique em Servidor1.\n" + 
			"Se quiser ver detalhes do produto Jogo1.0, clique em Jogo1.0.\n" + 
			"Os detalhes aparecem na parte \"Detalhes\" da screen.\n" +
			"\n" + 
			"É possível Tranferir dinheiro entre você e sua empresa e entre sua empresa e você clicando no botão Tranferir ou Receber.\n" + 
			"\n" + 
			"É possível avançar um mês clicando no botão Passar o tempo\n" + 
			"Quando você passa o tempo, softwares hospedados em servidores são vendidos, hardwares são produzidos nas suas fábricas e armazenados nos galpões e depois são vendidos.\n" + 
			"Toda essa operação gera débitos e créditos na verba da sua empresa, o Relatório Mensal, mostra todos os acontecimentos da sua empresa no mês que se passou.\n" + 
			"\n" + 
			"É possível save os dados de seu empresário clicando no Menu Game e depois no sub-menu Salvar.\n" +
			"É possível sair do jogo clicando no Menu Game e depois no sub-menu Sair.\n" +
			"É possível ver o Sobre o jogo clicando no Menu Ajuda e depois no sub-menu Sobre\n" + 
			"É possível ver o Como Jogar clicando no Menu Ajuda e depois no sub-menu Como Jogar.\n" + 
			"";
	
	HowToPlay() {
		setTitle("Como Jogar");
		
		ScrollPane panel = new ScrollPane();
		
		panel.setPadding(new Insets(10));
		
		panel.setContent(new Label(howToPlay));
		
		Scene scene = new Scene(panel, 600, 400);
		
		this.setScene(scene);
	}
}
