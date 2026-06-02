package com.thallis01.br;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import javax.swing.JFrame;
public class TesteJanela {
    public static void main(String[] args) {
        //Apenas para conseguir abrir a webcam e mostrar o vídeo em uma janela, sem detecção de mão ou abertura de navegador!
        OpenCVFrameGrabber grabber=new OpenCVFrameGrabber(0);
        try{
            grabber.start();
            CanvasFrame janela=new CanvasFrame("Teste Janela!", CanvasFrame.getDefaultGamma()/grabber.getGamma());
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
                while(janela.isVisible()){
                    Frame frame=grabber.grab();
                    if(frame==null)break;
                    janela.showImage(frame);
                }
                janela.dispose();
                grabber.stop();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
