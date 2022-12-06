class WhatIsThatMelody extends Program {

    public String nom = "";
    public String difficulte = "";

    ////////////////////////////////          TESTS            ////////////////////////////////

    

    ////////////////////////////////        FONCTIONS          ////////////////////////////////

    void init() {
        boolean bon = false;
        print("Quel est votre nom ? ");
        nom = ReadString();
        print("Quelle difficulté  voulez-vous ? A) facile, B) Moyen, C) Difficile");
        char choix = readChar().toUpperCase();
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

    ////////////////////////////////    PROGRAMME PRINCIPAL    ////////////////////////////////

}