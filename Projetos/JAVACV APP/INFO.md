# Documentação do Projeto: Rastreamento de Mão e Automação com JavaCV

Este documento explica detalhadamente a arquitetura, a lógica matemática e o fluxo de dados do programa de automação por visão computacional construído em Java. O objetivo deste sistema é monitorar o fluxo de vídeo da webcam, isolar a pele humana, rastrear a geometria de uma mão e disparar gatilhos no sistema operacional (abrir e fechar o navegador) com base na presença do usuário.

---

## 1. Arquitetura Geral do Pipeline

O programa opera como um pipeline linear de processamento de imagem em tempo real. Cada frame capturado passa por 5 estágios principais antes de gerar uma tomada de decisão:

```
[ Webcam ] ──> [ Captura (BGR) ] ──> [ Espaço HSV ] ──> [ Filtro InRange ]
                                                                │
[ Automação OS ] <── [ Máquina de Estados ] <── [ Contornos ] <── [ Morfologia ]
```

---

## 2. Explicação Detalhada dos Componentes

### A. Captura e Gerenciamento de Memória Nativa
O OpenCV e o JavaCV trabalham como wrappers (envelopes) sobre bibliotecas escritas em C++. Diferente do comportamento padrão do Java, as matrizes de imagem (`Mat`) alocam blocos pesados diretamente na memória RAM nativa (fora da Máquina Virtual Java - JVM).

* **`OpenCVFrameGrabber(0)`**: Inicializa o hardware da câmera apontando para o índice `0`. Abre o stream de vídeo estruturado como uma sequência rápida de fotos estáticas.
* **`OpenCVFrameConverter.ToMat()`**: Converte o formato genérico de `Frame` (usado para renderização de tela) em uma matriz `Mat`, essencial para operações matemáticas pixel a pixel.
* **O método `.close()`**: Essencial dentro do laço `while`. Como o Garbage Collector do Java gerencia apenas a memória heap da JVM, se os objetos nativos `Mat` não forem destruídos explicitamente a cada iteração, o programa sofrerá um *Memory Leak* (vazamento de memória) severo, travando o computador em poucos segundos.

### B. O Espaço de Cores HSV
Por padrão, a câmera captura imagens no formato **BGR** (Blue, Green, Red). No entanto, o BGR é extremamente sensível a variações de iluminação. Se uma nuvem tapar o sol ou uma lâmpada for acesa, os valores de azul, verde e vermelho da sua pele mudam drasticamente, quebrando o algoritmo.

Para contornar isso, o código converte a imagem para o espaço **HSV**:
* **H (Hue / Matiz)**: Representa a "cor" pura em um espectro angular (ex: a faixa do vermelho/laranja onde se localiza a pele humana). **O Matiz não muda com a variação de luz**, tornando o filtro estável.
* **S (Saturation / Saturação)**: A vivacidade ou pureza da cor.
* **V (Value / Valor)**: O brilho ou intensidade luminosa da imagem.

A função `opencv_imgproc.cvtColor(..., COLOR_BGR2HSV)` faz essa transposição matemática de coordenadas de cor.

### C. Binarização com `inRange`
Uma vez em HSV, o método `opencv_core.inRange` escaneia a matriz inteira. Ele compara cada pixel individual com os limites definidos:
* **Limite Inferior**: `Scalar(0, 20, 50, 0)`
* **Limite Superior**: `Scalar(25, 170, 255, 0)`

Se os valores do pixel estiverem contidos nesse intervalo, o pixel recebe o valor máximo absoluto (**255 - Branco**). Se estiver fora, recebe o valor mínimo (**0 - Preto**). O resultado é uma imagem binária (máscara), onde a silhueta da pele humana brilha em branco contra um fundo totalmente escuro.

### D. Filtros Morfológicos (Tratamento de Ruído)
A imagem binária gerada pelo `inRange` sofre com imperfeições físicas: sombras criam "buracos" pretos na mão e poeira visual cria "chuviscos" brancos no fundo. Usamos o `getStructuringElement` para criar uma máscara geométrica em elipse de 5x5 pixels que varre a imagem aplicando duas equações:

