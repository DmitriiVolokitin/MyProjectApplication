package com.MyProject.ui.utils;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
//import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Helper util to assist in user interface
 * 
 * @author Di
 */
public class UIUtils implements Serializable {
	private static final long serialVersionUID = 7872083365595569634L;

	private int viewLoadCount = 0;
	
	public void greetOnViewLoad(ComponentSystemEvent event) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (viewLoadCount < 1 && !context.isPostback()) {
//			String firstName = (String) event.getComponent().getAttributes().get("firstName");
//			String lastName = (String) event.getComponent().getAttributes().get("lastName");
			
//			FacesMessage message = new FacesMessage(String.format("Welcome to your account %s %s", firstName, lastName));
//			context.addMessage("growlMessages", message);
			
//			ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
//			HttpServletResponse response = (HttpServletResponse)ectx.getResponse();
//			HttpSession session = (HttpSession)ectx.getSession(true);
//			session.invalidate();
			
//			ServletRequestAttributes attr = (ServletRequestAttributes) 
//					RequestContextHolder.currentRequestAttributes();
//				HttpSession session= attr.getRequest().getSession(true);
			
			viewLoadCount++;
			
		}
	}
}
