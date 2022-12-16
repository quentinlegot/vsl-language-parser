# TP2 PDS, version Java

Auteur : Legot Quentin

## Structure de fichiers

La structure mon projet java est celle-ci :

- TP2.ASD
  - Contient l'axiome de l'ASD (Program.java) ainsi que le RetExpression
- TP2.ASD.expression
  - Les classes java à l'intérieur ainsi que celle qui sont dans des
  sous paquets dans ce paquet contiennent toutes les expressions de l'ASD
- TP2.ASD.type
  - Contient les types d'expression de l'ASD (Int, Void, Char, etc.)
- TP2.instruction
  - Contient Toutes les instructions LLVM appelées par les expressions
- TP2.llvm
  - Contient toutes les classes en lien avec Llvm

## Remarque d'implémentation

Les instructions print n'ajoute pas de \n à la fin, vous devez le mettre vous-même, par exemple:

```vsl
PRINT "var1 vaut ", var1, ".\n"
```

Mon implémentation de VSL ne supporte pas le shadowing, à la place, cela retournera une erreur pour vous indiquez que
vous avez redéclaré la variable.

## Tests

Les tests passent tous et s'exécutent sans soucis, à l'exception de ceux ci-dessous.

Les tests level3blocks, level3blocks1, level4t1
ne compile pas, car mon implémentation de VSL ne supporte pas le shadowing.

Ce n'est pas une erreur, mais un choix lors de la conception de mon langage.

Les tests level3tab4, level3tab5, level4testheap, level4testtri, test_incompat_proto3, test_incompat_proto4, 
test_incompat_proto5, test_invalid_call2, test_invalid_call3
ne compilent pas, car mon implémentation ne supporte pas les paramètres de function de type Tab

Le test level4diverge plante après plusieurs passages dans la boucle, car on déclare une variable qui n'est pas libéré 
après le bloc, elle est donc déclarée une multitude de fois jusqu'à créer une erreur core dump.
