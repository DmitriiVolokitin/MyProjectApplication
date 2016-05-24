package com.company.module.bean.report;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.MyProject.dao.TaskTimeTrackingDao;
import com.MyProject.services.impl.ProjectServiceImpl;
import com.company.module.bean.common.AbstractBaseReportBean;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS, value="session")
public class TimeTrackingJasperReportsBean extends AbstractBaseReportBean {

	private TaskTimeTrackingDao taskTimeTrackingDao;
	private ProjectServiceImpl projectServiceImpl;
	private final String COMPILE_FILE_NAME = "timeTraking";

	@Override
	protected String getCompileFileName() {
		return COMPILE_FILE_NAME;
	}

	@Override
	protected Map<String, Object> getReportParameters() {
		Map<String, Object> reportParameters = new HashMap<String, Object>();

		reportParameters.put("ReportTitle", "Time Tracking");

		return reportParameters;
	}

	@Override
	protected JRDataSource getJRDataSource() {
		// return your custom datasource implementation

		JRDataSource reportSource = new JRBeanCollectionDataSource(taskTimeTrackingDao.findByProject(projectServiceImpl.getSelectedCurrentProject()));

		//MyFirstJasperReportsDataSource dataSource = new MyFirstJasperReportsDataSource();

		return reportSource;
	}

	public String execute() {
		try {
			super.prepareReport();
		} catch (Exception e) {
			// make your own exception handling
			e.printStackTrace();
		}

		return null;
	}

	public ProjectServiceImpl getProjectServiceImpl() {
		return projectServiceImpl;
	}

	public void setProjectServiceImpl(ProjectServiceImpl projectServiceImpl) {
		this.projectServiceImpl = projectServiceImpl;
	}

	public TaskTimeTrackingDao getTaskTimeTrackingDao() {
		return taskTimeTrackingDao;
	}

	public void setTaskTimeTrackingDao(TaskTimeTrackingDao taskTimeTrackingDao) {
		this.taskTimeTrackingDao = taskTimeTrackingDao;
	}


}