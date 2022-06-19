import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 500;



    public static void main(String[] args){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\dzouz\\Downloads\\chromedriver_win32\\chromedriver.exe");
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
            JLabel examplePhoneNumberTitle = new JLabel("EXAMPLE 👉 *0538232843* 👈");
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
                    driver.get("https://web.whatsapp.com/send?phone=972" + phoneNumber.getText());
                   try {                                                                    /* lines 83-87 are temporary*/
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    WebElement input = driver.findElement(By.cssSelector("#main > footer > div._2BU3P.tm2tP.copyable-area > div > span:nth-child(2) > div > div._2lMWa > div.p3_M1 > div > div._13NKt.copyable-text.selectable-text"));
                    input.click();
                    input.sendKeys(message.getText());
                    input.sendKeys(Keys.ENTER);
//                    for (int i = 0; i < 10000; i++) {                     // This is if you want it to past a number every second.
//                        input.sendKeys(message.getText()+i);
//                        input.sendKeys(Keys.ENTER);
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
                } else if(!isValidPhoneNumber(phoneNumber)){
                    System.out.println("phone number error working");
                        JLabel phoneNumberError = new JLabel("⛔ INVALID PHONE NUMBER ⛔");
                        examplePhoneNumberTitle.setVisible(false);
                        phoneNumberError.setBounds(examplePhoneNumberTitle.getX(), examplePhoneNumberTitle.getY(), examplePhoneNumberTitle.getWidth(), examplePhoneNumberTitle.getHeight());
                        this.add(phoneNumberError);
                        try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        phoneNumberError.setVisible(false);
                        examplePhoneNumberTitle.setVisible(true);
                } else if (message.getText() == null) {
                        JLabel messageError = new JLabel("⛔ INVALID MESSAGE ⛔");
                        messageError.setBounds(examplePhoneNumberTitle.getX(), messageTitle.getY() + 150, 200, 30);
                        this.add(messageError);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        messageError.setVisible(false);
                }
            });
            repaint();
        });
    }
    public static boolean isValidPhoneNumber(JTextField textField){
       String tempText = "";
        for (int i = 0; i < 3; i++) {
            tempText += textField.getText().charAt(i);
        }
        System.out.println(tempText);
        if(textField.getDocument().getLength() == 10 && (tempText.equals("050")||tempText.equals("051") || tempText.equals("052") || tempText.equals("053") || tempText.equals("054"))){
            System.out.println(textField.getDocument().getLength() == 10);
            return true;
        }
        return false;

    }
}
