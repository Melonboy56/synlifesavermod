package melon.syn.lifesaver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class installer implements ActionListener {
    public static void install(String folder) throws IOException, URISyntaxException {
        String install_jar_file = installer.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().substring(1);


        Path install_copy_file = Paths.get(install_jar_file);
        Path install_paste_file = Paths.get(folder,"Melonboy56-Syn-life_saver-Mod.jar");
        // Melonboy56-Syn-life_saver-Mod.jar

        Files.copy(install_copy_file, install_paste_file);

    }
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        System.out.println("Melon's mod installer v1.0");
        System.out.println("Creating GUI");
        JFrame frame = new JFrame("Mod installer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,150);
        // Creates controls and text
        JLabel modinfo = new JLabel("This installer will install a mod into your mods folder");
        JLabel pickfolder = new JLabel("Please select your minecraft mods folder");
        JTextField selectdirtext = new JTextField(System.getProperty("user.home") + "\\appdata\\roaming\\.minecraft\\mods");
        JButton selectdirbutton = new JButton("...");
        JButton installbutton = new JButton("Install");

        modinfo.setBounds(0,0, 300,30);
        pickfolder.setBounds(0,15,250,30);
        selectdirtext.setBounds(0,40, 250,30);
        selectdirbutton.setBounds(250,40, 15,30);
        installbutton.setBounds(265,40,100,30);

        frame.add(modinfo);
        frame.add(pickfolder);
        frame.add(selectdirtext);
        frame.add(selectdirbutton);
        frame.add(installbutton);

        installbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Starting Mod install");
                try {
                    install(selectdirtext.getText());
                    System.out.println("Succcess! Installed Syn life saver mod");
                    JOptionPane.showMessageDialog(frame, "Successly installed Syn life saver mod", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exception) {
                    System.out.println("Error: Couldn't install Syn life saver mod");
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Unable to install Syn life saver mod", "Error", JOptionPane.ERROR_MESSAGE);
                }



            }
        });

        selectdirbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opening folder selector");
                JFileChooser selectfolder = new JFileChooser();
                selectfolder.setCurrentDirectory(new java.io.File(selectdirtext.getText()));
                selectfolder.setDialogTitle("Please select minecraft mods folder");
                selectfolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                selectfolder.setAcceptAllFileFilterUsed(false);

                if (selectfolder.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    System.out.println(selectfolder.getCurrentDirectory());
                    System.out.println(selectfolder.getSelectedFile());
                    selectdirtext.setText(String.valueOf(selectfolder.getSelectedFile()));
                } else {
                    System.out.println("User didn't select a folder");
                }
            }
        });

        frame.setLayout(null);
        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {

    }
}
