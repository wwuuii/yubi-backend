package com.yuxian.yubi.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxian.yubi.bizmq.BiMessageProducer;
import com.yuxian.yubi.enums.ChartStatusEnum;
import com.yuxian.yubi.enums.ErrorCode;
import com.yuxian.yubi.exception.ThrowUtils;
import com.yuxian.yubi.mapper.ChartMapper;
import com.yuxian.yubi.model.dto.chart.req.GenChartAnalyseReqDto;
import com.yuxian.yubi.model.entity.Chart;
import com.yuxian.yubi.service.ChartService;
import com.yuxian.yubi.utils.ExcelUtils;
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
		implements ChartService {

	@Resource
	private ChartMapper chartMapper;
	@Resource
	private BiMessageProducer biMessageProducer;

	@Override
	public void genChartAnalyse(MultipartFile multipartFile, GenChartAnalyseReqDto genChartAnalyseReqDto) {
		String csvData = ExcelUtils.excelToCsv(multipartFile);

		//入库
		Chart chart = new Chart();
		chart.setUserId(genChartAnalyseReqDto.getUserId());
		chart.setChartData(csvData);
		chart.setStatus(ChartStatusEnum.WAIT.getCode());
		chart.setName(genChartAnalyseReqDto.getName());
		chart.setGoal(genChartAnalyseReqDto.getGoal());
		chart.setChartType(genChartAnalyseReqDto.getChartType());
		int saveResult = chartMapper.insert(chart);
		ThrowUtils.throwIf(saveResult <= 0, ErrorCode.SYSTEM_ERROR, "图表保存失败");

		//异步提交分析图表
//		ChartAnalyseJob chartAnalyseJob = new ChartAnalyseJob(chart.getId(), openAiApi, question, this, chartAnalyseOverflowService);
//		CompletableFuture.runAsync(() -> {
//			try {
//				retryer.call(chartAnalyseJob);
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//		}, threadPoolExecutor).handle((result, ex) -> {
//			if (!Objects.isNull(ex)) {
//				LambdaUpdateWrapper<Chart> updateWrapper = new LambdaUpdateWrapper<>();
//				updateWrapper.eq(Chart::getId, chart.getId()).set(Chart::getStatus, ChartStatusEnum.FAILED.getCode()).set(Chart::getExecMessage, ex.getMessage());
//				update(updateWrapper);
//			}
//			return "";
//		});
		//分析图表任务提交至消息队列
		biMessageProducer.sendMessage(String.valueOf(chart.getId()));

	}

	@Override
	public void updateChartStatus(Long id, Integer status) {
		LambdaUpdateWrapper<Chart> wrapper = new LambdaUpdateWrapper<>();
		wrapper.eq(Chart::getId, id).set(Chart::getStatus, status);
		boolean update = update(wrapper);
		ThrowUtils.throwIf(!update, ErrorCode.SYSTEM_ERROR, "图表状态更新失败");
	}


}




