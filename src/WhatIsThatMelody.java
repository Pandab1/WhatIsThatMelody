import extensions.CSVFile;

class WhatIsThatMelody extends Program {

    ////////////////////////////        VARIABLES GLOBALES          ///////////////////////////

    public String nom = "";
    public String difficulte = "";
    public String type = "";
    public int ligneTitre = 0;
    public Chanson chanson = new Chanson();
    public Paroles paroles = new Paroles();
    public int score = 0;
    
    ////////////////////////////////        FONCTIONS          ////////////////////////////////

    void start() {
        boolean bon = false;
        char choix;
        print("Quel est votre nom ? ");
        nom = readString();
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
        bon = false;
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
        println();
        println("Vous avez choisi la difficulté " + difficulte + " avec le mode " + type);
        println();
    }

    void afficher(String[] titres) {
        for (int i = 0; i<length(titres); i++) {
            println(i+1 + ") " + titres[i]);
        }
    }

    void choixChanson(CSVFile csv) {
        String[] titres = new String[rowCount(csv)];
        for (int line = 0; line<rowCount(csv); line++) {
            titres[line] = getCell(csv, line, 0);
        }
        println("Quelles comptines voulez-vous ?");
        afficher(titres);
        int choix = readInt() - 1;
        ligneTitre = choix;
        chanson.titre = getCell(csv, ligneTitre, 0);
        println("Vous avez choisi '" + chanson.titre + "'");
        //AJOUTER UNE VERIF
    }

    String init(CSVFile chansonCSV, CSVFile paroleCSV) {
        String retour = "La comptine " + chanson.titre + " est une comptine écrite par ";
        chanson.auteur = getCell(chansonCSV, ligneTitre, 1);
        chanson.dateSortie = getCell(chansonCSV, ligneTitre, 2);
        retour += chanson.auteur + " en/au " + chanson.dateSortie + "\n";
        paroles.preced = "";
        paroles.reponse = "";
        paroles.type = "";
        paroles.propositions = new String[3];
        for (int line = 0; line<rowCount(paroleCSV); line++) {
            if (equals(getCell(paroleCSV, line, 0), chanson.titre) && equals(getCell(paroleCSV, line, 4), type)) {
                paroles.preced = getCell(paroleCSV, line, 1);
                paroles.reponse = getCell(paroleCSV, line, 2);
                paroles.type = getCell(paroleCSV, line, 3);
                paroles.propositions[0] = getCell(paroleCSV, line, 4);
                paroles.propositions[1] = getCell(paroleCSV, line, 5);
                paroles.propositions[2] = getCell(paroleCSV, line, 6);
            }
        }
        retour += "Choisissez la bonne proposition !\n" + paroles.preced + "\n" + "1) " + paroles.propositions[0] + " 2) " + paroles.propositions[1] + " 3) " + paroles.propositions[2] + "\n";
        return retour;
    }

    void print(CSVFile csv) {
        for(int line = 0; line<rowCount(csv); line++) {
            for (int column = 0; column<columnCount(csv, line); column++){
                print(getCell(csv, line, column )+" | ");
            }
            println();
        }
    }

    ////////////////////////////////    PROGRAMME PRINCIPAL    ////////////////////////////////

    void algorithm() {
        start();
        String chansonsCSV = "../ressources/chanson.csv";
        String paroleCSV = "../ressources/parole.csv";
        choixChanson(loadCSV(chansonsCSV));
        String texte = init(loadCSV(chansonsCSV), loadCSV(paroleCSV));
        //print(presentateur);
        print(texte);
    }
}