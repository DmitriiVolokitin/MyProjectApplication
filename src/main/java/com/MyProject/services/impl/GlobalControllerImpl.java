package com.MyProject.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;
import org.springframework.webflow.engine.RequestControlContext;
import org.springframework.webflow.execution.Event;
import org.primefaces.context.RequestContext;
import org.springframework.webflow.execution.RequestContextHolder;

import com.MyProject.domain.commons.ContractEntity;
import com.MyProject.domain.commons.ContractorOrganizationEntity;
import com.MyProject.domain.commons.CustomerOrganizationEntity;
import com.MyProject.domain.commons.FormatTimeTrackingEntity;
import com.MyProject.domain.commons.ProjectUserRoleEntity;
import com.MyProject.domain.project.ProjectCostSettingEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.project.ProjectFileEntity;
import com.MyProject.domain.project.ProjectMilestoneEntity;
import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskFileEntity;
import com.MyProject.domain.task.TaskHistoryEntity;
import com.MyProject.domain.task.TaskPriorityEntity;
import com.MyProject.domain.task.TaskStatusEntity;
import com.MyProject.domain.task.TaskStatusHistoryEntity;
import com.MyProject.domain.task.TaskTimeTrackingEntity;
import com.MyProject.domain.task.TaskTypeEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.domain.user.UserRoleEntity;
import com.MyProject.domain.user.UserSettingEntity;
import com.MyProject.email.Email;
import com.MyProject.dao.ContractDao;
import com.MyProject.dao.ContractorOrganizationDao;
import com.MyProject.dao.CustomerOrganizationDao;
import com.MyProject.dao.FormatTimeTrackingDao;
import com.MyProject.dao.ProjectCostSettingDao;
import com.MyProject.dao.ProjectDao;
import com.MyProject.dao.ProjectFileDao;
import com.MyProject.dao.ProjectMilestoneDao;
import com.MyProject.dao.ProjectUserRoleDao;
import com.MyProject.dao.TaskDao;
import com.MyProject.dao.TaskFileDao;
import com.MyProject.dao.TaskHistoryDao;
import com.MyProject.dao.TaskPriorityDao;
import com.MyProject.dao.TaskStatusDao;
import com.MyProject.dao.TaskStatusHistoryDao;
import com.MyProject.dao.TaskTimeTrackingDao;
import com.MyProject.dao.TaskTypeDao;
import com.MyProject.dao.UserDao;
import com.MyProject.dao.UserRoleDao;
import com.MyProject.dao.UserSettingDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class GlobalControllerImpl implements Serializable {

	private static final long serialVersionUID = 7852979771015680707L;


	private TaskServiceImpl 					taskServiceImpl;
	private TaskTimeTrackingServiceImpl 		taskTimeTrackingServiceImpl;
	private TaskHistoryServiceImpl 				taskHistoryServiceImpl;
	private TaskStatusHistoryServiceImpl		taskStatusHistoryServiceImpl;
	private TaskStatusServiceImpl				taskStatusServiceImpl;
	private TaskTypeServiceImpl					taskTypeServiceImpl;
	private TaskPriorityServiceImpl				taskPriorityServiceImpl;
	private TaskFileServiceImpl 				taskFileServiceImpl;
	private UserServiceImpl 					userServiceImpl;
	private UserRoleServiceImpl 				userRoleServiceImpl;
	private UserSettingServiceImpl				userSettingServiceImpl;
	private ContractServiceImpl					contractServiceImpl;
	private ContractorOrganizationServiceImpl 	contractorOrganizationServiceImpl;
	private CustomerOrganizationServiceImpl   	customerOrganizationServiceImpl;
	private ProjectServiceImpl					projectServiceImpl;
	private ProjectFileServiceImpl				projectFileServiceImpl;
	private ProjectMilestoneServiceImpl			projectMilestoneServiceImpl;
	private ProjectCostSettingEntityServiceImpl projectCostSettingEntityServiceImpl;
	private ProjectUserRoleServiceImpl			projectUserRoleServiceImpl;
	private FormatTimeTrackingServiceImpl		formatTimeTrackingServiceImpl;


	private ProjectDao 						projectDao;
	private FormatTimeTrackingDao			formatTimeTrackingDao;
	private UserDao 						userDao;
	private UserRoleDao						userRoleDao;
	private UserSettingDao					userSettingDao;
	private TaskDao 						taskDao;
	private TaskFileDao 					taskFileDao;
	private TaskStatusHistoryDao			taskStatusHistoryDao;
	private TaskTimeTrackingDao				taskTimeTrackingDao;
	private TaskHistoryDao					taskHistoryDao;
	private TaskStatusDao					taskStatusDao;
	private TaskTypeDao						taskTypeDao;
	private TaskPriorityDao					taskPriorityDao;
	private ContractDao 					contractDao;
	private ContractorOrganizationDao 		contractorOrganizationDao;
	private CustomerOrganizationDao 		customerOrganizationDao;
	private ProjectFileDao					projectFileDao;
	private ProjectMilestoneDao				projectMilestoneDao;
	private ProjectUserRoleDao				projectUserRoleDao;
	private ProjectCostSettingDao			projectCostSettingDao;

	int menuIndex 			= 0;
	int taskTabView 		= 0;
	int directoryTabView 	= 0;
	int settingTabView 		= 0;

	private List<String> images;
	boolean flagNewUser = false;

	String reportType 								= "";
	UserEntity reportUser 							= null;
	List<UserEntity> reportUsers 					= null;
	List<UserEntity> selectedReportUsers 			= null;
	String selectedValuesReportUsersDisplayString 	= "";
	Date reportDataStart 							= new Date();
	Date reportDataFinish 							= new Date();

	@PostConstruct
	public void init() {
		System.out.println("New bean Global");
		setReportType("Cost and Time");
		initImages();
	}

	public void initImages() {
		images = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {

			images.add("nature" + i + ".jpg");
		}
	}

	public void projectMilestoneCheckAvailableNameByProject(FacesContext arg0, UIComponent arg1, Object val)
			throws ValidatorException {

		long projectID 	 = (long) arg1.getAttributes().get("projectID");
		long milestoneID = -1;

		if (arg1.getAttributes().get("milestoneID") != null){
			milestoneID = (long) arg1.getAttributes().get("milestoneID");
		}

		String name = (String) val;
		ProjectEntity projectEntity = projectDao.findById(projectID);
		ProjectMilestoneEntity milestoneEntity = projectMilestoneDao.findById(milestoneID);

		boolean available = projectMilestoneDao.checkAvailableByProject(projectEntity, name);

		if (milestoneEntity == null){
			if (!available) {
				FacesMessage message = new FacesMessage();
				message.setDetail("Please enter a valid Name");
				message.setSummary("Name not valid");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}
		else{
			if(!milestoneEntity.getName().equals(name)) {
				if (!available) {
					FacesMessage message = new FacesMessage();
					message.setDetail("Please enter a valid Name");
					message.setSummary("Name not valid");
					message.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(message);
				}
			}
		}
	}

	public void formatTimeTrackingCheckAvailableNameByProject(FacesContext arg0, UIComponent arg1, Object val)
			throws ValidatorException {

		long projectID = (long) arg1.getAttributes().get("projectID");
		String name = (String) val;
		ProjectEntity projectEntity = projectDao.findById(projectID);
		boolean available = formatTimeTrackingDao.checkAvailableByProject(projectEntity, name);

		long currentObjectId = -1;
		if (arg1.getAttributes().get("currentObjectId") != null){
			currentObjectId = (long) arg1.getAttributes().get("currentObjectId");
			FormatTimeTrackingEntity currentObjectEntity = formatTimeTrackingDao.findById(currentObjectId);
			if(currentObjectEntity !=null) {
				if(!currentObjectEntity.getName().equals(name)){
					if (!available) {
						FacesMessage message = new FacesMessage();
						message.setDetail("Please enter a valid Name");
						message.setSummary("Name not valid");
						message.setSeverity(FacesMessage.SEVERITY_ERROR);
						throw new ValidatorException(message);
					}
				}
			}
		}
		else{
			if (!available) {
				FacesMessage message = new FacesMessage();
				message.setDetail("Please enter a valid Name");
				message.setSummary("Name not valid");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}

	}

	public void taskTypeCheckAvailableNameByProject(FacesContext arg0, UIComponent arg1, Object val)
			throws ValidatorException {

		long projectID = (long) arg1.getAttributes().get("projectID");
		String name = (String) val;
		ProjectEntity projectEntity = projectDao.findById(projectID);
		boolean available = taskTypeDao.checkAvailableByProject(projectEntity, name);

		long currentObjectId = -1;
		if (arg1.getAttributes().get("currentObjectId") != null){
			currentObjectId = (long) arg1.getAttributes().get("currentObjectId");
			TaskTypeEntity currentObjectEntity = taskTypeDao.findById(currentObjectId);
			if(currentObjectEntity !=null) {
				if(!currentObjectEntity.getName().equals(name)){
					if (!available) {
						FacesMessage message = new FacesMessage();
						message.setDetail("Please enter a valid Name");
						message.setSummary("Name not valid");
						message.setSeverity(FacesMessage.SEVERITY_ERROR);
						throw new ValidatorException(message);
					}
				}
			}
		}
		else{
			if (!available) {
				FacesMessage message = new FacesMessage();
				message.setDetail("Please enter a valid Name");
				message.setSummary("Name not valid");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}
	}

	public void taskStatusCheckAvailableNameByProject(FacesContext arg0, UIComponent arg1, Object val)
			throws ValidatorException {

		long projectID = (long) arg1.getAttributes().get("projectID");
		String name = (String) val;
		ProjectEntity projectEntity = projectDao.findById(projectID);
		boolean available = taskStatusDao.checkAvailableByProject(projectEntity, name);

		long currentObjectId = -1;
		if (arg1.getAttributes().get("currentObjectId") != null){
			currentObjectId = (long) arg1.getAttributes().get("currentObjectId");
			TaskStatusEntity currentObjectEntity = taskStatusDao.findById(currentObjectId);
			if(currentObjectEntity !=null) {
				if(!currentObjectEntity.getName().equals(name)){
					if (!available) {
						FacesMessage message = new FacesMessage();
						message.setDetail("Please enter a valid Name");
						message.setSummary("Name not valid");
						message.setSeverity(FacesMessage.SEVERITY_ERROR);
						throw new ValidatorException(message);
					}
				}
			}
		}
		else{
			if (!available) {
				FacesMessage message = new FacesMessage();
				message.setDetail("Please enter a valid Name");
				message.setSummary("Name not valid");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}

	}

	public void taskPriorityCheckAvailableNameByProject(FacesContext arg0, UIComponent arg1, Object val)
			throws ValidatorException {

		long projectID = (long) arg1.getAttributes().get("projectID");
		String name = (String) val;
		ProjectEntity projectEntity = projectDao.findById(projectID);
		boolean available = taskPriorityDao.checkAvailableByProject(projectEntity, name);

		long currentObjectId = -1;
		if (arg1.getAttributes().get("currentObjectId") != null){
			currentObjectId = (long) arg1.getAttributes().get("currentObjectId");
			TaskPriorityEntity currentObjectEntity = taskPriorityDao.findById(currentObjectId);
			if(currentObjectEntity !=null) {
				if(!currentObjectEntity.getName().equals(name)){
					if (!available) {
						FacesMessage message = new FacesMessage();
						message.setDetail("Please enter a valid Name");
						message.setSummary("Name not valid");
						message.setSeverity(FacesMessage.SEVERITY_ERROR);
						throw new ValidatorException(message);
					}
				}
			}
		}
		else{
			if (!available) {
				FacesMessage message = new FacesMessage();
				message.setDetail("Please enter a valid Name");
				message.setSummary("Name not valid");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}
	}

	public void customerOrganizationCheckAvailableNameByProject(FacesContext arg0, UIComponent arg1, Object val)
			throws ValidatorException {

		long projectID = (long) arg1.getAttributes().get("projectID");
		String name = (String) val;
		ProjectEntity projectEntity = projectDao.findById(projectID);
		boolean available = customerOrganizationDao.checkAvailableByProject(projectEntity, name);

		long currentObjectId = -1;
		if (arg1.getAttributes().get("currentObjectId") != null){
			currentObjectId = (long) arg1.getAttributes().get("currentObjectId");
			CustomerOrganizationEntity currentObjectEntity = customerOrganizationDao.findById(currentObjectId);
			if(currentObjectEntity !=null) {
				if(!currentObjectEntity.getName().equals(name)){
					if (!available) {
						FacesMessage message = new FacesMessage();
						message.setDetail("Please enter a valid Name");
						message.setSummary("Name not valid");
						message.setSeverity(FacesMessage.SEVERITY_ERROR);
						throw new ValidatorException(message);
					}
				}
			}
		}
		else{
			if (!available) {
				FacesMessage message = new FacesMessage();
				message.setDetail("Please enter a valid Name");
				message.setSummary("Name not valid");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}

	}

	public void contractorOrganizationCheckAvailableNameByProject(FacesContext arg0, UIComponent arg1, Object val)
			throws ValidatorException {

		long projectID = (long) arg1.getAttributes().get("projectID");
		String name = (String) val;
		ProjectEntity projectEntity = projectDao.findById(projectID);
		boolean available = contractorOrganizationDao.checkAvailableByProject(projectEntity, name);

		long currentObjectId = -1;
		if (arg1.getAttributes().get("currentObjectId") != null){
			currentObjectId = (long) arg1.getAttributes().get("currentObjectId");
			ContractorOrganizationEntity currentObjectEntity = contractorOrganizationDao.findById(currentObjectId);
			if(currentObjectEntity !=null) {
				if(!currentObjectEntity.getName().equals(name)){
					if (!available) {
						FacesMessage message = new FacesMessage();
						message.setDetail("Please enter a valid Name");
						message.setSummary("Name not valid");
						message.setSeverity(FacesMessage.SEVERITY_ERROR);
						throw new ValidatorException(message);
					}
				}
			}
		}
		else{
			if (!available) {
				FacesMessage message = new FacesMessage();
				message.setDetail("Please enter a valid Name");
				message.setSummary("Name not valid");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}

	}

	public void contractCheckAvailableNameByProject(FacesContext arg0, UIComponent arg1, Object val)
			throws ValidatorException {

		long projectID 	= (long) arg1.getAttributes().get("projectID");
		long contractID = -1;

		if (arg1.getAttributes().get("contractID") != null){
			contractID = (long) arg1.getAttributes().get("contractID");
		}

		String name 					= (String) val;
		ProjectEntity projectEntity   	= projectDao.findById(projectID);
		ContractEntity contractEntity 	= contractDao.findById(contractID);

		boolean available = contractDao.checkAvailableByProject(projectEntity, name);

		if (contractEntity == null){
			if (!available) {
				FacesMessage message = new FacesMessage();
				message.setDetail("Please enter a valid Name");
				message.setSummary("Name not valid");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}
		else {
			if(!contractEntity.getName().equals(name)) {
				if (!available) {
					FacesMessage message = new FacesMessage();
					message.setDetail("Please enter a valid Name");
					message.setSummary("Name not valid");
					message.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(message);
				}
			}
		}
	}

	public void onTaskTabViewChange(TabChangeEvent event) {
		TabView tv = (TabView) event.getComponent(); 
		this.taskTabView = tv.getChildren().indexOf(event.getTab());
	}

	public void onDirectoryTabViewChange(TabChangeEvent event) {
		TabView tv = (TabView) event.getComponent(); 
		this.directoryTabView = tv.getChildren().indexOf(event.getTab());
	}

	public void onSettingTabViewChange(TabChangeEvent event) {
		TabView tv = (TabView) event.getComponent(); 
		this.settingTabView = tv.getChildren().indexOf(event.getTab());
	}

	// Reports	
	public BarChartModel initBarModel(String reportType, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		BarChartModel model = new BarChartModel();

		ChartSeries boys = new ChartSeries();
		boys.setLabel("Boys");
		boys.set("2004", 120);
		boys.set("2005", 100);
		boys.set("2006", 44);
		boys.set("2007", 150);
		boys.set("2008", 25);

		ChartSeries girls = new ChartSeries();
		girls.setLabel("Girls");
		girls.set("2004", 52);
		girls.set("2005", 60);
		girls.set("2006", 110);
		girls.set("2007", 135);
		girls.set("2008", 120);

		model.addSeries(boys);
		model.addSeries(girls);

		return model;
	}

	public void preProcessXLS(Object document){

		//		HSSFWorkbook wb = (HSSFWorkbook) document;
		//        HSSFSheet sheet = wb.getSheetAt(0);
		//        HSSFRow header = sheet.getRow(0);
		//
		//        HSSFCellStyle cellStyle = wb.createCellStyle();
		//        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		//        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//
		//        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
		//            HSSFCell cell = header.getCell(i);
		//
		//            cell.setCellValue(cell.getStringCellValue().toUpperCase());
		//            cell.setCellStyle(cellStyle);
		//
		//            sheet.autoSizeColumn(i);
		//        }

	}

	public void postProcessXLS(Object document){

		//		HSSFWorkbook wb = (HSSFWorkbook) document;
		//		HSSFSheet sheet = wb.getSheetAt(0);
		//		HSSFRow header = sheet.getRow(0);
		//
		//		HSSFCellStyle cellStyle = wb.createCellStyle();
		//		cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//
		//		int colToRemove = 0;
		//
		//		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
		//
		//			HSSFCell cell = header.getCell(i);
		//
		//			if (cell.toString().equals("Operations") 
		//					|| cell.toString().equals("View") 
		//					|| cell.toString().equals("Delete")) { 
		//				colToRemove = i;
		//			}
		//			else{
		//				sheet.autoSizeColumn(i);
		//			}
		//
		//		}
		//
		//		HSSFRow row;
		//		HSSFCell cell;
		//		Iterator rowIter = sheet.iterator();
		//
		//		if (colToRemove != 0) {
		//			while (rowIter.hasNext()) {
		//				row = (HSSFRow) rowIter.next();
		//				cell = row.getCell (colToRemove);
		//				row.removeCell(cell);
		//			}
		//		}

	}

	public HorizontalBarChartModel createHorizontalBarModel(String reportType, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		ProjectEntity currentProject = projectServiceImpl.getSelectedCurrentProject();

		HorizontalBarChartModel horizontalBarModel = new HorizontalBarChartModel();

		if (reportType.equals("Time")) {

			List<Object[]> resultQuery = taskTimeTrackingDao.getTimeByProjectAndUsers(currentProject, dateStart, dateFinish, users);

			ChartSeries Accepted = new ChartSeries();
			Accepted.setLabel("Accepted");

			ChartSeries notAccepted = new ChartSeries();
			notAccepted.setLabel("Not accepted");

			ArrayList<String> acceptedData = new ArrayList<String>();
			ArrayList<String> notAcceptedData = new ArrayList<String>();

			for (int i = 0; i < resultQuery.size(); i++) {

				String getDisplayName = ((UserEntity) resultQuery.get(i)[0]).getDisplayName();

				if ((boolean)resultQuery.get(i)[1]){

					Accepted.set(getDisplayName, (Double) resultQuery.get(i)[2]);
					acceptedData.add(getDisplayName);
				}
				else{
					notAccepted.set(getDisplayName, (Double) resultQuery.get(i)[2]);
					notAcceptedData.add(getDisplayName);
				}

			}

			if (resultQuery.size() <= 0) {
				Accepted.set("Data will appear here", 100);
				notAccepted.set("Data will appear here", 100);
			}
			else {

				for(String foundAcceptedData: acceptedData){
					if(!notAcceptedData.contains(foundAcceptedData)){
						notAccepted.set(foundAcceptedData, 0);
					}
				}

				for(String foundNotAcceptedData: notAcceptedData){
					if(!acceptedData.contains(foundNotAcceptedData)){
						Accepted.set(foundNotAcceptedData, 0);
					}
				}
			}

			horizontalBarModel.addSeries(Accepted);
			horizontalBarModel.addSeries(notAccepted);

			Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
			//xAxis.setLabel("Time");
			xAxis.setMin(0);
			xAxis.setMax(100);

			//Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
			//yAxis.setLabel("Employees");

		}

		if (reportType.equals("Cost")) {

			List<Object[]> resultQuery = taskTimeTrackingDao.getCostByProjectAndUsers(currentProject, dateStart, dateFinish, users);

			ChartSeries Accepted = new ChartSeries();
			Accepted.setLabel("Accepted");

			ChartSeries notAccepted = new ChartSeries();
			notAccepted.setLabel("Not accepted");

			ArrayList<String> acceptedData = new ArrayList<String>();
			ArrayList<String> notAcceptedData = new ArrayList<String>();

			for (int i = 0; i < resultQuery.size(); i++) {

				String getDisplayName = ((String) resultQuery.get(i)[0]);

				if ((boolean)resultQuery.get(i)[1]){

					Accepted.set(getDisplayName, (Double) resultQuery.get(i)[2]);
					acceptedData.add(getDisplayName);
				}
				else{
					notAccepted.set(getDisplayName, (Double) resultQuery.get(i)[2]);
					notAcceptedData.add(getDisplayName);
				}

			}

			if (resultQuery.size() <= 0) {
				Accepted.set("Data will appear here", 100);
				notAccepted.set("Data will appear here", 100);
			}
			else {

				for(String foundAcceptedData: acceptedData){
					if(!notAcceptedData.contains(foundAcceptedData)){
						notAccepted.set(foundAcceptedData, 0);
					}
				}

				for(String foundNotAcceptedData: notAcceptedData){
					if(!acceptedData.contains(foundNotAcceptedData)){
						Accepted.set(foundNotAcceptedData, 0);
					}
				}
			}

			horizontalBarModel.addSeries(Accepted);
			horizontalBarModel.addSeries(notAccepted);

			Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
			//xAxis.setLabel("Time");
			xAxis.setMin(0);

			//Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
			//yAxis.setLabel("Employees");

		}

		if (reportType.equals("TaskDeadline")) {

			List<Object[]> resultQuery = taskDao.countTaskDeadLineByProjectAndUsers(currentProject, dateStart, dateFinish, users);

			ChartSeries Done = new ChartSeries();
			Done.setLabel("Done");

			ChartSeries Requared = new ChartSeries();
			Requared.setLabel("Requared");

			String taskName = "";

			for (int i = 0; i < resultQuery.size(); i++) {

				taskName = "Task #" + (String) resultQuery.get(i)[0].toString();
				//+ " " + (String) resultQuery.get(i)[1].toString();

				Done.set(taskName, (Double) resultQuery.get(i)[2]);
				Requared.set(taskName, 100 - ((Double) resultQuery.get(i)[2]));
			}

			if (resultQuery.size() <= 0) {
				Done.set("Data will appear here", 50);
				Requared.set("Data will appear here", 50);
			}

			horizontalBarModel.addSeries(Done);
			horizontalBarModel.addSeries(Requared);

			Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
			//			xAxis.setLabel("Done %");
			xAxis.setMin(0);
			xAxis.setMax(100);

			//			Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
			//			yAxis.setLabel("Task");

		}

		horizontalBarModel.setLegendPosition("e");
		horizontalBarModel.setStacked(true);
		horizontalBarModel.setAnimate(true);

		horizontalBarModel.setExtender("skinHorizantalBar");

		return horizontalBarModel;
	}

	public PieChartModel createPieModel(String reportType, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		PieChartModel pieModel = new PieChartModel();

		if (reportType.equals("Time")) {

			List<Object[]> resultQuery = taskTimeTrackingDao.countTimeByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				if ((boolean)resultQuery.get(i)[0]){
					pieModel.set("Accepted", (Double) resultQuery.get(i)[1]);
				}
				else{
					pieModel.set("Not accepted", (Double) resultQuery.get(i)[1]);
				}
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}

		}

		if (reportType.equals("Cost")) {

			List<Object[]> resultQuery = taskTimeTrackingDao.countCostByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				if ((boolean)resultQuery.get(i)[0]){
					pieModel.set("Accepted", (Double) resultQuery.get(i)[1]);
				}
				else{
					pieModel.set("Not accepted", (Double) resultQuery.get(i)[1]);
				}
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}

		}

		if (reportType.equals("CostFormatTimeTrackingAccepted")) {

			List<Object[]> resultQuery = taskTimeTrackingDao.countCostFormatTimeTrackingByProjectAcceptedAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Double) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}

		}

		if (reportType.equals("TimeFormatTimeTrackingAccepted")) {

			List<Object[]> resultQuery = taskTimeTrackingDao.countTimeFormatTimeTrackingByProjectAcceptedAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Double) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}

		}

		if (reportType.equals("CostFormatTimeTrackingNotAccepted")) {

			List<Object[]> resultQuery = taskTimeTrackingDao.countCostFormatTimeTrackingByProjectNotAcceptedAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Double) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}

		}

		if (reportType.equals("TimeFormatTimeTrackingNotAccepted")) {

			List<Object[]> resultQuery = taskTimeTrackingDao.countTimeFormatTimeTrackingByProjectNotAcceptedAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Double) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}

		}

		if (reportType.equals("FormatTimeTracking")) {

			List<Object[]> resultQuery = taskTimeTrackingDao.countFormatTimeTrackingByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}

		}

		if (reportType.equals("TaskType")) {

			List<Object[]> resultQuery = taskDao.countTaskTypeByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}

		}

		if (reportType.equals("TaskDeadlineType")) {

			List<Object[]> resultQuery = taskDao.countTaskDeadlineTypeByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}

		}

		if (reportType.equals("TaskStatus")) {

			List<Object[]> resultQuery = taskDao.countTaskStatusByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}

		}

		if (reportType.equals("TaskDeadlineStatus")) {

			List<Object[]> resultQuery = taskDao.countTaskDeadlineStatusByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}

		}

		if (reportType.equals("TaskPriority")) {

			List<Object[]> resultQuery = taskDao.countTaskPriorityByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}

		}

		if (reportType.equals("TaskDeadlinePriority")) {

			List<Object[]> resultQuery = taskDao.countTaskDeadlinePriorityByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}

		}

		pieModel.setLegendPosition("w");
		pieModel.setExtender("skinPie");

		return pieModel;
	}

	public LineChartModel initLinearModel(String reportType, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		ProjectEntity currentProject = projectServiceImpl.getSelectedCurrentProject();

		List<Object[]> resultQuery = taskHistoryDao.countTaskActivityByProject(currentProject, currentProject.getStartDate(), currentProject.getFinishDate());

		LineChartModel model = new LineChartModel();

		LineChartSeries lineChartSeries = new LineChartSeries();
		lineChartSeries.setLabel("Task Activity");

		for (int i = 0; i < resultQuery.size(); i++) {

			lineChartSeries.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);

		}

		if (resultQuery.size() <= 0) {
			lineChartSeries.set(currentProject.getStartDate().toString(), 0);
		}

		model.addSeries(lineChartSeries);

		model.setLegendPosition("e");

		model.getAxis(AxisType.Y);
		DateAxis axis = new DateAxis();
		axis.setTickAngle(-51);
		axis.setMin(currentProject.getStartDate().toString());
		axis.setMax(currentProject.getFinishDate().toString());
		axis.setTickFormat("%b %#d, %y");
		model.getAxes().put(AxisType.X, axis);
		model.setAnimate(true);

		model.setExtender("skinChart");

		return model;
	}

	public void reportUpdate(AjaxBehaviorEvent event) {

		org.springframework.webflow.execution.RequestContext requestContext = RequestContextHolder.getRequestContext();
		RequestControlContext rec = (RequestControlContext) requestContext;
		//place variables you need in next flow phase here; flash,application,session scope
		rec.handleEvent(new Event(this, "MenuReports"));
		return;
	}

	public void reportUpdate() {

		org.springframework.webflow.execution.RequestContext requestContext = RequestContextHolder.getRequestContext();
		RequestControlContext rec = (RequestControlContext) requestContext;
		//place variables you need in next flow phase here; flash,application,session scope
		rec.handleEvent(new Event(this, "MenuReports"));
		return;
	}

	public List<String> getImages() {
		return images;
	}


	public String onFlowProcess(FlowEvent event) {  

		return event.getNewStep();  
	}


	public void curentPageIsProjects() {

		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot view = context.getViewRoot();
		String viewId = view.getViewId();

		if (viewId.toLowerCase().contains("projects.xhtml")) {
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.update(":projectsForm:projectsUsers");
			requestContext.update(":projectsForm:projectsUserRoles");
			requestContext.update(":projectsForm:projects");
		}
	}


	public void changeListenerCurrentProjectChange(AjaxBehaviorEvent event) {

		currentProjectChange(projectServiceImpl.getSelectedCurrentProject());
		setMenuIndex(0);

		org.springframework.webflow.execution.RequestContext requestContext = RequestContextHolder.getRequestContext();
		RequestControlContext rec = (RequestControlContext) requestContext;
		//place variables you need in next flow phase here; flash,application,session scope
		rec.handleEvent(new Event(this, "MenuTask"));
		return;
	}


	public void changeListenerEndWizard() {

		//		setMenuIndex(0);

		//		RequestContext requestContext = RequestContextHolder.getRequestContext();
		//		RequestControlContext rec = (RequestControlContext) requestContext;
		//		//place variables you need in next flow phase here; flash,application,session scope
		//		rec.handleEvent(new Event(this, "MenuTask"));
	}

	public void initAppByUser(UserEntity userEntity) {

		setFlagNewUser(false);

		projectUserRoleServiceImpl.setProjects(projectUserRoleDao.findProject(userEntity, true));
		projectUserRoleServiceImpl.setProjectsByProjectManager(projectUserRoleDao.findProjectByUserRoleProjectMnager(userEntity, userRoleDao.findByName("Project Manager"), true));

		if (projectUserRoleServiceImpl.getProjects().isEmpty() || projectUserRoleServiceImpl.getProjectsByProjectManager().isEmpty()) {

			setFlagNewUser(true);

			projectUserRoleServiceImpl.setNewProject(projectServiceImpl.createProjectForNewUser(userEntity));

			projectUserRoleServiceImpl.setUserRoles(projectUserRoleDao.findUserRole(projectUserRoleServiceImpl.getNewProject(), userEntity, true));

			if (formatTimeTrackingDao.countEntityByProject(projectUserRoleServiceImpl.getNewProject()) <= 0) {
				formatTimeTrackingServiceImpl.createTaskFormatTimeTrackingByProjectOnStartNewDataBase(projectUserRoleServiceImpl.getNewProject());
			}

			userSettingServiceImpl.createUserSettingOnStartNewDataBase(projectUserRoleServiceImpl.getNewProject(), userEntity, formatTimeTrackingDao.findByProjectAndName(projectUserRoleServiceImpl.getNewProject(),"By time"));

			if (projectUserRoleServiceImpl.getUserRoles().isEmpty()) {
				projectUserRoleServiceImpl.setNewUserRole(userRoleDao.findByName("Project Manager"));
			}

			projectUserRoleServiceImpl.createProjectUserRole(projectUserRoleServiceImpl.getNewProject(), userEntity, projectUserRoleServiceImpl.getNewUserRole());
			projectUserRoleServiceImpl.createProjectUserRole(projectUserRoleServiceImpl.getNewProject(), userEntity, userRoleDao.findByName("Base"));
		}
		projectUserRoleServiceImpl.setProjects(projectUserRoleDao.findProject(userEntity, true));
		projectUserRoleServiceImpl.setProjectsByProjectManager(projectUserRoleDao.findProjectByUserRoleProjectMnager(userEntity, userRoleDao.findByName("Project Manager"), true));
		projectServiceImpl.setProjects(projectUserRoleServiceImpl.getProjects());

		ProjectEntity projectOpenByDefault = userSettingDao.findByUser(userEntity).getProjectByDefault();

		if (projectOpenByDefault != null) {
			projectServiceImpl.setSelectedCurrentProject(projectOpenByDefault);
			initByProject(projectOpenByDefault);
			projectUserRoleServiceImpl.setSelectedProject(projectOpenByDefault);
		}

		// Create on start new project: contract, contractorOrganization, 
		// customerOrganization, projectMilestone, projectCostSetting, userSetting
		if (contractDao.countEntityByProject(projectOpenByDefault) <= 0) {
			contractServiceImpl.createContractOnStartNewDataBase(projectOpenByDefault);
		}
		if (contractorOrganizationDao.countEntityByProject(projectOpenByDefault) <= 0) {
			contractorOrganizationServiceImpl.createContractorOrganizationOnStartNewDataBase(projectOpenByDefault);
		}
		if (customerOrganizationDao.countEntityByProject(projectOpenByDefault) <= 0) {
			customerOrganizationServiceImpl.createCustomerOrganizationOnStartNewDataBase(projectOpenByDefault);
		}
		if (projectMilestoneDao.countEntityByProject(projectOpenByDefault) <= 0) {
			projectMilestoneServiceImpl.createProjectMilestoneOnStartNewDataBase(projectOpenByDefault);
		}
		if (userSettingDao.countEntityByUserAndProject(userEntity, projectOpenByDefault) <= 0) {
			userSettingServiceImpl.createUserSettingOnStartNewDataBase(projectOpenByDefault, userEntity, formatTimeTrackingDao.findByProjectAndName(projectOpenByDefault, "By time"));
		}
		if (taskStatusDao.countEntityByProject(projectOpenByDefault) <= 0) {
			taskStatusServiceImpl.createTaskStatusByProjectOnStartNewDataBase(projectOpenByDefault);
		}
		if (taskTypeDao.countEntityByProject(projectOpenByDefault) <= 0) {
			taskTypeServiceImpl.createTaskTypeByProjectOnStartNewDataBase(projectOpenByDefault);
		}
		if (taskPriorityDao.countEntityByProject(projectOpenByDefault) <= 0) {
			taskPriorityServiceImpl.createTaskPriorityByProjectOnStartNewDataBase(projectOpenByDefault);
		}
		if (formatTimeTrackingDao.countEntityByProject(projectOpenByDefault) <= 0) {
			formatTimeTrackingServiceImpl.createTaskFormatTimeTrackingByProjectOnStartNewDataBase(projectOpenByDefault);
		}
		if (projectCostSettingDao.countEntityByProject(projectOpenByDefault) <= 0) {
			projectCostSettingEntityServiceImpl.createProjectCostSettingOnStartNewDataBase(projectOpenByDefault);
		}

		// Update contract, contractorOrganization, 
		// customerOrganization, projectMilestone, projectCostSettings, userSetting
		contractServiceImpl.setContracts(contractDao.findByProject(projectOpenByDefault));
		contractorOrganizationServiceImpl.setContractorOrganizations(contractorOrganizationDao.findByProject(projectOpenByDefault));
		customerOrganizationServiceImpl.setCustomerOrganizations(customerOrganizationDao.findByProject(projectOpenByDefault));
		projectMilestoneServiceImpl.setProjectMilestones(projectMilestoneDao.findByProject(projectOpenByDefault));

		taskStatusServiceImpl.setTaskStatuses(taskStatusDao.findByProject(projectOpenByDefault));
		taskTypeServiceImpl.setTaskTypes(taskTypeDao.findByProject(projectOpenByDefault));
		taskPriorityServiceImpl.setTaskPriorities(taskPriorityDao.findByProject(projectOpenByDefault));
		formatTimeTrackingServiceImpl.setFormatTimeTrackings(formatTimeTrackingDao.findByProject(projectOpenByDefault));

		projectCostSettingEntityServiceImpl.setProjectCostSettings(projectCostSettingDao.findByProject(projectOpenByDefault));
		userSettingServiceImpl.setUserSetting(userSettingDao.findByUserAndProject(userEntity, projectOpenByDefault));

		//Update projectFile
		projectFileServiceImpl.setFiles(projectFileDao.findByProject(projectOpenByDefault, !checkAvailableProjectManager(projectOpenByDefault, userEntity)));
		projectFileServiceImpl.setSelectedProjectFile(new ProjectFileEntity());

		userServiceImpl.setUsers(projectUserRoleDao.findUser(projectOpenByDefault, true));
		userServiceImpl.setSelectedUser(userEntity);
		userServiceImpl.setCurrentUser(userEntity);
		projectUserRoleServiceImpl.setUserADD(new UserEntity());

		//Update users, UserRole
		projectUserRoleServiceImpl.setProjects(projectUserRoleDao.findProject(userEntity, true));
		projectUserRoleServiceImpl.setProjectsByProjectManager(projectUserRoleDao.findProjectByUserRoleProjectMnager(userEntity, userRoleDao.findByName("Project Manager"), true));
		projectUserRoleServiceImpl.setSearchUserRoleEntities(userRoleDao.findAll());
		projectUserRoleServiceImpl.setUsers(projectUserRoleDao.findUser(projectOpenByDefault, true));
		projectUserRoleServiceImpl.setSelectedUser(userEntity);
		projectUserRoleServiceImpl.setSelectedProject(projectOpenByDefault);
		projectUserRoleServiceImpl.setUserRoles(projectUserRoleDao.findUserRole(projectOpenByDefault, userEntity, true));

		if (!(projectUserRoleServiceImpl.getUserRoles().isEmpty())) {
			projectUserRoleServiceImpl.setSelectedUserRole(projectUserRoleServiceImpl.getUserRoles().get(0));
		}

		if (checkAvailableProjectManager(projectOpenByDefault,userEntity)) { 
			setReportUsers(projectUserRoleDao.findUser(projectOpenByDefault, true));
			setSelectedReportUsers(projectUserRoleDao.findUser(projectOpenByDefault, true));
		}
		else{
			List<UserEntity> listCurrentReportUser = new ArrayList<UserEntity>();
			listCurrentReportUser.add(userEntity);
			setReportUsers(listCurrentReportUser);
			setSelectedReportUsers(listCurrentReportUser);
		}

	}

	public void updateAppByUserAndProject(UserEntity userEntity, ProjectEntity projectEntity) {

		userServiceImpl.update(userEntity);

		taskServiceImpl.setTasks(taskDao.findByProject(projectEntity));

		//Update taskFile, taskHistory, taskTimeTracking
		if (!(taskServiceImpl.getTasks().isEmpty())) {
			taskServiceImpl.setSelectedTask(taskServiceImpl.getTasks().get(0));
			taskFileServiceImpl.setFiles(taskServiceImpl.getSelectedTask());
			taskStatusHistoryServiceImpl.setStatusHistories(taskServiceImpl.getSelectedTask());
			taskTimeTrackingServiceImpl.setTimeTrackings(taskServiceImpl.getSelectedTask());
			taskHistoryServiceImpl.setHistories(taskServiceImpl.getSelectedTask());
		}
		else {
			taskServiceImpl.setSelectedTask(new TaskEntity());
			taskFileServiceImpl.setFiles(new ArrayList<TaskFileEntity>());
			taskFileServiceImpl.setSelectedTaskFile(new TaskFileEntity());
			taskStatusHistoryServiceImpl.setStatusHistories(new ArrayList<TaskStatusHistoryEntity>());
			taskStatusHistoryServiceImpl.setSelectedTaskStatusHistory(new TaskStatusHistoryEntity());
			taskTimeTrackingServiceImpl.setTimeTrackings(new ArrayList<TaskTimeTrackingEntity>());
			taskTimeTrackingServiceImpl.setSelectedTaskTimeTracking(new TaskTimeTrackingEntity());
			taskHistoryServiceImpl.setTaskHistories(new ArrayList<TaskHistoryEntity>());
			taskHistoryServiceImpl.setSelectedTaskHistory(new TaskHistoryEntity());
		}

		//Update users
		userServiceImpl.setUsers(projectUserRoleDao.findUser(projectEntity, true));
		projectUserRoleServiceImpl.setUsers(projectUserRoleDao.findUser(projectEntity, true));

	}

	public void currentProjectChange(ProjectEntity currentProjectEntity) {

		taskServiceImpl.setTasks(taskDao.findByProject(currentProjectEntity));

		//Update taskFile, taskHistory, taskTimeTracking
		if (!(taskServiceImpl.getTasks().isEmpty())) {
			taskServiceImpl.setSelectedTask(taskServiceImpl.getTasks().get(0));
			taskFileServiceImpl.setFiles(taskServiceImpl.getSelectedTask());
			taskStatusHistoryServiceImpl.setStatusHistories(taskServiceImpl.getSelectedTask());
			taskTimeTrackingServiceImpl.setTimeTrackings(taskServiceImpl.getSelectedTask());
			taskHistoryServiceImpl.setHistories(taskServiceImpl.getSelectedTask());
		}
		else {
			taskServiceImpl.setSelectedTask(new TaskEntity());
			taskFileServiceImpl.setFiles(new ArrayList<TaskFileEntity>());
			taskFileServiceImpl.setSelectedTaskFile(new TaskFileEntity());
			taskStatusHistoryServiceImpl.setStatusHistories(new ArrayList<TaskStatusHistoryEntity>());
			taskStatusHistoryServiceImpl.setSelectedTaskStatusHistory(new TaskStatusHistoryEntity());
			taskTimeTrackingServiceImpl.setTimeTrackings(new ArrayList<TaskTimeTrackingEntity>());
			taskTimeTrackingServiceImpl.setSelectedTaskTimeTracking(new TaskTimeTrackingEntity());
			taskHistoryServiceImpl.setTaskHistories(new ArrayList<TaskHistoryEntity>());
			taskHistoryServiceImpl.setSelectedTaskHistory(new TaskHistoryEntity());
		}

		//Update contract, contractorOrganization, customerOrganization
		contractServiceImpl.setContracts(contractDao.findByProject(currentProjectEntity));
		contractorOrganizationServiceImpl.setContractorOrganizations(contractorOrganizationDao.findByProject(currentProjectEntity));
		customerOrganizationServiceImpl.setCustomerOrganizations(customerOrganizationDao.findByProject(currentProjectEntity));

		//Update projectFile, projectMilestone, projectCostSetting 
		projectFileServiceImpl.setFiles(projectFileDao.findByProject(currentProjectEntity, !checkAvailableProjectManager(currentProjectEntity, userServiceImpl.getCurrentUser())));
		projectMilestoneServiceImpl.setProjectMilestones(projectMilestoneDao.findByProject(currentProjectEntity));
		projectCostSettingEntityServiceImpl.setProjectCostSettings(projectCostSettingDao.findByProject(currentProjectEntity));

		taskStatusServiceImpl.setTaskStatuses(taskStatusDao.findByProject(currentProjectEntity));
		taskTypeServiceImpl.setTaskTypes(taskTypeDao.findByProject(currentProjectEntity));
		taskPriorityServiceImpl.setTaskPriorities(taskPriorityDao.findByProject(currentProjectEntity));
		formatTimeTrackingServiceImpl.setFormatTimeTrackings(formatTimeTrackingDao.findByProject(currentProjectEntity));

		//Update users
		userServiceImpl.setUsers(projectUserRoleDao.findUser(currentProjectEntity, true));
		userServiceImpl.setSelectedUser(new UserEntity());

		projectUserRoleServiceImpl.setUserByProject(currentProjectEntity);

		if (checkAvailableProjectManager(currentProjectEntity, userServiceImpl.getCurrentUser())) { 
			setReportUsers(projectUserRoleDao.findUser(currentProjectEntity, true));
			setSelectedReportUsers(projectUserRoleDao.findUser(currentProjectEntity, true));
		}
		else{
			List<UserEntity> listCurrentReportUser = new ArrayList<UserEntity>();
			listCurrentReportUser.add(userServiceImpl.getCurrentUser());
			setReportUsers(listCurrentReportUser);
			setSelectedReportUsers(listCurrentReportUser);
		}

	}

	public void createUserByProject(UserEntity userEntity, ProjectEntity projectEntity) {

		if (userServiceImpl.createUser(userEntity)){

			projectUserRoleServiceImpl.createProjectUserRole(projectEntity, userEntity);
			//Update users
			userServiceImpl.setUsers(projectUserRoleDao.findUser(projectEntity, true));
			projectUserRoleServiceImpl.setUserByProject(projectEntity);
			userServiceImpl.setSelectedUser(new UserEntity());
		}

	}

	public void createProjectByUser(ProjectEntity projectEntity, UserEntity userEntity) {

		projectDao.save(projectEntity);
		projectUserRoleServiceImpl.createProjectUserRole(projectEntity, userEntity, userRoleDao.findByName("Project Manager"));
		projectUserRoleServiceImpl.createProjectUserRole(projectEntity, userEntity, userRoleDao.findByName("Base"));

		// Create on start new project: contract,
		// contractorOrganization, customerOrganization. projectCostSetting
		contractServiceImpl.createContractOnStartNewDataBase(projectEntity);
		contractorOrganizationServiceImpl.createContractorOrganizationOnStartNewDataBase(projectEntity);
		customerOrganizationServiceImpl.createCustomerOrganizationOnStartNewDataBase(projectEntity);
		projectMilestoneServiceImpl.createProjectMilestoneOnStartNewDataBase(projectEntity);
		taskStatusServiceImpl.createTaskStatusByProjectOnStartNewDataBase(projectEntity);
		taskTypeServiceImpl.createTaskTypeByProjectOnStartNewDataBase(projectEntity);
		taskPriorityServiceImpl.createTaskPriorityByProjectOnStartNewDataBase(projectEntity);
		formatTimeTrackingServiceImpl.createTaskFormatTimeTrackingByProjectOnStartNewDataBase(projectEntity);
		projectCostSettingEntityServiceImpl.createProjectCostSettingOnStartNewDataBase(projectEntity);

		// Update projects, users
		projectUserRoleServiceImpl.setProjects(projectUserRoleDao.findProject(userEntity, true));
		projectUserRoleServiceImpl.setProjectsByProjectManager(projectUserRoleDao.findProjectByUserRoleProjectMnager(userEntity, userRoleDao.findByName("Project Manager"), true));

	}

	public void afterTaskSelect() {

		taskFileServiceImpl.setFiles(taskServiceImpl.getSelectedTask());
		taskStatusHistoryServiceImpl.setStatusHistories(taskServiceImpl.getSelectedTask());
		taskTimeTrackingServiceImpl.setTimeTrackings(taskServiceImpl.getSelectedTask());
		taskHistoryServiceImpl.setHistories(taskServiceImpl.getSelectedTask());

	}

	public void beforeTaskSelect(TaskEntity taskEntity) {

		taskServiceImpl.setSelectedTask(taskEntity);
		taskFileServiceImpl.setFiles(taskServiceImpl.getSelectedTask());
		taskStatusHistoryServiceImpl.setStatusHistories(taskServiceImpl.getSelectedTask());
		taskTimeTrackingServiceImpl.setTimeTrackings(taskServiceImpl.getSelectedTask());
		taskHistoryServiceImpl.setHistories(taskServiceImpl.getSelectedTask());

	}

	public void projectSelectUpdateTask() {

		initByProject(projectServiceImpl.getSelectedCurrentProject());

	}

	public void initByProject(ProjectEntity projectEntity) {

		taskServiceImpl.setTasks(taskDao.findByProject(projectEntity));

		//Update taskFile, taskHistory, taskTimeTracking
		if (!(taskServiceImpl.getTasks().isEmpty())) {
			taskServiceImpl.setSelectedTask(taskServiceImpl.getTasks().get(0));
			taskFileServiceImpl.setFiles(taskServiceImpl.getSelectedTask());
			taskStatusHistoryServiceImpl.setStatusHistories(taskServiceImpl.getSelectedTask());
			taskTimeTrackingServiceImpl.setTimeTrackings(taskServiceImpl.getSelectedTask());
			taskHistoryServiceImpl.setHistories(taskServiceImpl.getSelectedTask());
		}
		else {
			taskServiceImpl.setSelectedTask(new TaskEntity());
			taskFileServiceImpl.setFiles(new ArrayList<TaskFileEntity>());
			taskFileServiceImpl.setSelectedTaskFile(new TaskFileEntity());
			taskStatusHistoryServiceImpl.setStatusHistories(new ArrayList<TaskStatusHistoryEntity>());
			taskStatusHistoryServiceImpl.setSelectedTaskStatusHistory(new TaskStatusHistoryEntity());
			taskTimeTrackingServiceImpl.setTimeTrackings(new ArrayList<TaskTimeTrackingEntity>());
			taskTimeTrackingServiceImpl.setSelectedTaskTimeTracking(new TaskTimeTrackingEntity());
			taskHistoryServiceImpl.setTaskHistories(new ArrayList<TaskHistoryEntity>());
			taskHistoryServiceImpl.setSelectedTaskHistory(new TaskHistoryEntity());
		}

	}

	public void updateUserSettings(UserEntity userEntity){

		userServiceImpl.update(userEntity);

	}

	public void createProjectTask(ProjectEntity projectEntity) {

		try {

			taskServiceImpl.createTask(projectEntity);
			//Create history Entity
			TaskHistoryEntity taskHistoryEntity = new TaskHistoryEntity();
			taskHistoryEntity.setTask(taskServiceImpl.getTaskADD());
			taskHistoryEntity.setChangeDate(new Date());
			taskHistoryEntity.setChangeAuthor(userServiceImpl.getCurrentUser());
			taskHistoryEntity.setChangeEvent("Created task");
			taskHistoryDao.save(taskHistoryEntity);

			//Create status history
			TaskStatusHistoryEntity taskStatusHistoryEntity = new TaskStatusHistoryEntity();
			taskStatusHistoryEntity.setDate(new Date());
			taskStatusHistoryEntity.setTask(taskServiceImpl.getTaskADD());
			taskStatusHistoryEntity.setStatus(taskServiceImpl.getTaskADD().getTaskStatus());
			taskStatusHistoryEntity.setUserAuthor(taskServiceImpl.getTaskADD().getUserResponsible());
			taskStatusHistoryEntity.setUserExecutor(taskServiceImpl.getTaskADD().getUsercurrentExecutor());
			taskStatusHistoryEntity.setComment(taskServiceImpl.getTaskADD().getTaskStatus().toString());


			taskStatusHistoryServiceImpl.setStatusHistoryADD(taskStatusHistoryEntity);
			taskStatusHistoryServiceImpl.createTaskStatusHistory(taskServiceImpl.getTaskADD());

			Email emailSend = new Email("SendEmailNewTask", taskServiceImpl.getTaskADD(), taskStatusHistoryEntity);
			emailSend.start();

			//Create history Entity
			taskHistoryEntity = new TaskHistoryEntity();
			taskHistoryEntity.setTask(taskServiceImpl.getTaskADD());
			taskHistoryEntity.setChangeDate(new Date());
			taskHistoryEntity.setChangeAuthor(userServiceImpl.getCurrentUser());
			taskHistoryEntity.setChangeEvent("Set Status");
			taskHistoryDao.save(taskHistoryEntity);

			taskHistoryServiceImpl.setHistories(taskServiceImpl.getTaskADD());

			taskServiceImpl.setTaskADD(new TaskEntity());

			initByProject(projectEntity);

		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public boolean checkAvailableProjectToDelete(ProjectEntity projectEntity, UserEntity userEntity) {

		if (projectUserRoleDao.checkAvailableRole(projectEntity, userEntity, userRoleDao.findByName("Project Manager"))){

			if (projectUserRoleDao.countEntityByUserAndUserRole(userEntity, userRoleDao.findByName("Project Manager")) > 1) {
				return true;
			}

			return false;

		}

		return false;

	}

	public boolean checkAvailableUserToDeleteByProject(ProjectEntity projectEntity, UserEntity userEntity) {

		if (projectUserRoleDao.checkAvailableRole(projectEntity, userEntity, userRoleDao.findByName("Project Manager"))){

			if (projectUserRoleDao.countEntityUserByProjectAndUserRole(projectEntity, userRoleDao.findByName("Project Manager")) > 1){
				return true;
			}

			return false;

		}else{
			return true;
		}

	}

	public boolean checkAvailableRoleToDelete(ProjectEntity projectEntity, UserEntity userEntity, UserRoleEntity userRoleEntity){

		if(userRoleEntity.equals(userRoleDao.findByName("Base"))) {
			return false;
		}

		UserRoleEntity userRoleProjectManager = userRoleDao.findByName("Project Manager");

		if (userRoleEntity.equals(userRoleProjectManager)){

			if (projectUserRoleDao.checkAvailableRole(projectEntity, userEntity, userRoleProjectManager)){

				if (projectUserRoleDao.countEntityUserByProjectAndUserRole(projectEntity, userRoleProjectManager) > 1){
					return true;
				}

				return false;

			}else{
				return true;
			}
		}else{
			return true;
		}

	}

	public void deleteProject(ProjectEntity projectEntity) {

		try {

			for(TaskEntity taskEntity:taskDao.findByProject(projectEntity)){
				deleteProjectTask(taskEntity);
			}

			for(ProjectUserRoleEntity projectUserRoleEntity:projectUserRoleDao.findEntityByProject(projectEntity, false)){
				projectUserRoleDao.delete(projectUserRoleEntity);
			}

			for(ContractEntity contractEntity:contractDao.findByProject(projectEntity)){
				contractDao.delete(contractEntity);
			}

			for(ContractorOrganizationEntity contractorOrganizationEntity:contractorOrganizationDao.findByProject(projectEntity)){
				contractorOrganizationDao.delete(contractorOrganizationEntity);
			}

			for(CustomerOrganizationEntity customerOrganizationEntity:customerOrganizationDao.findByProject(projectEntity)){
				customerOrganizationDao.delete(customerOrganizationEntity);
			}

			for(ProjectMilestoneEntity projectMilestoneEntity:projectMilestoneDao.findByProject(projectEntity)){
				projectMilestoneDao.delete(projectMilestoneEntity);
			}

			for(ProjectFileEntity projectFileEntity:projectFileDao.findByProject(projectEntity, false)){
				projectFileDao.delete(projectFileEntity);
			}

			for(ProjectCostSettingEntity projectCostSettingEntity:projectCostSettingDao.findByProject(projectEntity)){
				projectCostSettingDao.delete(projectCostSettingEntity);
			}

			for(TaskPriorityEntity taskPriorityEntity:taskPriorityServiceImpl.getTaskPriorities()){
				taskPriorityDao.delete(taskPriorityEntity);
			}

			for(TaskTypeEntity taskTypeEntity:taskTypeServiceImpl.getTaskTypes()){
				taskTypeDao.delete(taskTypeEntity);
			}

			for(TaskStatusEntity taskStatusEntity:taskStatusServiceImpl.getTaskStatuses()){
				taskStatusDao.delete(taskStatusEntity);
			}

			for(FormatTimeTrackingEntity formatTimeTrackingEntity:formatTimeTrackingServiceImpl.getFormatTimeTrackings()){
				formatTimeTrackingDao.delete(formatTimeTrackingEntity);
			}


			//ProjectByDefault
			UserSettingEntity userSettingEntity = userSettingDao.findByUser(userServiceImpl.getCurrentUser()); 
			if(projectEntity.equals(userSettingEntity.getProjectByDefault())){

				for(ProjectEntity forProjectEntity:projectUserRoleServiceImpl.getProjectsByProjectManager()){
					if (!forProjectEntity.equals(projectEntity)) {
						userSettingEntity.setProjectByDefault(forProjectEntity);
						userSettingDao.update(userSettingEntity);
						projectServiceImpl.setSelectedCurrentProject(forProjectEntity);
					}
				}

			}

			projectServiceImpl.delete(projectEntity);

			//Update the hole APP
			initAppByUser(userServiceImpl.getCurrentUser());

			setMenuIndex(0);

			org.springframework.webflow.execution.RequestContext requestContext = RequestContextHolder.getRequestContext();
			RequestControlContext rec = (RequestControlContext) requestContext;
			//place variables you need in next flow phase here; flash,application,session scope
			rec.handleEvent(new Event(this, "MenuTask"));
			return;

		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void updateProjectTask(TaskEntity taskEntity, ProjectEntity projectEntity) {

		try {

			taskServiceImpl.update(taskEntity, projectEntity);
			//Create history Entity
			TaskHistoryEntity taskHistoryEntity = new TaskHistoryEntity();
			taskHistoryEntity.setTask(taskEntity);
			taskHistoryEntity.setChangeDate(new Date());
			taskHistoryEntity.setChangeAuthor(userServiceImpl.getCurrentUser());
			taskHistoryEntity.setChangeEvent("Updated task");
			taskHistoryDao.save(taskHistoryEntity);

			initByProject(projectEntity);

		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void deleteProjectTask(TaskEntity task) {

		try {

			taskFileServiceImpl.setFiles(task);
			for(TaskFileEntity taskFileEntity:taskFileServiceImpl.getFiles()){
				taskFileDao.delete(taskFileEntity);
			}

			taskStatusHistoryServiceImpl.setStatusHistories(task);
			for(TaskStatusHistoryEntity taskStatusHistoryEntity:taskStatusHistoryServiceImpl.getStatusHistories()){
				taskStatusHistoryDao.delete(taskStatusHistoryEntity);
			}

			taskTimeTrackingServiceImpl.setTimeTrackings(task);
			for(TaskTimeTrackingEntity taskTimeTrackingEntity:taskTimeTrackingServiceImpl.getTimeTrackings()){
				taskTimeTrackingDao.delete(taskTimeTrackingEntity);
			}

			taskHistoryServiceImpl.setHistories(task);
			for(TaskHistoryEntity taskHistoryEntity:taskHistoryServiceImpl.getTaskHistories()){
				taskHistoryDao.delete(taskHistoryEntity);
			}

			taskServiceImpl.delete(task);
			initByProject(projectServiceImpl.getSelectedCurrentProject());

		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void createTaskTimeTracking(TaskEntity taskID) {

		taskTimeTrackingServiceImpl.createTaskTimeTracking(taskID);
		//Create history Entity
		TaskHistoryEntity taskHistoryEntity = new TaskHistoryEntity();
		taskHistoryEntity.setTask(taskID);
		taskHistoryEntity.setChangeDate(new Date());
		taskHistoryEntity.setChangeAuthor(userServiceImpl.getCurrentUser());
		taskHistoryEntity.setChangeEvent("Created Time Tracking");
		taskHistoryDao.save(taskHistoryEntity);
		taskHistoryServiceImpl.setHistories(taskID);

		double actualTaskTime = 0.0;

		for(TaskTimeTrackingEntity entityList : taskTimeTrackingServiceImpl.getTimeTrackings()) {
			actualTaskTime= taskTimeTrackingServiceImpl.addTime(actualTaskTime, entityList.getActualTime());
		}

		taskID.setActualTime(actualTaskTime);
		taskServiceImpl.update(taskID, projectServiceImpl.getSelectedCurrentProject());

	}

	public void deleteTaskTimeTracking(TaskTimeTrackingEntity taskTimeTrackingEntity, TaskEntity taskID, UserEntity userEntity) {

		if(taskTimeTrackingEntity.getAccepted()){

			if(checkAvailableAcceptanceTask(projectServiceImpl.getSelectedCurrentProject(), userEntity)) {

				taskTimeTrackingServiceImpl.delete(taskTimeTrackingEntity, taskID);
				//Create history Entity
				TaskHistoryEntity taskHistoryEntity = new TaskHistoryEntity();
				taskHistoryEntity.setTask(taskID);
				taskHistoryEntity.setChangeDate(new Date());
				taskHistoryEntity.setChangeAuthor(userServiceImpl.getCurrentUser());
				taskHistoryEntity.setChangeEvent("Deleted Time Tracking");
				taskHistoryDao.save(taskHistoryEntity);
				taskHistoryServiceImpl.setHistories(taskID);

				double actualTaskTime = 0.0;

				for(TaskTimeTrackingEntity entityList : taskTimeTrackingServiceImpl.getTimeTrackings()) {
					actualTaskTime= taskTimeTrackingServiceImpl.addTime(actualTaskTime, entityList.getActualTime());
				}

				taskID.setActualTime(actualTaskTime);
				taskServiceImpl.update(taskID, projectServiceImpl.getSelectedCurrentProject());
			}
		}
		else{
			taskTimeTrackingServiceImpl.delete(taskTimeTrackingEntity, taskID);
			//Create history Entity
			TaskHistoryEntity taskHistoryEntity = new TaskHistoryEntity();
			taskHistoryEntity.setTask(taskID);
			taskHistoryEntity.setChangeDate(new Date());
			taskHistoryEntity.setChangeAuthor(userServiceImpl.getCurrentUser());
			taskHistoryEntity.setChangeEvent("Deleted Time Tracking");
			taskHistoryDao.save(taskHistoryEntity);
			taskHistoryServiceImpl.setHistories(taskID);

			double actualTaskTime = 0.0;

			for(TaskTimeTrackingEntity entityList : taskTimeTrackingServiceImpl.getTimeTrackings()) {
				actualTaskTime= taskTimeTrackingServiceImpl.addTime(actualTaskTime, entityList.getActualTime());
			}

			taskID.setActualTime(actualTaskTime);
			taskServiceImpl.update(taskID, projectServiceImpl.getSelectedCurrentProject());
		}

	}

	public void updateTaskTimeTracking(TaskTimeTrackingEntity taskTimeTrackingEntity) {

		taskTimeTrackingServiceImpl.update(taskTimeTrackingEntity);
		//Create history Entity
		TaskHistoryEntity taskHistoryEntity = new TaskHistoryEntity();
		taskHistoryEntity.setTask(taskServiceImpl.getSelectedTask());
		taskHistoryEntity.setChangeDate(new Date());
		taskHistoryEntity.setChangeAuthor(userServiceImpl.getCurrentUser());
		taskHistoryEntity.setChangeEvent("Updated Time Tracking");
		taskHistoryDao.save(taskHistoryEntity);
		taskHistoryServiceImpl.setHistories(taskServiceImpl.getSelectedTask());

		double actualTaskTime = 0.0;

		for(TaskTimeTrackingEntity entityList : taskTimeTrackingServiceImpl.getTimeTrackings()) {
			actualTaskTime= taskTimeTrackingServiceImpl.addTime(actualTaskTime, entityList.getActualTime());
		}

		taskServiceImpl.getSelectedTask().setActualTime(actualTaskTime);
		taskServiceImpl.update(taskServiceImpl.getSelectedTask(), projectServiceImpl.getSelectedCurrentProject());

	}

	public void createTaskStatusHistory(TaskEntity taskEntity) {

		try {
			taskEntity.setUsercurrentExecutor(taskStatusHistoryServiceImpl.getStatusHistoryADD().getUserExecutor());
			taskEntity.setTaskStatus(taskStatusHistoryServiceImpl.getStatusHistoryADD().getStatus());

			taskServiceImpl.update(taskEntity, projectServiceImpl.getSelectedCurrentProject());

			taskStatusHistoryServiceImpl.getStatusHistoryADD().setTask(taskEntity);
			taskStatusHistoryDao.save(taskStatusHistoryServiceImpl.getStatusHistoryADD());

			Email emailSend = new Email("SendEmailChangeStatus", taskEntity, taskStatusHistoryServiceImpl.getStatusHistoryADD());
			emailSend.start();

			//Create history Entity
			TaskHistoryEntity taskHistoryEntity = new TaskHistoryEntity();
			taskHistoryEntity.setTask(taskEntity);
			taskHistoryEntity.setChangeDate(new Date());
			taskHistoryEntity.setChangeAuthor(userServiceImpl.getCurrentUser());
			taskHistoryEntity.setChangeEvent("Changed Status");
			taskHistoryDao.save(taskHistoryEntity);
			taskHistoryServiceImpl.setHistories(taskEntity);

			taskStatusHistoryServiceImpl.setStatusHistoryADD(new TaskStatusHistoryEntity());
			taskStatusHistoryServiceImpl.setStatusHistories(taskStatusHistoryDao.findByTaskID(taskEntity));

		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void createTaskFile(TaskEntity taskID) {

		try {

			taskFileServiceImpl.createTaskFile(taskID);
			//Create history Entity
			TaskHistoryEntity taskHistoryEntity = new TaskHistoryEntity();
			taskHistoryEntity.setTask(taskID);
			taskHistoryEntity.setChangeDate(new Date());
			taskHistoryEntity.setChangeAuthor(userServiceImpl.getCurrentUser());
			taskHistoryEntity.setChangeEvent("Add File");
			taskHistoryDao.save(taskHistoryEntity);
			taskHistoryServiceImpl.setHistories(taskID);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void deleteTaskFile(TaskFileEntity file, TaskEntity taskID) {

		try {
			taskFileServiceImpl.delete(file, taskID);
			//Create history Entity
			TaskHistoryEntity taskHistoryEntity = new TaskHistoryEntity();
			taskHistoryEntity.setTask(taskID);
			taskHistoryEntity.setChangeDate(new Date());
			taskHistoryEntity.setChangeAuthor(userServiceImpl.getCurrentUser());
			taskHistoryEntity.setChangeEvent("Deleted File");
			taskHistoryDao.save(taskHistoryEntity);
			taskHistoryServiceImpl.setHistories(taskID);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void updateTaskFile(TaskFileEntity taskFileEntity) {

		try {
			taskFileServiceImpl.update(taskFileEntity);
			//Create history Entity
			TaskHistoryEntity taskHistoryEntity = new TaskHistoryEntity();
			taskHistoryEntity.setTask(taskServiceImpl.getSelectedTask());
			taskHistoryEntity.setChangeDate(new Date());
			taskHistoryEntity.setChangeAuthor(userServiceImpl.getCurrentUser());
			taskHistoryEntity.setChangeEvent("Update File");
			taskHistoryDao.save(taskHistoryEntity);
			taskHistoryServiceImpl.setHistories(taskServiceImpl.getSelectedTask());
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void createProjectFile(ProjectEntity projectEntity, UserEntity userEntity) {

		projectFileServiceImpl.createProjectFile(projectEntity);
		projectFileServiceImpl.setFiles(projectFileDao.findByProject(projectEntity, !checkAvailableProjectManager(projectEntity, userEntity)));

	}

	public void deleteProjectFile(ProjectFileEntity projectFile, ProjectEntity projectEntity) {

		projectFileServiceImpl.delete(projectFile, projectEntity);
		projectFileServiceImpl.setFiles(projectFileDao.findByProject(projectEntity, !checkAvailableProjectManager(projectEntity, userServiceImpl.getCurrentUser())));

	}

	public void updateProjectFile(ProjectFileEntity projectFileEntity) {

		projectFileServiceImpl.update(projectFileEntity);
		projectFileServiceImpl.setFiles(projectFileDao.findByProject(projectServiceImpl.getSelectedCurrentProject(), !checkAvailableProjectManager(projectServiceImpl.getSelectedCurrentProject(), userServiceImpl.getCurrentUser())));

	}

	public boolean fileChossen(TaskFileEntity taskFileEntity){

		if(taskFileEntity == null){
			return false;
		}

		if(taskFileEntity.getName() == null) {
			return false;
		}

		return true;
	}

	public boolean fileChossen(ProjectFileEntity projectFileEntity){

		if(projectFileEntity == null){
			return false;
		}

		if(projectFileEntity.getName() == null) {
			return false;
		}

		return true;
	}

	public boolean checkAvailableProjectManager(ProjectEntity projectEntity, UserEntity userEntity) {

		return projectUserRoleDao.checkAvailableRole(projectEntity, userEntity, userRoleDao.findByName("Project Manager"));

	}

	public boolean checkAvailableAcceptanceTask(ProjectEntity projectEntity, UserEntity userEntity) {

		boolean flag = projectUserRoleDao.checkAvailableRole(projectEntity, userEntity, userRoleDao.findByName("Acceptance Task"));

		if(!flag){

		}

		return projectUserRoleDao.checkAvailableRole(projectEntity, userEntity, userRoleDao.findByName("Acceptance Task"));

	}

	public boolean checkAvailableAcceptanceTask(ProjectEntity projectEntity, TaskTimeTrackingEntity taskTimeTrackingEntity, UserEntity userEntity) {

		boolean flag = projectUserRoleDao.checkAvailableRole(projectEntity, userEntity, userRoleDao.findByName("Acceptance Task"));

		if(flag) {
			return true;
		}
		else{
			if(taskTimeTrackingEntity.getAccepted()){
				return false;
			}
		}

		if(!flag){

			if (taskTimeTrackingEntity.getAccepted()){
				return false;
			}
			else{
				if(taskTimeTrackingEntity.getUserAuthor().equals(userEntity)){
					return true;
				}
				else{
					return false;
				}
			}

		}
		else {
			return true;
		}

	}

	public boolean checkAvailableToDelete(TaskStatusEntity taskStatusEntity) {

		return taskStatusEntity.getConstant();

	}

	public boolean checkAvailableNameByProjectForFormatTimeTracking(AjaxBehaviorEvent event) {

		InputText nameFormatTimeTracking = (InputText) event.getSource();
		String value = (String) nameFormatTimeTracking.getValue();

		boolean available = formatTimeTrackingDao.checkAvailableByProject(projectServiceImpl.getSelectedCurrentProject(), value);

		if (!available) {
			FacesMessage message = constructErrorMessage(null, String.format(getMessageBundle().getString("ExistsMsg")));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		} else {
			FacesMessage message = constructInfoMessage(null, String.format(getMessageBundle().getString("AvailableMsg")));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		}

		return available;

	}

	public boolean checkAvailableNameByProjectForTaskPriority(AjaxBehaviorEvent event) {

		InputText priorityName = (InputText) event.getSource();
		String value = (String) priorityName.getValue();

		boolean available = taskPriorityDao.checkAvailableByProject(projectServiceImpl.getSelectedCurrentProject(), value);

		if (!available) {
			FacesMessage message = constructErrorMessage(null, String.format(getMessageBundle().getString("ExistsMsg")));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		} else {
			FacesMessage message = constructInfoMessage(null, String.format(getMessageBundle().getString("AvailableMsg")));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		}

		return available;

	}

	public boolean checkAvailableNameByProjectForTaskStatus(AjaxBehaviorEvent event) {

		InputText statusName = (InputText) event.getSource();
		String value = (String) statusName.getValue();

		boolean available = taskStatusDao.checkAvailableByProject(projectServiceImpl.getSelectedCurrentProject(), value);

		if (!available) {
			FacesMessage message = constructErrorMessage(null, String.format(getMessageBundle().getString("ExistsMsg")));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		} else {
			FacesMessage message = constructInfoMessage(null, String.format(getMessageBundle().getString("AvailableMsg")));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		}

		return available;

	}

	public boolean checkAvailableNameByProjectForTaskType(AjaxBehaviorEvent event) {

		InputText typeName = (InputText) event.getSource();
		String value = (String) typeName.getValue();

		boolean available = taskTypeDao.checkAvailableByProject(projectServiceImpl.getSelectedCurrentProject(), value);

		if (!available) {
			FacesMessage message = constructErrorMessage(null, String.format(getMessageBundle().getString("ExistsMsg")));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		} else {
			FacesMessage message = constructInfoMessage(null, String.format(getMessageBundle().getString("AvailableMsg")));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		}

		return available;

	}

	public boolean checkSelectedUserProject() {

		try {
			if(projectUserRoleServiceImpl.getSelectedProject().getName()==null) {
				return false;
			}
		} catch(Exception e) {
			return false;
		}

		return true;

	}

	public boolean checkSelectedUserRoleProject() {

		try {
			if(projectUserRoleServiceImpl.getSelectedUser().getFirstName()==null) {
				return false;
			}
		} catch(Exception e) {
			return false;
		}

		return true;

	}

	public boolean checkSelectedProjectTask() {

		try {
			if(taskServiceImpl.getSelectedTask().getShortContent()==null) {
				return false;
			}
		} catch(Exception e) {
			return false;
		}

		return true;

	}

	public void createProjectUser(ProjectEntity projectEntity, UserEntity userEntity) {

		projectUserRoleServiceImpl.createProjectUser(projectEntity, userEntity);

		//Update users
		userServiceImpl.setUsers(projectUserRoleDao.findUser(projectEntity, true));
		userServiceImpl.setSelectedUser(new UserEntity());

	}

	protected FacesMessage constructErrorMessage(String message, String detail){
		return new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detail);
	}

	protected FacesMessage constructInfoMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_INFO, message, detail);
	}

	protected FacesMessage constructFatalMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_FATAL, message, detail);
	}

	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	protected ResourceBundle getMessageBundle() {
		return ResourceBundle.getBundle("message-labels");
	}

	public TaskServiceImpl getTaskServiceImpl() {
		return taskServiceImpl;
	}

	public void setTaskServiceImpl(TaskServiceImpl taskServiceImpl) {
		this.taskServiceImpl = taskServiceImpl;
	}

	public TaskTimeTrackingServiceImpl getTaskTimeTrackingServiceImpl() {
		return taskTimeTrackingServiceImpl;
	}

	public void setTaskTimeTrackingServiceImpl(TaskTimeTrackingServiceImpl taskTimeTrackingServiceImpl) {
		this.taskTimeTrackingServiceImpl = taskTimeTrackingServiceImpl;
	}

	public TaskHistoryServiceImpl getTaskHistoryServiceImpl() {
		return taskHistoryServiceImpl;
	}

	public void setTaskHistoryServiceImpl(TaskHistoryServiceImpl taskHistoryServiceImpl) {
		this.taskHistoryServiceImpl = taskHistoryServiceImpl;
	}

	public TaskFileServiceImpl getTaskFileServiceImpl() {
		return taskFileServiceImpl;
	}

	public void setTaskFileServiceImpl(TaskFileServiceImpl taskFileServiceImpl) {
		this.taskFileServiceImpl = taskFileServiceImpl;
	}

	public CustomerOrganizationServiceImpl getCustomerOrganizationServiceImpl() {
		return customerOrganizationServiceImpl;
	}

	public void setCustomerOrganizationServiceImpl(CustomerOrganizationServiceImpl customerOrganizationServiceImpl) {
		this.customerOrganizationServiceImpl = customerOrganizationServiceImpl;
	}

	public UserServiceImpl getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public UserRoleServiceImpl getUserRoleServiceImpl() {
		return userRoleServiceImpl;
	}

	public void setUserRoleServiceImpl(UserRoleServiceImpl userRoleServiceImpl) {
		this.userRoleServiceImpl = userRoleServiceImpl;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public TaskDao getTaskDao() {
		return taskDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public ContractServiceImpl getContractServiceImpl() {
		return contractServiceImpl;
	}

	public void setContractServiceImpl(ContractServiceImpl contractServiceImpl) {
		this.contractServiceImpl = contractServiceImpl;
	}

	public ContractorOrganizationServiceImpl getContractorOrganizationServiceImpl() {
		return contractorOrganizationServiceImpl;
	}

	public void setContractorOrganizationServiceImpl(ContractorOrganizationServiceImpl contractorOrganizationServiceImpl) {
		this.contractorOrganizationServiceImpl = contractorOrganizationServiceImpl;
	}

	public ContractDao getContractDao() {
		return contractDao;
	}

	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}

	public ContractorOrganizationDao getContractorOrganizationDao() {
		return contractorOrganizationDao;
	}

	public void setContractorOrganizationDao(ContractorOrganizationDao contractorOrganizationDao) {
		this.contractorOrganizationDao = contractorOrganizationDao;
	}

	public CustomerOrganizationDao getCustomerOrganizationDao() {
		return customerOrganizationDao;
	}

	public void setCustomerOrganizationDao(CustomerOrganizationDao customerOrganizationDao) {
		this.customerOrganizationDao = customerOrganizationDao;
	}

	public ProjectFileServiceImpl getProjectFileServiceImpl() {
		return projectFileServiceImpl;
	}

	public void setProjectFileServiceImpl(ProjectFileServiceImpl projectFileServiceImpl) {
		this.projectFileServiceImpl = projectFileServiceImpl;
	}

	public ProjectFileDao getProjectFileDao() {
		return projectFileDao;
	}

	public void setProjectFileDao(ProjectFileDao projectFileDao) {
		this.projectFileDao = projectFileDao;
	}

	public ProjectMilestoneServiceImpl getProjectMilestoneServiceImpl() {
		return projectMilestoneServiceImpl;
	}

	public void setProjectMilestoneServiceImpl(ProjectMilestoneServiceImpl projectMilestoneServiceImpl) {
		this.projectMilestoneServiceImpl = projectMilestoneServiceImpl;
	}

	public ProjectMilestoneDao getProjectMilestoneDao() {
		return projectMilestoneDao;
	}

	public void setProjectMilestoneDao(ProjectMilestoneDao projectMilestoneDao) {
		this.projectMilestoneDao = projectMilestoneDao;
	}

	public ProjectUserRoleServiceImpl getProjectUserRoleServiceImpl() {
		return projectUserRoleServiceImpl;
	}

	public void setProjectUserRoleServiceImpl(ProjectUserRoleServiceImpl projectUserRoleServiceImpl) {
		this.projectUserRoleServiceImpl = projectUserRoleServiceImpl;
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

	public UserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	public int getMenuIndex() {
		return menuIndex;
	}

	public void setMenuIndex(int menuIndex) {
		this.menuIndex = menuIndex;
		System.out.println("set menuIndex: " + menuIndex);
	}

	public UserSettingDao getUserSettingDao() {
		return userSettingDao;
	}

	public void setUserSettingDao(UserSettingDao userSettingDao) {
		this.userSettingDao = userSettingDao;
	}

	public ProjectCostSettingDao getProjectCostSettingDao() {
		return projectCostSettingDao;
	}

	public void setProjectCostSettingDao(ProjectCostSettingDao projectCostSettingDao) {
		this.projectCostSettingDao = projectCostSettingDao;
	}

	public ProjectCostSettingEntityServiceImpl getProjectCostSettingEntityServiceImpl() {
		return projectCostSettingEntityServiceImpl;
	}

	public void setProjectCostSettingEntityServiceImpl(ProjectCostSettingEntityServiceImpl projectCostSettingEntityServiceImpl) {
		this.projectCostSettingEntityServiceImpl = projectCostSettingEntityServiceImpl;
	}

	public UserSettingServiceImpl getUserSettingServiceImpl() {
		return userSettingServiceImpl;
	}

	public void setUserSettingServiceImpl(UserSettingServiceImpl userSettingServiceImpl) {
		this.userSettingServiceImpl = userSettingServiceImpl;
	}

	public FormatTimeTrackingDao getFormatTimeTrackingDao() {
		return formatTimeTrackingDao;
	}

	public void setFormatTimeTrackingDao(FormatTimeTrackingDao formatTimeTrackingDao) {
		this.formatTimeTrackingDao = formatTimeTrackingDao;
	}

	public TaskStatusHistoryServiceImpl getTaskStatusHistoryServiceImpl() {
		return taskStatusHistoryServiceImpl;
	}

	public void setTaskStatusHistoryServiceImpl(TaskStatusHistoryServiceImpl taskStatusHistoryServiceImpl) {
		this.taskStatusHistoryServiceImpl = taskStatusHistoryServiceImpl;
	}

	public TaskFileDao getTaskFileDao() {
		return taskFileDao;
	}

	public void setTaskFileDao(TaskFileDao taskFileDao) {
		this.taskFileDao = taskFileDao;
	}

	public TaskStatusHistoryDao getTaskStatusHistoryDao() {
		return taskStatusHistoryDao;
	}

	public void setTaskStatusHistoryDao(TaskStatusHistoryDao taskStatusHistoryDao) {
		this.taskStatusHistoryDao = taskStatusHistoryDao;
	}

	public TaskTimeTrackingDao getTaskTimeTrackingDao() {
		return taskTimeTrackingDao;
	}

	public void setTaskTimeTrackingDao(TaskTimeTrackingDao taskTimeTrackingDao) {
		this.taskTimeTrackingDao = taskTimeTrackingDao;
	}

	public TaskHistoryDao getTaskHistoryDao() {
		return taskHistoryDao;
	}

	public void setTaskHistoryDao(TaskHistoryDao taskHistoryDao) {
		this.taskHistoryDao = taskHistoryDao;
	}

	public TaskStatusServiceImpl getTaskStatusServiceImpl() {
		return taskStatusServiceImpl;
	}

	public void setTaskStatusServiceImpl(TaskStatusServiceImpl taskStatusServiceImpl) {
		this.taskStatusServiceImpl = taskStatusServiceImpl;
	}

	public TaskTypeServiceImpl getTaskTypeServiceImpl() {
		return taskTypeServiceImpl;
	}

	public void setTaskTypeServiceImpl(TaskTypeServiceImpl taskTypeServiceImpl) {
		this.taskTypeServiceImpl = taskTypeServiceImpl;
	}

	public TaskPriorityServiceImpl getTaskPriorityServiceImpl() {
		return taskPriorityServiceImpl;
	}

	public void setTaskPriorityServiceImpl(TaskPriorityServiceImpl taskPriorityServiceImpl) {
		this.taskPriorityServiceImpl = taskPriorityServiceImpl;
	}

	public FormatTimeTrackingServiceImpl getFormatTimeTrackingServiceImpl() {
		return formatTimeTrackingServiceImpl;
	}

	public void setFormatTimeTrackingServiceImpl(FormatTimeTrackingServiceImpl formatTimeTrackingServiceImpl) {
		this.formatTimeTrackingServiceImpl = formatTimeTrackingServiceImpl;
	}

	public TaskStatusDao getTaskStatusDao() {
		return taskStatusDao;
	}

	public void setTaskStatusDao(TaskStatusDao taskStatusDao) {
		this.taskStatusDao = taskStatusDao;
	}

	public TaskTypeDao getTaskTypeDao() {
		return taskTypeDao;
	}

	public void setTaskTypeDao(TaskTypeDao taskTypeDao) {
		this.taskTypeDao = taskTypeDao;
	}

	public TaskPriorityDao getTaskPriorityDao() {
		return taskPriorityDao;
	}

	public void setTaskPriorityDao(TaskPriorityDao taskPriorityDao) {
		this.taskPriorityDao = taskPriorityDao;
	}

	public boolean isFlagNewUser() {
		return flagNewUser;
	}

	public void setFlagNewUser(boolean flagNewUser) {
		this.flagNewUser = flagNewUser;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public Date getReportDataStart() {
		return reportDataStart;
	}

	public void setReportDataStart(Date reportDataStart) {
		this.reportDataStart = reportDataStart;
	}

	public Date getReportDataFinish() {
		return reportDataFinish;
	}

	public void setReportDataFinish(Date reportDataFinish) {
		this.reportDataFinish = reportDataFinish;
	}

	public UserEntity getReportUser() {
		return reportUser;
	}

	public void setReportUser(UserEntity reportUser) {
		this.reportUser = reportUser;
	}

	public List<UserEntity> getReportUsers() {
		return reportUsers;
	}

	public void setReportUsers(List<UserEntity> reportUsers) {
		this.reportUsers = reportUsers;
	}

	public List<UserEntity> getSelectedReportUsers() {
		return selectedReportUsers;
	}

	public void setSelectedReportUsers(List<UserEntity> selectedReportUsers) {
		if(selectedReportUsers.isEmpty()){
			selectedReportUsers.add(userServiceImpl.getCurrentUser());
		}
		this.selectedReportUsers = selectedReportUsers;
		updateSelectedValuesReportUsersDisplayString();
	}

	public String getSelectedValuesReportUsersDisplayString() {
		return selectedValuesReportUsersDisplayString;
	}

	public void setSelectedValuesReportUsersDisplayString(String selectedValuesReportUsersDisplayString) {
		this.selectedValuesReportUsersDisplayString = selectedValuesReportUsersDisplayString;
	}

	public void updateSelectedValuesReportUsersDisplayString(){

		String currentUsersDisplayString = getSelectedReportUsers().toString();
		currentUsersDisplayString.replace('[', ' ');
		currentUsersDisplayString.replace(']', ' ');
		if (currentUsersDisplayString.length() > 20){
			currentUsersDisplayString = currentUsersDisplayString.substring(0, 19) + "..."; 
		}
		setSelectedValuesReportUsersDisplayString(currentUsersDisplayString);
	}

	public int getTaskTabView() {
		return taskTabView;
	}

	public void setTaskTabView(int taskTabView) {
		this.taskTabView = taskTabView;
	}

	public int getDirectoryTabView() {
		return directoryTabView;
	}

	public void setDirectoryTabView(int directoryTabView) {
		this.directoryTabView = directoryTabView;
	}

	public int getSettingTabView() {
		return settingTabView;
	}

	public void setSettingTabView(int settingTabView) {
		this.settingTabView = settingTabView;
	}




}
