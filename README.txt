=== SERVEUR D'ANNUAIRE ===

Le projet consistait à mettre en place un annuaire d'étudiants hebergé sur un serveur.

Il se compose d'une application côté serveur et d'une application client.


****************************** 1- Serveur ******************************************************************************

Le serveur (localhost) est un serveur "multi-thread" puisse qu'il est capable de communiquer avec
plusieurs clients simultanément.

Le serveur intéragit avec une base de données où sont stockées les informations sur les étudiants.
Ainsi, plusieurs clients peuvent modifier en même temps une même base de donnée.

Pourquoi une base de donnée ?
Il est vrai que cela aurait été plus simple de stocker les données sur les étudiants dans une liste 
ou bien un tableau mais en utilant ces méthodes je n'est pas réussis à implémenter un système de 
journalisation. C'est pour cela que j'ai opté pour l'utilisation d'une BDD car pour chaque requête envoyée,  
je me reconnecte à la BDD. Cette reconnexion fait donc office de rafraichissement.


NB: utiliser un tableau m'aurais permis de manipuler plus de classes... Voilà le gros inconvénient de ma décision.

!!! IMPORTANT !!!
Conditions de connexion à la base de donnée.
	- Modifier ou non des informations pour se connecter à Mysql (user, password, numéro de port...)
	  Par défaut le mot de pass de l'utilisateur 'root' est : ''.
	- Créer au préalable une database nommée: "basetu"
	- Vérifier que "mysql-connector-java-5.1.6-bin.jar" est toujours bien placé dans la librairie, indispensable pour l'accès à Mysql.

Le serveur peut aussi être concidéré comme un historique des modifications apportées à la base.



******************************* 2- Client ********************************************************************************

L'application client ne sert qu'à faire l'intermédiaire entre l'utilisateur et le serveur.
Il n'y a pas d'interface graphique, les décision de l'utilisateur se feront via le terminal.
Suivant les choix fait par l'utilisateur, le serveur ne se comportera pas de la même façon.




******************************** 3- Lancement *****************************************************************************

Tout d'abord il faut lancer l'application serveur qui écoutera sur un certain port. Ensuite plusieurs applications client 
peuvent être lancées et se connecteront automatiquement au serveur.



