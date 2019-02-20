package spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: JsoupTED    @author: shan junwei
 * @description
 * @create: 2019-02-18 14:07
 **/
public class clawTed {
    private static List<String> themeList = new ArrayList<String>();

    static {
        String str = "Technology,Entertainment,Design,Business,Science,Global+issues";     // �����������
        themeList = Arrays.asList(str.split(","));
    }

    // Ŀ��ҳ
    public static void getOriginalUrls() {
        Document doc = null;
        for (String theme : themeList) {         // ��������
            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data\\" + theme + ".txt"), "utf-8"));
                for (int i = 1; i <= 10; i++) {     //  ��ҳ
                    String url = "https://www.ted.com/talks?topics%5B%5D=" + theme + "&sort=oldest&page=" + i;
                    doc = Jsoup.connect(url).get();
                    if (doc == null) break;
                    Elements links = doc.select("#browse-results > div.row.row-sm-4up.row-lg-6up.row-skinny > div > div > div > div > div.media__message > h4.h9.m5 > a");
                    for (Element element : links) {
                        bw.write(element.text() + "\n");
                    }
                }
                bw.close();
            } catch (Exception ex) {

            }
            //  ����Ƶ���ⰴ����д���ļ�
        }
    }


    //  https://www.ted.com/talks/juan_enriquez_the_age_of_genetic_wonder_feb_2019

    /**
     * ��ted �ݽ�����ҳ��ʼץȡ��Ļ   head > meta:nth-child(48)
     *
     * @param tedTalkPageUrl
     */
    public static void clawSrtFromTalkDetailPage(String tedTalkPageUrl) {
        try {
            Document document = Jsoup.connect(tedTalkPageUrl).get();
            String str = document.select("#shoji > div > div > div.main.talks-main > script:nth-child(1)").toString();
            Matcher matcher = Pattern.compile("'\\d+'").matcher(str);
            if (matcher.find()) {
                String uniqueId = matcher.group().replace("'", "");
                clawSrt.clawSrtData(Integer.parseInt(uniqueId),"data");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        clawSrtFromTalkDetailPage("https://www.ted.com/talks/juan_enriquez_the_age_of_genetic_wonder_feb_2019");
    }
}
