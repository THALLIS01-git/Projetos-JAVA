package com.thallis01.br;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import javax.swing.JFrame;

public class FiltroCorPele {
    public static void main(String[] args) {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);// 0 para webcam padrão do PC
        try {
            grabber.start();// Inicia a captura de vídeo da webcam
            CanvasFrame janela = new CanvasFrame("- Filtro de Cor (Pele) -");
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Configura a janela para fechar o programa ao clicar no 'X'
            // NOVO: Conversor necessário para transformar Frame em Mat
            OpenCVFrameConverter.ToMat conversor = new OpenCVFrameConverter.ToMat();
            // NOVO: Definição dos limites para detecção de pele no espaço HSV
            // Scalar(H, S, V, 0) -> Tom de pele geralmente fica no início do espectro H (vermelho/laranja)
            Mat limiteInferior = new Mat(new Scalar(0, 20, 50, 0));
            Mat limiteSuperior = new Mat(new Scalar(25, 170, 255, 0));
            while (janela.isVisible()) {
                Frame frame = grabber.grab();
                if (frame == null) break;
                // 1. Converte o frame bruto em uma matriz (Mat)
                Mat imagemOriginal = conversor.convert(frame);
                // 2. Cria matrizes vazias para guardar os resultados dos filtros
                Mat imagemHSV = new Mat();
                Mat mascaraPele = new Mat();
                // 3. Muda o espaço de cor de BGR para HSV
                opencv_imgproc.cvtColor(imagemOriginal, imagemHSV, opencv_imgproc.COLOR_BGR2HSV);
                // 4. Aplica o filtro: o que estiver entre os limites vira branco na 'mascaraPele'
                opencv_core.inRange(imagemHSV, limiteInferior, limiteSuperior, mascaraPele);
                // 5. Exibe a imagem filtrada (em preto e branco) na janela
                janela.showImage(conversor.convert(mascaraPele));
                // 6. IMPORTANTE!!: Libera a memória dessas matrizes criadas dentro do loop(Para evitar vazamento de memória)
                imagemHSV.close();
                mascaraPele.close();
                Thread.sleep(30);
            }
            // Limpeza final ao fechar o programa(Para evitar vazamento de memória)
            limiteInferior.close();                
            limiteSuperior.close();
            janela.dispose();
            grabber.stop();
            System.out.println("Programa encerrado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}