package de.fhb.graph.utility;
/**
 * @author Maxime Schubert, Sebastian M�ller, Max Gregor
 * @date 22.10.2012
 * @version 1.0
 *
 * Das Programm wandelt, in einem Teil, Zahlen aus verschiedenen Zahlsystemen (von Basis 2-16) 
 * in Zahlen von anderen Zahlensystemen (von Basis 2-16) um.
 * Im anderen Teil wird die modulare Potenz einer Zahl berechnet.
 *
 */
import de.fhb.graph.gModel.Edge;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class TextFieldDoc extends PlainDocument {

    // ************* Attribute ************************************************
    private static final long serialVersionUID = 3L;
    private short maxWert;
    private int basisIn;
    private Edge edge;

    // ************* Konstruktor ********************************************
    /**
     * Konstruktor
     *
     * @param maxWer maximale akzeptierte Anzahl der eingegebenen Zeichen
     * @basisIn Basis der eingegebenen Zahl
     */

    public TextFieldDoc(short maxWert, int basisIn, Edge edge) {
        super();
        this.maxWert = maxWert;
        this.basisIn = basisIn;
        this.edge = edge;
    }

    // ************* sonstige Methoden ****************************************
    /**
     * Ist fuer das Ver�ndern des Textes zustaendig
     * F�r Eingabe-Basen kleiner 10 begrenzt die Methode die eingebbaren Zahlen,
     * kontrolliert hierf�r jede eingegebene Zahl einzeln und vergleicht sie mit der Basis.
     * Ist sie gr��er oder gleich der Basis wird sie nicht akzeptiert.
     * Durch die Implementierung dieser und der folgenden Methoden reduzieren wir das vorkommen von Exceptions
     * und machen das Programm an sich weniger fehleranf�llig.
     * @param offs Startposition im document
     * @param str einzuf�gende Zeichenkette
     * @param a informationsn �ber die grafische Ausgabe
     * @exception  Checked exeption wird geworfen, wenn auf eine ung�ltige Stelle im Dokument verwiesen wird
     */
    public void insertString(int offs, String str, AttributeSet a) throws NumberFormatException {
        int help;


        if (Character.isDigit(str.charAt(0)) && str.length() == 1) {
            help = Character.digit(str.charAt(0),10);
            if (help < basisIn) {
                if (super.getLength() + str.length() > maxWert) {
                    str = str.substring(0, maxWert - super.getLength());
                }
                try {
                    super.insertString(offs, str, a);

                    String text = super.getText(0, super.getLength());
                    if(text != null && !text.equals("")){
                        edge.setWeight(Integer.parseInt(text));
                    }

                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }

        

//        if (basisIn < 10) {
//
//        } else {
//            hoehererZahlenbereich(offs, str, a);
//        }
    }
    /**
     * Methode zur Verarbeitung h�herer Zahlenbereiche.
     * Bei der Eingabe eines Buchstabens wird dieser mit den Buchstaben in einem Char Array verglichen.
     * Anhand der Basis l�sst sich errechnen, bis zu welcher Indexstelle verglichen werden darf.
     * Ist der eingegebene Buchstabe unter denen, die bei der jeweiligen Basis im Char-Array adressiert werden d�rfen, so wird er ausgegeben.
     * Wenn nicht, dann nicht.
     * @param offs Startposition im document
     * @param str einzuf�gende Zeichenkette
     * @param a informationsn �ber die grafische Ausgabe
     *  @exception  Checked exeption wird geworfen, wenn auf eine ung�ltige Stelle im Dokument verwiesen wird
     */
//    private void hoehererZahlenbereich(int offs, String str, AttributeSet a){
//        char[] bereichArray = { 'a', 'b', 'c', 'd', 'e', 'f' };
//
//        if (Character.isLetter(str.charAt(0)) && str.length() == 1) {
//            for (int i = 0; i < (basisIn - 10); i++) {
//                if (bereichArray[i] == str.charAt(0) && str.length() == 1) {
//                    if (super.getLength() + str.length() > maxWert) {
//                        str = str.substring(0, maxWert - super.getLength());
//                    }
//                    try {
//                        super.insertString(offs, str, a);
//                    } catch (BadLocationException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } else if (Character.isDigit(str.charAt(0)) && str.length() == 1) {
//            if (super.getLength() + str.length() > maxWert) {
//                str = str.substring(0, maxWert - super.getLength());
//            }
//            try {
//                super.insertString(offs, str, a);
//            } catch (BadLocationException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
