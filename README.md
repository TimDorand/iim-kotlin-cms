# Rendu kotlin CMS

> Timothée DORAND IIM A5 IWM

Démarrer la base de donnée mysql port 6000 à l'aide de docker-compose.yml

```bash
docker-compose up -d
```

URL du CMS: [http://localhost:8888](http://localhost:8888)
URL de l'Admin: [http://localhost:8888/admin](http://localhost:8888/admin)

Credentials: 
- user: admin
- pass: root

Mysqldump: [mysqldump.sql]('./mysqldump.sql')



### Consignes


En plus du projet actuel :
- Affichage des commentaires d'un article (table commentaire : id, idArticle, text).
- Possibilite de poster un commentaire (sans etre connecte) depuis la page d'un article.
- Connection a une interface d'administration avec login / mot de passe.
- Gestion de la session.
- Possibilite de se deconnecter.
- Une fois connecte en tant qu'admin, possibilite d'ajouter ou supprimer un article.
- Si on supprime un article, ses commentaires doivent etre supprimes (soit manuellement, soit via les MySQL Foreign keys).
- Une fois connecte en tant qu'admin, possibilite de supprimer un commentaire.
- Les controlleurs correspondant a ces nouvelles fonctionalites doivent etre correctement testes.

Le rendu doit etre fait par mail au plus tard dimanche 17 mars 2019.
Le rendu doit inclure un dump de votre base de donnees, et le login / mot de passe a utiliser pour se connecter.
Le code doit etre propre !!!

Tout bonus (jolie interface utilisateur avec CSS externe, test du model via H2, utilisation de BCrypt pour le mot de passe) sera apprecie a sa juste valeur ;).

Je reste disponible pour toutes questions a salomon@kodein.net
