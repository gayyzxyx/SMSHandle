import com.Ostermiller.util.ExcelCSVParser;
import com.Ostermiller.util.LabeledCSVParser;
import com.lowagie.text.DocumentException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gayyzxyx
 * Date: 13-2-2
 * Time: 下午1:25
 * To change this template use File | Settings | File Templates.
 */
public class CsvFileParser {
    private LabeledCSVParser csvParser;//CSV解析器对第一行的表头信息自动加载索引关键字
    private int currLineNum = -1;//文件所读到的行数
    private String [] currLine = null;//当前行
    //构造函数
    protected CsvFileParser(InputStreamReader inputStream) throws IOException{
        csvParser = new LabeledCSVParser(new ExcelCSVParser(inputStream));
        currLineNum = csvParser.getLastLineNumber();
    }
    //检查是否还有数据
    public boolean hasMore() throws IOException{
        currLine = csvParser.getLine();
        currLineNum = csvParser.getLastLineNumber();
        if(null == currLine)
            return false;
        return true;
    }

    /**
     *返回当前航数据，关键字所指向的数据
     * @param fieldName 该行的表头              g
     * @return
     */
    public String getByFieldName(String fieldName){
        return csvParser.getValueByLabel(fieldName);
    }

    /**
     * 关闭解析器
     * @throws IOException
     */
    public void close() throws IOException{
        csvParser.close();
    }

    /**
     * 获取当前航数据
     * @return
     * @throws IOException
     */
    public String [] readLine() throws IOException{
        currLine = csvParser.getLine();
        currLineNum = csvParser.getLastLineNumber();
        return currLine;
    }

    /**
     * 获取行号
     * @return
     */
    public int getCurrLineNum(){
        return currLineNum;
    }

    public static  void main(String[] args) throws IOException,DocumentException{
        InputStream  in = new FileInputStream(new File("C:\\Users\\gayyzxyx\\Desktop\\2013-02-02-17-01-46-msg-20.csv"));
        InputStreamReader reader = new InputStreamReader(in,"GBK");
        CsvFileParser parser = new CsvFileParser(reader);
        List<Message> messages = new ArrayList<Message>();
        Message message = new Message();
        File outFile = new File("c:\\2013-1.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
        byte[] bytes = new byte[8192];
        while(parser.hasMore()){
            message.setTime(parser.getByFieldName("发送时间"));
            String direction = parser.getByFieldName("收件人");
            if(direction.equals("我"))
                message.setDirection("from");
            else if(direction.equals("+8613797063382")|| direction.equals("13797063382")||direction.equals("小菇凉"))
                message.setDirection("to");
            message.setPhoneNumber("+8613797063382");
            message.setMessageContent(parser.getByFieldName("内容") + "");
            messages.add(message);
            System.out.print(message.getWholeMessage());
            bytes = message.getWholeMessage().getBytes();
            fileOutputStream.write(bytes);
        }
        fileOutputStream.write(("total:"+messages.size()).getBytes());
        fileOutputStream.flush();


        parser.close();
    }
}
