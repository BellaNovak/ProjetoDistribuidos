package cliente;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.BancoDados;
import dao.CandidateDAO;
import entities.Candidate;
import enumerations.Operacoes;
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
	
	
	public static void main(String[] args) throws IOException, SQLException {
        
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
        
        System.out.println("1- Login\n2- Logout\n3- SignUp\n4- LookUp\n5- Update\n6- Delete\n7- Finalizar\n");
        System.out.print("Digite a opcao: ");

        while ((userInput = stdIn.readLine()) != null) {
        		
        		switch(userInput){
        			case "1":
        				
        				//PRECISA PASSAR O EMAIL
        				//PRECISA PASSAR A SENHA
        				
        				System.out.print("Digite o email: ");
        				String email1 = stdIn.readLine();
                	
        				System.out.print("Digite a senha: ");
        				String password1 = stdIn.readLine();
        				
        				Connection conn1 = BancoDados.conectar();
        				new CandidateDAO(conn1).buscarPorEmail(email1);
                	
        				LoginCandidateRequisicao loginRequisicao = new LoginCandidateRequisicao(Operacoes.LOGIN_CANDIDATE, email1, password1);
                	
        				String jsonRequisicaoLogin = gson.toJson(loginRequisicao);
        				System.out.println("Requisição enviada" + jsonRequisicaoLogin);
        				out.println(jsonRequisicaoLogin);
                    
        				LoginCandidateResposta loginResposta = gson.fromJson(in.readLine(), LoginCandidateResposta.class);
        				String jsonRespostaLogin = gson.toJson(loginResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaLogin);
        				
        				System.out.print("Nova opção: ");
                    
        			break;
                
        			case "2": 
        				
        				//PRECISA PASSAR O TOKEN
        				
        				System.out.print("Digite o token: ");
        				String token2 = stdIn.readLine();
        				
        				LogoutCandidateRequisicao logoutRequisicao = new LogoutCandidateRequisicao(Operacoes.LOGOUT_CANDIDATE, token2);
        			
        				String jsonRequisicaoLogout = gson.toJson(logoutRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoLogout);
        				out.println(jsonRequisicaoLogout);
                    
        				LogoutCandidateResposta logoutResposta = gson.fromJson(in.readLine(), LogoutCandidateResposta.class);
        				String jsonRespostaLogout = gson.toJson(logoutResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaLogout);
        				
        				System.out.print("Nova opção: ");
        			break;
                
        			case "3":
        				System.out.print("Digite o email: ");
        				String email3 = stdIn.readLine();
                	
        				System.out.print("Digite a senha: ");
        				String password3 = stdIn.readLine();
                	
        				System.out.print("Digite o nome: ");
        				String name3 = stdIn.readLine();
        				
        				Candidate candidate3 = new Candidate();
        				candidate3.setEmail(email3);
        				candidate3.setPassword(password3);
        				candidate3.setName(name3);

        				Connection conn3 = BancoDados.conectar();
        				new CandidateDAO(conn3).cadastrar(candidate3);
                	
        				SignUpCandidateRequisicao signUpRequisicao = new SignUpCandidateRequisicao(Operacoes.SIGNUP_CANDIDATE, email3, password3, name3);
        			
        				String jsonRequisicaoSignUp = gson.toJson(signUpRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoSignUp);
        				out.println(jsonRequisicaoSignUp);
                    
        				SignUpCandidateResposta signUpResposta = gson.fromJson(in.readLine(), SignUpCandidateResposta.class);
        				String jsonRespostaSignUp = gson.toJson(signUpResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaSignUp);
        				
        				System.out.print("Nova opção: ");
        			break; 
                 
        			case "4":
        				//PRECISA PASSAR O TOKEN
        				
        				System.out.print("Digite o token: ");
        				String token4 = stdIn.readLine();
        				
        				System.out.print("Digite o id do candidato para buscar: ");
        				String numero4 = stdIn.readLine();
        				int id4 = Integer.parseInt(numero4);
        				
        				Connection conn4 = BancoDados.conectar();
        				new CandidateDAO(conn4).buscarPorCodigo(id4);
        				
        				LookUpCandidateRequisicao lookUpRequisicao = new LookUpCandidateRequisicao(Operacoes.LOOKUP_ACCOUNT_CANDIDATE, token4);
        				
        				String jsonRequisicaoLookUp = gson.toJson(lookUpRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoLookUp);
        				out.println(jsonRequisicaoLookUp);
                    
        				LookUpCandidateResposta lookUpResposta = gson.fromJson(in.readLine(), LookUpCandidateResposta.class);
        				String jsonRespostaLookUp = gson.toJson(lookUpResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaLookUp);
        				
        				System.out.print("Nova opção: ");
        			break;
                 
        			case "5":
        				
        				//PRECISA PASSAR O TOKEN
        				//PRECISA PASSAR O EMAIL
        				
        				System.out.print("Digite o token: ");
        				String token5 = stdIn.readLine();
        				
        				System.out.print("Digite o id procurado: ");
        				String numero5 = stdIn.readLine();
        				int id5 = Integer.parseInt(numero5);
        				
        				System.out.print("Digite o email: ");
        				String email5 = stdIn.readLine();
                	
        				System.out.print("Digite a senha: ");
        				String password5 = stdIn.readLine();
                	
        				System.out.print("Digite o nome: ");
        				String name5 = stdIn.readLine();
        				
        				Candidate candidate5 = new Candidate();
        				candidate5.setIdCandidate(id5);
        				candidate5.setEmail(email5);
        				candidate5.setPassword(password5);
        				candidate5.setName(name5);
        				
        				Connection conn5 = BancoDados.conectar();
        				new CandidateDAO(conn5).atualizar(candidate5);
                	
        				UpdateCandidateRequisicao updateRequisicao = new UpdateCandidateRequisicao(Operacoes.UPDATE_ACCOUNT_CANDIDATE, token5, email5, password5, name5);
        			
        				String jsonRequisicaoUpdate = gson.toJson(updateRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoUpdate);
        				out.println(jsonRequisicaoUpdate);
                    
        				UpdateCandidateResposta updateResposta = gson.fromJson(in.readLine(), UpdateCandidateResposta.class);
        				String jsonRespostaUpdate = gson.toJson(updateResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaUpdate);
        				
        				System.out.print("Nova opção: ");
        			break;
                 
        			case "6":
        				//PRECISA PASSAR O TOKEN
        				
        				System.out.print("Digite o token: ");
        				String token6 = stdIn.readLine();
        				
        				System.out.print("Digite o id que deseja excluir: ");
        				String numero6 = stdIn.readLine();
        				int id6 = Integer.parseInt(numero6);
        				
        				Connection conn = BancoDados.conectar();
        				new CandidateDAO(conn).excluir(id6);
        				
        				DeleteCandidateRequisicao deleteRequisicao = new DeleteCandidateRequisicao(Operacoes.DELETE_ACCOUNT_CANDIDATE, token6);
                	
        				String jsonRequisicaoDelete = gson.toJson(deleteRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoDelete);
        				out.println(jsonRequisicaoDelete);
                    
        				DeleteCandidateResposta deleteResposta = gson.fromJson(in.readLine(), DeleteCandidateResposta.class);
        				String jsonRespostaDelete = gson.toJson(deleteResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaDelete);
        				
        				System.out.print("Nova opção: ");
        			break;
        			
        			case "7":
        				return;
        			
        			default:
        				System.out.println("ERRO: Opção inválida");
        				System.out.print("Nova opção: ");
        			break;	
                
        		}
        	
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }

}