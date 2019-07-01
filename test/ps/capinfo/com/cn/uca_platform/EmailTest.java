package ps.capinfo.com.cn.uca_platform;

import java.util.HashMap;
import java.util.Map;

import cn.com.capinfo.core.utils.SendEmail;
import cn.com.capinfo.core.utils.SystemProperties;

public class EmailTest {

    public static void main(String[] args) {

        Map attachMap = new HashMap();

        attachMap.put("测试.xml", "E:/works/uca_platform_email_test.xml");

//         SendEmail.sendSSLMessage("hechao@capinfo.com.cn", "变动后的XML",
        // "附件是变动后的XML文件:", attachMap);

        SystemProperties.loadPropertyFile("mail.properties");

        String receiveEmail = SystemProperties.getProperty("receive.email");
        String subjectEmail = SystemProperties.getProperty("subject.email");
        String messageEmail = SystemProperties.getProperty("message.email");
        boolean sendSSLMessage = SendEmail.sendSSLMessage(receiveEmail, subjectEmail, messageEmail, attachMap);
        System.out.println(sendSSLMessage);

    }

}