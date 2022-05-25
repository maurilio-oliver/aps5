package aps.main.view;

import aps.main.controller.service.MessageService;
import aps.main.controller.service.UserService;
import aps.main.model.user.UserModel;
import aps.main.view.components.ChatComponent;
import aps.main.view.components.ContactsComponent;

import javax.annotation.processing.Generated;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

public class ContactsView extends JFrame {

    ScrollPane scrollContacts = new ScrollPane();
    ScrollPane scrollPaneChat = new ScrollPane();

    private MessageService messageService = new MessageService();

    TextField textArea = new TextField();

    String to;


    UserModel user;


//configuraçoes da janela
public ContactsView(UserModel o){
    this.user = o;
    this.setLayout(null);
    this.setSize(800,800);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.setTitle("aps V");
    this.loadComponents();
    this.textArea.setBounds(this.getWidth()/2,700, this.getWidth()/2, 50);
    this.add(textArea);
    this.enterKey();
    this.setVisible(true);
}

public void setSession(UserModel user){
    this.user = user;
}

void loadComponents(){
    this.loadContactsComponent();
    this.add(scrollContacts);

}

void loadContactsComponent(){
    //janela de contatos
    ContactsComponent contacts = new ContactsComponent();
    //regula o tamanho da janela de contatos
    this.scrollContacts.setSize((this.getWidth()/2), this.getHeight()-50);
    //percorre os contatos
    this.user.contacts().forEach(contact ->{
        //cria o modal
        contacts.contactsModal(() -> {
            this.to = contact;
            //tamanho
            this.scrollPaneChat.setBounds(400,0,400,800);
            //inicia container do chat
            var a =new ChatComponent(this.user.email(),contact);
            //seta o path
            a.setPath(this.user.email(),contact);
            //adiciona o chate a este container
            this.scrollPaneChat.add(a);
            this.add(this.scrollPaneChat);

            //recarrega o grafico
            this.revalidate();
            this.repaint();
            scrollPaneChat.revalidate();
            scrollPaneChat.repaint();
        },contact);
    });
    this.scrollContacts.add(contacts);
}

private String getContent(){
    String content = this.textArea.getText();
    return content;
}
private void reload(){
    this.repaint();
    this.revalidate();
}
void enterKey(){
    this.textArea.addKeyListener(new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

            if (e.getKeyChar() == '\n') {
                messageService.send(textArea.getText(), user.email(), to);
                reload();
            }

        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    });
}

    public static void main(String[] args) throws SQLException {
       UserModel o = new UserService().findByEmail("fluxo@teste.com");
        new ContactsView(o);


    }
}
