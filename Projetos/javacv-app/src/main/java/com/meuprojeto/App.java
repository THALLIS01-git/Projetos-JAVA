package com.meuprojeto;

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

public class App {
    private static Process processoNavegador = null;
    private static boolean videoAberto = false; 
    public static void main(String[] args) {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        try {
            grabber.start();
            CanvasFrame janela = new CanvasFrame("Mão", CanvasFrame.getDefaultGamma() / grabber.getGamma());
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
            String urlVideo = "https://github.com/THALLIS01-git";
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
                if (!videoAberto) {
                System.out.println("Mão detectada");
                videoAberto = true; 
                String comando = "cmd /c start chrome \"" + urlVideo + "\"";
                processoNavegador = Runtime.getRuntime().exec(comando);
                }    
                } else {
                    opencv_imgproc.rectangle(imagemMat, zonaAtivacao, new Scalar(0, 0, 255, 0), 2, 0, 0);
                    opencv_imgproc.putText(imagemMat, "✋", new org.bytedeco.opencv.opencv_core.Point(50, 40), 
                    opencv_imgproc.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(0, 0, 255, 0), 2, 0, false);
                    if (videoAberto) {
                    System.out.println("Mão removida Fechando");
                    videoAberto = false;
                    Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
                    processoNavegador = null;    
                    }
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