/**
 * Created with IntelliJ IDEA.
 * User: gayyzxyx
 * Date: 13-2-2
 * Time: 下午2:36
 * To change this template use File | Settings | File Templates.
 */
public class Message {
    private String time;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    private String direction;
    private String phoneNumber;
    private String MessageContent;

    public String getWholeMessage() {
        return time+" "+this.direction+" "+phoneNumber+"\r\n"+MessageContent+"\r\n";
    }

    private String wholeMessage;
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
    }
}
