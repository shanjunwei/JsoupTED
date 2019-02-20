package spider;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

import static util.HttpTool.doGet;

/**
 * @program: JsoupTED    @author: shan junwei
 * @description ץȡTED ��Ļ
 * @create: 2019-02-18 15:45
 **/
public class clawSrt {
    public static void clawByStartAndEnd(int start, int end) {
        for (int i = start; i <= end; i++) {
            clawSrtData(i);
        }
    }

    //  �������ز��ɹ����ļ� �հ��ļ���������
    public static void clawBlankFile(String fileDirectory) {
        File file = new File(fileDirectory);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (!file2.isDirectory() && file2.length() < 100) {
                    String fileName = file2.getName();
                    int id = Integer.parseInt(fileName.substring(0, fileName.lastIndexOf(".")));
                    clawSrtData(id, "out-add");
                }
            }
        }
    }


    /**
     * Ĭ��·��������Ӣƽ������
     *
     * @param talkId
     */
    public static void clawSrtData(int talkId) {
        clawSrtData(talkId, "out-new");
    }

    /**
     * ͬʱ������Ӣ����Ļ
     *
     * @param talkId
     */
    public static void clawSrtData(int talkId, String savePath) {
        try {
            String str = "zh-cn,en";     // �����������
            List<String> languageList = Arrays.asList(str.split(","));
            for (String language : languageList) {
                Map<String, String> param = new HashMap<>();
                param.put("language", language);
                String srt = doGet("https://www.ted.com/talks/" + talkId + "/transcript.json?language=" + language, null);
                System.out.println("���ص�����  >>" + srt);
                Thread.sleep(300);
                if (StringUtils.isNotBlank(srt)) {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(savePath + "\\" + talkId + "." + language), "utf-8"));
                    bw.write(srt);
                    bw.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void queryTitle() {    // ���ݱ����ѯ
        try {
            String url = "https://ted2srt.org/search?q=A+roadmap+to+end+aging";
            System.out.println(Jsoup.connect(url).header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36").get());
            String detailUrl = Jsoup.connect(url).get().select("body > div > div > ul > li > div > h3 > a").text();
            System.out.println(detailUrl);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        clawBlankFile("out-new");
    }
}
