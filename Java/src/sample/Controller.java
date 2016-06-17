package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;


public class Controller extends Component {
    String path="C:\\";

    @FXML
    private Button btnSelection;

    @FXML
    private Button btndownloadthis;

    @FXML
    private Button btndownloadall;

    @FXML
    private TextField textfieldURL;

    @FXML
    private Label labelpath;


    @FXML
    public void action1 () {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            path = ""+chooser.getSelectedFile();
        }
        if (path!=""){
            labelpath.setText(path);
        }
    }





    public void action2 () {
        String ssilkaimg ="";
        String glavnaja="";
        Document doc;
        int nameimg = 1;
        int b =0;
        int i=0;
        boolean flag;
        String urlLINK = textfieldURL.getText();
        char[] cchArray = urlLINK.toCharArray();
        for ( i = 0; i < cchArray.length; i++) {
            if (cchArray[i] == '/'  ) {
                b++;
                if (b==3){
                    glavnaja = urlLINK.substring(0, i);
                }
            }
        }
        try {
            doc = Jsoup.connect(urlLINK).get();
            Elements urlsimg = doc.select("img");
            for(Element urlimg : urlsimg) {
                ssilkaimg = urlimg.attr("src");
                char[] imgccchArray = ssilkaimg.toCharArray();
                try {
                    if (imgccchArray[0] == 'h' && imgccchArray[1] == 't' && imgccchArray[2] == 't' && imgccchArray[3] == 'p') {
                    } else {
                        if (imgccchArray[0] == '/') {
                            ssilkaimg = glavnaja + ssilkaimg;
                        } else {
                            ssilkaimg = glavnaja + "/" + ssilkaimg;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                    InputStream in = null;
                    OutputStream out = null;
                    URL url1 = new URL(ssilkaimg);
                    HttpURLConnection httpConn = (HttpURLConnection) url1.openConnection();
                    httpConn.setRequestMethod("GET");
                    httpConn.connect();
                    in = httpConn.getInputStream();
                    out = new FileOutputStream(path+"\\" + nameimg + ".jpg");
                    nameimg++;
                    byte buffer[] = new byte[1024];
                    int c = 0;
                    while ((c = in.read(buffer)) > 0) {
                        out.write(buffer, 0, c);
                    }
                    out.flush();
            }
        } catch (IOException e) {
        }
    }




    public void action3 () {
        String urlLINK = textfieldURL.getText();
        String ssilka ="";
        String ssilkaimg ="";
        String glavnaja="";
        Document doc;
        int prohod=0;
        int nameimg = 1;
        int b =0;
        int ii=0;
        int iii=0;
        boolean flag;
        String [] newssilkiNAstranicii=new String[1000];
        String [] ssilkiNAkartinki=new String[100000];
        char[] cchArray = urlLINK.toCharArray();
        for (int i = 0; i < cchArray.length; i++) {
            if (cchArray[i] == '/'  ) {
                b++;
                if (b==3){
                    glavnaja = urlLINK.substring(0, i);
                }
            }
        }
        try {
            while (prohod<newssilkiNAstranicii.length)
            {
                prohod++;
                doc = Jsoup.connect(urlLINK).get();
                Elements urls = doc.select("a");
                for(Element url : urls){
                    ssilka =url.attr("href");
                    char[] ccchArray = ssilka.toCharArray();
                    try {
                        if (ccchArray[0] == 'h' &&  ccchArray[1] == 't' &&  ccchArray[2] == 't'&&  ccchArray[3] == 'p' )  {}
                        else{
                            if (  ccchArray[0] == '/'){
                                ssilka=glavnaja+ssilka;
                            }
                            else {
                                ssilka=glavnaja+"/"+ssilka;
                            }
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                    }

                    flag = false;
                    for (int v=0; v<ii; v++) {
                        if (newssilkiNAstranicii[v].equals(ssilka)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag == false) {
                        if(ssilka.indexOf(glavnaja) == 0)
                        {
                            newssilkiNAstranicii[ii] = ssilka;
                            ii++;
                            try {
                                doc = Jsoup.connect(ssilka).get();
                                Elements urlsimg = doc.select("img");
                                for(Element urlimg : urlsimg) {
                                    ssilkaimg = urlimg.attr("src");
                                    char[] imgccchArray = ssilkaimg.toCharArray();
                                    try {
                                        if (imgccchArray[0] == 'h' && imgccchArray[1] == 't' && imgccchArray[2] == 't' && imgccchArray[3] == 'p') {
                                        } else {
                                            if (imgccchArray[0] == '/') {
                                                ssilkaimg = glavnaja + ssilkaimg;
                                            } else {
                                                ssilkaimg = glavnaja + "/" + ssilkaimg;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    flag = false;
                                    for (int v = 0; v < iii; v++) {
                                        if (ssilkiNAkartinki[v].equals(ssilkaimg)) {
                                            flag = true;
                                            break;
                                        }
                                    }
                                    if (flag == false) {
                                        ssilkiNAkartinki[iii] = ssilkaimg;
                                        iii++;
                                        InputStream in = null;
                                        OutputStream out = null;
                                        URL url1 = new URL(ssilkaimg);
                                        HttpURLConnection httpConn = (HttpURLConnection) url1.openConnection();
                                        httpConn.setRequestMethod("GET");
                                        httpConn.connect();
                                        in = httpConn.getInputStream();
                                        out = new FileOutputStream(path+"\\" + nameimg + ".jpg");
                                        nameimg++;
                                        byte buffer[] = new byte[1024];
                                        int c = 0;
                                        while ((c = in.read(buffer)) > 0) {
                                            out.write(buffer, 0, c);
                                        }
                                        out.flush();
                                    }
                                }
                            } catch (IOException e) {
                            }
                        }
                    }
                }
                if (newssilkiNAstranicii[prohod-1]!=null){urlLINK=newssilkiNAstranicii[prohod-1];} else {break;}
            }
        }
        catch (IOException e) {
        }
    }
}

