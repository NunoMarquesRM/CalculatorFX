package calculadorafx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import pt.ubi.ihc.Registadora;

public class FXMLDocumentController implements Initializable {
    private Registadora modelo;
    
    @FXML private TextArea textArea;
    @FXML private Label label;

    private String total;
    private String text;
    private int numNeg;
    private boolean flag, flagDiv, flagPonto;
    
    @FXML private void BA_0(ActionEvent event) {
        //modelo.regista(Double.parseDouble(btn_0.getText()));
        number("0");
    }
    @FXML private void BA_1(ActionEvent event) {
        number("1");
    }
    @FXML private void BA_2(ActionEvent event) {
        number("2");
    }
    @FXML private void BA_3(ActionEvent event) {
        number("3");
    }
    @FXML private void BA_4(ActionEvent event) {
        number("4");
    }
    @FXML private void BA_5(ActionEvent event) {
        number("5");
    }
    @FXML private void BA_6(ActionEvent event) {
        number("6");
    }
    @FXML private void BA_7(ActionEvent event) {
        number("7");
    }
    @FXML private void BA_8(ActionEvent event) {
        number("8");
    }
    @FXML private void BA_9(ActionEvent event) {
        number("9");
    }
    @FXML private void BA_Sum(ActionEvent event) {
        if(flag){
            oper('+');
            flagDiv = false;
        }
    }
    @FXML private void BA_Sub(ActionEvent event) {
        if(numNeg == 0 && text.equals("")){
            number("-");
            numNeg = 1;
        }
        else{
            oper('-');
            numNeg = 0;
        }
    }
    @FXML private void BA_Multi(ActionEvent event) {
        if(flag){
            oper('x');
            flagDiv = false;
        }
    }
    @FXML private void BA_Div(ActionEvent event) {
        if(flag){
            oper('/');
            flagDiv = true;
        }
    }
    @FXML private void BA_Ponto(ActionEvent event) {
        if(!flagPonto){
            number(".");
            flagPonto = true;
        }
    }
    @FXML private void BA_Resultado(ActionEvent event) {
        oper('=');
        reset();
    }
    @FXML private void BA_C(ActionEvent event) {
        total = "";
        text = "";
        textArea.setText(text);
        reset();
    }
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        modelo = new Registadora();
        total = "";
        text = "";
        flag = false;
        flagDiv = false;
        flagPonto = false;
        numNeg = 0;
    }
    
    private void number(String aux){
        String str;
        if(text.equals("")){
            text = aux;
            str = total + text;
        }
        else{
            text = text.concat(aux);
            str = total + text;
        }
        textArea.setText(str);
        flag = true;
    }
    
    private void oper(char c){
        boolean divZero = false;
        double number;
        
        if(text.equals("")){
            total += "-----------------"+"\n"+String.valueOf(modelo.getResultado())+"\n"+"\n";
        }
        else{
            number = Double.parseDouble(text);
            /* Verifica se:
            *  -> o número é zero
            *  -> a operação anterior é a divisão
            *  (se for entra) */
            if(number == 0.0 && flagDiv){
                text = " " + text +"\n";
                text += " "+"Divisão por zero"+"\n";
                divZero = true;
                total += text;
            }
            else{
                modelo.regista(number);
                modelo.defineOperador(c);
                label.setText(modelo.toString());
            }
            //Verifica se o utilizador carregou no botão "Resultado"
            if(c == '='){
                /* Verifica se entrou no if anterior!
                *  Se não entrou..divZero = false
                *  logo entra neste if 
                *  para adicionar no textArea o '=' */
                if(!divZero){
                    if(text.indexOf('+') == -1 || text.indexOf('-') == -1 || text.indexOf('x') == -1 || text.indexOf('/') == -1){
                        total += " "+ c +" "+ text + "\n";
                    }
                }
                number = modelo.getResultado();
                // Se for numero negativo troca-se a posição do sinal '-'
                if(number < 0){
                    String str = String.valueOf(number);
                    str = str.substring(1, str.length());
                    total += "-----------------"+"\n "+"-"+str+"\n"+"\n";
                }
                else{
                    total += "-----------------"+"\n "+String.valueOf(number)+"\n"+"\n";
                }
            }
            else{
                total += " "+ c +" "+ text + "\n";
            }
        }
        textArea.setText(total);
        text = "";
        flag = false;
    }
    
    private void reset(){
        label.setText("0.000");
        modelo = new Registadora();
        flag = false;
        flagDiv = false;
        flagPonto = false;
        numNeg = 0;
    }
}