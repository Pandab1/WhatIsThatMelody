import extensions.CSVFile;

class WhatIsThatMelody extends Program {

    ////////////////////////////        VARIABLES GLOBALES          ///////////////////////////

    public final String ANSI_GREEN = "\u001B[32m";
    public final String ANSI_BLUE = "\u001B[34m";
    public String nom = "";
    public String difficulte = "";
    // public String type = "";
    public int ligneTitre = 0;
    public Chanson chanson = new Chanson();
    public Paroles paroles = new Paroles();
    public int score = 0;
    public int place = 0;
    public int nbPoint = 1;
    
    ////////////////////////////////        FONCTIONS          ////////////////////////////////

    void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    void joueur(String[][] content) {
        char choix;
        int cpt = 0;
        boolean bon = false;
        print(ANSI_BLUE + "As-tu déjà joué ? (1 pour oui ou 2 pour non)\n>> ");
        while (!bon) {
            choix = readChar();
            if (choix == '1') {
                print("Heureux de te revoir ! Quel était ton nom ?\n>> ");
                nom = readString().toLowerCase();
                for (int i = 0; i < length(content); i++) {
                    if (equals(content[i][0], nom)) {
                        score = Integer.parseInt(content[i][1]);
                        place = i;
                        println("Ton score était de " + score);
                    }
                }
                bon = true;
            } else if (choix == '2') {
                print("Bienvenue parmis nous ! Quel est ton nom ?\n>> ");
                nom = readString().toLowerCase();
                bon = true;
                while(!equals(content[cpt][0], "null") && cpt < length(content)) {
                    place = cpt;
                    cpt++;
                } 
            } else {
                print("Choisissez un chiffre valide entre 1 ou 2 !\n>> ");
            }
        }
    }

    String choixDifficulte() {
        boolean bon = false;
        char choix;
        print("Quelle difficulté voulez-vous ? 1) Facile, 2) Moyen, 3) Difficile\n>> ");
        while (!bon) {
            choix = readChar();
            if (choix == '1'){
                // difficulte = "facile";
                // bon = true;
                println("Vous avez choisi la difficulte facile.");
                return "facile";
            } else if (choix == '2') {
                // difficulte = "moyen";
                // bon = true;
                nbPoint = 2;
                println("Vous avez choisi la difficulte moyen.");
                return "moyen";
            } else if (choix == '3') {
                // difficulte = "difficile";
                // bon = true;
                nbPoint = 3;
                println("Vous avez choisi la difficulte difficile.");
                return "difficile";
            } else {
                print("Choisissez une réponse valide entre 1, 2 ou 3 !\n>> ");
            }
        }
        return "Erreur, veuillez relancer le jeu.";
    }

    String choixMode() {
        boolean bon = false;
        char choix;
        print("Quel mode voulez-vous ? 1) Conjugaison, 2) Orthographe, 3) Mémoire\n>> ");
        while (!bon) {
            choix = readChar();
            if (choix == '1'){
                // type = "CONJUGAISON";
                // bon = true;
                return "CONJUGAISON";
            } else if (choix == '2') {
                // type = "ORTHOGRAPHE";
                // bon = true;
                return "ORTHOGRAPHE";
            } else if (choix == '3') {
                // type = "MEMOIRE";
                // bon = true;
                return "MEMOIRE";
            } else {
                print("Choisissez une réponse valide entre 1, 2 ou 3 !\n>> ");
            }
        }
        return "Erreur, veuillez relancer le jeu.";
    }

    void afficher(String[] titres) {
        for (int i = 0; i<length(titres); i++) {
            println(i+1 + ") " + titres[i]);
        }
    }

    void choixChanson(CSVFile csv, String mode) {
        // boolean bon = false;
        // println("Quelle chanson voulez-vous ?");
        // afficher(titres);
        // print(">> ");
        // int choix_int = 0;
        // char choix = readChar();
        // choix_int = choix - '1';
        // println(choix_int);
        // while (!bon) {
        //     if (choix_int >= 0 && choix_int < longtitre) {
        //         bon = true;
        //     } else {
        //         print("Veuillez prendre un chiffre disponible !\n>> ");
        //         choix = readChar();
        //         choix_int = (int) choix - '1';
        //         println(choix_int);
        //     }
        // }
        // ligneTitre = choix_int;
        // chanson.titre = getCell(csv, ligneTitre, 0);
        // println("Vous avez choisi '" + chanson.titre + "'");

        boolean ok = false;
        String[] titres = new String[rowCount(csv)];
        for (int line = 0; line<rowCount(csv); line++) {
            if (getCell(csv, line, 3).equals(mode)) {
                titres[line] = getCell(csv, line, 0);
            }
        }
        int longtitre = length(titres);
        int choix = (int) (random() * longtitre);
        ligneTitre = choix;
        chanson.titre = getCell(csv, choix, 0);

        print("Patientez");
        wait(1000);
        print(".");
        wait(1000);
        print(".");
        wait(1000);
        println(".");
        wait(1000);
    }

