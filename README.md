# TP2 PDS, version Java

Auteur : Legot Quentin

## Pré-requis

Le projet a été construit avec gradle 7.6 et ne devrait pas fonctionner avec une version inférieure à la version 7.x.

De même, il a été construit avec Java 11 et aussi testé sur java 19, il se peut qu'il fonctionne sur Java 8, mais aucune garantie n'est donnée.

## Utilisation

### Construire le projet

Exécutez la commande suivante:

`gradle build`

Le résultat apparait dans `build/libs`.

### Lancer le projet


Plusieurs solutions :

#### Solution 1 (Recommandé)

Utilisez le fichier compile de la manière suivante :

`./compile chemin/vers/un/fichier/vsl`

Cette exécutable compilera le fichier puis l'exécutera.

#### Solution 2

`gradle run --args="chemin/vers/un/fichier/vsl"`

Le désavantage de cette méthode est qu'il est impossible d'exclure les messages de build de gradle qui se place entre l'exécution de Java.
De plus, celui-ci ne compile que vers LLVM IR, il vous faut donc exécuter clang à la main ensuite.