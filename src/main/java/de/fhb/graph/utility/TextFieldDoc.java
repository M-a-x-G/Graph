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

    public TextFieldDoc(short maxWert, Edge edge) {
        super();
        this.maxWert = maxWert;
        this.edge = edge;
    }

    // ************* sonstige Methoden ****************************************
    /**
     * Filters all input of the textfield for everything except digits.
     * The weight information of the selected edge is updated as soon as
     * The user starts typing a number into the field.
     */
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws NumberFormatException {
        int help;


        if (Character.isDigit(str.charAt(0))) {
            help = Character.digit(str.charAt(0),10);
            if (help < 10) {
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
    }

    /**
     * Updates the weight information stored in the currently selected edge
     * as soon as the user deletes digits of the stored weight.
     */
    @Override
    public void remove(int offs, int len) throws BadLocationException {
        super.remove(offs, len);
        String text = super.getText(0, super.getLength());
        if(text != null && !text.equals("")){
            this.edge.setWeight(Integer.parseInt(text));
        } else {
            this.edge.setWeight(0);
        }
    }
}
