package com.company.module.bean.report;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.MyProject.dao.ProjectUserRoleDao;
import com.MyProject.services.impl.ProjectServiceImpl;
import com.company.module.bean.common.AbstractBaseReportBean;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class UsersByProjectJasperReportsBean extends AbstractBaseReportBean {

	private ProjectUserRoleDao projectUserRoleDao;
	private ProjectServiceImpl projectServiceImpl;
	private final String COMPILE_FILE_NAME = "users";

	@Override
	protected String getCompileFileName() {
		return COMPILE_FILE_NAME;
	}

	@Override
	protected Map<String, Object> getReportParameters() {
		Map<String, Object> reportParameters = new HashMap<String, Object>();

		reportParameters.put("ReportTitle", "Users");

		return reportParameters;
	}

	@Override
	protected JRDataSource getJRDataSource() {
		// return your custom datasource implementation

		JRDataSource reportSource = new JRBeanCollectionDataSource(getProjectUserRoleDao().findUser(projectServiceImpl.getSelectedCurrentProject(), true));

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

	public ProjectUserRoleDao getProjectUserRoleDao() {
		return projectUserRoleDao;
	}

	public void setProjectUserRoleDao(ProjectUserRoleDao projectUserRoleDao) {
		this.projectUserRoleDao = projectUserRoleDao;
	}

	public ProjectServiceImpl getProjectServiceImpl() {
		return projectServiceImpl;
	}

	public void setProjectServiceImpl(ProjectServiceImpl projectServiceImpl) {
		this.projectServiceImpl = projectServiceImpl;
	}


}