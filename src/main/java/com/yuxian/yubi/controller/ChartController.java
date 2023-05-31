package com.yuxian.yubi.controller;

import com.yuxian.yubi.common.BaseResponse;
import com.yuxian.yubi.common.ErrorCode;
import com.yuxian.yubi.common.ResultUtils;
import com.yuxian.yubi.exception.ThrowUtils;
import com.yuxian.yubi.model.dto.chart.ChartRequestDto;
import com.yuxian.yubi.service.ChartService;
import com.yuxian.yubi.utils.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author yuxian&羽弦
 * date 2023/05/29 22:31
 * description:
 * @version 1.0
 **/
@RestController
@RequestMapping("/char")
public class ChartController {

	@Resource
	private ChartService chartService;

	@PostMapping("/genChartQuestion")
	public BaseResponse<String> genChartQuestion(@RequestPart("file") MultipartFile multipartFile,
												  ChartRequestDto chartRequestDto) {
		//参数校验
		String name = chartRequestDto.getName();
		String goal = chartRequestDto.getGoal();
		ThrowUtils.throwIf(StringUtils.isBlank(goal), ErrorCode.PARAMS_ERROR, "分析目标不能为空");
		ThrowUtils.throwIf(StringUtils.isBlank(goal) || name.length() > 100, ErrorCode.PARAMS_ERROR, "图表名字长度不能超过100");
		return ResultUtils.success(chartService.genChartQuestion(multipartFile, chartRequestDto));
	}
}
