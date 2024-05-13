package cliente;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import enumerations.Operacoes;
import gui.ConexaoWindow;
import gui.LoginCandidateWindow;
import gui.SignUpCandidateWindow;
import operacoes.DeleteCandidateRequisicao;
import operacoes.DeleteCandidateResposta;
import operacoes.DeleteRecruiterRequisicao;
import operacoes.DeleteRecruiterResposta;
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

public class Cliente {
	
	
	public static void main(String[] args) throws IOException, SQLException {
        
		/*ConexaoWindow conexao = new ConexaoWindow();
        conexao.setVisible(true);
        
        String serverHost = null;
        while (serverHost == null) {
            serverHost = conexao.getServerIp();
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
		
		InetAddress serverHost = InetAddress.getLocalHost();
		int serverPort = 21234;
		
		/*System.out.println("Qual o IP do servidor?");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String serverHost = br.readLine();

		System.out.println("Qual a porta do servidor?");
		br = new BufferedReader(new InputStreamReader(System.in));
		int serverPort = Integer.parseInt(br.readLine());*/

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
        
        System.out.println("1- Login candidato\n2- Logout candidato\n3- SignUp candidato\n4- LookUp candidato\n5- Update candidato\n6- Delete candidato\n");
        System.out.println("7- Login empresa\n8- Logout empresa\n9- SignUp empresa\n10- LookUp empresa\n11- Update empresa\n12- Delete empresa\n13- Finalizar\n");
        System.out.print("Digite a opcao: ");
        
        String token = null;
        String token1 = null;
        
        while ((userInput = stdIn.readLine()) != null) {
        		
        		switch(userInput){
        			case "1":
        				
        				System.out.print("Digite o email: ");
        				String email1 = stdIn.readLine();
                	
        				System.out.print("Digite a senha: ");
        				String password1 = stdIn.readLine();
        				
        				/*LoginCandidateWindow loginWindow = new LoginCandidateWindow();
        		        loginWindow.setVisible(true);
        				
        				String email1 = null;
        				String password1 = null;
        		        while (email1 == null && password1 == null) {
        		            email1 = loginWindow.getEmail();
        		            password1 = loginWindow.getPassword();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }*/
        				
        				LoginCandidateRequisicao loginCandidatoRequisicao = new LoginCandidateRequisicao(Operacoes.LOGIN_CANDIDATE, email1, password1);
        				
        				String jsonRequisicaoCandidatoLogin = gson.toJson(loginCandidatoRequisicao);
        				System.out.println("Requisição enviada" + jsonRequisicaoCandidatoLogin);
        				out.println(jsonRequisicaoCandidatoLogin);
                    
        				LoginCandidateResposta loginCandidatoResposta = gson.fromJson(in.readLine(), LoginCandidateResposta.class);
        				String jsonRespostaCandidatoLogin = gson.toJson(loginCandidatoResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaCandidatoLogin);
        				token = loginCandidatoResposta.getData().get("token"); 
        				
        				System.out.print("Nova opção: ");
                    
        			break;
                
        			case "2": 
        				
        				LogoutCandidateRequisicao logoutCandidatoRequisicao = new LogoutCandidateRequisicao(Operacoes.LOGOUT_CANDIDATE, token);
        			
        				String jsonRequisicaoCandidatoLogout = gson.toJson(logoutCandidatoRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoCandidatoLogout);
        				out.println(jsonRequisicaoCandidatoLogout);
                    
        				LogoutCandidateResposta logoutCandidatoResposta = gson.fromJson(in.readLine(), LogoutCandidateResposta.class);
        				String jsonRespostaCandidatoLogout = gson.toJson(logoutCandidatoResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaCandidatoLogout);
        				token = null;
        				
        				System.out.print("Nova opção: ");
        			break;
                
        			case "3":
        				System.out.print("Digite o email: ");
        				String email3 = stdIn.readLine();
                	
        				System.out.print("Digite a senha: ");
        				String password3 = stdIn.readLine();
                	
        				System.out.print("Digite o nome: ");
        				String name3 = stdIn.readLine();
        				
        				/*SignUpCandidateWindow signUpWindow = new SignUpCandidateWindow();
        		        signUpWindow.setVisible(true);
        				
        				String email3 = null;
        				String password3 = null;
        				String name3 = null;
        		        while (email3 == null && password3 == null && name3 == null ) {
        		            email3 = signUpWindow.getEmail();
        		            password3 = signUpWindow.getPassword();
        		            name3 = signUpWindow.getName();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }*/
                	
        				SignUpCandidateRequisicao signUpCandidatoRequisicao = new SignUpCandidateRequisicao(Operacoes.SIGNUP_CANDIDATE, email3, password3, name3);
        			
        				String jsonRequisicaoCandidatoSignUp = gson.toJson(signUpCandidatoRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoCandidatoSignUp);
        				out.println(jsonRequisicaoCandidatoSignUp);
                    
        				SignUpCandidateResposta signUpCandidatoResposta = gson.fromJson(in.readLine(), SignUpCandidateResposta.class);
        				String jsonRespostaCandidatoSignUp = gson.toJson(signUpCandidatoResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaCandidatoSignUp);
        				
        				System.out.print("Nova opção: ");
        			break; 
                 
        			case "4":
        				
        				LookUpCandidateRequisicao lookUpCandidatoRequisicao = new LookUpCandidateRequisicao(Operacoes.LOOKUP_ACCOUNT_CANDIDATE, token);
        				
        				String jsonRequisicaoCandidatoLookUp = gson.toJson(lookUpCandidatoRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoCandidatoLookUp);
        				out.println(jsonRequisicaoCandidatoLookUp);
                    
        				LookUpCandidateResposta lookUpCandidatoResposta = gson.fromJson(in.readLine(), LookUpCandidateResposta.class);
        				String jsonRespostaCandidatoLookUp = gson.toJson(lookUpCandidatoResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaCandidatoLookUp);
        				
        				System.out.print("Nova opção: ");
        			break;
                 
        			case "5":
        				
        				System.out.print("Digite o email: ");
        				String email5 = stdIn.readLine();
                	
        				System.out.print("Digite a senha: ");
        				String password5 = stdIn.readLine();
                	
        				System.out.print("Digite o nome: ");
        				String name5 = stdIn.readLine();
                	
        				UpdateCandidateRequisicao updateCandidatoRequisicao = new UpdateCandidateRequisicao(Operacoes.UPDATE_ACCOUNT_CANDIDATE, token, email5, password5, name5);
        			
        				String jsonRequisicaoCandidatoUpdate = gson.toJson(updateCandidatoRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoCandidatoUpdate);
        				out.println(jsonRequisicaoCandidatoUpdate);
                    
        				UpdateCandidateResposta updateCandidatoResposta = gson.fromJson(in.readLine(), UpdateCandidateResposta.class);
        				String jsonRespostaCandidatoUpdate = gson.toJson(updateCandidatoResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaCandidatoUpdate);
        				
        				System.out.print("Nova opção: ");
        				
        			break;
                 
        			case "6":
        				
        				DeleteCandidateRequisicao deleteCandidatoRequisicao = new DeleteCandidateRequisicao(Operacoes.DELETE_ACCOUNT_CANDIDATE, token);
                	
        				String jsonRequisicaoCandidatoDelete = gson.toJson(deleteCandidatoRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoCandidatoDelete);
        				out.println(jsonRequisicaoCandidatoDelete);
                    
        				DeleteCandidateResposta deleteCandidatoResposta = gson.fromJson(in.readLine(), DeleteCandidateResposta.class);
        				String jsonRespostaCandidatoDelete = gson.toJson(deleteCandidatoResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaCandidatoDelete);
        				//token = null;
        				
        				System.out.print("Nova opção: ");
        			break;
        			
        			case "7":
        				
        				System.out.print("Digite o email: ");
        				String email7 = stdIn.readLine();
                	
        				System.out.print("Digite a senha: ");
        				String password7 = stdIn.readLine();
        				
        				/*LoginCandidateWindow loginWindow = new LoginCandidateWindow();
        		        loginWindow.setVisible(true);
        				
        				String email1 = null;
        				String password1 = null;
        		        while (email1 == null && password1 == null) {
        		            email1 = loginWindow.getEmail();
        		            password1 = loginWindow.getPassword();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }*/
        				
        				LoginRecruiterRequisicao loginEmpresaRequisicao = new LoginRecruiterRequisicao(Operacoes.LOGIN_RECRUITER, email7, password7);
        				
        				String jsonRequisicaoEmpresaLogin = gson.toJson(loginEmpresaRequisicao);
        				System.out.println("Requisição enviada" + jsonRequisicaoEmpresaLogin);
        				out.println(jsonRequisicaoEmpresaLogin);
                    
        				LoginRecruiterResposta loginEmpresaResposta = gson.fromJson(in.readLine(), LoginRecruiterResposta.class);
        				String jsonRespostaEmpresaLogin = gson.toJson(loginEmpresaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaEmpresaLogin);
        				token1 = loginEmpresaResposta.getData().get("token"); 
        				
        				System.out.print("Nova opção: ");
        				
        			break;
        			
        			case "8": 
        				
        				LogoutRecruiterRequisicao logoutEmpresaRequisicao = new LogoutRecruiterRequisicao(Operacoes.LOGOUT_RECRUITER, token1);
            			
        				String jsonRequisicaoEmpresaLogout = gson.toJson(logoutEmpresaRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoEmpresaLogout);
        				out.println(jsonRequisicaoEmpresaLogout);
                    
        				LogoutRecruiterResposta logoutEmpresaResposta = gson.fromJson(in.readLine(), LogoutRecruiterResposta.class);
        				String jsonRespostaEmpresaLogout = gson.toJson(logoutEmpresaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaEmpresaLogout);
        				token1 = null;
        				
        				System.out.print("Nova opção: ");
        				
        			break;	
        			
        			case "9":
        				
        				System.out.print("Digite o email: ");
        				String email9 = stdIn.readLine();
                	
        				System.out.print("Digite a senha: ");
        				String password9 = stdIn.readLine();
                	
        				System.out.print("Digite o nome: ");
        				String name9 = stdIn.readLine();
        				
        				System.out.print("Digite a indústria: ");
        				String industry9 = stdIn.readLine();
        				
        				System.out.print("Digite a descrição: ");
        				String description9 = stdIn.readLine();
        				
        				/*SignUpCandidateWindow signUpWindow = new SignUpCandidateWindow();
        		        signUpWindow.setVisible(true);
        				
        				String email3 = null;
        				String password3 = null;
        				String name3 = null;
        		        while (email3 == null && password3 == null && name3 == null ) {
        		            email3 = signUpWindow.getEmail();
        		            password3 = signUpWindow.getPassword();
        		            name3 = signUpWindow.getName();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }*/
                	
        				SignUpRecruiterRequisicao signUpEmpresaRequisicao = new SignUpRecruiterRequisicao(Operacoes.SIGNUP_RECRUITER, email9, password9, name9, industry9, description9);
        			
        				String jsonRequisicaoEmpresaSignUp = gson.toJson(signUpEmpresaRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoEmpresaSignUp);
        				out.println(jsonRequisicaoEmpresaSignUp);
                    
        				SignUpRecruiterResposta signUpEmpresaResposta = gson.fromJson(in.readLine(), SignUpRecruiterResposta.class);
        				String jsonRespostaEmpresaSignUp = gson.toJson(signUpEmpresaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaEmpresaSignUp);
        				
        				System.out.print("Nova opção: ");
        				
        			break;	
        			
        			case "10": 
        				
        				LookUpRecruiterRequisicao lookUpEmpresaRequisicao = new LookUpRecruiterRequisicao(Operacoes.LOOKUP_ACCOUNT_RECRUITER, token1);
        				
        				String jsonRequisicaoEmpresaLookUp = gson.toJson(lookUpEmpresaRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoEmpresaLookUp);
        				out.println(jsonRequisicaoEmpresaLookUp);
                    
        				LookUpCandidateResposta lookUpEmpresaResposta = gson.fromJson(in.readLine(), LookUpCandidateResposta.class);
        				String jsonRespostaEmpresaLookUp = gson.toJson(lookUpEmpresaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaEmpresaLookUp);
        				
        				System.out.print("Nova opção: ");
        				
        			break;
        			
        			case "11":
        				
        				System.out.print("Digite o email: ");
        				String email11 = stdIn.readLine();
                	
        				System.out.print("Digite a senha: ");
        				String password11 = stdIn.readLine();
                	
        				System.out.print("Digite o nome: ");
        				String name11 = stdIn.readLine();
        				
        				System.out.print("Digite a indústria: ");
        				String industry11 = stdIn.readLine();
        				
        				System.out.print("Digite a descrição: ");
        				String description11 = stdIn.readLine();
                	
        				UpdateRecruiterRequisicao updateEmpresaRequisicao = new UpdateRecruiterRequisicao(Operacoes.UPDATE_ACCOUNT_RECRUITER, token1, email11, password11, name11, industry11, description11);
        			
        				String jsonRequisicaoEmpresaUpdate = gson.toJson(updateEmpresaRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoEmpresaUpdate);
        				out.println(jsonRequisicaoEmpresaUpdate);
                    
        				UpdateRecruiterResposta updateEmpresaResposta = gson.fromJson(in.readLine(), UpdateRecruiterResposta.class);
        				String jsonRespostaEmpresaUpdate = gson.toJson(updateEmpresaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaEmpresaUpdate);
        				
        				System.out.print("Nova opção: ");
        				
        			break;
        			
        			case "12":
        				
        				DeleteRecruiterRequisicao deleteEmpresaRequisicao = new DeleteRecruiterRequisicao(Operacoes.DELETE_ACCOUNT_RECRUITER, token1);
                    	
        				String jsonRequisicaoEmpresaDelete = gson.toJson(deleteEmpresaRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoEmpresaDelete);
        				out.println(jsonRequisicaoEmpresaDelete);
                    
        				DeleteRecruiterResposta deleteEmpresaResposta = gson.fromJson(in.readLine(), DeleteRecruiterResposta.class);
        				String jsonRespostaEmpresaDelete = gson.toJson(deleteEmpresaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaEmpresaDelete);
        				//token1 = null;
        				
        				System.out.print("Nova opção: ");
        				
        			break;	
        					
        			case "13":
        				return;
        			
        			default:
        				
        				RequisicaoInvalida invalidaRequisicao = new RequisicaoInvalida(Operacoes.NAO_EXISTE);
        				
        				String jsonRequisicaoInvalida = gson.toJson(invalidaRequisicao);
        				System.out.println("Requisição enviada" + jsonRequisicaoInvalida);
        				out.println(jsonRequisicaoInvalida);
        				
        				RespostaInvalida invalidaResposta = gson.fromJson(in.readLine(), RespostaInvalida.class);
        				String jsonRespostaInvalida = gson.toJson(invalidaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaInvalida);
        				
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