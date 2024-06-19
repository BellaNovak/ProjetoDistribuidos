package cliente;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import enumerations.Operacoes;
import gui.ConexaoWindow;
import gui.LoginCandidateWindow;
import gui.OpcaoAreaWindow;
import gui.OpcoesCandidateWindow;
import gui.OpcoesRecruiterWindow;
import gui.SignUpCandidateWindow;
import gui.UpdateCandidateWindow;
import operacoes.DeleteCandidateRequisicao;
import operacoes.DeleteCandidateResposta;
import operacoes.DeleteJobRequisicao;
import operacoes.DeleteJobResposta;
import operacoes.DeleteRecruiterRequisicao;
import operacoes.DeleteRecruiterResposta;
import operacoes.DeleteSkillRequisicao;
import operacoes.DeleteSkillResposta;
import operacoes.IncludeJobRequisicao;
import operacoes.IncludeJobResposta;
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
import operacoes.LookUpJobRequisicao;
import operacoes.LookUpJobResposta;
import operacoes.LookUpJobsetRequisicao;
import operacoes.LookUpJobsetResposta;
import operacoes.LookUpRecruiterRequisicao;
import operacoes.LookUpSkillRequisicao;
import operacoes.LookUpSkillResposta;
import operacoes.LookUpSkillsetRequisicao;
import operacoes.LookUpSkillsetResposta;
import operacoes.RequisicaoInvalida;
import operacoes.RespostaInvalida;
import operacoes.SearchJobRequisicao;
import operacoes.SearchJobResposta;
import operacoes.SignUpCandidateRequisicao;
import operacoes.SignUpCandidateResposta;
import operacoes.SignUpRecruiterRequisicao;
import operacoes.SignUpRecruiterResposta;
import operacoes.UpdateCandidateRequisicao;
import operacoes.UpdateCandidateResposta;
import operacoes.UpdateJobRequisicao;
import operacoes.UpdateJobResposta;
import operacoes.UpdateRecruiterRequisicao;
import operacoes.UpdateRecruiterResposta;
import operacoes.UpdateSkillRequisicao;
import operacoes.UpdateSkillResposta;

