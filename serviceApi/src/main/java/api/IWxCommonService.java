package api;

import base.JsonResult;
import dto.AccessTokenDTO;

public interface IWxCommonService {

    public AccessTokenDTO getAccessToken();

    /**
     * 发送模板消息
     * @param touser 接收者openId
     */
    void sendMessageTemplate( String touser);
}
