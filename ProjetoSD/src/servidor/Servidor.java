package servidor;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import modelo.Mensagem;


public class Servidor extends Thread {

	private final Socket cliente;
	private Gson gson = new GsonBuilder().create();

	public static void main(String[] args) {
		try {
			Servidor.startConnection();
		} catch (IOException e) {
			System.exit(1);
		}
	}

	private Servidor(Socket clienteSock){
	        cliente = clienteSock;
	        start();
	    }

	private static void startConnection() throws IOException {
		
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//System.out.println("Which port should be used?");
		//int servidorPort = Integer.parseInt(br.readLine());
		int servidorPort = 21234;
		
		try (ServerSocket servidor = new ServerSocket(servidorPort, 0)) {
			
			System.out.println("Connection Socket Created");
			while (true) 
			{
				try {
					
					System.out.println("Waiting for Connection");
					new Servidor(servidor.accept());
					
				} catch (IOException e) {
					
					System.err.println("Accept failed.");
					System.exit(1);
				}
			}
			
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + servidorPort);
			System.exit(1);
		}
	}

	@Override
	public void run() {
		
		System.out.println("Novo cliente conectado " + cliente.getInetAddress().getHostAddress() + " na " + cliente.getPort() + " porta");
		//System.out.println("New thread started");

		try (
				cliente;
				PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()))) {
			
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				
				String json = inputLine;
				System.out.println(json);
				Mensagem mensagemRecebida = gson.fromJson(json, Mensagem.class);
				System.out.println(mensagemRecebida);
				System.out.println("Message from" + cliente.getInetAddress() + ": " + mensagemRecebida.getMensagem());
				Mensagem mensagemEnviada = new Mensagem(mensagemRecebida.getMensagem().toUpperCase());
				String responseMessageJson = gson.toJson(mensagemEnviada);
				out.println(responseMessageJson);
			}
			
			out.close();
			in.close();
			cliente.close();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
