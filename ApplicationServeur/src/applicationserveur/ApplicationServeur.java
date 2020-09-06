/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationserveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 *
 * @author romain
 */
public class ApplicationServeur extends Thread {
    private int clientcompt;    //compteur de clients qui se connectent
    InetAddress LocaleAdresse;
    public static void main(String[] args) {
        new ApplicationServeur().start();   // je créer un thread
                                            //start fait appel à la méthode run
    
    }
    @Override
    public void run() {
        try {
            System.out.println("Démarage du serveur...");
            
            LocaleAdresse = InetAddress.getLocalHost(); //@IP du serveur
            System.out.println("L'adresse locale est : "+LocaleAdresse );
            
            ServerSocket SS=new ServerSocket(789); //on démare un serveur qui écoute sur le port 789
            System.out.println("Attente de clients sur le port "+SS.getLocalPort()+"...");
            
            
            while(true){
                Socket s=SS.accept();   //connecte une application client
                clientcompt+=1;
                new Echanger(s, clientcompt).start(); //démare un thread qui s'occupe de la socket s
                                                      //à partir du moment où un client se connecte
            }
        } catch (IOException ex) {
            Logger.getLogger(ApplicationServeur.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
    
    
    
    
    class Echanger extends Thread{  // c'est cette classe qui va permettre de communiquer avec le client
        private Socket sooocket;
        private int clientnum;  //numéro du client connecté

        public Echanger(Socket s, int numduclient) {
            this.sooocket = s;
            this.clientnum=numduclient;
        }
        
        @Override
        public void run() {     //quand un client se connecte au serveur, la méthode run va s'exécuter
            //puis on créer les objets de communication
            try {
                //lire chaine de caracteres envoyé par cient
                InputStream IS=sooocket.getInputStream();      
                InputStreamReader ISR=new InputStreamReader(IS);
                BufferedReader BR=new BufferedReader(ISR);
                //envoi de chaine de caracteres au client
                OutputStream OS=sooocket.getOutputStream();
                PrintWriter PW=new PrintWriter(OS, true);
                
                String IP=sooocket.getRemoteSocketAddress().toString(); //qui retourne l'IP du client
                System.out.println("Connexion du clien n° "+clientnum+" ------ son IP est "+IP); //affichage au niveau du serveur
                
                PW.println("Vous êtes le client n° "+clientnum); //affichage au niveau du client
                
                String nom_client=BR.readLine();
                System.out.println("\t"+"Le client n° "+clientnum+" s'appelle "+nom_client);
                PW.println("Bienvenue, vous êtes mit en relation avec la base de donnée basetu. A présent, il vous possible d'intéragir avec cette BDD.");
                CreationTable(); //création de la table sur le serveur local de la machine
                
                boolean z=true;
                while(z=true){        //boucle (infinie) qui permet l'echange entre le client et le serveur
                                    // lorsque z=false on sort de la boucle et s'est fini (fermeture de la socket pour le client)
                    
                    PW.println("Que voulez vous faire ?"); //on demande au client de choisr une otption
                    PW.println("\t\t"+"Tapez 1 >>> ajouter un etudiant");
                    PW.println("\t\t"+"Tapez 2 >>> supprimer un etudiant");
                    PW.println("\t\t"+"Tapez 3 >>> modifier les informations concernant un etudiant");
                    PW.println("\t\t"+"Tapez 4 >>> consulter la liste des etudiants");
                    String requete=BR.readLine();  //on attend une reponse de type chaine de caracteres
                        if ("1".equals(requete)){
                            System.out.println(nom_client+"  a demandé un ajout");
                            //on affecte aux variablex de la fonction ajouter, les valeurs données par le client
                            String name=BR.readLine();
                            String firstname=BR.readLine();
                            String mail=BR.readLine();
                            String tel=BR.readLine();
                            
                            Connection conn=BaseConnection();
                            PreparedStatement PS =conn.prepareStatement("INSERT INTO etudiants (Nom, Prenom, Mail, Telephone) VALUES('"+name+"', '"+firstname+"', '"+mail+"', '"+tel+"')");
                            PS.executeUpdate(); 
                            System.out.println("Nouvel ajout a la table -etudiants-.");
                        }
                        
                        
                        if ("2".equals(requete)){
                            System.out.println(nom_client+"  a demandé une suppression");
                            String id=BR.readLine(); //le client donne l'ID de l'etudiant à supprimer
                            
                            Connection conn=BaseConnection();
                            PreparedStatement PS=conn.prepareStatement("DELETE * FROM etudiants WHERE ID_Etudiant="+id); 
                            PS.executeUpdate();
                            System.out.println("Un etudiant à été supprimé de la table -etudiants-.");
                        }
                        
                        
                        if ("3".equals(requete)){
                            System.out.println(nom_client+"  a demandé une modification");
                            String id=BR.readLine();
                            String name=BR.readLine();
                            String firstname=BR.readLine();
                            String mail=BR.readLine();
                            String tel=BR.readLine();
                            
                            Connection conn=BaseConnection();
                            PreparedStatement PS=conn.prepareStatement("UPDATE etudiants set Nom='"+name+"', Prenom='"+firstname+"', Mail='"+mail+"', Telephnoe="+tel+" WHERE ID_Etudiant="+id);
                            PS.executeUpdate();
                        }
                        
                        
                        if ("4".equals(requete)){
                            System.out.println(nom_client+"  a demandé une consultation");
                            String typedeconsultation=BR.readLine();
                            if ("1".equals(typedeconsultation)){
                                String name=BR.readLine();
                                Connection conn=BaseConnection();
                                PreparedStatement PS=conn.prepareStatement("SELECT * FROM etudiants WHERE Nom='"+name+"' LIMIT 2 ORDER BY DESC");
                                ResultSet RS=PS.executeQuery();
                                ResultSetMetaData RSMD=RS.getMetaData();
                                int nbColone=RSMD.getColumnCount();
                                PW.println(nbColone);
                                for(int i=1; i<=RSMD.getColumnCount(); i++){    //on envoie le nom de chaque colone au client
                                    PW.println(RSMD.getColumnName(i)+"\t\t");
                                }
                                while(RS.next()){
                                    for(int i=1; i<=RSMD.getColumnCount(); i++){    //on envoi toutes les colones
                                    PW.println(RS.getString(i)+"\t\t");
                                    }
                                }
                            }
                            
                            else{
                                Connection conn=BaseConnection();
                                PreparedStatement PS=conn.prepareStatement("SELECT * FROM etudiants LIMIT 2 ORDER BY DESC"); //on selectionne tous les éléments que l'on classe par ordre alpha
                                ResultSet RS=PS.executeQuery();
                                ResultSetMetaData RSMD=RS.getMetaData();
                                int nbColone=RSMD.getColumnCount();
                                PW.println(nbColone);
                                for(int i=1; i<=RSMD.getColumnCount(); i++){
                                    PW.println(RSMD.getColumnName(i)+"\t"); 
                                }
                                
                                while(RS.next()){
                                    for(int i=1; i<=RSMD.getColumnCount(); i++){    //on envoi toutes les colones
                                    PW.println(RS.getString(i)+"\t\t");
                                    }
                                }
                            }
                        }
                        
                        String ans=BR.readLine();
                        if ("n".equals(ans)){
                            z=false;
                        }
                }
                sooocket.close(); //fermeture de la socket du client
                        
            } catch (IOException ex) {
                Logger.getLogger(ApplicationServeur.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ApplicationServeur.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
    }
    
    
    
    
    //focntions qui permettent d'agir sur la database
    public static Connection BaseConnection() throws Exception{
            try {
            System.out.println("Connexion a la base de donnee...");
            Class.forName("com.mysql.jdbc.Driver");
            String url= "jdbc:mysql://localhost:3306/basetu";
            String user="root";
            String password ="";

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connecté à la database!");
            }
            
            catch (Exception e) {
            e.printStackTrace();
            }
                    
        return null;
    }
    
    public static void CreationTable() throws Exception{
        try{
            Connection conn=BaseConnection();
            //on créer une table "Etudiants" dans la database avec les paramètres donnés
            PreparedStatement PS;
            PS = conn.prepareStatement("CREATE TABLE IF NOT EXISTS etudiants (ID_Etudiant INTEGER NOT NULL AUTO_INCREMENT, Nom varchar(50), Prenom varchar(50), Mail varchar(250), Telephone varchar(30), PRIMARY KEY(ID_Etudiant)");
            PS.executeUpdate();
        }
        catch (Exception e) {
        e.printStackTrace();
        }
        finally{System.out.println("la table -etudiants- a bien été créée.");}
    }
     
    public static void Ajouter() throws Exception{
      String name=null;
      String firstname=null;    
      String mail=null;
      String tel=null;
      
      try{
          Connection conn=BaseConnection();
          PreparedStatement PS =conn.prepareStatement("INSERT INTO etudiants (Nom, Prenom, Mail, Telephone) VALUES('"+name+"', '"+firstname+"', '"+mail+"', '"+tel+"')");
          PS.executeUpdate();
      }
      catch (Exception e) {
      e.printStackTrace();
      }
      finally{System.out.println("Nouvel ajout a la table -etudiants-.");}
    }
    
    public static void Consulter() throws Exception{
        String name=null;
        try{
            Connection conn=BaseConnection();
            PreparedStatement PS=conn.prepareStatement("SELECT * FROM etudiants WHERE Nom='"+name+"' LIMIT 2 ORDER BY DESC"); //on selectionne tous les éléments que l'on classe par ordre alpha
            ResultSet RS=PS.executeQuery();
            ResultSetMetaData RSMD=RS.getMetaData();
            for(int i=1; i<=RSMD.getColumnCount(); i++){
                System.out.println(RSMD.getColumnName(i)+"\t"); //afficher le nom de toute les colonnes
            }
            System.out.println(); //retour à la ligne
            while(RS.next()){
                for(int i=1; i<=RSMD.getColumnCount(); i++){    //toutes les personnes sont affichées
                    System.out.print(RS.getString(i)+"\t\t");
                }
            }
        }
        catch (Exception e) {
        e.printStackTrace();
        }
    }
    
    public static void Supprimer() throws Exception{
        int id = 0;
        try{
            Connection conn=BaseConnection();
            PreparedStatement PS=conn.prepareStatement("DELETE * FROM etudiants WHERE ID_Etudiant="+id); 
            PS.executeUpdate();
                }
        catch (Exception e) {
        e.printStackTrace();
        }
        finally{System.out.println("Un etudiant à été supprimé de la table -etudiants-.");}
    }
    
    public static void Modifier() throws Exception{
        String name = null;
        String firstname =null;
        String mail=null;
        int tel=0, id=0;
        try{
            Connection conn=BaseConnection();
            PreparedStatement PS=conn.prepareStatement("UPDATE etudiants set Nom='"+name+"', Prenom='"+firstname+"', Mail='"+mail+"', Telephnoe="+tel+" WHERE ID_Etudiant="+id);
            PS.executeUpdate();
            }
        catch (Exception e) {
        e.printStackTrace();
        }
    }

}
