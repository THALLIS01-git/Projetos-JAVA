
# # Sistema Mao: Automação Residencial por Visão Computacional e IoT

O **Sistema Mao** é uma solução inteligente de automação de desktop e de hardware residencial que integra **Visão Computacional em tempo real**, **Protocolos de Rede de Baixo Nível (Wake-on-LAN)** e uma **Interface Web Hub**. O ecossistema analisa continuamente o fluxo de vídeo da webcam para detectar a presença de uma mão humana dentro de uma Zona de Ativação específica. A partir desse gatilho físico, o sistema coordena ações síncronas: envia pacotes de rede para ligar ou desligar televisores locais, controla processos do sistema operacional e exibe um portal interativo tridimensional de alto desempenho.

---

## ## ⚙️ Arquitetura e Engenharia do Sistema

O projeto adota uma arquitetura modularizada, isolando as responsabilidades de captura de hardware, inteligência algorítmica, comunicação de rede e renderização de interface.

```
[Webcam Física] 
       │
       ▼
[FiltroCorPele.java] ──> Converte BGR para HSV ──> Aplica inRange (Segmentação Binária)
       │
       ▼ (Se Densidade de Pixels Brancos > 8000)
[Main.java] ───────────> Gerencia Região de Interesse (ROI) & Máquina de Estados (Debounce)
       │
       ├───────────────────────────────────────┐
       ▼ (Gatilho de Ativação)                 ▼ (Gatilho de Ativação)
[Tv.java (Camada de Rede)]             [Automação de OS (Runtime)]
       │                                       │
       ├──> Monta Magic Packet (0xFF * 6)      ├──> Executa/Fecha Navegador Padrão
       └──> Envia Socket UDP na Porta 9        └──> Serve o Portal Web (`index.html`)

```

### ### 1. Núcleo Orquestrador (`Main.java`)

Gerencia o ciclo de vida da captura de vídeo utilizando a biblioteca **JavaCV** (wrapper nativo para OpenCV).

* **Região de Interesse (ROI):** Para otimizar o uso de CPU, o sistema define uma submatriz restrita na imagem (`zonaAtivacao` de $200 \times 200$ pixels). O processamento pesado ocorre exclusivamente dentro dessa janela espacial.
* **Máquina de Estados & Debounce:** Implementa uma lógica de travamento através das flags `maoJaDetectada` e `navegadorAbertoIA`. Isso impede múltiplos disparos idênticos em frações de segundo enquanto a mão estiver estática, garantindo estabilidade operacional.

### ### 2. Filtro de Segmentação Cromática (`FiltroCorPele.java`)

Responsável pelo isolamento do tom de pele humana sob condições variáveis de iluminação.

* **Espaço de Cores HSV:** Transforma os frames brutos do formato BGR para **HSV** (*Hue, Saturation, Value*), isolando a componente de matiz ($H$) do brilho ($V$).
* **Limiarização Binária (*Thresholding*):** Isola os pixels que estão dentro do espectro pré-calibrado para pele humana (entre os escalares `(0, 20, 50)` e `(25, 170, 255)`). Pixels válidos viram branco ($255$) e o restante preto ($0$).
* **Análise Volumétrica:** A função `countNonZero` varre a máscara binária resultante da ROI. O gatilho de ativação só é disparado se a contagem de pixels brancos ultrapassar o limiar crítico de $8000$ pixels, eliminando falsos positivos.

### ### 3. Protocolo de Rede IoT (`Tv.java`)

Implementa de forma manual o protocolo **Wake-on-LAN (WoL)** sobre a camada de transporte.

* **Construção do Magic Packet:** O código monta uma carga útil binária bruta composta por um cabeçalho de 6 bytes preenchidos com `0xFF` seguidos por exatamente 16 repetições do endereço MAC da TV alvo.
* **Rede Sockets UDP:** O pacote é encapsulado em um `DatagramPacket` e transmitido de forma assíncrona via `DatagramSocket` para o endereço de broadcast IP da sub-rede local através da porta padrão 9.

### ### 4. Ambiente de Laboratório (`TesteJanela.java`)

Atua como um teste de fumaça (*smoke test*). Valida a integridade do pipeline de hardware e drivers da webcam sem acionar a pilha de processamento de imagem ou integrações externas, simplificando o isolamento de bugs de hardware.

### ### 5. Interface Web Hub (`index.html`, `script.js`, `style.css`)

O ponto final da experiência do usuário, apresentando recursos avançados de engenharia de front-end:

