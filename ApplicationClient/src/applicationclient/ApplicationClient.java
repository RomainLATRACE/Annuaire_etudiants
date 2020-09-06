/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author romain
 */
public class ApplicationClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConnexionAuServeur();
        
    }
    
     public static void ConnexionAuServeur(){
                try{
            Socket s=new Socket("localhost", 789);
            System.out.println("Connexion au serveur...");
            InputStream IS=s.getInputStream();
            InputStreamReader ISR=new InputStreamReader(IS);
            BufferedReader BR=new BufferedReader(ISR);
            OutputStream OS=s.getOutputStream();
            PrintWriter PW=new PrintWriter(OS, true);
            
            String mess=BR.readLine();
            System.out.println(mess);
            
            Scanner clavier=new Scanner(System.in);
            System.out.println("Donnez votre nom: ");
            String mon_nom=clavier.next(); //le nom est entré au clavier
            PW.println(mon_nom);  // le nom est envoyé au serveur
            
            String Nom, Prenom, Mail, Tel;
            int id;
            
            boolean y=true;
            while(y=true){
                mess=BR.readLine();
                System.out.println(mess);
                mess=BR.readLine();   //reponse du serveur concernant la question
                System.out.println(mess);
                
                String result;
                //affichage des options proposées par le serveur
                for(int n=0; n<4; n++){
                    result=BR.readLine();   
                    System.out.println(result);
                }
                
                clavier=new Scanner(System.in);
                System.out.println("Votre choix: ");
                String choix=clavier.next(); //le choix est entré au clavier
                PW.println(choix);  // le choix est envoyé au serveur
                
                    if ("1".equals(choix)){
                        System.out.println("Vous avez demandé un ajout");
                        //envoi des infos à propos du nouvel etudiant
                        clavier=new Scanner(System.in);
                        System.out.println("Nom: ");
                        Nom=clavier.next();
                        PW.println(Nom);
                        
                        clavier=new Scanner(System.in);
                        System.out.println("Prenom: ");
                        Prenom=clavier.next();
                        PW.println(Prenom);
                        
                        clavier=new Scanner(System.in);
                        System.out.println("Mail: ");
                        Mail =clavier.next();
                        PW.println(Mail);
                        
                        clavier=new Scanner(System.in);
                        System.out.println("Numéro de téléphone: ");
                        Tel=clavier.next();
                        PW.println(Tel);   
                    }
                    
                    
                    
                    if ("2".equals(choix)){
                        System.out.println("Vous avez demandé une suppression");
                        clavier=new Scanner(System.in);
                        System.out.println("ID de la personne à supprimer: ");
                        id=clavier.nextInt();
                        PW.println(id);
                    }
                    
                    
                    
                    if ("3".equals(choix)){
                        System.out.println("Vous avez demandé une modification");
                        clavier=new Scanner(System.in);
                        System.out.println("ID de la personne à modifier: ");
                        id=clavier.nextInt();
                        PW.println(id);
                        
                        clavier=new Scanner(System.in);
                        System.out.println("Nom: ");
                        Nom=clavier.next();
                        PW.println(Nom);
                        
                        clavier=new Scanner(System.in);
                        System.out.println("Prenom: ");
                        Prenom=clavier.next();
                        PW.println(Prenom);
                        
                        clavier=new Scanner(System.in);
                        System.out.println("Mail: ");
                        Mail =clavier.next();
                        PW.println(Mail);
                        
                        clavier=new Scanner(System.in);
                        System.out.println("Numéro de téléphone: ");
                        Tel=clavier.next();
                        PW.println(Tel); 
                    }
                    
                    
                    
                    
                    if ("4".equals(choix)){
                            System.out.println("Vous avez demandé une consultation");
                        
                            clavier=new Scanner(System.in);
                            System.out.println("\t\t"+"Tapez 1 >>> simple consultation");
                            System.out.println("\t\t"+"Tapez 1 >>> consultation générale");
                            String consult=clavier.next();
                            PW.println(consult);

                            if ("1".equals(consult)){
                                clavier=new Scanner(System.in);
                                System.out.println("Nom de l'étudiant recherché: ");
                                Nom=clavier.next();
                                PW.println(Nom);
                        
                                String nombreColString=BR.readLine();    //j'ai l'impression que "BR.readLine()"(OU "PW.printer()") convertis ce qu'il recoit en String
                                int nombreCol = Integer.decode(nombreColString);//conversion d'un string en int
                                for (int i=0; i<nombreCol; i++){
                                    String nom_colones=BR.readLine();
                                    System.out.println(nom_colones);//affichage des nom des colones
                                }
                                System.out.println(); //saut de ligne
                                for (int i=0; i<nombreCol; i++){
                                    String donneesetu=BR.readLine();
                                    System.out.println(donneesetu);//affichage des infos sur chaque etudiants concernés par la requete
                                }
                            }
                            
                            else{
                                String nombreColString=BR.readLine();
                                int nombreCol = Integer.decode(nombreColString);
                                for (int i=0; i<nombreCol; i++){
                                    String nom_colones=BR.readLine();
                                    System.out.println(nom_colones);    
                                }
                                System.out.println();
                                for (int i=0; i<nombreCol; i++){
                                    String donneesetu=BR.readLine();
                                    System.out.println(donneesetu);
                                }
                                
                            }    
                    }   
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    //on demande à l'utilisateur si il veut continuer ou non de manipuler la base de donnée
                    System.out.println("########################################################");
                    System.out.println("Voulez vous continuer ? (y/n): ");
                    Scanner rep = new Scanner(System.in);
                    String Codeco = rep.next();
                    PW.println(Codeco);
                    if ("n".equals(rep)){
                        y=false;
                    }
                    System.out.println("########################################################");
            }
        
                    System.out.println("Vous vous êtes déconnecté du serveur");
            
        } catch (IOException ex) {
            Logger.getLogger(ApplicationClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
}
