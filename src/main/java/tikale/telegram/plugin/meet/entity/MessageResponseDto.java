package tikale.telegram.plugin.meet.entity;

public class MessageResponseDto {

    private String message;
    private String chatName;
    private SendType type;
    private boolean disableWebPagePreview = true;

    public MessageResponseDto() {
        super();
    }

    public MessageResponseDto(String message, String chatName, SendType type) {
        super();
        this.message = message;
        this.chatName = chatName;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public SendType getType() {
        return type;
    }

    public void setType(SendType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MessageResponseDto [message=" + message + ", chatName=" + chatName + ", type=" + type + ", disableWebPagePreview=" + disableWebPagePreview
                + "]";
    }

    public boolean isDisableWebPagePreview() {
        return disableWebPagePreview;
    }

    public void setDisableWebPagePreview(boolean disableWebPagePreview) {
        this.disableWebPagePreview = disableWebPagePreview;
    }

}
