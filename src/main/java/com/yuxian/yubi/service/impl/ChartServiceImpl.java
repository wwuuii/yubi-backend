package com.yuxian.yubi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxian.yubi.api.OpenAiApi;
import com.yuxian.yubi.enums.AIModelEnum;
import com.yuxian.yubi.enums.ChartTypeEnum;
import com.yuxian.yubi.enums.ErrorCode;
import com.yuxian.yubi.exception.ThrowUtils;
import com.yuxian.yubi.model.dto.chart.GenChartAnalyseReqDto;
import com.yuxian.yubi.model.dto.chart.GenChartAnalyseRespDto;
import com.yuxian.yubi.model.entity.Chart;
import com.yuxian.yubi.service.ChartService;
import com.yuxian.yubi.mapper.ChartMapper;
import com.yuxian.yubi.utils.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
* @author admin
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2023-05-27 16:32:46
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

	@Resource
	private OpenAiApi openAiApi;
	@Resource
	private ChartMapper chartMapper;


	@Override
	public GenChartAnalyseRespDto genChartAnalyse(MultipartFile multipartFile, GenChartAnalyseReqDto genChartAnalyseReqDto) {
		String csvData = ExcelUtils.excelToCsv(multipartFile);
		//生成用户需求
		String question = genUserDemand(csvData, genChartAnalyseReqDto);

		String chartAnalyseResult = openAiApi.genChartAnalyse(AIModelEnum.CHART_MODEL.getId(), question);
		ThrowUtils.throwIf(StringUtils.isBlank(chartAnalyseResult), ErrorCode.OPERATION_ERROR, "生成AI回答失败");
		String[] results = chartAnalyseResult.split("【【【【【");
		ThrowUtils.throwIf(results.length != 3, ErrorCode.OPERATION_ERROR, "生成AI回答失败");
		results[1] = results[1].substring(results[1].indexOf('{'), results[1].lastIndexOf('}') + 1);
		//入库
		Chart chart = new Chart();
		chart.setChartData(csvData);
		chart.setGenChart(results[1]);
		chart.setGenResult(results[2]);
		chart.setName(genChartAnalyseReqDto.getName());
		chart.setGoal(genChartAnalyseReqDto.getGoal());
		chart.setChartType(genChartAnalyseReqDto.getChartType());
		chartMapper.insert(chart);
		return new GenChartAnalyseRespDto(results[1], results[2], chart.getId());
	}

	private String genUserDemand(String csvData, GenChartAnalyseReqDto genChartAnalyseReqDto) {

		StringBuilder question = new StringBuilder();
		question.append("分析需求：\n").append("{").append(genChartAnalyseReqDto.getGoal())
				.append(",生成").append(ChartTypeEnum.LINE_CHART.getTypeName()).append("}\n")
				.append("原始数据:\n").append("{").append(csvData).append("}");
		return question.toString();
	}
}




