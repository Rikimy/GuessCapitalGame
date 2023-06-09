# GuessCapitalGame
Game to guess the capital of European countries

- TP : Jeu des capitales
    - Enoncé
        - Vous disposez d’une base de données contenant la liste des pays européens et leurs capitales (countries.mv.db).

          Ce fichier est à télécharger et à placer à la racine du projet Java

          [countries.mv.db](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/6181ec5a-7fe9-43f0-b828-be1829bd669c/countries.mv.db)

          Il s’agit d’une base de données H2

          avec la table `countries` qui contient les colonnes suivantes:

            - `id`
            - `name`
            - `capital`

          `CREATE TABLE countries (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL, capital VARCHAR(255) NOT NULL);`
      
        - Ps: Si le lien en dessus ne marche pas, tu pourras trouver la db à la racine de projet.

        - Ecrire un projet Java qui va permettre de jouer à deviner les capitales des pays

          Exemple:

          > Quelle est la capitale du pays : Espagne
          >
          >
          > Saisir au clavier le nombre correspondant à la proposition choisie:
          >
          > 1. Paris
          > 2. Londres
          > 3. Madrid
          > 4. Lisbonne

            <aside>
            👉 Afficher si la réponse est correcte ou non

            </aside>

        - Objectif final : Le programme devra sélectionner aléatoirement 4 pays pour afficher 4 propositions et sélectionner parmi ces 4 lequel sera utilisé pour la question et donc valider la réponse.

    - Etapes supplémentaires
        - Faire en sorte lors du tirage au sort qu’il n’y pas plusieurs fois le même pays
        - Faire en sorte qu’une partie contienne plusieurs questions et que ce soit le joueur qui décide quand arrêter la partie
        - Calcul du score
            - En début de partie, le joueur saisi son nom
            - Pour une bonne réponse son score augmente de 1
            - Pour une mauvaise réponse son score diminue de 1
            - A la fin de la partie en enregistre son score dans la base de données qui sera cumulé avec l’ancien score s’il existe
    - Aide
        - Pour lire le clavier, on utilise la classe Scanner
            - Exemple

                ```java
                import java.util.Scanner;
                ```

                ```java
                
                Scanner clavier = new Scanner(System.in);
                //lire un nombre:
                int nombre = clavier.nextInt();
                //lire une chaine de caracteres
                String message = clavier.next();
                       
                ```

        - Nombre aléatoire

          Utiliser la classe Random

            ```java
            Random random = new Random();
            int num = random.nextInt(users.size());
            ```


    - Suggestions d’étapes
        - Tester la récupérations des données
        - Créer une classe qui représente un pays
        - Créer un ArrayList contenant des objets Pays remplis avec les données
        - Choisir 4 pays
        - Les afficher à l’écran
        - Lire le clavier pour connaitre la réponse du joueur
        - Dire si la réponse est bonne ou non
