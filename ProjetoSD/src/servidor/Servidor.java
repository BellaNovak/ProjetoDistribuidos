package servidor;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.TreeMap;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.BancoDados;
import dao.CandidateDAO;
import entities.Candidate;
import enumerations.Role;
import enumerations.Status;
import operacoes.DeleteCandidateRequisicao;
import operacoes.DeleteCandidateResposta;
import operacoes.LoginCandidateRequisicao;
import operacoes.LoginCandidateResposta;
import operacoes.LogoutCandidateRequisicao;
import operacoes.LookUpCandidateRequisicao;
import operacoes.LookUpCandidateResposta;
import operacoes.Requisicao;
import operacoes.RequisicaoInvalida;
import operacoes.RespostaInvalida;
import operacoes.SignUpCandidateRequisicao;
import operacoes.SignUpCandidateResposta;
import operacoes.UpdateCandidateRequisicao;
import operacoes.UpdateCandidateResposta;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm; 
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.JWTVerifier;


public class Servidor extends Thread {

	private final Socket cliente;
	private Gson gson = new GsonBuilder().create();
	
	private final Algorithm algorithm = Algorithm.HMAC256("DISTRIBUIDOS");
	private final JWTVerifier verifica = JWT.require(algorithm).build();

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
						
						LoginCandidateRequisicao login = gson.fromJson(json, LoginCandidateRequisicao.class);
						
						TreeMap<String, String> data1 = (TreeMap<String,String>) login.getData();
						
						String email1 = data1.get("email");
						String password1 = data1.get("password");
						
						System.out.println(email1);
						System.out.println(password1);
						
