package spider;

import org.apache.commons.lang.StringUtils;
import util.HttpTool;

/**
 * @program: JsoupTED    @author: shan junwei
 * @description: TED演讲 音频抓取
 * @create: 2019-02-19 16:19
 **/
public class audioDownloadTask {

    /**
     *   分页下载，以支持多线程 多进程下载音频
     * @param start
     * @param end
     */
    public static void unitTask(int start, int end) {
        for (int talkId = start; talkId < end; talkId++) {
            downloadAudioByTalkId(talkId);
        }
    }

    /**
     * 根据talkId  下载ted音频文件
     * @param talkId
     */
    public static void downloadAudioByTalkId(int talkId) {
        String temp = HttpTool.doGet("https://hls.ted.com/talks/" + talkId + ".m3u8?preroll=Thousands&qr");
        if (StringUtils.isBlank(temp)) return;
        // 通过这个得出下载音频的唯一标识符  需要的是
        String tempUrl = temp.split("\\n")[3];
        String[] array = tempUrl.split("/");
        String nameAndAge = array[2];
        String year = nameAndAge.split("_")[1];
        String uniqueId = array[4].split("=")[3];
        //  目标页面链接   https://pb.tedcdn.com/talk/hls/audio/2006/DavidPogue_2006-950k-6de26e30.aac
        String audioUrl = "https://pb.tedcdn.com/talk/hls/audio/" + year + "/" + nameAndAge + "-600k-" + uniqueId + ".aac";
        //  需要的参数 950k(固定)  DavidPogue_2006   唯一标识符: 6de26e30
        try {
            HttpTool.downLoadFromUrl(audioUrl, "audio/" + talkId + "#" + nameAndAge + "-950k-" + uniqueId + ".aac");
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }


    public static void main(String[] args) {
        if (args.length <= 1) {
            System.out.println("请输入 起始与结束下标值，以空格分割，如 10 20");
        }
        int start = Integer.parseInt(args[0]);
        int end = Integer.parseInt(args[1]);
        unitTask(start, end);
    }
}
