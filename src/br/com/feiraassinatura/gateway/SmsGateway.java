package br.com.feiraassinatura.gateway;

public class SmsGateway {
    public void enviarSms(String celular, String mensagem) {
        System.out.println("[SMS simulado para " + celular + "] " + mensagem);
    }
}
