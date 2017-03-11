package view;

import database.hibernate.model.User;
import database.hibernate.service.UserService;
import database.util.SessionUtils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class LoginView implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;
	
	private String password;
	private String message;
	private String userName;

    private User user = new User();

	@ManagedProperty("#{userService}")
	private UserService userService;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	//validate login
	public String validateUsernamePassword() {
//		boolean valid = LoginDAO.validate(userName, password);

		boolean valid = userService.authenticateUser(userName,password);
		if (valid) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", userName);
			return "index";
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Passowrd",
							"Please enter correct username and Password"));
			return "login";
		}
	}

	//logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "login";
	}

    public void logoutButton(ActionEvent actionEvent) {
        logout();
    }

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