public class Cliente {
	
	
	public static void main(String[] args) throws IOException, SQLException {
        
		/*ConexaoWindow conexao = new ConexaoWindow();
        conexao.setVisible(true);
        
        String serverHost = null;
        String porta = null;
        while (serverHost == null && porta == null) {
            serverHost = conexao.getServerIp();
            porta = conexao.getServerPorta();
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        int serverPort = Integer.parseInt(porta);*/
		
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
        
        System.out.println("1- Login candidato\n2- Logout candidato\n3- SignUp candidato\n4- LookUp candidato\n5- Update candidato\n6- Delete candidato");
        System.out.println("7- Login empresa\n8- Logout empresa\n9- SignUp empresa\n10- LookUp empresa\n11- Update empresa\n12- Delete empresa");
        System.out.println("13- Include competência\n14- LookUp competência\n15- Update competência\n16- Delete competência");
        System.out.println("17- Include vaga\n18- LookUp vaga\n19- Update vaga\n20- Delete vaga");
        System.out.println("21- LookUp skillset\n22- LookUp jobset\n23- Search job competência\n24- Search job experiência\n25- Search job filtro\n26- Finalizar\n");
        System.out.print("Digite a opcao: ");
        
        String token = null;
        String token1 = null;
        
        /*OpcaoAreaWindow opcaoAreaWindow = new OpcaoAreaWindow();
        opcaoAreaWindow.setVisible(true);
        
        String opArea = null;
        while (opArea == null) {
            opArea = opcaoAreaWindow.getOpFinal();
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        String op = null;
        if(opArea.equals("1")) {
        	
        	OpcoesCandidateWindow opcaoCandidatoWindow = new OpcoesCandidateWindow();
            opcaoCandidatoWindow.setVisible(true);
            
            while (op == null) {
                op = opcaoCandidatoWindow.getOpFinal();
                try {
                    Thread.sleep(100); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
        } else {
        	
        	OpcoesRecruiterWindow opcaoEmpresaWindow = new OpcoesRecruiterWindow();
            opcaoEmpresaWindow.setVisible(true);
            
            while (op == null) {
                op = opcaoEmpresaWindow.getOpFinal();
                try {
                    Thread.sleep(100); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
        }
        
        userInput = op;*/
        
        while ((userInput = stdIn.readLine()) != null) {
        		
        		switch(userInput){
        			case "1":
        				
        				System.out.print("Digite o email: ");
        				String email1 = stdIn.readLine();
                	
        				System.out.print("Digite a senha: ");
        				String password1 = stdIn.readLine();
        				
        				/*LoginCandidateWindow loginCandidatoWindow = new LoginCandidateWindow();
        		        loginCandidatoWindow.setVisible(true);
        				
        				String email1 = null;
        				String password1 = null;
        		        while (email1 == null && password1 == null) {
        		            email1 = loginCandidatoWindow.getEmail();
        		            password1 = loginCandidatoWindow.getPassword();
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
        				
        				/*OpcoesCandidateWindow opcaoCandidatoWindow1 = new OpcoesCandidateWindow();
        		        opcaoCandidatoWindow1.setVisible(true);
        		        
        		        String op1 = null;
        		        while (op1 == null) {
        		            op1 = opcaoCandidatoWindow1.getOpFinal();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }
        		        
        		        userInput = op1;*/
                    
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
        				
        				/*OpcaoAreaWindow opcaoAreaWindow2 = new OpcaoAreaWindow();
        		        opcaoAreaWindow2.setVisible(true);
        		        
        		        String opArea2 = null;
        		        while (opArea2 == null) {
        		            opArea2 = opcaoAreaWindow2.getOpFinal();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }
        		        
        		        String op2 = null;
        		        if(opArea.equals("1")) {
        		        	
        		        	OpcoesCandidateWindow opcaoCandidatoWindow2 = new OpcoesCandidateWindow();
        		            opcaoCandidatoWindow2.setVisible(true);
        		            
        		            while (op2 == null) {
        		                op2 = opcaoCandidatoWindow2.getOpFinal();
        		                try {
        		                    Thread.sleep(100); 
        		                } catch (InterruptedException e) {
        		                    e.printStackTrace();
        		                }
        		            }
        		            
        		        } else {
        		        	
        		        	OpcoesRecruiterWindow opcaoEmpresaWindow2 = new OpcoesRecruiterWindow();
        		            opcaoEmpresaWindow2.setVisible(true);
        		            
        		            while (op2 == null) {
        		                op2 = opcaoEmpresaWindow2.getOpFinal();
        		                try {
        		                    Thread.sleep(100); 
        		                } catch (InterruptedException e) {
        		                    e.printStackTrace();
        		                }
        		            }
        		            
        		        }
        		        
        		        userInput = op2;*/
        			break;
                
        			case "3":
        				
        				System.out.print("Digite o email: ");
        				String email3 = stdIn.readLine();
                	
        				System.out.print("Digite a senha: ");
        				String password3 = stdIn.readLine();
                	
        				System.out.print("Digite o nome: ");
        				String name3 = stdIn.readLine();
        				
        				/*SignUpCandidateWindow signUpCandidatoWindow = new SignUpCandidateWindow();
        		        signUpCandidatoWindow.setVisible(true);
        				
        				String email3 = null;
        				String password3 = null;
        				String name3 = null;
        		        while (email3 == null && password3 == null && name3 == null ) {
        		            email3 = signUpCandidatoWindow.getEmail();
        		            password3 = signUpCandidatoWindow.getPassword();
        		            name3 = signUpCandidatoWindow.getName();
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
        				
        				/*OpcoesCandidateWindow opcaoCandidatoWindow3 = new OpcoesCandidateWindow();
        		        opcaoCandidatoWindow3.setVisible(true);
        		        
        		        String op3 = null;
        		        while (op3 == null) {
        		            op3 = opcaoCandidatoWindow3.getOpFinal();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }
        		        
        		        userInput = op3;*/
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
        				
        				/*OpcoesCandidateWindow opcaoCandidatoWindow4 = new OpcoesCandidateWindow();
        		        opcaoCandidatoWindow4.setVisible(true);
        		        
        		        String op4 = null;
        		        while (op4 == null) {
        		            op4 = opcaoCandidatoWindow4.getOpFinal();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }
        		        
        		        userInput = op4;*/
        			break;
                 
        			case "5":
        				
        				System.out.print("Digite o email: ");
        				String email5 = stdIn.readLine();
                	
        				System.out.print("Digite a senha: ");
        				String password5 = stdIn.readLine();
                	
        				System.out.print("Digite o nome: ");
        				String name5 = stdIn.readLine();
        				
        				/*UpdateCandidateWindow updateCandidatoWindow = new UpdateCandidateWindow();
        		        updateCandidatoWindow.setVisible(true);
        				
        				String email5 = null;
        				String password5 = null;
        				String name5 = null;
        		        while (email5 == null && password5 == null && name5 == null ) {
        		            email5 = updateCandidatoWindow.getEmail();
        		            password5 = updateCandidatoWindow.getPassword();
        		            name5 = updateCandidatoWindow.getName();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }*/
                	
        				UpdateCandidateRequisicao updateCandidatoRequisicao = new UpdateCandidateRequisicao(Operacoes.UPDATE_ACCOUNT_CANDIDATE, token, email5, password5, name5);
        			
        				String jsonRequisicaoCandidatoUpdate = gson.toJson(updateCandidatoRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoCandidatoUpdate);
        				out.println(jsonRequisicaoCandidatoUpdate);
                    
        				UpdateCandidateResposta updateCandidatoResposta = gson.fromJson(in.readLine(), UpdateCandidateResposta.class);
        				String jsonRespostaCandidatoUpdate = gson.toJson(updateCandidatoResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaCandidatoUpdate);
        				
        				System.out.print("Nova opção: ");
        				
        				/*OpcoesCandidateWindow opcaoCandidatoWindow5 = new OpcoesCandidateWindow();
        		        opcaoCandidatoWindow1.setVisible(true);
        		        
        		        String op5 = null;
        		        while (op5 == null) {
        		            op5 = opcaoCandidatoWindow5.getOpFinal();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }
        		        
        		        userInput = op5;*/
        				
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
        				
        				/*OpcoesCandidateWindow opcaoCandidatoWindow6 = new OpcoesCandidateWindow();
        		        opcaoCandidatoWindow6.setVisible(true);
        		        
        		        String op6 = null;
        		        while (op6 == null) {
        		            op6 = opcaoCandidatoWindow6.getOpFinal();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }
        		        
        		        userInput = op6;*/
        			break;
        			
        			case "7":
        				
        				System.out.print("Digite o email: ");
        				String email7 = stdIn.readLine();
                	
        				System.out.print("Digite a senha: ");
        				String password7 = stdIn.readLine();
        				
        				/*LoginRecruiterWindow loginEmpresaWindow = new LoginRecruiterWindow();
        		        loginEmpresaWindow.setVisible(true);
        				
        				String email7 = null;
        				String password7 = null;
        		        while (email7 == null && password7 == null) {
        		            email7 = loginEmpresaWindow.getEmail();
        		            password7 = loginEmpresaWindow.getPassword();
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
        				
        				/*OpcoesRecruiterWindow opcaoEmpresaWindow7 = new OpcoesRecruiterWindow();
        		        opcaoEmpresaWindow7.setVisible(true);
        		        
        		        String op7 = null;
        		        while (op7 == null) {
        		            op7 = opcaoEmpresaWindow7.getOpFinal();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }
        		        
        		        userInput = op7;*/
        				
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
        				
        				/*OpcaoAreaWindow opcaoAreaWindow8 = new OpcaoAreaWindow();
        		        opcaoAreaWindow8.setVisible(true);
        		        
        		        String opArea8 = null;
        		        while (opArea8 == null) {
        		            opArea8 = opcaoAreaWindow2.getOpFinal();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }
        		        
        		        String op8 = null;
        		        if(opArea.equals("1")) {
        		        	
        		        	OpcoesCandidateWindow opcaoCandidatoWindow8 = new OpcoesCandidateWindow();
        		            opcaoCandidatoWindow8.setVisible(true);
        		            
        		            while (op8 == null) {
        		                op8 = opcaoCandidatoWindow8.getOpFinal();
        		                try {
        		                    Thread.sleep(100); 
        		                } catch (InterruptedException e) {
        		                    e.printStackTrace();
        		                }
        		            }
        		            
        		        } else {
        		        	
        		        	OpcoesRecruiterWindow opcaoEmpresaWindow8 = new OpcoesRecruiterWindow();
        		            opcaoEmpresaWindow8.setVisible(true);
        		            
        		            while (op8 == null) {
        		                op8 = opcaoEmpresaWindow8.getOpFinal();
        		                try {
        		                    Thread.sleep(100); 
        		                } catch (InterruptedException e) {
        		                    e.printStackTrace();
        		                }
        		            }
        		            
        		        }
        		        
        		        userInput = op8;*/
        				
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
        				
        				/*SignUpRecruiterWindow signUpEmpresaWindow = new SignUpRecruiterWindow();
        		        signUpEmpresaWindow.setVisible(true);
        				
        				String email9 = null;
        				String password9 = null;
        				String name9 = null;
        				String industry9 = null;
        				String description9 = null;
        		        while (email9 == null && password9 == null && name9 == null && industry9 == null && description9 == null) {
        		            email9 = signUpEmpresaWindow.getEmail();
        		            password9 = signUpEmpresaWindow.getPassword();
        		            name9 = signUpEmpresaWindow.getName();
        		            industry9 = signUpEmpresaWindow.getIndustry();
        		            description9 = signUpEmpresaWindow.gerDescription();
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
        				
        				/*OpcoesRecruiterWindow opcaoEmpresaWindow9 = new OpcoesCandidateWindow();
        		        opcaoEmpresaWindow9.setVisible(true);
        		        
        		        String op9 = null;
        		        while (op9 == null) {
        		            op9 = opcaoEmpresaWindow9.getOpFinal();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }
        		        
        		        userInput = op9;*/
        				
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
        				
        				/*OpcoesRecruiterWindow opcaoEmpresaWindow10 = new OpcoesCandidateWindow();
        		        opcaoEmpresaWindow10.setVisible(true);
        		        
        		        String op10 = null;
        		        while (op10 == null) {
        		            op10 = opcaoEmpresaWindow10.getOpFinal();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }
        		        
        		        userInput = op10;*/
        				
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
        				
        				/*UpdateRecruiterWindow updateEmpresaWindow = new UpdateRecruiterWindow();
        		        updateEmpresaWindow.setVisible(true);
        				
        				String email11 = null;
        				String password11 = null;
        				String name11 = null;
        		        String industry11 = null;
        				String description11 = null;
        		        while (email11 == null && password11 == null && name11 == null && industry11 == null && description11 == null) {
        		            email11 = updateEmpresaWindow.getEmail();
        		            password11 = updateEmpresaWindow.getPassword();
        		            name11 = updateEmpresaWindow.getName();
        		            industry11 = updateUpEmpresaWindow.getIndustry();
        		            description11 = updateEmpresaWindow.gerDescription();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }*/
                	
        				UpdateRecruiterRequisicao updateEmpresaRequisicao = new UpdateRecruiterRequisicao(Operacoes.UPDATE_ACCOUNT_RECRUITER, token1, email11, password11, name11, industry11, description11);
        			
        				String jsonRequisicaoEmpresaUpdate = gson.toJson(updateEmpresaRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoEmpresaUpdate);
        				out.println(jsonRequisicaoEmpresaUpdate);
                    
        				UpdateRecruiterResposta updateEmpresaResposta = gson.fromJson(in.readLine(), UpdateRecruiterResposta.class);
        				String jsonRespostaEmpresaUpdate = gson.toJson(updateEmpresaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaEmpresaUpdate);
        				
        				System.out.print("Nova opção: ");
        				
        				/*OpcoesRecruiterWindow opcaoEmpresaWindow11 = new OpcoesCandidateWindow();
        		        opcaoEmpresaWindow11.setVisible(true);
        		        
        		        String op11 = null;
        		        while (op11 == null) {
        		            op11 = opcaoEmpresaWindow11.getOpFinal();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }
        		        
        		        userInput = op11;*/
        				
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
        				
        				/*OpcoesRecruiterWindow opcaoEmpresaWindow12 = new OpcoesCandidateWindow();
        		        opcaoEmpresaWindow12.setVisible(true);
        		        
        		        String op12 = null;
        		        while (op12 == null) {
        		            op12 = opcaoEmpresaWindow12.getOpFinal();
        		            try {
        		                Thread.sleep(100); 
        		            } catch (InterruptedException e) {
        		                e.printStackTrace();
        		            }
        		        }
        		        
        		        userInput = op12;*/
        				
        			break;
        			
        			case "13":
        				
        				System.out.print("Digite a competência: ");
        				String skill13 = stdIn.readLine();
                	
        				System.out.print("Digite a experiência: ");
        				String experience13 = stdIn.readLine();
        				
        				IncludeSkillRequisicao includeCompetenciaRequisicao = new IncludeSkillRequisicao(Operacoes.INCLUDE_SKILL, token, skill13, experience13);
            			
        				String jsonRequisicaoCompetenciaInclude = gson.toJson(includeCompetenciaRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoCompetenciaInclude);
        				out.println(jsonRequisicaoCompetenciaInclude);
                    
        				IncludeSkillResposta includeCompetenciaResposta = gson.fromJson(in.readLine(), IncludeSkillResposta.class);
        				String jsonRespostaCompetenciaInclude = gson.toJson(includeCompetenciaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaCompetenciaInclude);
        				
        				System.out.print("Nova opção: ");
        				
        			break;
        				
        			case "14":
        				
        				System.out.print("Digite a competência: ");
        				String skill14 = stdIn.readLine();
        				
        				LookUpSkillRequisicao lookUpCompetenciaRequisicao = new LookUpSkillRequisicao(Operacoes.LOOKUP_SKILL, token, skill14);
            			
        				String jsonRequisicaoCompetenciaLookUp = gson.toJson(lookUpCompetenciaRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoCompetenciaLookUp);
        				out.println(jsonRequisicaoCompetenciaLookUp);
                    
        				LookUpSkillResposta lookUpCompetenciaResposta = gson.fromJson(in.readLine(), LookUpSkillResposta.class);
        				String jsonRespostaCompetenciaLookUp = gson.toJson(lookUpCompetenciaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaCompetenciaLookUp);
        				
        				System.out.print("Nova opção: ");
        				
        			break;
        			
        			case "15":
        				
        				System.out.print("Digite a competência para atualizar: ");
        				String skill15 = stdIn.readLine();
                	
        				System.out.print("Digite a experiência: ");
        				String experience15 = stdIn.readLine();
        				
        				System.out.print("Digite a nova competência: ");
        				String newSkill15 = stdIn.readLine();
        				
        				UpdateSkillRequisicao updateCompetenciaRequisicao = new UpdateSkillRequisicao(Operacoes.UPDATE_SKILL, token, skill15, experience15, newSkill15);
            			
        				String jsonRequisicaoCompetenciaUpdate = gson.toJson(updateCompetenciaRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoCompetenciaUpdate);
        				out.println(jsonRequisicaoCompetenciaUpdate);
                    
        				UpdateSkillResposta updateCompetenciaResposta = gson.fromJson(in.readLine(), UpdateSkillResposta.class);
        				String jsonRespostaCompetenciaUpdate = gson.toJson(updateCompetenciaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaCompetenciaUpdate);
        				
        				System.out.print("Nova opção: ");
        				
        			break;	
        				
        				
        			case "16":
        				
        				System.out.print("Digite a competência: ");
        				String skill16 = stdIn.readLine();
        				
        				DeleteSkillRequisicao deleteCompetenciaRequisicao = new DeleteSkillRequisicao(Operacoes.DELETE_SKILL, token, skill16);
            			
        				String jsonRequisicaoCompetenciaDelete = gson.toJson(deleteCompetenciaRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoCompetenciaDelete);
        				out.println(jsonRequisicaoCompetenciaDelete);
                    
        				DeleteSkillResposta deleteCompetenciaResposta = gson.fromJson(in.readLine(), DeleteSkillResposta.class);
        				String jsonRespostaCompetenciaDelete = gson.toJson(deleteCompetenciaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaCompetenciaDelete);
        				
        				System.out.print("Nova opção: ");
        				
        			break;
        				
        			case "17":
        				
        				System.out.print("Digite a competência: ");
        				String skill17 = stdIn.readLine();
                	
        				System.out.print("Digite a experiência: ");
        				String experience17 = stdIn.readLine();
        				
        				IncludeJobRequisicao includeVagaRequisicao = new IncludeJobRequisicao(Operacoes.INCLUDE_JOB, token1, skill17, experience17);
            			
        				String jsonRequisicaoVagaInclude = gson.toJson(includeVagaRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoVagaInclude);
        				out.println(jsonRequisicaoVagaInclude);
                    
        				IncludeJobResposta includeVagaResposta = gson.fromJson(in.readLine(), IncludeJobResposta.class);
        				String jsonRespostaVagaInclude = gson.toJson(includeVagaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaVagaInclude);
        				
        				System.out.print("Nova opção: ");
        				
        			break;
        			
        			case "18":
        				
        				System.out.print("Digite o id: ");
        				String id18 = stdIn.readLine();
        				
        				LookUpJobRequisicao lookUpVagaRequisicao = new LookUpJobRequisicao(Operacoes.LOOKUP_JOB, token1, id18);
            			
        				String jsonRequisicaoVagaLookUp = gson.toJson(lookUpVagaRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoVagaLookUp);
        				out.println(jsonRequisicaoVagaLookUp);
                    
        				LookUpJobResposta lookUpVagaResposta = gson.fromJson(in.readLine(), LookUpJobResposta.class);
        				String jsonRespostaVagaLookUp = gson.toJson(lookUpVagaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaVagaLookUp);
        				
        				System.out.print("Nova opção: ");
        				
        			break;
        			
        			case "19":
        				
        				System.out.print("Digite o id: ");
        				String id19 = stdIn.readLine();
        				
        				System.out.print("Digite a competência para atualizar: ");
        				String skill19 = stdIn.readLine();
                	
        				System.out.print("Digite a experiência: ");
        				String experience19 = stdIn.readLine();
        				
        				UpdateJobRequisicao updateVagaRequisicao = new UpdateJobRequisicao(Operacoes.UPDATE_JOB, token1, id19, skill19, experience19);
            			
        				String jsonRequisicaoVagaUpdate = gson.toJson(updateVagaRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoVagaUpdate);
        				out.println(jsonRequisicaoVagaUpdate);
                    
        				UpdateJobResposta updateVagaResposta = gson.fromJson(in.readLine(), UpdateJobResposta.class);
        				String jsonRespostaVagaUpdate = gson.toJson(updateVagaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaVagaUpdate);
        				
        				System.out.print("Nova opção: ");
        				
        			break;
        			
        			case "20":
        				
        				System.out.print("Digite o id: ");
        				String id20 = stdIn.readLine();
        				
        				DeleteJobRequisicao deleteVagaRequisicao = new DeleteJobRequisicao(Operacoes.DELETE_JOB, token1, id20);
            			
        				String jsonRequisicaoVagaDelete = gson.toJson(deleteVagaRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoVagaDelete);
        				out.println(jsonRequisicaoVagaDelete);
                    
        				DeleteJobResposta deleteVagaResposta = gson.fromJson(in.readLine(), DeleteJobResposta.class);
        				String jsonRespostaVagaDelete = gson.toJson(deleteVagaResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaVagaDelete);
        				
        				System.out.print("Nova opção: ");
        				
        			break;	
        			
        			case "21":
        				
        				LookUpSkillsetRequisicao lookUpSkillsetRequisicao = new LookUpSkillsetRequisicao(Operacoes.LOOKUP_SKILLSET, token);
        				
        				String jsonRequisicaoSkillsetLookUp = gson.toJson(lookUpSkillsetRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoSkillsetLookUp);
        				out.println(jsonRequisicaoSkillsetLookUp);
                    
        				LookUpSkillsetResposta lookUpSkillsetResposta = gson.fromJson(in.readLine(), LookUpSkillsetResposta.class);
        				String jsonRespostaSkillsetLookUp = gson.toJson(lookUpSkillsetResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaSkillsetLookUp);
        				
        				System.out.print("Nova opção: ");
        			
        			break;
        			
        			case "22":
        				
        				LookUpJobsetRequisicao lookUpJobsetRequisicao = new LookUpJobsetRequisicao(Operacoes.LOOKUP_JOBSET, token1);
        				
        				String jsonRequisicaoJobsetLookUp = gson.toJson(lookUpJobsetRequisicao);
        				System.out.println("Requisição enviada: " + jsonRequisicaoJobsetLookUp);
        				out.println(jsonRequisicaoJobsetLookUp);
                    
        				LookUpJobsetResposta lookUpJobsetResposta = gson.fromJson(in.readLine(), LookUpJobsetResposta.class);
        				String jsonRespostaJobsetLookUp = gson.toJson(lookUpJobsetResposta);
        				System.out.println("Resposta recebida: " + jsonRespostaJobsetLookUp);
        				
        				System.out.print("Nova opção: ");
        			
        			break;
        			
        			case "23":
        				
						List<String> habilidades23 = new ArrayList<>();
						System.out.println("Digite as habilidades (digite 'sair' para terminar):");
						while (true) {
							String habilidade = stdIn.readLine();
							if (habilidade.equalsIgnoreCase("sair")) {
								break;
							}
							habilidades23.add(habilidade);
						}

						//String[] habilidadesArray23 = habilidades23.toArray(new String[0]);
						//SearchJobRequisicao searchJobRequisicaoSkill = new SearchJobRequisicao(Operacoes.SEARCH_JOB, token, habilidadesArray23);

						SearchJobRequisicao searchJobRequisicaoSkill = new SearchJobRequisicao(Operacoes.SEARCH_JOB, token, habilidades23);
						
						String jsonRequisicaoSearchJobSkill = gson.toJson(searchJobRequisicaoSkill);
						System.out.println("Requisição enviada: " + jsonRequisicaoSearchJobSkill);
						out.println(jsonRequisicaoSearchJobSkill);

						SearchJobResposta searchJobRespostaSkill = gson.fromJson(in.readLine(), SearchJobResposta.class);
						String jsonRespostaSearchJobSkill = gson.toJson(searchJobRespostaSkill);
						System.out.println("Resposta recebida: " + jsonRespostaSearchJobSkill);

						System.out.print("Nova opção: ");

        			break;	
        				
        			case "24":
        				
        				System.out.print("Digite a experiência: ");
        				String experience24 = stdIn.readLine();
        				
        				SearchJobRequisicao searchJobRequisicaoExperience = new SearchJobRequisicao(Operacoes.SEARCH_JOB, token, experience24);

						String jsonRequisicaoSearchJobExperience = gson.toJson(searchJobRequisicaoExperience);
						System.out.println("Requisição enviada: " + jsonRequisicaoSearchJobExperience);
						out.println(jsonRequisicaoSearchJobExperience);

						SearchJobResposta searchJobRespostaExperience = gson.fromJson(in.readLine(), SearchJobResposta.class);
						String jsonRespostaSearchJobExperience = gson.toJson(searchJobRespostaExperience);
						System.out.println("Resposta recebida: " + jsonRespostaSearchJobExperience);
						
						System.out.print("Nova opção: ");
        				
        			break;
        				
        			case "25":
        				
        				List<String> habilidades25 = new ArrayList<>();
						System.out.println("Digite as habilidades (digite 'sair' para terminar):");
						while (true) {
							String habilidade = stdIn.readLine();
							if (habilidade.equalsIgnoreCase("sair")) {
								break;
							}
							habilidades25.add(habilidade);
						}

						//String[] habilidadesArray25 = habilidades25.toArray(new String[0]);
						//SearchJobRequisicao searchJobRequisicaoFilter = new SearchJobRequisicao(Operacoes.SEARCH_JOB, token, habilidadesArray25, experience25, filter25);

						
						System.out.print("Digite a experiência: ");
        				String experience25 = stdIn.readLine();
        				
        				System.out.print("Digite o filtro (E/OU): ");
        				String filter25 = stdIn.readLine();

						SearchJobRequisicao searchJobRequisicaoFilter = new SearchJobRequisicao(Operacoes.SEARCH_JOB, token, habilidades25, experience25, filter25);

						String jsonRequisicaoSearchJobFilter = gson.toJson(searchJobRequisicaoFilter);
						System.out.println("Requisição enviada: " + jsonRequisicaoSearchJobFilter);
						out.println(jsonRequisicaoSearchJobFilter);

						SearchJobResposta searchJobRespostaFilter = gson.fromJson(in.readLine(), SearchJobResposta.class);
						String jsonRespostaSearchJobFilter = gson.toJson(searchJobRespostaFilter);
						System.out.println("Resposta recebida: " + jsonRespostaSearchJobFilter);

						System.out.print("Nova opção: ");
        				
        			break;
        			
        			case "26":
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