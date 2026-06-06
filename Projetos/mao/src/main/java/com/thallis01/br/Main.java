package com.thallis01.br;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import javax.swing.JFrame;
import java.awt.Desktop;
import java.io.File;

public class Main {
    private static Process processoNavegador = null;
    private static boolean navegadorAbertoIA = false; 
    private static boolean maoJaDetectada = false;
    public static boolean tvLigada = false;
    public static void main(String[] args) {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        try {
            grabber.start();
            CanvasFrame janela = new CanvasFrame("Mão", CanvasFrame.getDefaultGamma() / grabber.getGamma());
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
            OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
            String caminhoHtmlLocal = "C:\\xxxx\\xxxx\\xxx\\xxxx\\xxxx\\xxxx\\mao\\portal\\index.html"; //Caminho do HTML local!!      
            Mat limiteInferior = new Mat(new Scalar(0, 30, 60, 0));
            Mat limiteSuperior = new Mat(new Scalar(20, 150, 255, 0));
            System.out.println("Ligado");            
            while (janela.isVisible()) {
                Frame frame = grabber.grab();
                if (frame == null) break;               
                Mat imagemMat = converter.convert(frame);               
                Rect zonaAtivacao = new Rect(50, 50, 200, 200);
                Mat regiaoAnalise = new Mat(imagemMat, zonaAtivacao);              
                Mat imagemHSV = new Mat();
                opencv_imgproc.cvtColor(regiaoAnalise, imagemHSV, opencv_imgproc.COLOR_BGR2HSV);
                Mat mascaraPele = new Mat();
                opencv_core.inRange(imagemHSV, limiteInferior, limiteSuperior, mascaraPele);                
                int pixelsPele = opencv_core.countNonZero(mascaraPele);                                
                imagemHSV.close();
                mascaraPele.close();
                regiaoAnalise.close();               
                if (pixelsPele > 8000) {
                    opencv_imgproc.rectangle(imagemMat, zonaAtivacao, new Scalar(0, 255, 0, 0), 3, 0, 0);
                    opencv_imgproc.putText(imagemMat, "MAO DETECTADA", new org.bytedeco.opencv.opencv_core.Point(50, 40), 
                    opencv_imgproc.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(0, 255, 0, 0), 2, 0, false);
                    if (!maoJaDetectada) {
                        maoJaDetectada = true;                                              
                        if (!navegadorAbertoIA) {
                            System.out.println("Mão detectada: Abrindo HTML local, ligando a TV...");
                            navegadorAbertoIA = true; 
                            File arquivoHtml = new File(caminhoHtmlLocal);
                            if (arquivoHtml.exists() && Desktop.isDesktopSupported()) {
                                Desktop.getDesktop().browse(arquivoHtml.toURI());
                            } else {
                                System.out.println("Erro: Arquivo HTML não encontrado no caminho especificado.");
                            }
                            if (tvLigada == false) {
                                Tv.ligarTV();
                                System.out.println("Enviando sinal para a TV!");
                                tvLigada = true;
                            }
                        } else {
                            System.out.println("Mão detectada: Fechando Navegador...");
                            navegadorAbertoIA = false;
                            Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
                            processoNavegador = null; 
                            System.out.println("A TV já esta ligada!");
                        }
                    }                   
                } 
                else {
                    opencv_imgproc.rectangle(imagemMat, zonaAtivacao, new Scalar(0, 0, 255, 0), 2, 0, 0);
                    opencv_imgproc.putText(imagemMat, "Aguardando...", new org.bytedeco.opencv.opencv_core.Point(50, 40), 
                    opencv_imgproc.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(0, 0, 255, 0), 2, 0, false);
                    maoJaDetectada = false;
                }                
                janela.showImage(converter.convert(imagemMat));
                Thread.sleep(50);
            }          
            limiteInferior.close();
            limiteSuperior.close();                      
            if (processoNavegador != null && processoNavegador.isAlive()) {
                Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
            }
            janela.dispose();
            grabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}