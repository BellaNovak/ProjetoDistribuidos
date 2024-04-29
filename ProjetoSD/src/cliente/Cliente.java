package cliente;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import modelo.Mensagem;

public class Cliente {
	
	public static void main(String[] args) throws IOException {
        
		InetAddress serverHost = InetAddress.getLocalHost();
		int serverPort = 21234;

        System.out.println("serverHost: " + serverHost);
        System.out.println("serverPort: " + serverPort);

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
        	
            echoSocket = new Socket(serverHost, serverPort);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            
        } catch (UnknownHostException e) {
        	
            System.err.println("Don't know about host: " + serverHost);
            System.exit(1);
            
        } catch (IOException e) {
        	
        	System.err.println("Couldn't get I/O for the connection to: " + serverHost);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        Gson gson = new GsonBuilder().create();

        System.out.print ("input: ");
        
        while ((userInput = stdIn.readLine()) != null) {
            Mensagem mensagemEnviada = new Mensagem(userInput);
            String json = gson.toJson(mensagemEnviada);
            out.println(json);
            Mensagem mensagemRecebida = gson.fromJson(in.readLine(), Mensagem.class);
            System.out.println("Server: " + mensagemRecebida.getMensagem());
            System.out.print ("input: ");
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }

}
