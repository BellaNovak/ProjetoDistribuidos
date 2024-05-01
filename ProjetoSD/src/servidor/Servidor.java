package servidor;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.BancoDados;
import dao.CandidateDAO;
import entities.Candidate;
import enumerations.Status;
import operacoes.DeleteCandidateRequisicao;
import operacoes.DeleteCandidateResposta;
import operacoes.LoginCandidateRequisicao;
import operacoes.LoginCandidateResposta;
import operacoes.LogoutCandidateRequisicao;
import operacoes.LookUpCandidateRequisicao;
import operacoes.LookUpCandidateResposta;
import operacoes.Requisicao;
import operacoes.SignUpCandidateRequisicao;
import operacoes.SignUpCandidateResposta;
import operacoes.UpdateCandidateRequisicao;
import operacoes.UpdateCandidateResposta;


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

		try (
				cliente;
				PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()))) {
			
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				
				String json = inputLine;
				///System.out.println(json);
				Requisicao mensagemRecebida = gson.fromJson(json, Requisicao.class);
				
				switch(mensagemRecebida.getOperation()) {
					
					case LOGIN_CANDIDATE: 
						
						//PRECISA RECEBER O EMAIL
						//PRECISA RECEBER A SENHA
						
						String email1 = "bella";
						
						Connection conn1 = BancoDados.conectar();
						Candidate candidate1 = new CandidateDAO(conn1).buscarPorEmail(email1);
						
						
						String password1 = "10101010";
						
						LoginCandidateRequisicao login = gson.fromJson(json, LoginCandidateRequisicao.class);
						
						
						if (candidate1 != null) {
							if(candidate1.getPassword().equals(password1)) {
								
								LoginCandidateResposta mensagemLoginEnviada = new LoginCandidateResposta(login.getOperation(), Status.SUCCESS, "DISTRIBUIDOS");
								String jsonResposta1 = gson.toJson(mensagemLoginEnviada);
								System.out.println(jsonResposta1);
								out.println(jsonResposta1);
							} else {
								LoginCandidateResposta mensagemLoginEnviada = new LoginCandidateResposta(login.getOperation(), Status.INVALID_LOGIN);
								String jsonResposta1 = gson.toJson(mensagemLoginEnviada);
								System.out.println(jsonResposta1);
								out.println(jsonResposta1);
							}
							
						} else {
							LoginCandidateResposta mensagemLoginEnviada = new LoginCandidateResposta(login.getOperation(), Status.INVALID_LOGIN);
							String jsonResposta1 = gson.toJson(mensagemLoginEnviada);
							System.out.println(jsonResposta1);
							out.println(jsonResposta1);
						}
						
						/*LoginCandidateRequisicao login = gson.fromJson(json, LoginCandidateRequisicao.class);
						LoginCandidateResposta mensagemLoginEnviada = new LoginCandidateResposta(login.getOperation(), Status.SUCCESS, "DISTRIBUIDOS");
						String jsonResposta1 = gson.toJson(mensagemLoginEnviada);
						System.out.println(jsonResposta1);
						out.println(jsonResposta1);*/
						
					break;
				
					case LOGOUT_CANDIDATE:
						
						//PRECISA RECEBER E VALIDAR O TOKEN
						
						LogoutCandidateRequisicao logout = gson.fromJson(json,  LogoutCandidateRequisicao.class);
						LoginCandidateResposta mensagemLogoutEnviada = new LoginCandidateResposta(logout.getOperation(), Status.SUCCESS);
						String jsonResposta2 = gson.toJson(mensagemLogoutEnviada);
						System.out.println(jsonResposta2);
						out.println(jsonResposta2);
						
					
					break;
					
					case SIGNUP_CANDIDATE:
						
						SignUpCandidateRequisicao signUp = gson.fromJson(json, SignUpCandidateRequisicao.class);
						SignUpCandidateResposta mensagemSignUpEnviada = new SignUpCandidateResposta(signUp.getOperation(), Status.SUCCESS);
						String jsonResposta3 = gson.toJson(mensagemSignUpEnviada);
						System.out.println(jsonResposta3);
						out.println(jsonResposta3);
						
					break;
					
					case LOOKUP_ACCOUNT_CANDIDATE:
						
						//PRECISA RECEBER O TOKEN E VALDIAR
						
						int id4 = 5;
						
						Connection conn4 = BancoDados.conectar();
						Candidate candidate4 = new CandidateDAO(conn4).buscarPorCodigo(id4);
						
						
						LookUpCandidateRequisicao lookUp = gson.fromJson(json, LookUpCandidateRequisicao.class);
						LookUpCandidateResposta mensagemLookUpEnviada = new LookUpCandidateResposta(lookUp.getOperation(), Status.SUCCESS, candidate4.getEmail(), candidate4.getPassword(), candidate4.getName());
						String jsonResposta4 = gson.toJson(mensagemLookUpEnviada);
						System.out.println(jsonResposta4);
						out.println(jsonResposta4);
						
					break;
					
					case UPDATE_ACCOUNT_CANDIDATE:
						
						//PRECISA RECEBER O TOKEN E VALIDAR
						//PRECISA RECEBER O EMAIL
						
						UpdateCandidateRequisicao update = gson.fromJson(json, UpdateCandidateRequisicao.class);
						UpdateCandidateResposta mensagemUpdateEnviada = new UpdateCandidateResposta(update.getOperation(), Status.SUCCESS);
						String jsonResposta5 = gson.toJson(mensagemUpdateEnviada);
						System.out.println(jsonResposta5);
						out.println(jsonResposta5);
						
					break;
					
					case DELETE_ACCOUNT_CANDIDATE:
						
						//PRECISA RECEBER O TOKEN E VALIDAR
						
						DeleteCandidateRequisicao delete = gson.fromJson(json, DeleteCandidateRequisicao.class);
						DeleteCandidateResposta mensagemDeleteEnviada = new DeleteCandidateResposta(delete.getOperation(), Status.SUCCESS);
						String jsonResposta6 = gson.toJson(mensagemDeleteEnviada);
						System.out.println(jsonResposta6);
						out.println(jsonResposta6);
						
					break;	
					
					default:
						System.out.println("ERRO");
					break;
					
				}
			
			}
			
			out.close();
			in.close();
			cliente.close();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}