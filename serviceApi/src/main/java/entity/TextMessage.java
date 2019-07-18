package entity;

import lombok.Data;

@Data
public class TextMessage {

    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private String Content;

}
