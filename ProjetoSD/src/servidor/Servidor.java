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
import dao.RecruiterDAO;
import dao.SkillDAO;
import dao.SkillsetDAO;
import entities.Candidate;
import entities.Recruiter;
import entities.Skill;
import entities.Skillset;
import enumerations.Role;
import enumerations.Status;
import operacoes.DeleteCandidateRequisicao;
import operacoes.DeleteCandidateResposta;
import operacoes.DeleteRecruiterRequisicao;
import operacoes.DeleteRecruiterResposta;
import operacoes.DeleteSkillRequisicao;
import operacoes.DeleteSkillResposta;
import operacoes.IncludeSkillRequisicao;
import operacoes.IncludeSkillResposta;
import operacoes.LoginCandidateRequisicao;
import operacoes.LoginCandidateResposta;
import operacoes.LoginRecruiterRequisicao;
import operacoes.LoginRecruiterResposta;
import operacoes.LogoutCandidateRequisicao;
import operacoes.LogoutCandidateResposta;
import operacoes.LogoutRecruiterRequisicao;
import operacoes.LogoutRecruiterResposta;
import operacoes.LookUpCandidateRequisicao;
import operacoes.LookUpCandidateResposta;
import operacoes.LookUpRecruiterRequisicao;
import operacoes.LookUpRecruiterResposta;
import operacoes.LookUpSkillRequisicao;
import operacoes.LookUpSkillResposta;
import operacoes.Requisicao;
import operacoes.RequisicaoInvalida;
import operacoes.RespostaInvalida;
import operacoes.SignUpCandidateRequisicao;
import operacoes.SignUpCandidateResposta;
import operacoes.SignUpRecruiterRequisicao;
import operacoes.SignUpRecruiterResposta;
import operacoes.UpdateCandidateRequisicao;
import operacoes.UpdateCandidateResposta;
import operacoes.UpdateRecruiterRequisicao;
import operacoes.UpdateRecruiterResposta;
import operacoes.UpdateSkillRequisicao;
import operacoes.UpdateSkillResposta;

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
						
						LoginCandidateRequisicao loginCandidato = gson.fromJson(json, LoginCandidateRequisicao.class);
						
						TreeMap<String, String> data1 = (TreeMap<String,String>) loginCandidato.getData();
						
						String email1 = data1.get("email");
						String password1 = data1.get("password");
						
						System.out.println(email1);
						System.out.println(password1);
						
						Connection conn1 = BancoDados.conectar();
						Candidate candidate1 = new CandidateDAO(conn1).buscarPorEmail(email1);
						
						
						if(email1.trim().equals("")|| password1.trim().equals("")) {
							RespostaInvalida mensagemNulaEnviada = new RespostaInvalida(loginCandidato.getOperation(), Status.INVALID_FIELD);
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

									LoginCandidateResposta mensagemLoginEnviada = new LoginCandidateResposta(loginCandidato.getOperation(), Status.SUCCESS, token);
									String jsonResposta1 = gson.toJson(mensagemLoginEnviada);
									System.out.println(jsonResposta1);
									out.println(jsonResposta1);
									
								} else {
									LoginCandidateResposta mensagemLoginEnviada = new LoginCandidateResposta(loginCandidato.getOperation(), Status.INVALID_LOGIN);
									String jsonResposta1 = gson.toJson(mensagemLoginEnviada);
									System.out.println(jsonResposta1);
									out.println(jsonResposta1);
								}
									
							} else {
								LoginCandidateResposta mensagemLoginEnviada = new LoginCandidateResposta(loginCandidato.getOperation(), Status.INVALID_LOGIN);
								String jsonResposta1 = gson.toJson(mensagemLoginEnviada);
								System.out.println(jsonResposta1);
								out.println(jsonResposta1);
							}
						}
									
					break;
				
					case LOGOUT_CANDIDATE:

						LogoutCandidateRequisicao logoutCandidato = gson.fromJson(json,  LogoutCandidateRequisicao.class);
						
						System.out.println(logoutCandidato.getToken());
						
						try{
							verifica.verify(logoutCandidato.getToken());
							
							LogoutCandidateResposta mensagemLogoutEnviada = new LogoutCandidateResposta(logoutCandidato.getOperation(), Status.SUCCESS);
							String jsonResposta2 = gson.toJson(mensagemLogoutEnviada);
							System.out.println(jsonResposta2);
							out.println(jsonResposta2);
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(logoutCandidato.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta2 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta2);
							out.println(jsonResposta2);
						}
					
					break;
					
					case SIGNUP_CANDIDATE:
						
						SignUpCandidateRequisicao signUpCandidato = gson.fromJson(json, SignUpCandidateRequisicao.class);
						
						TreeMap<String, String> data3 = (TreeMap<String,String>) signUpCandidato.getData();
						
						String email3 = data3.get("email");
						String password3 = data3.get("password");
						String name3 = data3.get("name");
						
						System.out.println(email3);
						System.out.println(password3);
						System.out.println(name3);
						
						Connection conn3 = BancoDados.conectar();
						Candidate candidate3 = new CandidateDAO(conn3).buscarPorEmail(email3);
		
						
						if(email3.trim().equals("")|| password3.trim().equals("") || name3.trim().equals("")) {
							
							RespostaInvalida mensagemNulaEnviada = new RespostaInvalida(signUpCandidato.getOperation(), Status.INVALID_FIELD);
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
		        				
								SignUpCandidateResposta mensagemSignUpEnviada = new SignUpCandidateResposta(signUpCandidato.getOperation(), Status.SUCCESS);
								String jsonResposta3 = gson.toJson(mensagemSignUpEnviada);
								System.out.println(jsonResposta3);
								out.println(jsonResposta3);
								
							} else {
								
								SignUpCandidateResposta mensagemSignUpEnviada = new SignUpCandidateResposta(signUpCandidato.getOperation(), Status.USER_EXISTS);
								String jsonResposta3 = gson.toJson(mensagemSignUpEnviada);
								System.out.println(jsonResposta3);
								out.println(jsonResposta3);
								
							}
						
						}
						
					break;
					
					case LOOKUP_ACCOUNT_CANDIDATE:
						
						LookUpCandidateRequisicao lookUpCandidato = gson.fromJson(json, LookUpCandidateRequisicao.class);
						
						System.out.println(lookUpCandidato.getToken());
						
						try {
							verifica.verify(lookUpCandidato.getToken());
							Map<String, Claim> decoded = JWT.decode(lookUpCandidato.getToken()).getClaims();
		                    int id4 = decoded.get("id").asInt();

							Connection conn4 = BancoDados.conectar();
							Candidate candidate4 = new CandidateDAO(conn4).buscarPorCodigo(id4);
							
							LookUpCandidateResposta mensagemLookUpEnviada = new LookUpCandidateResposta(lookUpCandidato.getOperation(), Status.SUCCESS, candidate4.getEmail(), candidate4.getPassword(), candidate4.getName());
							String jsonResposta4 = gson.toJson(mensagemLookUpEnviada);
							System.out.println(jsonResposta4);
							out.println(jsonResposta4);
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(lookUpCandidato.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta4 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta4);
							out.println(jsonResposta4);
						}
						
						
					break;
					
					case UPDATE_ACCOUNT_CANDIDATE:
						
						UpdateCandidateRequisicao updateCandidato = gson.fromJson(json, UpdateCandidateRequisicao.class);
						
						System.out.println(updateCandidato.getToken());
						
						try {
							
							verifica.verify(updateCandidato.getToken());
							Map<String, Claim> decoded = JWT.decode(updateCandidato.getToken()).getClaims();
		                    int id5 = decoded.get("id").asInt();
							
							TreeMap<String, String> data5 = (TreeMap<String,String>) updateCandidato.getData();
							
							String email5 = data5.get("email");
							String password5 = data5.get("password");
							String name5 = data5.get("name");
							
							System.out.println(email5);
							System.out.println(password5);
							System.out.println(name5);
							
							Connection conn5 = BancoDados.conectar();
							Candidate candidate5 = new CandidateDAO(conn5).buscarPorEmail(email5);
							
							if(email5.trim().equals("")|| password5.trim().equals("") || name5.trim().equals("")) {
								
								RespostaInvalida mensagemNulaEnviada = new RespostaInvalida(updateCandidato.getOperation(), Status.INVALID_FIELD);
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
									
									UpdateCandidateResposta mensagemUpdateEnviada = new UpdateCandidateResposta(updateCandidato.getOperation(), Status.SUCCESS);
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
										
										UpdateCandidateResposta mensagemUpdateEnviada = new UpdateCandidateResposta(updateCandidato.getOperation(), Status.SUCCESS);
										String jsonResposta5 = gson.toJson(mensagemUpdateEnviada);
										System.out.println(jsonResposta5);
										out.println(jsonResposta5);
										
									} else {
										
										UpdateCandidateResposta mensagemUpdateEnviada = new UpdateCandidateResposta(updateCandidato.getOperation(), Status.INVALID_EMAIL);
										String jsonResposta5 = gson.toJson(mensagemUpdateEnviada);
										System.out.println(jsonResposta5);
										out.println(jsonResposta5);
										
									}
								}	
							}
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(updateCandidato.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta5 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta5);
							out.println(jsonResposta5);
						}
						
					break;
					
					case DELETE_ACCOUNT_CANDIDATE:
						
						DeleteCandidateRequisicao deleteCandidato = gson.fromJson(json, DeleteCandidateRequisicao.class);
						
						System.out.println(deleteCandidato.getToken());
						
						try {
							
							verifica.verify(deleteCandidato.getToken());
							Map<String, Claim> decoded = JWT.decode(deleteCandidato.getToken()).getClaims();
		                    int id6 = decoded.get("id").asInt();
		                    
		                    Connection conn6 = BancoDados.conectar();
							new CandidateDAO(conn6).excluir(id6);
							
							DeleteCandidateResposta mensagemDeleteEnviada = new DeleteCandidateResposta(deleteCandidato.getOperation(), Status.SUCCESS);
							String jsonResposta6 = gson.toJson(mensagemDeleteEnviada);
							System.out.println(jsonResposta6);
							out.println(jsonResposta6);
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(deleteCandidato.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta6 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta6);
							out.println(jsonResposta6);
						}
						
						
					break;
					
					case LOGIN_RECRUITER:
						
						LoginRecruiterRequisicao loginEmpresa = gson.fromJson(json, LoginRecruiterRequisicao.class);
						
						TreeMap<String, String> data7 = (TreeMap<String,String>) loginEmpresa.getData();
						
						String email7 = data7.get("email");
						String password7 = data7.get("password");
						
						System.out.println(email7);
						System.out.println(password7);
						
						Connection conn7 = BancoDados.conectar();
						Recruiter recruiter7 = new RecruiterDAO(conn7).buscarPorEmail(email7);
						
						
						if(email7.trim().equals("")|| password7.trim().equals("")) {
							RespostaInvalida mensagemNulaEnviada = new RespostaInvalida(loginEmpresa.getOperation(), Status.INVALID_FIELD);
							String jsonResposta7 = gson.toJson(mensagemNulaEnviada);
							System.out.println(jsonResposta7);
							out.println(jsonResposta7);
							
						} else {
							
							if (recruiter7 != null) {
								if(recruiter7.getPassword().equals(password7)) {
			
									String token = null;
									
									try{
										token = JWT.create().withClaim("id", recruiter7.getIdRecruiter()).withClaim("role", Role.RECRUITER.toString()).sign(this.algorithm);
									
									} catch(JWTCreationException e){
										System.out.println(e);
									}

									LoginRecruiterResposta mensagemLoginEnviada = new LoginRecruiterResposta(loginEmpresa.getOperation(), Status.SUCCESS, token);
									String jsonResposta7 = gson.toJson(mensagemLoginEnviada);
									System.out.println(jsonResposta7);
									out.println(jsonResposta7);
									
								} else {
									LoginRecruiterResposta mensagemLoginEnviada = new LoginRecruiterResposta(loginEmpresa.getOperation(), Status.INVALID_LOGIN);
									String jsonResposta7 = gson.toJson(mensagemLoginEnviada);
									System.out.println(jsonResposta7);
									out.println(jsonResposta7);
								}
									
							} else {
								LoginRecruiterResposta mensagemLoginEnviada = new LoginRecruiterResposta(loginEmpresa.getOperation(), Status.INVALID_LOGIN);
								String jsonResposta7 = gson.toJson(mensagemLoginEnviada);
								System.out.println(jsonResposta7);
								out.println(jsonResposta7);
							}
						}
						
					break;	
						
					case LOGOUT_RECRUITER:
						
						LogoutRecruiterRequisicao logoutEmpresa = gson.fromJson(json,  LogoutRecruiterRequisicao.class);
						
						System.out.println(logoutEmpresa.getToken());
						
						try{
							verifica.verify(logoutEmpresa.getToken());
							
							LogoutRecruiterResposta mensagemLogoutEnviada = new LogoutRecruiterResposta(logoutEmpresa.getOperation(), Status.SUCCESS);
							String jsonResposta8 = gson.toJson(mensagemLogoutEnviada);
							System.out.println(jsonResposta8);
							out.println(jsonResposta8);
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(logoutEmpresa.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta8 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta8);
							out.println(jsonResposta8);
						}
						
					break;
					
					case SIGNUP_RECRUITER:
						
						SignUpRecruiterRequisicao signUpEmpresa = gson.fromJson(json, SignUpRecruiterRequisicao.class);
						
						TreeMap<String, String> data9 = (TreeMap<String,String>) signUpEmpresa.getData();
						
						String email9 = data9.get("email");
						String password9 = data9.get("password");
						String name9 = data9.get("name");
						String industry9 = data9.get("industry");
						String description9 = data9.get("description");
						
						System.out.println(email9);
						System.out.println(password9);
						System.out.println(name9);
						System.out.println(industry9);
						System.out.println(description9);
						
						Connection conn9 = BancoDados.conectar();
						Recruiter recruiter9 = new RecruiterDAO(conn9).buscarPorEmail(email9);
		
						
						if(email9.trim().equals("")|| password9.trim().equals("") || name9.trim().equals("") || industry9.trim().equals("") || description9.trim().equals("")) {
							
							RespostaInvalida mensagemNulaEnviada = new RespostaInvalida(signUpEmpresa.getOperation(), Status.INVALID_FIELD);
							String jsonResposta9 = gson.toJson(mensagemNulaEnviada);
							System.out.println(jsonResposta9);
							out.println(jsonResposta9);
						} else {
							
							if(recruiter9 == null)
							{
								Recruiter recruiter99 = new Recruiter();
		        				recruiter99.setEmail(email9);
		        				recruiter99.setPassword(password9);
		        				recruiter99.setName(name9);
		        				recruiter99.setIndustry(industry9);
		        				recruiter99.setDescription(description9);

		        				Connection conn99 = BancoDados.conectar();
		        				new RecruiterDAO(conn99).cadastrar(recruiter99);
		        				
								SignUpRecruiterResposta mensagemSignUpEnviada = new SignUpRecruiterResposta(signUpEmpresa.getOperation(), Status.SUCCESS);
								String jsonResposta9 = gson.toJson(mensagemSignUpEnviada);
								System.out.println(jsonResposta9);
								out.println(jsonResposta9);
								
							} else {
								
								SignUpRecruiterResposta mensagemSignUpEnviada = new SignUpRecruiterResposta(signUpEmpresa.getOperation(), Status.USER_EXISTS);
								String jsonResposta9 = gson.toJson(mensagemSignUpEnviada);
								System.out.println(jsonResposta9);
								out.println(jsonResposta9);
								
							}
						}
						
					break;	
					
					case LOOKUP_ACCOUNT_RECRUITER:
						
						LookUpRecruiterRequisicao lookUpEmpresa = gson.fromJson(json, LookUpRecruiterRequisicao.class);
						
						System.out.println(lookUpEmpresa.getToken());
						
						try {
							verifica.verify(lookUpEmpresa.getToken());
							Map<String, Claim> decoded = JWT.decode(lookUpEmpresa.getToken()).getClaims();
		                    int id10 = decoded.get("id").asInt();

							Connection conn10 = BancoDados.conectar();
							Recruiter recruiter10 = new RecruiterDAO(conn10).buscarPorCodigo(id10);
							
							LookUpRecruiterResposta mensagemLookUpEnviada = new LookUpRecruiterResposta(lookUpEmpresa.getOperation(), Status.SUCCESS, recruiter10.getEmail(), recruiter10.getPassword(), recruiter10.getName(), recruiter10.getIndustry(), recruiter10.getDescription());
							String jsonResposta10 = gson.toJson(mensagemLookUpEnviada);
							System.out.println(jsonResposta10);
							out.println(jsonResposta10);
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(lookUpEmpresa.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta10 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta10);
							out.println(jsonResposta10);
						}
						
					break;	
					
					case UPDATE_ACCOUNT_RECRUITER:
						
						UpdateRecruiterRequisicao updateEmpresa = gson.fromJson(json, UpdateRecruiterRequisicao.class);
						
						System.out.println(updateEmpresa.getToken());
						
						try {
							
							verifica.verify(updateEmpresa.getToken());
							Map<String, Claim> decoded = JWT.decode(updateEmpresa.getToken()).getClaims();
		                    int id11 = decoded.get("id").asInt();
							
							TreeMap<String, String> data11 = (TreeMap<String,String>) updateEmpresa.getData();
							
							String email11 = data11.get("email");
							String password11 = data11.get("password");
							String name11 = data11.get("name");
							String industry11 = data11.get("industry");
							String description11 = data11.get("description");
							
							System.out.println(email11);
							System.out.println(password11);
							System.out.println(name11);
							System.out.println(industry11);
							System.out.println(description11);
							
							Connection conn11 = BancoDados.conectar();
							Recruiter recruiter11 = new RecruiterDAO(conn11).buscarPorEmail(email11);
							
							if(email11.trim().equals("")|| password11.trim().equals("") || name11.trim().equals("") || industry11.trim().equals("") || description11.trim().equals("")) {
								
								RespostaInvalida mensagemNulaEnviada = new RespostaInvalida(updateEmpresa.getOperation(), Status.INVALID_FIELD);
								String jsonResposta11 = gson.toJson(mensagemNulaEnviada);
								System.out.println(jsonResposta11);
								out.println(jsonResposta11);
								
							} else {
								
								if(recruiter11 == null) {
									
									Recruiter recruiter111 = new Recruiter();
									
									recruiter111.setIdRecruiter(id11);
									recruiter111.setEmail(email11);
			        				recruiter111.setPassword(password11);
			        				recruiter111.setName(name11);
			        				recruiter111.setIndustry(industry11);
			        				recruiter111.setDescription(description11);
			        				
			        				Connection conn111 = BancoDados.conectar();
			        				new RecruiterDAO(conn111).atualizar(recruiter111);
									
									UpdateRecruiterResposta mensagemUpdateEnviada = new UpdateRecruiterResposta(updateEmpresa.getOperation(), Status.SUCCESS);
									String jsonResposta11 = gson.toJson(mensagemUpdateEnviada);
									System.out.println(jsonResposta11);
									out.println(jsonResposta11);
									
								} else {
									
									Connection conn111 = BancoDados.conectar();
									Recruiter recruiter111 = new RecruiterDAO(conn111).buscarPorCodigo(id11);
									
									if(email11.equals(recruiter111.getEmail())) {
										
										Recruiter recruiter1111 = new Recruiter();
										
										recruiter1111.setIdRecruiter(id11);
										recruiter1111.setEmail(email11);
				        				recruiter1111.setPassword(password11);
				        				recruiter1111.setName(name11);
				        				recruiter1111.setIndustry(industry11);
				        				recruiter1111.setDescription(description11);
				        				
				        				Connection conn1111 = BancoDados.conectar();
				        				new RecruiterDAO(conn1111).atualizar(recruiter1111);
										
										UpdateRecruiterResposta mensagemUpdateEnviada = new UpdateRecruiterResposta(updateEmpresa.getOperation(), Status.SUCCESS);
										String jsonResposta11 = gson.toJson(mensagemUpdateEnviada);
										System.out.println(jsonResposta11);
										out.println(jsonResposta11);
										
									} else {
										
										UpdateRecruiterResposta mensagemUpdateEnviada = new UpdateRecruiterResposta(updateEmpresa.getOperation(), Status.INVALID_EMAIL);
										String jsonResposta11 = gson.toJson(mensagemUpdateEnviada);
										System.out.println(jsonResposta11);
										out.println(jsonResposta11);
										
									}
								}	
							}
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(updateEmpresa.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta11 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta11);
							out.println(jsonResposta11);
						}
						
					break;	
					
					case DELETE_ACCOUNT_RECRUITER:
						
						DeleteRecruiterRequisicao deleteEmpresa = gson.fromJson(json, DeleteRecruiterRequisicao.class);
						
						System.out.println(deleteEmpresa.getToken());
						
						try {
							
							verifica.verify(deleteEmpresa.getToken());
							Map<String, Claim> decoded = JWT.decode(deleteEmpresa.getToken()).getClaims();
		                    int id12 = decoded.get("id").asInt();
		                    
		                    Connection conn12 = BancoDados.conectar();
							new RecruiterDAO(conn12).excluir(id12);
							
							DeleteRecruiterResposta mensagemDeleteEnviada = new DeleteRecruiterResposta(deleteEmpresa.getOperation(), Status.SUCCESS);
							String jsonResposta12 = gson.toJson(mensagemDeleteEnviada);
							System.out.println(jsonResposta12);
							out.println(jsonResposta12);
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(deleteEmpresa.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta12 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta12);
							out.println(jsonResposta12);
						}
						
					break;	
					
					case INCLUDE_SKILL:
						
						IncludeSkillRequisicao includeCompetencia = gson.fromJson(json, IncludeSkillRequisicao.class);
												
						System.out.println(includeCompetencia.getToken());
						
						try {
							
							verifica.verify(includeCompetencia.getToken());
							Map<String, Claim> decoded = JWT.decode(includeCompetencia.getToken()).getClaims();
		                    int id13 = decoded.get("id").asInt();
		                    
		                    TreeMap<String, String> data13 = (TreeMap<String,String>) includeCompetencia.getData();
							
							String skill13 = data13.get("skill");
							String experience13 = data13.get("experience");
							
							System.out.println(skill13);
							System.out.println(experience13);
							
							Connection conn13 = BancoDados.conectar();
							Skill skillNome13 = new SkillDAO(conn13).buscarPorNome(skill13);
							
							if(skill13.trim().equals("")|| experience13.trim().equals("")) {
								
								RespostaInvalida mensagemNulaEnviada = new RespostaInvalida(includeCompetencia.getOperation(), Status.INVALID_FIELD);
								String jsonResposta13 = gson.toJson(mensagemNulaEnviada);
								System.out.println(jsonResposta13);
								out.println(jsonResposta13);
								
							} else {
								
								if (skillNome13 != null) {
									
									Connection conn = BancoDados.conectar();
									Skillset skillset13 = new SkillsetDAO(conn).buscarEspecifica(id13, skillNome13.getIdSkill());
									
									if(skillset13 != null) {
										
										IncludeSkillResposta mensagemIncludeEnviada = new IncludeSkillResposta(includeCompetencia.getOperation(), Status.SKILL_EXISTS);
										String jsonResposta13 = gson.toJson(mensagemIncludeEnviada);
										System.out.println(jsonResposta13);
										out.println(jsonResposta13);
										
									} else {
										
										Skill skill1313 = new Skill();
										skill1313.setIdSkill(skillNome13.getIdSkill());
										
										Candidate candidate13 = new Candidate();
										candidate13.setIdCandidate(id13);
										
										Skillset skillset1313 = new Skillset();
										skillset1313.setExperience(experience13);
										skillset1313.setSkill(skill1313);
										skillset1313.setCandidate(candidate13);

				        				Connection conn1313 = BancoDados.conectar();
				        				new SkillsetDAO(conn1313).cadastrar(skillset1313);
				        				
										IncludeSkillResposta mensagemIncludeEnviada = new IncludeSkillResposta(includeCompetencia.getOperation(), Status.SUCCESS);
										String jsonResposta13 = gson.toJson(mensagemIncludeEnviada);
										System.out.println(jsonResposta13);
										out.println(jsonResposta13);
										
									}
										
								} else {
									IncludeSkillResposta mensagemIncludeEnviada = new IncludeSkillResposta(includeCompetencia.getOperation(), Status.SKILL_NOT_EXISTS);
									String jsonResposta13 = gson.toJson(mensagemIncludeEnviada);
									System.out.println(jsonResposta13);
									out.println(jsonResposta13);
								}
								
							}
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(includeCompetencia.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta13 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta13);
							out.println(jsonResposta13);
						}
						
						break;
					
					case LOOKUP_SKILL:
						
						LookUpSkillRequisicao lookUpCompetencia = gson.fromJson(json, LookUpSkillRequisicao.class);
						
						System.out.println(lookUpCompetencia.getToken());
						
						try {
							
							verifica.verify(lookUpCompetencia.getToken());
							Map<String, Claim> decoded = JWT.decode(lookUpCompetencia.getToken()).getClaims();
		                    int id14 = decoded.get("id").asInt();
		                    
		                    TreeMap<String, String> data14 = (TreeMap<String,String>) lookUpCompetencia.getData();
							
							String skill14 = data14.get("skill");
							
							System.out.println(skill14);
							
							Connection conn14 = BancoDados.conectar();
							Skill skillNome14 = new SkillDAO(conn14).buscarPorNome(skill14);
							
							if(skill14.trim().equals("")) {
								
								RespostaInvalida mensagemNulaEnviada = new RespostaInvalida(lookUpCompetencia.getOperation(), Status.INVALID_FIELD);
								String jsonResposta14 = gson.toJson(mensagemNulaEnviada);
								System.out.println(jsonResposta14);
								out.println(jsonResposta14);
								
							} else {
								
								if (skillNome14 != null) {
									
									Connection conn1414 = BancoDados.conectar();
									Skillset skillset14 = new SkillsetDAO(conn1414).buscarEspecifica(id14, skillNome14.getIdSkill());
									
									if(skillset14 != null)
									{
										LookUpSkillResposta mensagemLookUpEnviada = new LookUpSkillResposta(lookUpCompetencia.getOperation(), Status.SUCCESS, skill14, skillset14.getExperience(),Integer.toString(skillset14.getSkill().getIdSkill()));
										String jsonResposta14 = gson.toJson(mensagemLookUpEnviada);
										System.out.println(jsonResposta14);
										out.println(jsonResposta14);
										
									} else {
										
										LookUpSkillResposta mensagemLookUpEnviada = new LookUpSkillResposta(lookUpCompetencia.getOperation(), Status.SKILL_NOT_FOUND);
										String jsonResposta14 = gson.toJson(mensagemLookUpEnviada);
										System.out.println(jsonResposta14);
										out.println(jsonResposta14);
										
									}
				        				
								} else {
									LookUpSkillResposta mensagemLookUpEnviada = new LookUpSkillResposta(lookUpCompetencia.getOperation(), Status.SKILL_NOT_FOUND);
									String jsonResposta14 = gson.toJson(mensagemLookUpEnviada);
									System.out.println(jsonResposta14);
									out.println(jsonResposta14);
								}
								
							}
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(lookUpCompetencia.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta14 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta14);
							out.println(jsonResposta14);
						}
						
					break;
					
					case UPDATE_SKILL:
						
						UpdateSkillRequisicao updateCompetencia = gson.fromJson(json, UpdateSkillRequisicao.class);
						
						System.out.println(updateCompetencia.getToken());
						
						try {
							
							verifica.verify(updateCompetencia.getToken());
							Map<String, Claim> decoded = JWT.decode(updateCompetencia.getToken()).getClaims();
		                    int id15 = decoded.get("id").asInt();
		                    
		                    TreeMap<String, String> data15 = (TreeMap<String,String>) updateCompetencia.getData();
							
							String skill15 = data15.get("skill");
							String experience15 = data15.get("experience");
							String newSkill15 = data15.get("newSkill");
							
							System.out.println(skill15);
							System.out.println(experience15);
							System.out.println(newSkill15);
							
							Connection conn15 = BancoDados.conectar();
							Skill skillNome15 = new SkillDAO(conn15).buscarPorNome(skill15);
							
							Connection conn155 = BancoDados.conectar();
							Skill newSkillNome15 = new SkillDAO(conn155).buscarPorNome(newSkill15);
							
							
							
							if(skill15.trim().equals("")|| experience15.trim().equals("") || newSkill15.trim().equals("")) {
								
								RespostaInvalida mensagemNulaEnviada = new RespostaInvalida(updateCompetencia.getOperation(), Status.INVALID_FIELD);
								String jsonResposta15 = gson.toJson(mensagemNulaEnviada);
								System.out.println(jsonResposta15);
								out.println(jsonResposta15);
								
							} else {
								
								if (skillNome15 != null && newSkillNome15 != null) {
									
									Connection conn1515 = BancoDados.conectar();
									Skillset skillset15 = new SkillsetDAO(conn1515).buscarEspecifica(id15, skillNome15.getIdSkill());
									
									Connection conn15155 = BancoDados.conectar();
									Skillset newSkillset15 = new SkillsetDAO(conn15155).buscarEspecifica(id15, newSkillNome15.getIdSkill());
									
									
									if(skillset15 != null) {
										
										if(skill15.equals(newSkill15)) {

											Connection conn151515 = BancoDados.conectar();
											new SkillsetDAO(conn151515).atualizar(newSkillNome15.getIdSkill(), experience15, id15, skillNome15.getIdSkill());
											
											UpdateSkillResposta mensagemUpdateEnviada = new UpdateSkillResposta(updateCompetencia.getOperation(), Status.SUCCESS);
											String jsonResposta15 = gson.toJson(mensagemUpdateEnviada);
											System.out.println(jsonResposta15);
											out.println(jsonResposta15);
											
										} else {
											
											if(newSkillset15 != null) {
												
												UpdateSkillResposta mensagemUpdateEnviada = new UpdateSkillResposta(updateCompetencia.getOperation(), Status.SKILL_EXISTS);
												String jsonResposta15 = gson.toJson(mensagemUpdateEnviada);
												System.out.println(jsonResposta15);
												out.println(jsonResposta15);
												
											} else {
												
												Connection conn151515 = BancoDados.conectar();
												new SkillsetDAO(conn151515).atualizar(newSkillNome15.getIdSkill(), experience15, id15, skillNome15.getIdSkill());
												
												UpdateSkillResposta mensagemUpdateEnviada = new UpdateSkillResposta(updateCompetencia.getOperation(), Status.SUCCESS);
												String jsonResposta15 = gson.toJson(mensagemUpdateEnviada);
												System.out.println(jsonResposta15);
												out.println(jsonResposta15);
											}

										}
										
									} else {
										
										UpdateSkillResposta mensagemUpdateEnviada = new UpdateSkillResposta(updateCompetencia.getOperation(), Status.SKILL_NOT_FOUND);
										String jsonResposta15 = gson.toJson(mensagemUpdateEnviada);
										System.out.println(jsonResposta15);
										out.println(jsonResposta15);
									}
										
								} else {
									
									UpdateSkillResposta mensagemUpdateEnviada = new UpdateSkillResposta(updateCompetencia.getOperation(), Status.SKILL_NOT_FOUND);
									String jsonResposta15 = gson.toJson(mensagemUpdateEnviada);
									System.out.println(jsonResposta15);
									out.println(jsonResposta15);
								}
								
							}
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(updateCompetencia.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta15 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta15);
							out.println(jsonResposta15);
						}
						
						break;
					
					case DELETE_SKILL:
						
						DeleteSkillRequisicao deleteCompetencia = gson.fromJson(json, DeleteSkillRequisicao.class);
						
						System.out.println(deleteCompetencia.getToken());
						
						try {
							
							verifica.verify(deleteCompetencia.getToken());
							Map<String, Claim> decoded = JWT.decode(deleteCompetencia.getToken()).getClaims();
		                    int id16 = decoded.get("id").asInt();
		                    
		                    TreeMap<String, String> data16 = (TreeMap<String,String>) deleteCompetencia.getData();
							
							String skill16 = data16.get("skill");
							
							System.out.println(skill16);
							
							Connection conn16 = BancoDados.conectar();
							Skill skillNome16 = new SkillDAO(conn16).buscarPorNome(skill16);
							
							if(skill16.trim().equals("")) {
								
								RespostaInvalida mensagemNulaEnviada = new RespostaInvalida(deleteCompetencia.getOperation(), Status.INVALID_FIELD);
								String jsonResposta16 = gson.toJson(mensagemNulaEnviada);
								System.out.println(jsonResposta16);
								out.println(jsonResposta16);
								
							} else {
								
								if (skillNome16 != null) {
									
									Connection conn1616 = BancoDados.conectar();
									int skillset16 = new SkillsetDAO(conn1616).excluir(id16, skillNome16.getIdSkill());
									
									if(skillset16 > 0)
									{
										
										DeleteSkillResposta mensagemDeleteEnviada = new DeleteSkillResposta(deleteCompetencia.getOperation(), Status.SUCCESS);
										String jsonResposta16 = gson.toJson(mensagemDeleteEnviada);
										System.out.println(jsonResposta16);
										out.println(jsonResposta16);
										
									} else {
										
										DeleteSkillResposta mensagemDeleteEnviada = new DeleteSkillResposta(deleteCompetencia.getOperation(), Status.SKILL_NOT_FOUND);
										String jsonResposta16 = gson.toJson(mensagemDeleteEnviada);
										System.out.println(jsonResposta16);
										out.println(jsonResposta16);
										
									}
				        				
								} else {
									DeleteSkillResposta mensagemDeleteEnviada = new DeleteSkillResposta(deleteCompetencia.getOperation(), Status.SKILL_NOT_FOUND);
									String jsonResposta16 = gson.toJson(mensagemDeleteEnviada);
									System.out.println(jsonResposta16);
									out.println(jsonResposta16);
								}
								
							}
							
						} catch(Exception e) {
							
							RespostaInvalida mensagemTokenEnviada = new RespostaInvalida(deleteCompetencia.getOperation(), Status.INVALID_TOKEN);
							String jsonResposta16 = gson.toJson(mensagemTokenEnviada);
							System.out.println(jsonResposta16);
							out.println(jsonResposta16);
						}
						
					break;	
						
					case NAO_EXISTE:
						
						RequisicaoInvalida invalida1 = gson.fromJson(json,  RequisicaoInvalida.class);
						RespostaInvalida mensagemInvalidaEnviada1 = new RespostaInvalida(invalida1.getOperation(), Status.INVALID_OPERATION);
						String jsonResposta14 = gson.toJson(mensagemInvalidaEnviada1);
						System.out.println(jsonResposta14);
						out.println(jsonResposta14);
					
					default:
						
						/*RequisicaoInvalida invalida2 = gson.fromJson(json,  RequisicaoInvalida.class);
						RespostaInvalida mensagemInvalidaEnviada2 = new RespostaInvalida(invalida2.getOperation(), Status.INVALID_OPERATION);
						String jsonResposta15 = gson.toJson(mensagemInvalidaEnviada2);
						System.out.println(jsonResposta15);
						out.println(jsonResposta15);*/
						
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