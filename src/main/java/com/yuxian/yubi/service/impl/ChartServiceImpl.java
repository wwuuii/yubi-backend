package com.yuxian.yubi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxian.yubi.common.BaseResponse;
import com.yuxian.yubi.model.dto.chart.ChartRequestDto;
import com.yuxian.yubi.model.entity.Chart;
import com.yuxian.yubi.service.ChartService;
import com.yuxian.yubi.mapper.ChartMapper;
import com.yuxian.yubi.utils.ExcelUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
* @author admin
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2023-05-27 16:32:46
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

	@Override
	public String genChartQuestion(MultipartFile multipartFile, ChartRequestDto chartRequestDto) {
		String csvData = ExcelUtils.excelToCsv(multipartFile);
		StringBuilder result = new StringBuilder();
		result.append("你是一个数据分析师,接下来我给你我的分析目标和原始数据,请告诉我分析结果\n").append("分析目标：").append(chartRequestDto.getGoal())
				.append("\n").append("分析数据:\n").append(csvData);
		return result.toString();
	}
}




