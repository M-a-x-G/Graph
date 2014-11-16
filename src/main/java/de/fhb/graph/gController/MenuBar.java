package de.fhb.graph.gController;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.fhb.graph.gModel.Graph;
import de.fhb.graph.gView.Config;
import de.fhb.graph.gView.GFrame;
import de.fhb.graph.gView.View;


public class MenuBar extends JMenuBar {

    private static final long serialVersionUID = 1L;

    private Graph graph;
    private View view;
    private File file;
    private File directory;
    private GFrame frame;
    private static final String errorMessageMST = "Algorithmen zum finden von minimalen " +
            "Spannbäumen sind nur mit gewichteten Graphen möglich!";


    @SuppressWarnings("serial")
    public MenuBar(GFrame frame, View view, Graph graph) {
        super();
        this.graph = graph;
        this.view = view;
        this.frame = frame;
        JMenu fileMenu = new JMenu("Datei");
        this.add(fileMenu);
        JMenu editMenu = new JMenu("Bearbeiten");
        this.add(editMenu);
        JMenu algorithmsMenu = new JMenu("Algorithmen");
        this.add(algorithmsMenu);
        /*
		 * -------------- File Menu:  Open Action		
		 */
        AbstractAction openAction = new AbstractAction() {
            {
                putValue(Action.NAME, "Öffnen");
                putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        };
        fileMenu.add(openAction);
		/*
		 * -------------- File Menu: Save Action
		 */
        AbstractAction saveAction = new AbstractAction() {
            {
                putValue(Action.NAME, "Speichern");
                putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        };
        fileMenu.add(saveAction);
		
		/*
		 * -------------- File Menu: Save As Action
		 */
        AbstractAction saveAsAction = new AbstractAction() {
            {
                putValue(Action.NAME, "Speichern unter...");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                saveAs();
            }
        };
        fileMenu.add(saveAsAction);
		
		/*
		 * -------------- File Menu: Exit Action
		 */
        AbstractAction exitAction = new AbstractAction() {
            {
                putValue(Action.NAME, "Beenden");
                putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('Q', InputEvent.CTRL_MASK));
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        };
        fileMenu.add(exitAction);
		
		/*
		 * -------------- Edit Menu: Clear Action
		 */
        AbstractAction clearAction = new AbstractAction() {
            {
                putValue(Action.NAME, "Löschen");
                putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke((char) KeyEvent.VK_DELETE));
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        };
        editMenu.add(clearAction);

        /*
		 * -------------- Edit Menu: Delete Graph
		 */
        AbstractAction deleteGraphAction = new AbstractAction() {
            {
                putValue(Action.NAME, "Alles Löschen");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                deleteGraph();
            }
        };
        editMenu.add(deleteGraphAction);





		/*
		 * -------------- Edit Menu: Reset Action
		 */
        AbstractAction resetAction = new AbstractAction() {
            {
                putValue(Action.NAME, "Reset");
                putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('R', InputEvent.CTRL_MASK));
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        };
        editMenu.add(resetAction);

		/*
		 * -------------- Algorithms Menu: Find Components Action
		 */
        AbstractAction findComponentsAction = new AbstractAction() {
            {
                putValue(Action.NAME, "finde Komponenten");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                findComponents();
            }
        };
        algorithmsMenu.add(findComponentsAction);

        /*
		 * -------------- Algorithms Menu: Prim algorithm
		 */
        AbstractAction primAction = new AbstractAction() {
            {
                putValue(Action.NAME, "MSB -> Prim");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                primAlgorithm();
            }
        };
        algorithmsMenu.add(primAction);

         /*
		 * -------------- Algorithms Menu: Prim algorithm
		 */
        AbstractAction kruskalAction = new AbstractAction() {
            {
                putValue(Action.NAME, "MSB -> Kruskal");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                kruskalAlgorithm();
            }
        };
        algorithmsMenu.add(kruskalAction);


    }

    private void clear() {
        view.clear();
    }

    private void deleteGraph(){
        view.deleteGraph();
    }

    private void reset() {
        graph.resetNonPersistentProps();
    }

    private void findComponents() {
        graph.findComponents();
    }

    private void primAlgorithm(){
        try{
            graph.mstPrimAlgorithm();
        } catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(view.getGraphPanel(), errorMessageMST, "Fehler", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void kruskalAlgorithm(){
        try{
            graph.mstKruskalAlgorithm();
        } catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(view.getGraphPanel(), errorMessageMST, "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openFile() {
        JFileChooser dialog = new JFileChooser(directory);
        dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        dialog.addChoosableFileFilter(new FileNameExtensionFilter("Graph", Config.FILENAMESUFFIX));
        int returnVal = dialog.showOpenDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) return;
        file = dialog.getSelectedFile();
        if (directory == null) directory = file.getParentFile();
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
            graph = (Graph) is.readObject();
            is.close();
            view.setGraph(graph);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Die Datei " + file + " existiert nicht", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Fehler beim �ffnen der Datei " + file, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Die Datei " + file + " hat falsches Format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void saveToFile() {
        if (file == null) file = askForFilename();
        if (file == null) return;
        if (directory == null) directory = file.getParentFile();
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            graph.resetNonPersistentProps();
            os.writeObject(graph);
            os.close();
            graph.setChanged(false);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Die Datei " + file + " existiert nicht", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Fehler beim öffnen der Datei " + file, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private File askForFilename() {
        String filename;
        JFileChooser dialog = new JFileChooser(directory);
        dialog.addChoosableFileFilter(new FileNameExtensionFilter("Graph", Config.FILENAMESUFFIX));
        dialog.setAcceptAllFileFilterUsed(false);
        int returnVal = dialog.showSaveDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) return null;
        filename = dialog.getSelectedFile().getAbsolutePath();
        if (!filename.endsWith(Config.FILENAMESUFFIX))
            filename = filename + Config.FILENAMESUFFIX;
        return new File(filename);

    }

    private void saveAs() {
        file = null;
        saveToFile();
    }

    private void exit() {
        int answer = 0;
        if (graph.isChanged())
            answer = JOptionPane.showConfirmDialog(this,
                    "Sollen Ihre Änderungen gespeichert werden");
        if (answer == JOptionPane.OK_OPTION) saveToFile();
//        if (answer == JOptionPane.NO_OPTION) {}
        if (answer == JOptionPane.CANCEL_OPTION) return;
        frame.dispose();
    }
}
