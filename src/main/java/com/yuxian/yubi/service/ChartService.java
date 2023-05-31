package com.yuxian.yubi.service;

import com.yuxian.yubi.common.BaseResponse;
import com.yuxian.yubi.model.dto.chart.ChartRequestDto;
import com.yuxian.yubi.model.entity.Chart;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author admin
* @description 针对表【chart(图表信息表)】的数据库操作Service
* @createDate 2023-05-27 16:32:46
*/
public interface ChartService extends IService<Chart> {

	/**
	 * 生成用户请求字符串
	 *
	 * @param multipartFile
	 * @param chartRequestDto
	 * @return
	 */
	String genChartQuestion(MultipartFile multipartFile, ChartRequestDto chartRequestDto);
}