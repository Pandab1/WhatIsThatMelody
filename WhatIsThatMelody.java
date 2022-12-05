class WhatIsThatMelody extends Program {

    ////////////////////////////////          TESTS            ////////////////////////////////

    void testIsNumberCorrect() {
        int nbReponses = 4;
        assertTrue(isNumberCorrect(nbReponses, 3));
        assertTrue(isNumberCorrect(nbReponses, 0));
        assertFalse(isNumberCorrect(nbReponses, 4));
        assertFalse(isNumberCorrect(nbReponses, -1));
    }

    ////////////////////////////////        FONCTIONS          ////////////////////////////////

    boolean isNumberCorrect(int nbReponses, int reponses) {
        return reponses >= 0 && reponses < nbReponses;
    }

    ////////////////////////////////    PROGRAMME PRINCIPAL    ////////////////////////////////

}