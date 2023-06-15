package com.yuxian.yubi.job.once;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yuxian.yubi.api.OpenAiApi;
import com.yuxian.yubi.enums.AIModelEnum;
import com.yuxian.yubi.enums.ChartStatusEnum;
import com.yuxian.yubi.enums.ErrorCode;
import com.yuxian.yubi.exception.BusinessException;
import com.yuxian.yubi.exception.ThrowUtils;
import com.yuxian.yubi.model.entity.Chart;
import com.yuxian.yubi.service.ChartAnalyseOverflowService;
import com.yuxian.yubi.service.ChartService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Callable;

/**
 * @author yuxian&羽弦
 * date 2023/06/14 20:52
 * description:
 * @version 1.0
 **/
@Data
public class ChartAnalyseJob implements Runnable {

	/**
	 * 图表Id
	 */
	private Long chartId;

	private OpenAiApi openAiApi;

	private String question;

	private ChartService chartService;

	private ChartAnalyseOverflowService chartAnalyseOverflowService;

	public ChartAnalyseJob(Long chartId, OpenAiApi openAiApi, String question, ChartService chartService, ChartAnalyseOverflowService chartAnalyseOverflowService) {
		this.chartId = chartId;
		this.openAiApi = openAiApi;
		this.question = question;
		this.chartService = chartService;
		this.chartAnalyseOverflowService = chartAnalyseOverflowService;
	}

	@Override
	public void run() {
		String chartAnalyseResult = openAiApi.genChartAnalyse(AIModelEnum.CHART_MODEL.getId(), question);
		ThrowUtils.throwIf(StringUtils.isBlank(chartAnalyseResult), ErrorCode.OPERATION_ERROR, "生成AI回答失败");
		String[] results = chartAnalyseResult.split("【【【【【");
		ThrowUtils.throwIf(results.length != 3, ErrorCode.OPERATION_ERROR, "生成AI回答失败");
		results[1] = results[1].substring(results[1].indexOf('{'), results[1].lastIndexOf('}') + 1);
		results[1] = removeTitle(results[1]);
		// 生成图表类
		LambdaUpdateWrapper<Chart> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(Chart::getId, chartId).set(Chart::getGenChart, results[1]).set(Chart::getGenResult, results[2]).set(Chart::getStatus, ChartStatusEnum.SUCCEED.getCode());
		boolean updateResult = chartService.update(updateWrapper);
		ThrowUtils.throwIf(!updateResult, ErrorCode.SYSTEM_ERROR, "分析结果更新数据库失败");
	}

	/**
	 * 隐藏图表的 title
	 * @param jsonString
	 * @return
	 */
	private String removeTitle(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode jsonNode = mapper.readTree(jsonString);
			if (jsonNode.has("title")) {
				// 如果 JSON 中有 "title" 属性，则将其移除
				ObjectNode objectNode = (ObjectNode) jsonNode;
				objectNode.remove("title");
				jsonString = mapper.writeValueAsString(objectNode);
			}
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.OPERATION_ERROR, "生成AI回答失败");
		}
		return jsonString;
	}
}
