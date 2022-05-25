package aps.main.view;

import aps.main.controller.service.UserService;
import aps.main.model.user.UserModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.validation.Validator;
import javax.xml.validation.ValidatorHandler;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class HomeView extends JFrame {
JButton loginBtn = new JButton();
JLabel emailLabel = new JLabel();
JLabel passWordLabel = new JLabel();
JTextField emailField = new JTextField();
JTextField passWordField = new JTextField();
    ContactsView chat;
    int loop = 0;
UserService userService = new UserService();
    public HomeView(){

        this.setLayout(null);
        this.setSize(800,800);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("aps V");
        this.loadComponents();
        this.eventHandler();
        this.setVisible(true);
    }

    private void loadComponents(){
        //btn
        this.loginBtn.setText("entrar");
        this.emailLabel.setText("email:");
        this.passWordLabel.setText("senha:");


        this.emailLabel.setBounds(50,100,100,30);
        this.passWordLabel.setBounds(50,200,100,30);
        this.loginBtn.setBounds(400,700,60,30);
        this.passWordField.setBounds(110,200,200,40);
        this.emailField.setBounds(110,100,200,40);

        this.add(this.loginBtn);
        this.add(this.emailField);
        this.add(this.emailLabel);
        this.add(this.passWordField);
        this.add(this.passWordLabel);



    }

    private void eventHandler(){
        this.loginBtn.addActionListener(event ->{
            String email = this.emailField.getText().replace(" ","");
            String password = this.passWordField.getText();
            UserModel user;
            if(password != null){
                if (this.emailValidate(email)) {
                    try {
                       user = userService.findByEmail(email);
                        if (user.password().equals( this.passWordField.getText()) ){
                            if(loop == 0){
                                chat = new ContactsView(user);
                                chat.setSession(user);
                                this.dispose();
                                loop = 1;
                            }
                            chat.repaint();
                            chat.revalidate();

                        }
                    } catch (SQLException e) {
                        System.out.println("erro");
                        throw new RuntimeException(e);
                    }
                }else{
                    System.out.println("not valid");
                }
            }else{
                System.out.println("null");
            }
        });
        this.dispose();
    }

    private boolean emailValidate(String email){
        return (email.contains("@") && email.contains("."))
                && email.length() > 6
                && (email.charAt(email.length() -1) != '.');
    }




}
