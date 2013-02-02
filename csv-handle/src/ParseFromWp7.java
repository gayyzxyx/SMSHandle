import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: gayyzxyx
 * Date: 13-2-2
 * Time: 下午5:46
 * To change this template use File | Settings | File Templates.
 */
public class ParseFromWp7{
    List<Message> messages = new ArrayList<Message>();
    private String fileName = "十一月";
    byte[] bytes = new byte[8192];
    public ParseFromWp7() throws IOException {
        File file = new File("H:\\g_drive\\Google Drive\\WPSMS\\按年月封装\\2012年\\未排序\\"+fileName+".txt");
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\s*\\d{2}:\\d{2}:\\d{2}");

        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"GBK");
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String tempString = null;

            Message message = new Message();
            if((tempString = reader.readLine())!=null){
                message.setTime(tempString);
            }
            while((tempString=reader.readLine())!=null){
                Matcher matcher = pattern.matcher(tempString);
                if(matcher.find()){
                    messages.add(message);
                    message = new Message();
                    message.setTime(tempString);
                }else {
                    String tempMessage = message.getMessageContent();
                    if(tempMessage!=null&&!tempMessage.equals(""))
                        tempMessage =tempMessage +"\r\n"+ tempString;
                    else tempMessage = tempString;
                    message.setMessageContent(tempMessage);
                    message.setPhoneNumber("");
                    message.setDirection("");
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    public void convertToText() throws IOException {
        File outFile = new File("H:\\g_drive\\Google Drive\\WPSMS\\按年月封装\\2012年\\排序后\\"+fileName+"_1.txt");
        FileOutputStream writer = new FileOutputStream(outFile);
        for(int i = messages.size()-1;i>=0;i--){
            bytes = messages.get(i).getWholeMessage().getBytes();
            writer.write(bytes);
            writer.flush();
        }
    }

    public static void main(String[] args) throws IOException{
        ParseFromWp7 parseFromWp7 = new ParseFromWp7();
        parseFromWp7.convertToText();
    }
}