						Connection conn1 = BancoDados.conectar();
						Candidate candidate1 = new CandidateDAO(conn1).buscarPorEmail(email1);
						
						
						if(email1.trim().equals("")|| password1.trim().equals("")) {
							RespostaInvalida mensagemNulaEnviada = new RespostaInvalida(login.getOperation(), Status.INVALID_FIELD);
							String jsonResposta1 = gson.toJson(mensagemNulaEnviada);
							System.out.println(jsonResposta1);
							out.println(jsonResposta1);
							
						} else {
							
							if (candidate1 != null) {
								if(candidate1.getPassword().equals(password1)) {
			
									String token = null;
									
									try{
										token = JWT.create().withClaim("id", candidate1.getIdCandidate()).withClaim("role", Role.CANDIDATE.toString()).sign(this.algorithm);
									
									} catch(JWTCreationException e){
										System.out.println(e);
									}

									LoginCandidateResposta mensagemLoginEnviada = new LoginCandidateResposta(login.getOperation(), Status.SUCCESS, token);
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
						}
						
							
					break;
				
					case LOGOUT_CANDIDATE:

						LogoutCandidateRequisicao logout = gson.fromJson(json,  LogoutCandidateRequisicao.class);
						
						System.out.println(logout.getToken());
						
						try{
							verifica.verify(logout.getToken());
							
							LoginCandidateResposta mensagemLogoutEnviada = new LoginCandidateResposta(logout.getOperation(), Status.SUCCESS);
							String jsonResposta2 = gson.toJson(mensagemLogoutEnviada);
							System.out.println(jsonResposta2);
							out.println(jsonResposta2);
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(logout.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta2 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta2);
							out.println(jsonResposta2);
						}

						
					
					break;
					
					case SIGNUP_CANDIDATE:
						
						SignUpCandidateRequisicao signUp = gson.fromJson(json, SignUpCandidateRequisicao.class);
						
						TreeMap<String, String> data3 = (TreeMap<String,String>) signUp.getData();
						
						String email3 = data3.get("email");
						String password3 = data3.get("password");
						String name3 = data3.get("name");
						
						System.out.println(email3);
						System.out.println(password3);
						System.out.println(name3);
						
						Connection conn3 = BancoDados.conectar();
						Candidate candidate3 = new CandidateDAO(conn3).buscarPorEmail(email3);
		
						
						if(email3.trim().equals("")|| password3.trim().equals("") || name3.trim().equals("")) {
							
							RespostaInvalida mensagemNulaEnviada = new RespostaInvalida(signUp.getOperation(), Status.INVALID_FIELD);
							String jsonResposta3 = gson.toJson(mensagemNulaEnviada);
							System.out.println(jsonResposta3);
							out.println(jsonResposta3);
						} else {
							
							if(candidate3 == null)
							{
								Candidate candidate33 = new Candidate();
		        				candidate33.setEmail(email3);
		        				candidate33.setPassword(password3);
		        				candidate33.setName(name3);

		        				Connection conn33 = BancoDados.conectar();
		        				new CandidateDAO(conn33).cadastrar(candidate33);
		        				
								SignUpCandidateResposta mensagemSignUpEnviada = new SignUpCandidateResposta(signUp.getOperation(), Status.SUCCESS);
								String jsonResposta3 = gson.toJson(mensagemSignUpEnviada);
								System.out.println(jsonResposta3);
								out.println(jsonResposta3);
								
							} else {
								
								SignUpCandidateResposta mensagemSignUpEnviada = new SignUpCandidateResposta(signUp.getOperation(), Status.USER_EXISTS);
								String jsonResposta3 = gson.toJson(mensagemSignUpEnviada);
								System.out.println(jsonResposta3);
								out.println(jsonResposta3);
								
							}
						
						}
						
					break;
					
					case LOOKUP_ACCOUNT_CANDIDATE:
						
						LookUpCandidateRequisicao lookUp = gson.fromJson(json, LookUpCandidateRequisicao.class);
						
						System.out.println(lookUp.getToken());
						
						try {
							verifica.verify(lookUp.getToken());
							Map<String, Claim> decoded = JWT.decode(lookUp.getToken()).getClaims();
		                    int id4 = decoded.get("id").asInt();

							Connection conn4 = BancoDados.conectar();
							Candidate candidate4 = new CandidateDAO(conn4).buscarPorCodigo(id4);
							
							LookUpCandidateResposta mensagemLookUpEnviada = new LookUpCandidateResposta(lookUp.getOperation(), Status.SUCCESS, candidate4.getEmail(), candidate4.getPassword(), candidate4.getName());
							String jsonResposta4 = gson.toJson(mensagemLookUpEnviada);
							System.out.println(jsonResposta4);
							out.println(jsonResposta4);
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(lookUp.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta4 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta4);
							out.println(jsonResposta4);
						}
						
						
					break;
					
					case UPDATE_ACCOUNT_CANDIDATE:
						
						UpdateCandidateRequisicao update = gson.fromJson(json, UpdateCandidateRequisicao.class);
						
						System.out.println(update.getToken());
						
						try {
							
							verifica.verify(update.getToken());
							Map<String, Claim> decoded = JWT.decode(update.getToken()).getClaims();
		                    int id5 = decoded.get("id").asInt();
							
							TreeMap<String, String> data5 = (TreeMap<String,String>) update.getData();
							
							String email5 = data5.get("email");
							String password5 = data5.get("password");
							String name5 = data5.get("name");
							
							System.out.println(email5);
							System.out.println(password5);
							System.out.println(name5);
							
							Connection conn5 = BancoDados.conectar();
							Candidate candidate5 = new CandidateDAO(conn5).buscarPorEmail(email5);
							
							if(email5.trim().equals("")|| password5.trim().equals("") || name5.trim().equals("")) {
								
								RespostaInvalida mensagemNulaEnviada = new RespostaInvalida(update.getOperation(), Status.INVALID_FIELD);
								String jsonResposta5 = gson.toJson(mensagemNulaEnviada);
								System.out.println(jsonResposta5);
								out.println(jsonResposta5);
								
							} else {
								
								if(candidate5 == null) {
									
									Candidate candidate55 = new Candidate();
									
									candidate55.setIdCandidate(id5);
									candidate55.setEmail(email5);
			        				candidate55.setPassword(password5);
			        				candidate55.setName(name5);
			        				
			        				Connection conn55 = BancoDados.conectar();
			        				new CandidateDAO(conn55).atualizar(candidate55);
									
									UpdateCandidateResposta mensagemUpdateEnviada = new UpdateCandidateResposta(update.getOperation(), Status.SUCCESS);
									String jsonResposta5 = gson.toJson(mensagemUpdateEnviada);
									System.out.println(jsonResposta5);
									out.println(jsonResposta5);
									
								} else {
									
									Connection conn55 = BancoDados.conectar();
									Candidate candidate55 = new CandidateDAO(conn55).buscarPorCodigo(id5);
									
									if(email5.equals(candidate55.getEmail())) {
										
										Candidate candidate555 = new Candidate();
										
										candidate555.setIdCandidate(id5);
										candidate555.setEmail(email5);
				        				candidate555.setPassword(password5);
				        				candidate555.setName(name5);
				        				
				        				Connection conn555 = BancoDados.conectar();
				        				new CandidateDAO(conn555).atualizar(candidate555);
										
										UpdateCandidateResposta mensagemUpdateEnviada = new UpdateCandidateResposta(update.getOperation(), Status.SUCCESS);
										String jsonResposta5 = gson.toJson(mensagemUpdateEnviada);
										System.out.println(jsonResposta5);
										out.println(jsonResposta5);
										
									} else {
										
										UpdateCandidateResposta mensagemUpdateEnviada = new UpdateCandidateResposta(update.getOperation(), Status.INVALID_EMAIL);
										String jsonResposta5 = gson.toJson(mensagemUpdateEnviada);
										System.out.println(jsonResposta5);
										out.println(jsonResposta5);
										
									}
								}	
							}
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(update.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta5 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta5);
							out.println(jsonResposta5);
						}
						
					break;
					
					case DELETE_ACCOUNT_CANDIDATE:
						
						DeleteCandidateRequisicao delete = gson.fromJson(json, DeleteCandidateRequisicao.class);
						
						System.out.println(delete.getToken());
						
						try {
							
							verifica.verify(delete.getToken());
							Map<String, Claim> decoded5 = JWT.decode(delete.getToken()).getClaims();
		                    int id6 = decoded5.get("id").asInt();
		                    
		                    Connection conn6 = BancoDados.conectar();
							new CandidateDAO(conn6).excluir(id6);
							
							DeleteCandidateResposta mensagemDeleteEnviada = new DeleteCandidateResposta(delete.getOperation(), Status.SUCCESS);
							String jsonResposta6 = gson.toJson(mensagemDeleteEnviada);
							System.out.println(jsonResposta6);
							out.println(jsonResposta6);
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(delete.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta6 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta6);
							out.println(jsonResposta6);
						}
						
						
					break;
					
					case NAO_EXISTE:
						
						RequisicaoInvalida invalida = gson.fromJson(json,  RequisicaoInvalida.class);
						RespostaInvalida mensagemInvalidaEnviada = new RespostaInvalida(invalida.getOperation(), Status.INVALID_OPERATION);
						String jsonResposta7 = gson.toJson(mensagemInvalidaEnviada);
						System.out.println(jsonResposta7);
						out.println(jsonResposta7);
					
					default:

					break;
					
				}
			
			}
			
			out.close();
			in.close();
			cliente.close();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}