1. **`MORPH_CLOSE` (Fechamento)**: Executa uma *Dilatação* seguida por uma *Erosão*. Ele expande temporariamente as bordas brancas para fundir e eliminar pequenos buracos escuros de sombra internos do objeto, consolidando a forma da mão.
2. **`MORPH_OPEN` (Abertura)**: Executa uma *Erosão* seguida por uma *Dilatação*. Ele encolhe as formas brancas. Pontos minúsculos de ruído ao fundo desaparecem completamente por não terem massa crítica, sobrando apenas o objeto principal estruturado.

### E. Extração de Contornos e Análise Geométrica
Com a máscara perfeitamente limpa, a função `opencv_imgproc.findContours` agrupa pixels brancos vizinhos e traça vetores geométricos ao redor deles, gerando perímetros fechados (contornos).

Como o ambiente ainda pode conter o rosto do usuário ou objetos de tons parecidos ao fundo, aplicamos um filtro de relevância:
1. Calculamos a área interna de cada contorno via `opencv_imgproc.contourArea`.
2. Filtramos contornos irrelevantes (`area > 4000`), assumindo que ruídos ou objetos distantes serão menores que esse patamar.
3. Buscamos o **maior contorno ativo na tela**. Se a mão do usuário for o objeto mais próximo da câmera, ela naturalmente dominará a maior área.
4. O comando `opencv_imgproc.boundingRect` extrai as coordenadas geográficas extremas desse maior contorno, gerando um objeto `Rect` (retângulo) para fins de marcação visual e lógica.

---

## 3. Lógica de Negócio e Estabilidade (Debounce)

A automação pura aplicada a sensores visuais sofre com o efeito de oscilação rápida (*flickering*). Se o usuário piscar a mão ou mover os dedos de forma a ocultar temporariamente a pele por 1 único frame (1/30 de segundo), um código simplista fecharia o navegador e o abriria logo em seguida, gerando uma péssima experiência.

Para mitigar isso, o sistema implementa uma **Máquina de Estados de Tempo Discreto**:

```
                  ┌─────────────────┐
                  │   Mão na Tela   │──────┐
                  └─────────────────┘      │
                           ▲               │ Mão Some
                Mão Volta  │               ▼
                           │      ┌─────────────────┐
                           └──────│   Aguardando    │ (Tolerância: 15 frames)
                                  └─────────────────┘
                                           │
                                           │ Passaram 15 frames
                                           ▼
                                  ┌─────────────────┐
                                  │ Executa FECHAR  │
                                  └─────────────────┘
```

### Variáveis Controladoras:
* **`videoAberto` (boolean)**: Guarda o estado atual do navegador no sistema operacional. Evita chamadas duplicadas ao método de abertura (`Desktop.browse`), impedindo que dezenas de abas idênticas travem o sistema.
* **`framesSemMao` (int)**: Um acumulador numérico que atua como um temporizador baseado em quadros de vídeo.
* **`TOLERANCIA_FRAMES = 15`**: Define o limiar de segurança (aproximadamente 300 a 500 milissegundos, dependendo do hardware).

### Fluxo Decisório:
1. **Se a mão for detectada**: O contador `framesSemMao` é resetado para `0` instantaneamente. Se `videoAberto` for falso, o gatilho de inicialização dispara a URL e altera o estado do booleano para verdadeiro.
2. **Se a mão sumir**: O sistema não toma uma ação imediata. Ele incrementa `framesSemMao++` e altera o feedback textual na tela para *"Aguardando estabilidade..."*.
3. **Estouro da Tolerância**: Somente quando `framesSemMao` atinge ou supera o valor de `15`, o sistema assume com convicção matemática que a mão foi removida intencionalmente. O estado `videoAberto` volta a ser falso e o comando de sistema `taskkill /F /IM chrome.exe` é executado de forma limpa pelo `Runtime.getRuntime().exec()`.

---
*Documento gerado para consolidação de aprendizado em Engenharia de Visão Computacional e Arquitetura de Software.*