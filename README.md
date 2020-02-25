## Implémentation d'un serveur FTP en Java
## Nassim ARAB
## Mohamed CHABANE

### Introduction :
- Ce TP est une implémentation d'un serveur ftp en java dont l'objectif est d'utiliser les APIs Java
tel que `Socket` afin de creer un serveur fonctionnel avec un client FTP (**commande ftp**, **FileZilla**).
- Le serveur peut traiter les commandes ftp suivantes :
- USER, PASS, RETR, STOR, LIST, QUIT, PWD, CWD, CDUP,, RMD, MKD, PORT, PASV.

### Architecture :
- Package : `fil.car`
- Classes : `FtpRequest`,`ProcessCDUP`,`ProcessCWD`,`ProcessLIST`,`ProcessPass`,
`ProcessPASV`,`ProcessPORT`,`ProcessPWD`,`ProcessQuit`,
`ProcessRETR`,`ProcessRMD`,`ProcessSTOR`,
`Server`,`Users`,`OutputStreamReaderProxy`,`OSListener`,

- Tests : `UserTest`



### Exécution :
- `mvn package` : pour la compilation et les tests
- `mvn javadoc:javadoc` : pour générer la documentation
- `mvn clean` : pour nettoyer le projet


