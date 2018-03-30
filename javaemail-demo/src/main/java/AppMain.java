import com.demo.util.Email;
import com.demo.util.PMail;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppMain {

    public static void main(String[] args) {


        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        ///PMail mailSender = ctx.getBean(PMail.class);
        //mailSender.sendMail("bx new text", "good 2432", "asnjudy@163.com", "xuwentang@baosight.com");

        //Email email = ctx.getBean(Email.class);
        // email.sendMail("bx text", "good", "asnjudy@163.com", "xuwentang@baosight.com");


        Email email = ctx.getBean(Email.class);
        email.sendMail("123 xwt", "good 2432", "asnjudy@163.com", "xuwentang@baosight.com");
    }
}