    String init(CSVFile chansonCSV, CSVFile paroleCSV, String mode) {
        String retour = "La chanson " + ANSI_GREEN + chanson.titre + ANSI_BLUE + " est une chanson écrite par ";
        chanson.auteur = getCell(chansonCSV, ligneTitre, 1);
        chanson.dateSortie = getCell(chansonCSV, ligneTitre, 2);
        retour += ANSI_GREEN + chanson.auteur + ANSI_BLUE + " en/au " + chanson.dateSortie + "\n\n";
        paroles.preced = "";
        paroles.reponse = "";
        paroles.type = "";
        paroles.propositions = new String[3];
        for (int line = 0; line<rowCount(paroleCSV); line++) {
            if (equals(getCell(paroleCSV, line, 0), chanson.titre) && equals(getCell(paroleCSV, line, 3), mode)) {
                paroles.preced = getCell(paroleCSV, line, 1);
                paroles.reponse = getCell(paroleCSV, line, 2);
                paroles.type = getCell(paroleCSV, line, 3);
                paroles.propositions[0] = getCell(paroleCSV, line, 4);
                paroles.propositions[1] = getCell(paroleCSV, line, 5);
                paroles.propositions[2] = getCell(paroleCSV, line, 6);
            }
        }
        retour += "Choisissez la bonne proposition !\n\n" + paroles.preced + "\n" + "1) " + paroles.propositions[0] + " 2) " + paroles.propositions[1] + " 3) " + paroles.propositions[2] + "\n";
        return retour;
    }

    void reponse() {
        print(">> ");
        char choix = readChar();
        int reponse = (int) choix - '0';
        if (reponse > 3 || reponse < 1) {
            println("Choisi une réponse valide !");
            reponse();
        } else if ((reponse <= 3 || choix >= 1) && !equals(paroles.propositions[reponse - 1], paroles.reponse)) {
            println("Mauvaise réponse !");
        } else {
            println("Bonne réponse ! Tu gagnes " + nbPoint + " points !"); //modif nb point en fonction de la difficulté
            score += nbPoint;
        }
    }

    void print(CSVFile csv) {
        for(int line = 0; line<rowCount(csv); line++) {
            for (int column = 0; column<columnCount(csv, line); column++) {
                print(getCell(csv, line, column )+" ");
            }
            println();
        }
    }

    String[][] load(CSVFile csv) {
        String[][] save = new String[25][2];
        for (int line = 0; line<rowCount(csv); line++) {
            for (int column = 0; column<columnCount(csv, line); column++) {
                save[line][column] = getCell(csv, line, column);
            }
        }
        return save;
    }

    void save(String[][] content, String fileName) {
        content[place][0] = nom;
        content[place][1] = score + "";
        saveCSV(content, fileName);
    }

    ////////////////////////////////    PROGRAMME PRINCIPAL    ////////////////////////////////

    void algorithm() {

        boolean menu = true;
        char choix;
        String mode = "";
        String save = "../ressources/save_file.csv";
        String chansonsCSV = "../ressources/chanson.csv";
        String paroleCSV = "";
        String presentateurCSV = "../ressources/presentateur.csv";
        String texte = "";
        String[][] sauvegarde = load(loadCSV(save));

        clearScreen();
        print(loadCSV(presentateurCSV));
        println("BONJOUR !!\n\nBienvenue dans ce jeu très sympathique. Les règles sont très simples :\nJe vais vous poser des questions sur des comptines et des chansons et vous devrez y répondre sans vous tromper.\nVous avez 3 essais.\nPour répondre à une question, il vous suffit de taper le chiffre correspondant à la réponse puis d'appuyer sur la touche entrer.\nPour quitter le jeu, if vous suffit d'appuyer sur ctrl + c en même temps (ou cmd + c sur Mac) (ATTENTION, à n'utilisez quand cas d'urgence. Exemple, le jeu vous donne une erreur).\n\nVous pouvez aussi choisir le mode entraînement dans lequel vous pouvez choisir la chansons que vous voulez et vous avez autant d'essais que vous voulez.\n\nBon courage !");
        println();
        // wait(10000);
        joueur(sauvegarde);
        println("######################################");
        println("# Que choisissez-vous ?              #");

        while(menu) {
            println("#                                    #");
            print("# 1) Mode classique                  #\n# 2) Entraînement                    #\n# 3) Quitter                         #\n# >> ");
            choix = readChar();
            if (choix == '1') {
                
                paroleCSV = "../ressources/parole_" + choixDifficulte() + ".csv";
                mode = choixMode();
                println("Vous avez choisi le mode " + mode + ".");
                println("Vous pouvez revenir au menu à tout moment en appuyant sur 'm'.");

            } else if (choix == '2') {
                println("ok 2");
            } else if (choix == '3') {
                menu = false;
                clearScreen();
                print(loadCSV(presentateurCSV));
                println("A bientôt !");
                println();
            } else {
                println("Choisissez une réponse valide entre 1, 2 ou 3 !");
            }
            
        }
    }
}