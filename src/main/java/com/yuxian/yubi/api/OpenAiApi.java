package com.yuxian.yubi.api;

import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import com.yuxian.yubi.enums.ErrorCode;
import com.yuxian.yubi.exception.ThrowUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yuxian&羽弦
 * date 2023/06/02 10:39
 * description:
 * @version 1.0
 **/
@Service
public class OpenAiApi {

	@Resource
	private YuCongMingClient client;


	public String genChartAnalyse(Long modelId, String question) {
		DevChatRequest devChatRequest = new DevChatRequest();
		devChatRequest.setModelId(modelId);
		devChatRequest.setMessage(question);
		BaseResponse<DevChatResponse> response = client.doChat(devChatRequest);
		ThrowUtils.throwIf(response == null || response.getData() == null, ErrorCode.OPERATION_ERROR, "生成AI回答失败");
		return response.getData().getContent();
	}

}
