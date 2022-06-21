import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 500;


    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\dzouz\\Downloads\\chromedriver_win32\\chromedriver.exe");
        new Main();
    }

    public Main() {//מטודות חובה לפתיחת חלון גרפי
        this.getContentPane().setBackground(Color.cyan);
        this.setTitle("David Zouzout's and Orel Sabash's WhatsApp-Bot");
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        // טקסט שמאל למעלה ברוך הבא
        Font font1 = new Font("Ariel", Font.BOLD, 20);
        Font font2 = new Font("Ariel", Font.BOLD, 13);
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
            while (true) if (driver.getPageSource().contains("pane-side")) break;
            driver.manage().window().minimize();
            buttonToWhatsApp.setVisible(false);
            //התראת כניסה בהצלחה לוואצאפ
            JLabel title2 = new JLabel("You have connected to Whatsapp successfully:");
            title2.setBounds((WINDOW_WIDTH / 100), (WINDOW_HEIGHT / 5), 280, 30);
            this.add(title2);
            //טקסט מנחה להכנסת מספר טלפון
            JLabel phoneNumberTitle = new JLabel("Enter phone number here 👉");
            phoneNumberTitle.setBounds((WINDOW_WIDTH / 100), title2.getY() + 30, 200, 30);
            this.add(phoneNumberTitle);
            //תיבה להכנסת מספר טלפון
            JTextField phoneNumber = new JTextField();
            phoneNumber.setBounds(phoneNumberTitle.getX() + 200, phoneNumberTitle.getY(), 200, 30);
            this.add(phoneNumber);
            //דוגמא למספר טלפון תקין
            JLabel examplePhoneNumberTitle = new JLabel("EXAMPLE 👉 *0538232843* 👈");
            examplePhoneNumberTitle.setBounds(phoneNumber.getX() + phoneNumber.getWidth() + 10, phoneNumber.getY(), 185, 30);
            this.add(examplePhoneNumberTitle);
            //טקסט מנחה להכנסת הודעה
            JLabel messageTitle = new JLabel("Enter message here 👉");
            messageTitle.setBounds((WINDOW_WIDTH / 100), phoneNumber.getY() + 30, 150, 30);
            this.add(messageTitle);
            //  תיבה להכנסת הודעה
            JTextField message = new JTextField();
            message.setBounds(messageTitle.getX() + 200, phoneNumber.getY() + 30, 200, 30);
            this.add(message);
            // כפתור ששולח את ההודעה בעת הזנת מספר טלפון והודעה תקינה
            JButton buttonToSendMessage = new JButton("Press here to send message");
            buttonToSendMessage.setBounds(message.getX(), message.getY() + 30, 300, 50);
            this.add(buttonToSendMessage);
            //תיבת הסטטוס
            JLabel status = new JLabel("👇 STATUS OF PROGRAM 👇");
            status.setFont(font2);
            status.setBounds(title2.getX() + title2.getWidth() / 6, title2.getY() - title2.getHeight() - 20, 250, 80);
            this.add(status);
            //טקסט מודיע על שגיעה בהודאה
            JLabel messageError = new JLabel("⛔ INVALID MESSAGE ⛔");
            messageError.setBounds(message.getX() + message.getWidth(), messageTitle.getY(), 200, 30);
            messageError.setVisible(false);
            this.add(messageError);
            //טקסט מודיע על שגיעה במספר טלפון
            JLabel phoneNumberError = new JLabel("⛔ INVALID PHONE NUMBER ⛔");
            phoneNumberError.setVisible(false);
            phoneNumberError.setBounds(examplePhoneNumberTitle.getX() + examplePhoneNumberTitle.getWidth(), phoneNumber.getY(), 200, 30);
            this.add(phoneNumberError);

            // התראת שגיאה בעת מספר טלפון לא תקין והודעה לא תקינה
            buttonToSendMessage.addActionListener((event2) -> {
                if (isValidPhoneNumber(phoneNumber) && message.getText().length() != 0) {
                    phoneNumberError.setVisible(false);
                    messageError.setVisible(false);
                    repaint();
                    driver.manage().window().maximize();
                    driver.get("https://web.whatsapp.com/send?phone=972" + phoneNumber.getText());

                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    while (true) {
                        if (driver.getPageSource().contains("pane-side")) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            WebElement input = driver.findElement(By.cssSelector("#main > footer > div._2BU3P.tm2tP.copyable-area > div > span:nth-child(2) > div > div._2lMWa > div.p3_M1 > div > div._13NKt.copyable-text.selectable-text"));
                            input.click();
                            input.sendKeys(message.getText());
                            input.sendKeys(Keys.ENTER);
                            title2.setText("Message  has  sent successfully:");
                            break;
                        }
                    }
                } else if (!isValidPhoneNumber(phoneNumber) && message.getText().length() != 0) {
                    messageError.setVisible(false);
                    phoneNumberError.setVisible(true);
                } else if (message.getText().length() == 0 && isValidPhoneNumber(phoneNumber)) {
                    phoneNumberError.setVisible(false);
                    messageError.setVisible(true);
                    repaint();
                } else if (message.getText().length() == 0 && !isValidPhoneNumber(phoneNumber)) {
                    phoneNumberError.setVisible(true);
                    messageError.setVisible(true);
                    repaint();
                }
            });
            repaint();
        });
    }

    // בדיקה עם המספר טלפון תקין
    public static boolean isValidPhoneNumber(JTextField textField) {

        StringBuilder tempText = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            if (textField.getDocument().getLength() < 3) return false;
            else tempText.append(textField.getText().charAt(i));
        }
        if (textField.getDocument().getLength() == 10 && (tempText.toString().equals("050") || tempText.toString().equals("051") || tempText.toString().equals("052") || tempText.toString().equals("053") || tempText.toString().equals("054")) || tempText.toString().equals("055")) {
            return true;
        }
        if (textField.getDocument().getLength() == 0) {
            return false;
        }
        return false;

    }
}
