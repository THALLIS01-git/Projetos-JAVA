package com.thallis01.br;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Tv {
    public static void ligarTV() {
        String macDaTV = "XXXXXXXXXXXXXXXX"; //  MAC da sua TV!
        String ipBroadcast = "XXXXXXXXXXXXXXXX"; // IP da rede
        int porta = 9; // Porta(Wake-on-LAN)
        try {
            byte[] macBytes = converterMacParaBytes(macDaTV);
            byte[] pacoteBytes = new byte[6 + 16 * macBytes.length];
            for (int i = 0; i < 6; i++) {
                pacoteBytes[i] = (byte) 0xff;
            }
            for (int i = 6; i < pacoteBytes.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, pacoteBytes, i, macBytes.length);
            }
            InetAddress endereço = InetAddress.getByName(ipBroadcast);
            DatagramPacket datagrama = new DatagramPacket(pacoteBytes, pacoteBytes.length, endereço, porta);
            DatagramSocket socket = new DatagramSocket();
            socket.send(datagrama);
            socket.close();
            System.out.println("Ligar a TV");
        } catch (Exception e) {
            System.out.println("Erro ao tentar ligar a TV: " + e.getMessage());
        }
    }
    private static byte[] converterMacParaBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("MAC inválido. formato correto: AA:BB:CC:DD:EE:FF");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Caracteres inválidos no MAC.");
        }
        return bytes;
    }
}
