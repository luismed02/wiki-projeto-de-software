package gateway;

public class SmsGateway {
    public boolean enviarSms(String celular, String mensagem) {
        System.out.println();
        System.out.println("===========================================");
        System.out.println("[SMS] para " + celular);
        System.out.println("[SMS] " + mensagem);
        System.out.println("===========================================");
        System.out.println();
        return true;
    }
}
