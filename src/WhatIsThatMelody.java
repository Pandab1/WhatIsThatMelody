import extensions.CSVFile;

class WhatIsThatMelody extends Program {

    public String nom = "";
    public String difficulte = "";
    

    ////////////////////////////////          TESTS            ////////////////////////////////

    

    ////////////////////////////////        FONCTIONS          ////////////////////////////////

    void init() {
        boolean bon = false;
        print("Quel est votre nom ? ");
        nom = readString();
        print("Quelle difficulté  voulez-vous ? A) facile, B) Moyen, C) Difficile");
        char choix = readChar()
        ;
        while (!bon) {
            if (choix == 'A'){
                difficulte = "facile";
                bon = true;
            } else if (choix == 'B') {
                difficulte = "moyen";
                bon = true;
            } else if (choix == 'C') {
                difficulte = "difficile";
                bon = true;
            } else {
                print("choisissez une lettre valide entre A, B ou C");
            }
        }
    }

    /*Chanson choixChanson() {
        
    }*/

    void print(CSVFile csv) {
        for(int line = 0; line<rowCount(csv); line++) {
            for (int column = 0; column<columnCount(csv, line); column++){
                print(getCell(csv, line, column )+" | ");
            }
            println();
        }
    }

    void algorithm() {
        print(loadCSV("../ressources/chanson.csv",','));

    }
    ////////////////////////////////    PROGRAMME PRINCIPAL    ////////////////////////////////

}