import extensions.CSVFile;

class WhatIsThatMelody extends Program {

    ////////////////////////////        VARIABLES GLOBALES          ///////////////////////////

    public final String ANSI_GREEN = "\u001B[32m";
    public final String ANSI_WHITE = "\u001B[37m";
    public String nom = "";
    public String difficulte = "";
    public String type = "";
    public int ligneTitre = 0;
    public Chanson chanson = new Chanson();
    public Paroles paroles = new Paroles();
    public int score = 0;
    
    ////////////////////////////////        FONCTIONS          ////////////////////////////////

    void joueur(String[][] content) {
        int choix;
        boolean bon = false;
        print("As-tu déjà joué ? (1 pour oui ou 2 pour non)\n>> ");
        while (!bon) {
            choix = readInt();
            if (choix == 1) {
                print("Heureux de te revoir ! Quel était ton nom ?\n>> ");
                nom = readString().toLowerCase();
                for (int i = 0; i < length(content); i++) {
                    if (equals(content[i][0], nom)) {
                        score = Integer.parseInt(content[i][1]);
                    }
                }
                bon = true;
            } else if (choix == 2) {
                print("Bienvenue parmis nous ! Quel est ton nom ?\n>> ");
                nom = readString().toLowerCase();
                bon = true;
            } else {
                print("Choisissez un chiffre valide entre 1 ou 2 !\n>> ");
            }
        }
    }

    void choixDifficulte() {
        boolean bon = false;
        char choix;
        print("Quelle difficulté voulez-vous ? A) Facile, B) Moyen, C) Difficile\n>> ");
        while (!bon) {
            choix = readChar();
            if (choix == 'A' || choix == 'a'){
                difficulte = "facile";
                bon = true;
            } else if (choix == 'B' || choix == 'b') {
                difficulte = "moyen";
                bon = true;
            } else if (choix == 'C' || choix == 'c') {
                difficulte = "difficile";
                bon = true;
            } else {
                print("Choisissez une lettre valide entre A, B ou C !\n>> ");
            }
        }
    }

    void choixMode() {
        boolean bon = false;
        char choix;
        print("Quel mode voulez-vous ? A) Conjugaison, B) Orthographe, C) Mémoire\n>> ");
        while (!bon) {
            choix = readChar();
            if (choix == 'A' || choix == 'a'){
                type = "CONJUGAISON";
                bon = true;
            } else if (choix == 'B' || choix == 'b') {
                type = "ORTHOGRAPHE";
                bon = true;
            } else if (choix == 'C' || choix == 'c') {
                type = "MEMOIRE";
                bon = true;
            } else {
                print("Choisissez une lettre valide entre A, B ou C !\n>> ");
            }
        }
    }

    void afficher(String[] titres) {
        for (int i = 0; i<length(titres); i++) {
            println(i+1 + ") " + titres[i]);
        }
    }

    void choixChanson(CSVFile csv) {
        boolean bon = false;
        String[] titres = new String[rowCount(csv)];
        for (int line = 0; line<rowCount(csv); line++) {
            titres[line] = getCell(csv, line, 0);
        }
        println("Quelles comptines voulez-vous ?");
        afficher(titres);
        print(">> ");
        int choix = readInt() - 1;
        while (!bon) {
            if (choix >= 0 && choix < length(titres)) {
                bon = true;
            } else {
                print("Veuillez prendre un chiffre disponible !\n>> ");
                choix = readInt() - 1;
            }
        }
        ligneTitre = choix;
        chanson.titre = getCell(csv, ligneTitre, 0);
        println("Vous avez choisi '" + chanson.titre + "'");
    }

    String init(CSVFile chansonCSV, CSVFile paroleCSV) {
        String retour = "La comptine " + ANSI_GREEN + chanson.titre + ANSI_WHITE + " est une comptine écrite par ";
        chanson.auteur = getCell(chansonCSV, ligneTitre, 1);
        chanson.dateSortie = getCell(chansonCSV, ligneTitre, 2);
        retour += ANSI_GREEN + chanson.auteur + ANSI_WHITE + " en/au " + chanson.dateSortie + "\n\n";
        paroles.preced = "";
        paroles.reponse = "";
        paroles.type = "";
        paroles.propositions = new String[3];
        for (int line = 0; line<rowCount(paroleCSV); line++) {
            if (equals(getCell(paroleCSV, line, 0), chanson.titre) && equals(getCell(paroleCSV, line, 3), type)) {
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
        int choix = readInt();
        if (choix > 3 || choix < 1) {
            println("Choisi une réponse valide !");
            reponse();
        } else if ((choix <= 3 || choix >= 1) && !equals(paroles.propositions[choix - 1], paroles.reponse)) {
            println("Mauvaise réponse !");
        } else {
            println("Bonne réponse ! Tu gagnes 1 points !"); //modif nb point en fonction de la difficulté
            score += 1; //pour l'instant +1 mais tkt on arrive
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
        content[0][0] = nom;
        content[0][1] = score + "";
        saveCSV(content, fileName);
    }

    ////////////////////////////////    PROGRAMME PRINCIPAL    ////////////////////////////////

    void algorithm() {

        boolean fin = false;
        char fini;
        String save = "../ressources/save_file.csv";
        String chansonsCSV = "../ressources/chanson.csv";
        String paroleCSV = "";
        String presentateurCSV = "../ressources/presentateur.csv";
        String texte = "";

        String[][] sauvegarde = load(loadCSV(save));

        joueur(sauvegarde);
        choixDifficulte();

        while(!fin) {
            choixMode();
            paroleCSV = "../ressources/parole_" + difficulte + ".csv";
            println();
            println("Vous avez choisi la difficulté " + difficulte + " avec le mode " + type);
            println();
            choixChanson(loadCSV(chansonsCSV));
            println();
            texte = init(loadCSV(chansonsCSV), loadCSV(paroleCSV));
            print(loadCSV(presentateurCSV));
            print(texte);
            reponse();
            println();
            println("Ton score est de : " + score);
            println();
            print("Veux-tu continuer ?? A) Oui B) Non\n>> ");
            fini = readChar();
            if (fini == 'b' || fini == 'B') {
                fin = true;
            }
        }
        save(sauvegarde, save);
        println("Bien joué tu as atteint le score de " + score + " points !!");
    }
}