package com.yuxian.yubi.model.dto.chart;

import lombok.Data;

/**
 * @author yuxian&羽弦
 * date 2023/05/29 22:38
 * description:
 * @version 1.0
 **/
@Data
public class ChartRequestDto {

	/**
	 * 分析目标
	 */
	private String goal;
	/**
	 * 图表类型
	 */
	private String chartType;
	/**
	 * 图表名称
	 */
	private String name;

}
