import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends JFrame {
    private int WINDOW_WIDTH = 800;
    private int WINDOW_HEIGHT = 500;



    public static void main(String[] args){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\dzouz\\Downloads\\chromedriver_win32\\chromedriver.exe");
        Main main = new Main();
    }

    public Main() {//מטודות חובה לפתיחת חלון גרפי
        this.setVisible(true);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLocationRelativeTo(null);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(false);
        // טקסט שמאל למעלה ברוך הבא
        Font font1 = new Font("Ariel", Font.BOLD, 20);
        JLabel title1 = new JLabel("Welcome to WhatsAppBot");
        title1.setBounds((WINDOW_WIDTH / 3), (WINDOW_HEIGHT / 100), 250, 30);
        title1.setFont(font1);
        this.add(title1);
        //כפתור שנכנס לוואצאפ
        JButton buttonToWhatsApp = new JButton("Enter WhatsApp");
        buttonToWhatsApp.setBounds((WINDOW_WIDTH / 3) + 50, title1.getY() + 50, 140, 50);
        this.add(buttonToWhatsApp);
        String url = "https://web.whatsapp.com/";
        buttonToWhatsApp.addActionListener((event) -> {
            ChromeDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get(url);
            while(true) {
                if (driver.getPageSource().contains("pane-side")) break;
                else {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            title1.setVisible(false);
            driver.manage().window().minimize();
            JLabel title2 = new JLabel("You have connected to Whatsapp successfully");
            title2.setBounds((WINDOW_WIDTH /100), (WINDOW_HEIGHT / 5), 350, 30);
            this.add(title2);
            JLabel phoneNumberTitle = new JLabel("Enter phone number here 👉");
            phoneNumberTitle.setBounds((WINDOW_WIDTH /100), title2.getY() + 30, 200, 30);
            this.add(phoneNumberTitle);
            JTextField phoneNumber = new JTextField();
            phoneNumber.setBounds(phoneNumberTitle.getX() + 200, phoneNumberTitle.getY(), 150, 30);
            this.add(phoneNumber);
            JLabel examplePhoneNumberTitle = new JLabel("EXAMPLE 👉 *972538232843* 👈");
            examplePhoneNumberTitle.setBounds(phoneNumber.getX() + 150, phoneNumber.getY(), 250, 30);
            this.add(examplePhoneNumberTitle);
            JLabel messageTitle = new JLabel("Enter message here 👉");
            messageTitle.setBounds((WINDOW_WIDTH /100), phoneNumber.getY() + 30, 150, 30);
            this.add(messageTitle);
            JTextField message = new JTextField();
            message.setBounds(messageTitle.getX() + 200, phoneNumber.getY() + 30, 150, 30);
            this.add(message);
            JButton buttonToSendMessage = new JButton("Press here to send message");
            buttonToSendMessage.setBounds(message.getX(), message.getY() + 30,300, 50);
            this.add(buttonToSendMessage);
            buttonToSendMessage.addActionListener((event2) ->{
                if(isValidPhoneNumber(phoneNumber) && message.getText() != null){
                    driver.manage().window().maximize();
                    driver.get("https://web.whatsapp.com/send?phone=" + phoneNumber.getText());
                    while(true) {
                        if (driver.findElement(By.className("p3-M1")).isDisplayed()) {
                            System.out.println("hi");
                            break;
                        }
                        else {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
//                    try {
//                        Thread.sleep(60000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                    WebElement messageElement =  driver.findElement(By.className ("p3-M1"));
                    messageElement.click();
                    messageElement.sendKeys(message.getText());
//                    driver.findElement(By.className("p3-M1")).click();
//                    driver.findElement(By.className ("p3-M1")).sendKeys(message.getText());
//                    driver.findElement(By.className("p3_M1")).sendKeys(Keys.ENTER);
                } else if(isValidPhoneNumber(phoneNumber) == false){
                    phoneNumber.setText("⛔ INVALID PHONE NUMBER ⛔");
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    phoneNumber.setText("");
                } else if (message.getText() == null) {
                        message.setText("⛔ INVALID MESSAGE ⛔");
                        new Thread(()->{
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        message.setText("");
                }

            });
            repaint();
        });
    }
    public static boolean isValidPhoneNumber(JTextField textField){
       String tempText = "";
        for (int i = 0; i < 5; i++) {
            tempText += textField.getText().charAt(i);
        }
        System.out.println(tempText);
        if(textField.getSize().equals(12) && tempText.equals("97250")||tempText.equals("97251") || tempText.equals("97252") || tempText.equals("97253") || tempText.equals("97254")){
            System.out.println(textField.getSize().equals(12));
            return true;
        }
        return false;

    }
}
