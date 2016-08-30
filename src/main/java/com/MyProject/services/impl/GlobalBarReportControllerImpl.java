package com.MyProject.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.FlowEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;
import org.springframework.webflow.engine.RequestControlContext;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContextHolder;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.dao.TaskDao;
import com.MyProject.dao.TaskHistoryDao;
import com.MyProject.dao.TaskTimeTrackingDao;

/**
 * Global bar report controller implement
 * @author Di
 */
@javax.faces.bean.ManagedBean
@Scope(value="session")
public class GlobalBarReportControllerImpl implements Serializable {

	private static final long serialVersionUID = 7852979771015680707L;

	private UserServiceImpl 				userServiceImpl;
	private ProjectServiceImpl				projectServiceImpl;

	private TaskDao 						taskDao;
	private TaskTimeTrackingDao				taskTimeTrackingDao;
	private TaskHistoryDao					taskHistoryDao;

	String reportType 								= "";
	UserEntity reportUser 							= null;
	List<UserEntity> reportUsers 					= null;
	List<UserEntity> selectedReportUsers 			= null;
	String selectedValuesReportUsersDisplayString 	= "";
	Date reportDataStart 							= new Date();
	Date reportDataFinish 							= new Date();

	@PostConstruct
	public void init() {
		System.out.println("New bean Global Bar Report");
		setReportType("Cost and Time");
	}

	/**
	 * Create horizontal bar model by reportType
	 * @param String reportType (Time, Cost, TaskDeadline)
	 * @param Date dateStart
	 * @param Date dateFinish
	 * @param ArrayList<UserEntity> users
	 * @return HorizontalBarChartModel
	 * @author Di
	 */
	public HorizontalBarChartModel createHorizontalBarModel(String reportType, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		ProjectEntity currentProject = projectServiceImpl.getSelectedCurrentProject();

		HorizontalBarChartModel horizontalBarModel = new HorizontalBarChartModel();

		List<Object[]> resultQuery;
		ChartSeries Accepted 				= new ChartSeries();
		ChartSeries notAccepted 			= new ChartSeries();
		ArrayList<String> acceptedData 		= new ArrayList<String>();
		ArrayList<String> notAcceptedData 	= new ArrayList<String>();

		switch (reportType) {
		case "Time":
			resultQuery = taskTimeTrackingDao.getTimeByProjectAndUsers(currentProject, dateStart, dateFinish, users);

			Accepted.setLabel("Accepted");
			notAccepted.setLabel("Not accepted");

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
			xAxis.setMin(0);
			xAxis.setMax(100);
			break;

		case "Cost":

			resultQuery = taskTimeTrackingDao.getCostByProjectAndUsers(currentProject, dateStart, dateFinish, users);

			Accepted.setLabel("Accepted");

			notAccepted.setLabel("Not accepted");

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

			xAxis = horizontalBarModel.getAxis(AxisType.X);
			xAxis.setMin(0);
			break;

		case "TaskDeadline":

			resultQuery = taskDao.countTaskDeadLineByProjectAndUsers(currentProject, dateStart, dateFinish, users);

			ChartSeries Done = new ChartSeries();
			Done.setLabel("Done");

			ChartSeries Requared = new ChartSeries();
			Requared.setLabel("Requared");

			String taskName = "";

			for (int i = 0; i < resultQuery.size(); i++) {

				taskName = "Task #" + (String) resultQuery.get(i)[0].toString();

				Done.set(taskName, (Double) resultQuery.get(i)[2]);
				Requared.set(taskName, 100 - ((Double) resultQuery.get(i)[2]));
			}

			if (resultQuery.size() <= 0) {
				Done.set("Data will appear here", 50);
				Requared.set("Data will appear here", 50);
			}

			horizontalBarModel.addSeries(Done);
			horizontalBarModel.addSeries(Requared);

			xAxis = horizontalBarModel.getAxis(AxisType.X);
			xAxis.setMin(0);
			xAxis.setMax(100);
			break;

		default:
			break;
		}

		horizontalBarModel.setLegendPosition("e");
		horizontalBarModel.setStacked(true);
		horizontalBarModel.setAnimate(true);

		horizontalBarModel.setExtender("skinHorizantalBar");

		return horizontalBarModel;
	}

	
	/**
	 * Create pie model by reportType
	 * @param String reportType (Time, Cost, CostFormatTimeTrackingAccepted, TimeFormatTimeTrackingAccepted,
	 * CostFormatTimeTrackingNotAccepted, TimeFormatTimeTrackingNotAccepted FormatTimeTracking,
	 * TaskType, TaskDeadlineType, TaskStatus, TaskDeadlineStatus, TaskPriority, TaskDeadlinePriority)
	 * @param Date dateStart
	 * @param Date dateFinish
	 * @param ArrayList<UserEntity> users
	 * @return PieChartModel
	 * @author Di
	 */
	public PieChartModel createPieModel(String reportType, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		PieChartModel pieModel = new PieChartModel();
		List<Object[]> resultQuery;

		switch (reportType) {
		case "Time":
			resultQuery = taskTimeTrackingDao.countTimeByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

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
			break;

		case "Cost":

			resultQuery = taskTimeTrackingDao.countCostByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

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
			break;

		case "CostFormatTimeTrackingAccepted":

			resultQuery = taskTimeTrackingDao.countCostFormatTimeTrackingByProjectAcceptedAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Double) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}
			break;

