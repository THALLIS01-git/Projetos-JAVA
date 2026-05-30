package com.meuprojeto;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import javax.swing.JFrame;
import java.awt.Desktop;
import java.net.URI;

public class App {
    private static boolean videoAberto = false; 
    private static int framesSemMao = 0;
    private static final int TOLERANCIA_FRAMES = 15; 
    public static void main(String[] args) {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        try {
            grabber.start();
            CanvasFrame janela = new CanvasFrame("Passo 4 - Automação Concluída");
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            OpenCVFrameConverter.ToMat conversor = new OpenCVFrameConverter.ToMat();
            String urlVideo = "https://github.com/THALLIS01-git";
            Mat limiteInferior = new Mat(new Scalar(0, 20, 50, 0));
            Mat limiteSuperior = new Mat(new Scalar(25, 170, 255, 0));
            System.out.println("Sistema de Automação Pronto!");
            while (janela.isVisible()) {
                Frame frame = grabber.grab();
                if (frame == null) break;
                Mat imagemOriginal = conversor.convert(frame);
                Mat imagemHSV = new Mat();
                Mat mascaraPele = new Mat();
                opencv_imgproc.cvtColor(imagemOriginal, imagemHSV, opencv_imgproc.COLOR_BGR2HSV);
                opencv_core.inRange(imagemHSV, limiteInferior, limiteSuperior, mascaraPele);
                Mat elementoEstrutural = opencv_imgproc.getStructuringElement(opencv_imgproc.MORPH_ELLIPSE, new Size(5, 5));
                opencv_imgproc.morphologyEx(mascaraPele, mascaraPele, opencv_imgproc.MORPH_CLOSE, elementoEstrutural);
                opencv_imgproc.morphologyEx(mascaraPele, mascaraPele, opencv_imgproc.MORPH_OPEN, elementoEstrutural);
                elementoEstrutural.close();
                MatVector contornos = new MatVector();
                Mat hierarquia = new Mat();
                opencv_imgproc.findContours(mascaraPele, contornos, hierarquia, opencv_imgproc.RETR_EXTERNAL, opencv_imgproc.CHAIN_APPROX_SIMPLE);
                boolean maoDetectada = false;
                Rect maiorRetangulo = null;
                double maiorArea = 0;
                for (long i = 0; i < contornos.size(); i++) {
                    Mat contorno = contornos.get(i);
                    double area = opencv_imgproc.contourArea(contorno);
                    if (area > 4000 && area > maiorArea) {
                        maiorArea = area;
                        maiorRetangulo = opencv_imgproc.boundingRect(contorno);
                        maoDetectada = true; // Mão encontrada neste frame!
                    }
                    contorno.close();
                }
                if (maoDetectada && maiorRetangulo != null) {
                    framesSemMao = 0;
                    opencv_imgproc.rectangle(imagemOriginal, maiorRetangulo, new Scalar(0, 255, 0, 0), 3, 0, 0);
                    opencv_imgproc.putText(imagemOriginal, "MAO DETECTADA", new Point(50, 40), 
                            opencv_imgproc.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(0, 255, 0, 0), 2, 0, false);
                    if (!videoAberto) {
                        System.out.println("Mão detectada! Abrindo navegador...");
                        videoAberto = true; 
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                            Desktop.getDesktop().browse(new URI(urlVideo));
                        }
                    }
                } else {
                    if (videoAberto) {
                        framesSemMao++;
                    }
                    if (framesSemMao >= TOLERANCIA_FRAMES || !videoAberto) {
                        opencv_imgproc.putText(imagemOriginal, "Coloque a mao na tela", new Point(50, 40), 
                                opencv_imgproc.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(0, 0, 255, 0), 2, 0, false);
                    } else {
                        opencv_imgproc.putText(imagemOriginal, "Aguardando estabilidade...", new Point(50, 40), 
                                opencv_imgproc.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(0, 165, 255, 0), 2, 0, false);
                    }
                    if (videoAberto && framesSemMao >= TOLERANCIA_FRAMES) {
                        System.out.println("Ausência confirmada. Fechando o Chrome...");
                        videoAberto = false;
                        framesSemMao = 0;
                        Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
                    }
                }
                janela.showImage(conversor.convert(imagemOriginal));
                imagemHSV.close();
                mascaraPele.close();
                contornos.close();
                hierarquia.close();
                Thread.sleep(20);
            }
            limiteInferior.close();
            limiteSuperior.close();
            janela.dispose();
            grabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}