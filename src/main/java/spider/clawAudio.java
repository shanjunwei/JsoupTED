package spider;

import org.apache.commons.lang.StringUtils;
import util.HttpTool;

/**
 * @program: JsoupTED    @author: shan junwei
 * @description: TED  �ݽ���Ƶ����ץȡ
 * @create: 2019-02-19 14:42
 **/
public class clawAudio {
    public static void main(String[] args) {
        for (int i = 1; i < 2800; i++) {
            String temp = HttpTool.doGet("https://hls.ted.com/talks/" + i + ".m3u8?preroll=Thousands&qr");
            if (StringUtils.isBlank(temp)) continue;
            // ͨ������ó�������Ƶ��Ψһ��ʶ��  ��Ҫ����
            String tempUrl = temp.split("\\n")[3];
            String[] array = tempUrl.split("/");
            String nameAndAge = array[2];
            String year = nameAndAge.split("_")[1];
            String uniqueId = array[4].split("=")[3];
            //  Ŀ��ҳ������   https://pb.tedcdn.com/talk/hls/audio/2006/DavidPogue_2006-950k-6de26e30.aac
            String audioUrl = "https://pb.tedcdn.com/talk/hls/audio/" + year + "/" + nameAndAge + "-950k-" + uniqueId + ".aac";
            System.out.println(audioUrl);
            //  ��Ҫ�Ĳ��� 950k(�̶�)  DavidPogue_2006   Ψһ��ʶ��: 6de26e30
            try {
                HttpTool.downLoadFromUrl(audioUrl, "audio/" + i + "#" + nameAndAge + "-950k-" + uniqueId + ".aac");
            } catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
        }
    }
}
