import extensions.CSVFile;

class WhatIsThatMelody extends Program {

    public String nom = "";
    public String difficulte = "";
    public String type = "";
    public String titreChoisi = "";
    

    ////////////////////////////////          TESTS            ////////////////////////////////

    

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
        titreChoisi = titres[choix];
        println("Vous avez choisi '" + titreChoisi + "'");
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
        choixChanson(loadCSV(chansonsCSV));
    }

}