		case "TimeFormatTimeTrackingAccepted":

			resultQuery = taskTimeTrackingDao.countTimeFormatTimeTrackingByProjectAcceptedAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Double) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}
			break;

		case "CostFormatTimeTrackingNotAccepted":

			resultQuery = taskTimeTrackingDao.countCostFormatTimeTrackingByProjectNotAcceptedAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Double) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}
			break;

		case "TimeFormatTimeTrackingNotAccepted":

			resultQuery = taskTimeTrackingDao.countTimeFormatTimeTrackingByProjectNotAcceptedAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Double) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}
			break;

		case "FormatTimeTracking":

			resultQuery = taskTimeTrackingDao.countFormatTimeTrackingByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}
			break;

		case "TaskType":

			resultQuery = taskDao.countTaskTypeByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}
			break;

		case "TaskDeadlineType":

			resultQuery = taskDao.countTaskDeadlineTypeByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}
			break;

		case "TaskStatus":

			resultQuery = taskDao.countTaskStatusByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}
			break;

		case "TaskDeadlineStatus":

			resultQuery = taskDao.countTaskDeadlineStatusByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}
			break;

		case "TaskPriority":

			resultQuery = taskDao.countTaskPriorityByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}
			break;

		case "TaskDeadlinePriority":

			resultQuery = taskDao.countTaskDeadlinePriorityByProjectAndUsers(projectServiceImpl.getSelectedCurrentProject(), dateStart, dateFinish, users);

			for (int i = 0; i < resultQuery.size(); i++) {

				pieModel.set((String) resultQuery.get(i)[0].toString(), (Long) resultQuery.get(i)[1]);
			}

			if (resultQuery.size() <= 0) {
				pieModel.set("Data will appear here", 0);
			}
			break;
		default:
			break;
		}

		pieModel.setLegendPosition("w");
		pieModel.setExtender("skinPie");

		return pieModel;
	}

	/**
	 * Init linear model
	 * @param String reportType
	 * @param Date dateStart
	 * @param Date dateFinish
	 * @param ArrayList<UserEntity> users
	 * @return LineChartModel
	 * @author Di
	 */
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

	/**
	 * Update report
	 * @param AjaxBehaviorEvent event
	 * @author Di
	 */
	public void reportUpdate(AjaxBehaviorEvent event) {

		org.springframework.webflow.execution.RequestContext requestContext = RequestContextHolder.getRequestContext();
		RequestControlContext rec = (RequestControlContext) requestContext;
		rec.handleEvent(new Event(this, "MenuReports"));
		return;
	}

	/**
	 * Update report
	 * @author Di
	 */
	public void reportUpdate() {

		org.springframework.webflow.execution.RequestContext requestContext = RequestContextHolder.getRequestContext();
		RequestControlContext rec = (RequestControlContext) requestContext;
		rec.handleEvent(new Event(this, "MenuReports"));
		return;
	}


	/**
	 * On flow process
	 * @param FlowEvent event
	 * @return String
	 * @author Di
	 */
	public String onFlowProcess(FlowEvent event) {  
		return event.getNewStep();  
	}

	/**
	 * Get report type
	 * @return String
	 * @author Di
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * Set report type
	 * @param String reportType
	 * @author Di
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	/**
	 * Get report data start
	 * @return Date
	 * @author Di
	 */
	public Date getReportDataStart() {
		return reportDataStart;
	}

	/**
	 * Set report data start
	 * @param Date reportDataStart
	 * @author Di
	 */
	public void setReportDataStart(Date reportDataStart) {
		this.reportDataStart = reportDataStart;
	}

	/**
	 * Get report data finish
	 * @return Date
	 * @author Di
	 */
	public Date getReportDataFinish() {
		return reportDataFinish;
	}

	
	/**
	 * Set report data finish
	 * @param Date reportDataFinish
	 * @author Di
	 */
	public void setReportDataFinish(Date reportDataFinish) {
		this.reportDataFinish = reportDataFinish;
	}

	/**
	 * Get report user
	 * @return UserEntity
	 * @author Di
	 */
	public UserEntity getReportUser() {
		return reportUser;
	}

	/**
	 * Set report user
	 * @param UserEntity reportUser
	 * @author Di
	 */
	public void setReportUser(UserEntity reportUser) {
		this.reportUser = reportUser;
	}

	/**
	 * Get report users
	 * @return List<UserEntity>
	 * @author Di
	 */
	public List<UserEntity> getReportUsers() {
		return reportUsers;
	}

	/**
	 * Set report users
	 * @param List<UserEntity> reportUsers
	 * @author Di
	 */
	public void setReportUsers(List<UserEntity> reportUsers) {
		this.reportUsers = reportUsers;
	}

	/**
	 * Get selected report users
	 * @return List<UserEntity>
	 * @author Di
	 */
	public List<UserEntity> getSelectedReportUsers() {
		return selectedReportUsers;
	}

	/**
	 * Set selected report users
	 * @param List<UserEntity> selectedReportUsers
	 * @author Di
	 */
	public void setSelectedReportUsers(List<UserEntity> selectedReportUsers) {
		if(selectedReportUsers.isEmpty()){
			selectedReportUsers.add(userServiceImpl.getCurrentUser());
		}
		this.selectedReportUsers = selectedReportUsers;
		updateSelectedValuesReportUsersDisplayString();
	}

	/**
	 * Get selected values report users display string
	 * @return String
	 * @author Di
	 */
	public String getSelectedValuesReportUsersDisplayString() {
		return selectedValuesReportUsersDisplayString;
	}

	/**
	 * Set selected values report users display string
	 * @param String selectedValuesReportUsersDisplayString
	 * @author Di
	 */
	public void setSelectedValuesReportUsersDisplayString(String selectedValuesReportUsersDisplayString) {
		this.selectedValuesReportUsersDisplayString = selectedValuesReportUsersDisplayString;
	}

	/**
	 * Update selected values report users display string
	 * @author Di
	 */
	public void updateSelectedValuesReportUsersDisplayString(){

		String currentUsersDisplayString = getSelectedReportUsers().toString();
		currentUsersDisplayString.replace('[', ' ');
		currentUsersDisplayString.replace(']', ' ');
		if (currentUsersDisplayString.length() > 20){
			currentUsersDisplayString = currentUsersDisplayString.substring(0, 19) + "..."; 
		}
		setSelectedValuesReportUsersDisplayString(currentUsersDisplayString);
	}

	/**
	 * Get user service implement
	 * @return UserServiceImpl
	 * @author Di
	 */
	public UserServiceImpl getUserServiceImpl() {
		return userServiceImpl;
	}

	/**
	 * Set user service implement
	 * @param UserServiceImpl userServiceImpl
	 * @author Di
	 */
	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	/**
	 * Get project service implement
	 * @return ProjectServiceImpl
	 * @author Di
	 */
	public ProjectServiceImpl getProjectServiceImpl() {
		return projectServiceImpl;
	}

	/**
	 * Set project service implement
	 * @param ProjectServiceImpl projectServiceImpl
	 * @author Di
	 */
	public void setProjectServiceImpl(ProjectServiceImpl projectServiceImpl) {
		this.projectServiceImpl = projectServiceImpl;
	}

	/**
	 * Get TaskDao
	 * @return TaskDao
	 * @author Di
	 */
	public TaskDao getTaskDao() {
		return taskDao;
	}

	/**
	 * Set TaskDao
	 * @param TaskDao taskDao
	 * @author Di
	 */
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	/**
	 * Get TaskTimeTrackingDao
	 * @return TaskTimeTrackingDao
	 * @author Di
	 */
	public TaskTimeTrackingDao getTaskTimeTrackingDao() {
		return taskTimeTrackingDao;
	}

	/**
	 * Set TaskTimeTrackingDao
	 * @param TaskTimeTrackingDao taskTimeTrackingDao
	 * @author Di
	 */
	public void setTaskTimeTrackingDao(TaskTimeTrackingDao taskTimeTrackingDao) {
		this.taskTimeTrackingDao = taskTimeTrackingDao;
	}

	/**
	 * Get TaskHistoryDao
	 * @return TaskHistoryDao
	 * @author Di
	 */
	public TaskHistoryDao getTaskHistoryDao() {
		return taskHistoryDao;
	}

	/**
	 * Set TaskHistoryDao
	 * @param taskHistoryDao
	 * @author Di
	 */
	public void setTaskHistoryDao(TaskHistoryDao taskHistoryDao) {
		this.taskHistoryDao = taskHistoryDao;
	}

}
