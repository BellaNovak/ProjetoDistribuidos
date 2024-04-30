package cliente;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import enumerations.Operacoes;
import enumerations.Status;
import modelo.Mensagem;
import operacoes.DeleteCandidateRequisicao;
import operacoes.DeleteCandidateResposta;
import operacoes.LoginCandidateRequisicao;
import operacoes.LoginCandidateResposta;
import operacoes.LogoutCandidateRequisicao;
import operacoes.LogoutCandidateResposta;
import operacoes.LookUpCandidateRequisicao;
import operacoes.LookUpCandidateResposta;
import operacoes.SignUpCandidateRequisicao;
import operacoes.SignUpCandidateResposta;
import operacoes.UpdateCandidateRequisicao;
import operacoes.UpdateCandidateResposta;

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
        
        System.out.println("1- Login\n2- Logout\n3- SignUp\n4- LookUp\n5- Update\n6- Delete\n");
        System.out.print("Digite a opcao: ");

        
        while ((userInput = stdIn.readLine()) != null) {
        	
        	switch(userInput){
        		case "1":
        			System.out.print("Digite o email: ");
                	String email1 = stdIn.readLine();
                	
                	System.out.print("Digite a senha: ");
                	String password1 = stdIn.readLine();
                	
                	LoginCandidateRequisicao login = new LoginCandidateRequisicao(Operacoes.LOGIN_CANDIDATE, email1, password1);
                	//LoginCandidateResposta login = new LoginCandidateResposta(Operacoes.LOGIN_CANDIDATE, Status.SUCCESS, "DISTRIBUIDOS");
                	//LoginCandidateResposta login = new LoginCandidateResposta(Operacoes.LOGIN_CANDIDATE, Status.USER_NOT_FOUND);
                	
                	String json1 = gson.toJson(login);
                    System.out.println(json1);
                    out.println(json1);
                    Mensagem mensagemRecebida1 = gson.fromJson(in.readLine(), Mensagem.class);
                    System.out.println("Server: " + mensagemRecebida1.getMensagem());
                    System.out.print ("input: ");
                break;
                
        		case "2": 
        			LogoutCandidateRequisicao logout = new LogoutCandidateRequisicao(Operacoes.LOGOUT_CANDIDATE, "DISTRIBUIDOS");
        			String json2 = gson.toJson(logout);
                    System.out.println(json2);
                    out.println(json2);
                    Mensagem mensagemRecebida2 = gson.fromJson(in.readLine(), Mensagem.class);
                    System.out.println("Server: " + mensagemRecebida2.getMensagem());
                    System.out.print ("input: ");
                break;
                
        		case "3":
        			System.out.print("Digite o email: ");
                	String email3 = stdIn.readLine();
                	
                	System.out.print("Digite a senha: ");
                	String password3 = stdIn.readLine();
                	
                	System.out.print("Digite o nome: ");
                	String name3 = stdIn.readLine();
                	
        			SignUpCandidateRequisicao signUp = new SignUpCandidateRequisicao(Operacoes.SIGNUP_CANDIDATE, email3, password3, name3);
        			
        			String json3 = gson.toJson(signUp);
                    System.out.println(json3);
                    out.println(json3);
                    Mensagem mensagemRecebida3 = gson.fromJson(in.readLine(), Mensagem.class);
                    System.out.println("Server: " + mensagemRecebida3.getMensagem());
                    System.out.print ("input: ");
                break; 
                 
        		case "4":
        			LookUpCandidateRequisicao lookUp = new LookUpCandidateRequisicao(Operacoes.LOOKUP_ACCOUNT_CANDIDATE, "DISTRIBUIDOS");
            		//LookUpCandidateResposta lookUp = new LookUpCandidateResposta(Operacoes.LOOKUP_ACCOUNT_CANDIDATE, Status.SUCCESS, email, password, name);
        			
        			String json4 = gson.toJson(lookUp);
                    System.out.println(json4);
                    out.println(json4);
                    Mensagem mensagemRecebida4 = gson.fromJson(in.readLine(), Mensagem.class);
                    System.out.println("Server: " + mensagemRecebida4.getMensagem());
                    System.out.print ("input: ");
                break;
                 
        		case "5":
        			System.out.print("Digite o email: ");
                	String email5 = stdIn.readLine();
                	
                	System.out.print("Digite a senha: ");
                	String password5 = stdIn.readLine();
                	
                	System.out.print("Digite o nome: ");
                	String name5 = stdIn.readLine();
                	
        			UpdateCandidateRequisicao update = new UpdateCandidateRequisicao(Operacoes.UPDATE_ACCOUNT_CANDIDATE, "DISTRIBUIDOS", email5, password5, name5);
                	//UpdateCandidateResposta update = new UpdateCandidateResposta(Operacoes.UPDATE_ACCOUNT_CANDIDATE, Status.INVALID_EMAIL);
        			
        			String json5 = gson.toJson(update);
                    System.out.println(json5);
                    out.println(json5);
                    Mensagem mensagemRecebida5 = gson.fromJson(in.readLine(), Mensagem.class);
                    System.out.println("Server: " + mensagemRecebida5.getMensagem());
                    System.out.print ("input: ");
                break;
                 
        		case "6":
        			DeleteCandidateRequisicao delete = new DeleteCandidateRequisicao(Operacoes.DELETE_ACCOUNT_CANDIDATE, "DISTRIBUIDOS");
                	//DeleteCandidateResposta delete = new DeleteCandidateResposta(Operacoes.DELETE_ACCOUNT_CANDIDATE, Status.SUCCESS);
                	
        			String json6 = gson.toJson(delete);
                    System.out.println(json6);
                    out.println(json6);
                    Mensagem mensagemRecebida6 = gson.fromJson(in.readLine(), Mensagem.class);
                    System.out.println("Server: " + mensagemRecebida6.getMensagem());
                    System.out.print ("input: ");
                break;
                 
        	}
        	
        	/*System.out.print("Digite o email: ");
        	String email = userInput;
        	
        	System.out.print("Digite a senha: ");
        	String password = stdIn.readLine();
        	
        	System.out.print("Digite o nome: ");
        	String name = stdIn.readLine();*/
        	
        	//LoginCandidateRequisicao login = new LoginCandidateRequisicao(Operacoes.LOGIN_CANDIDATE, email, password);
        	//LoginCandidateResposta login = new LoginCandidateResposta(Operacoes.LOGIN_CANDIDATE, Status.SUCCESS, "DISTRIBUIDOS");
        	//LoginCandidateResposta login = new LoginCandidateResposta(Operacoes.LOGIN_CANDIDATE, Status.USER_NOT_FOUND);
        	//LogoutCandidateRequisicao logout = new LogoutCandidateRequisicao(Operacoes.LOGOUT_CANDIDATE, "DISTRIBUIDOS");
        	//LogoutCandidateResposta logout = new LogoutCandidateResposta(Operacoes.LOGOUT_CANDIDATE, Status.SUCCESS);
        	//SignUpCandidateRequisicao signUp = new SignUpCandidateRequisicao(Operacoes.SIGNUP_CANDIDATE, email, password, name);
        	//SignUpCandidateResposta signUp = new SignUpCandidateResposta(Operacoes.SIGNUP_CANDIDATE, Status.SUCCESS);
        	//LookUpCandidateRequisicao lookUp = new LookUpCandidateRequisicao(Operacoes.LOOKUP_ACCOUNT_CANDIDATE, "DISTRIBUIDOS");
        	//LookUpCandidateResposta lookUp = new LookUpCandidateResposta(Operacoes.LOOKUP_ACCOUNT_CANDIDATE, Status.SUCCESS, email, password, name);
        	//UpdateCandidateRequisicao update = new UpdateCandidateRequisicao(Operacoes.UPDATE_ACCOUNT_CANDIDATE, "DISTRIBUIDOS", email, password, name);
        	//UpdateCandidateResposta update = new UpdateCandidateResposta(Operacoes.UPDATE_ACCOUNT_CANDIDATE, Status.INVALID_EMAIL);
        	//DeleteCandidateRequisicao delete = new DeleteCandidateRequisicao(Operacoes.DELETE_ACCOUNT_CANDIDATE, "DISTRIBUIDOS");
        	//DeleteCandidateResposta delete = new DeleteCandidateResposta(Operacoes.DELETE_ACCOUNT_CANDIDATE, Status.SUCCESS);
        	
         
            /*String json = gson.toJson(signUp);
            System.out.println(json);
            out.println(json);
            Mensagem mensagemRecebida = gson.fromJson(in.readLine(), Mensagem.class);
            System.out.println("Server: " + mensagemRecebida.getMensagem());
            System.out.print ("input: ");*/
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }

}
