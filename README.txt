=== SERVEUR D'ANNUAIRE ===

Le projet consistait � mettre en place un annuaire d'�tudiants heberg� sur un serveur.

Il se compose d'une application c�t� serveur et d'une application client.


****************************** 1- Serveur ******************************************************************************

Le serveur (localhost) est un serveur "multi-thread" puisse qu'il est capable de communiquer avec
plusieurs clients simultan�ment.

Le serveur int�ragit avec une base de donn�es o� sont stock�es les informations sur les �tudiants.
Ainsi, plusieurs clients peuvent modifier en m�me temps une m�me base de donn�e.

Pourquoi une base de donn�e ?
Il est vrai que cela aurait �t� plus simple de stocker les donn�es sur les �tudiants dans une liste 
ou bien un tableau mais en utilant ces m�thodes je n'est pas r�ussis � impl�menter un syst�me de 
journalisation. C'est pour cela que j'ai opt� pour l'utilisation d'une BDD car pour chaque requ�te envoy�e,  
je me reconnecte � la BDD. Cette reconnexion fait donc office de rafraichissement.


NB: utiliser un tableau m'aurais permis de manipuler plus de classes... Voil� le gros inconv�nient de ma d�cision.

!!! IMPORTANT !!!
Conditions de connexion � la base de donn�e.
	- Modifier ou non des informations pour se connecter � Mysql (user, password, num�ro de port...)
	  Par d�faut le mot de pass de l'utilisateur 'root' est : ''.
	- Cr�er au pr�alable une database nomm�e: "basetu"
	- V�rifier que "mysql-connector-java-5.1.6-bin.jar" est toujours bien plac� dans la librairie, indispensable pour l'acc�s � Mysql.

Le serveur peut aussi �tre concid�r� comme un historique des modifications apport�es � la base.



******************************* 2- Client ********************************************************************************

L'application client ne sert qu'� faire l'interm�diaire entre l'utilisateur et le serveur.
Il n'y a pas d'interface graphique, les d�cision de l'utilisateur se feront via le terminal.
Suivant les choix fait par l'utilisateur, le serveur ne se comportera pas de la m�me fa�on.




******************************** 3- Lancement *****************************************************************************

Tout d'abord il faut lancer l'application serveur qui �coutera sur un certain port. Ensuite plusieurs applications client 
peuvent �tre lanc�es et se connecteront automatiquement au serveur.