* **Renderização Gráfica 3D (WebGL/Three.js):** Constrói uma cena tridimensional interativa no cabeçalho (*hero section*), renderizando esferas de partículas matemáticas calculadas dinamicamente em espaço vetorial e aplicando efeitos de paralaxe responsivos ao movimento do mouse.
* **Ciclo de Vida e Coleta de Lixo da GPU:** O script implementa uma rotina rigorosa de desmontagem (`destroyThreeJS` e `disposeObject`). Quando a janela é redimensionada para dispositivos móveis ou encerrada, as geometrias e os materiais são limpos explicitamente da memória de vídeo, prevenindo o vazamento de memória gráfica (*Memory Leaks*).
* **Otimização de Performance:** Utiliza a API `IntersectionObserver` para aplicar efeitos de surgimento animado (*scroll reveal*) nos cards de navegação sob demanda, processando-os apenas quando entram na área visível da tela.
* **Design System Fluido:** O arquivo CSS implementa propriedades modernas como a função `clamp()` para tipografia responsiva e variáveis centralizadas de escopo global (`:root`).

---

## ## 🛠️ Tecnologias, Frameworks e Conceitos Chave

* **Back-end:** Java (Manipulação de Streams de IO, Threads, Gerenciamento de Processos nativos do SO, Sockets UDP).
* **Visão Computacional:** OpenCV / JavaCV (Conversão de espaços de cores, limiarização binária, manipulação de matrizes de imagem).
* **Gerenciamento de Memória Off-Heap:** Uso do método `.close()` em objetos `Mat` do OpenCV de dentro do laço principal. Como o JavaCV encapsula binários nativos em C++, a desalocação explícita previne o estouro da memória RAM do sistema.
* **Front-end:** HTML5, CSS3 Avançado (Animações Bézier, Media Queries de Acessibilidade), JavaScript Moderno e Three.js (WebGL).

---

## ## 🚀 Como Executar o Ecossistema

### ### Pré-requisitos

1. **Java JDK** instalado.
2. Dependências do **JavaCV / OpenCV** configuradas no build path do seu projeto (via Maven, Gradle ou adição manual das bibliotecas `.jar`).
3. Uma webcam funcional mapeada no índice `0` do sistema.
4. Um dispositivo de exibição (TV/Monitor) na mesma rede local que seja compatível com o protocolo Wake-on-LAN.

### ### Ajustes de Configuração

1. No arquivo `Main.java`, configure o caminho absoluto do arquivo `index.html` na sua máquina:
```java
String caminhoHtmlLocal = "C:\\seu_caminho\\mao\\portal\\index.html";

```


2. No arquivo `Tv.java`, insira os parâmetros de rede correspondentes ao seu hardware:
```java
String macDaTV = "AA-BB-CC-DD-EE-FF"; // MAC address do seu dispositivo
String ipBroadcast = "192.168.1.255";  // IP de Broadcast da sua sub-rede

```



### ### Fluxo Operacional

1. Inicialize a classe `Main.java`. A janela do fluxo de vídeo abrirá exibindo um delimitador vermelho (**Zona de Ativação**).
2. Insira sua mão dentro do delimitador.
3. O delimitador mudará sua cor para **verde** e exibirá o rótulo `"MAO DETECTADA"`.
4. Em segundo plano, um pacote UDP acionará o Wake-on-LAN para ligar o televisor e o navegador do sistema operacional será inicializado renderizando o Portal Web com gráficos interativos 3D.
5. Ao inserir a mão novamente na região, o sistema computa a alternância de estado e encerra de forma limpa a instância do navegador através de chamadas nativas de terminal.

---
### ### 🌐 O Portal Hub e o Uso de Marcas de Terceiros (Disclaimer)

O **Portal Web** integrado ao ecossistema atua como um *Dashboard* ou *Hub* centralizado de produtividade, sendo disparado automaticamente assim que a automação por visão computacional valida o gesto do usuário. 

A interface renderiza cartões interativos que contêm links de direcionamento e logotipos de plataformas e corporações globalmente reconhecidas (como *Google, YouTube, GitHub, LinkedIn, Instagram e Wikipedia*). Em relação a este conteúdo, estabelece-se que:

* **Finalidade Exclusiva de Redirecionamento:** A inclusão dos logotipos e hiperlinks serve unicamente para conveniência e facilidade de navegação do usuário final, funcionando como atalhos rápidos para serviços web externos.
* **Isenção de Vínculo Institucional:** O desenvolvimento, o autor deste projeto e o **Sistema Mao** não possuem qualquer tipo de filiação, parceria, patrocínio, endosso ou vínculo comercial/institucional com as marcas mencionadas ou com as empresas proprietárias delas.
* **Ausência de Fins Lucrativos:** Este é um projeto estritamente acadêmico, de código aberto (*open-source*) e para fins de portfólio técnico. O sistema não monetiza, não exibe anúncios e não obtém lucros diretos ou indiretos através da exibição ou do clique em tais links.
* **Propriedade Intelectual:** Todos os direitos autorais, marcas registradas e identidade visual dos logotipos utilizados pertencem integralmente às suas respectivas empresas e detentores legais.
---

---
*Documento de Engenharia de Software gerado para consolidação de aprendizado em Arquitetura Core Java, Processamento Digital de Sinais (Visão Computacional), Sockets de Baixo Nível e Otimização Gráfica de Front-end.*
---