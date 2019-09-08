/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.AppuserDTO;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import session.UserFacadeRemote;

/**
 *
 * @author Jason
 */
@Named(value = "userManagedBean")
@RequestScoped
public class UserManagedBean {

    @EJB
    private UserFacadeRemote userFacade;
    @Inject
    private Conversation conversation;

    private String newPassword;
    private String id;
    private String name;
    private String phone;
    private String address;
    private String email;
    private String password;
    private String appGroup;

    private String confirmPassword;

    private String PIN; // Pin prompted from website user
    private String recoveryCode; // PIN sent to user email

    public UserManagedBean() {
        this.id = null;
        this.name = null;
        this.phone = null;
        this.address = null;
        this.email = null;
        this.password = null;
        this.appGroup = null;
        this.confirmPassword = null;
        this.PIN = null;
        this.recoveryCode = null;
        this.newPassword = null;
    }
    
    public String encryptPassword (String password)
    {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String s = bigInt.toString(16);
            System.out.print("Encrypted: "+ s);
            return s;
            
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e){ 
        }
        return "encryption error";
    }
  

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @PostConstruct
    public void init() {
        id = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getRecoveryCode() {
        return recoveryCode;
    }

    public void setRecoveryCode(String recoveryCode) {
        this.recoveryCode = recoveryCode;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Creates a new instance of UserManagedBean
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppGroup() {
        return appGroup;
    }

    public void setAppGroup(String appGroup) {
        this.appGroup = appGroup;
    }

    //---------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------
    public String addUser() {

        encryptPassword(password);
        System.out.print("sadasdsadasda");
        // check empId is null
        if (isNull(id)) {
            return "debug";
        }

        // all information seems to be valid
        // try add the employee
        appGroup = "ED-APP-USERS";
        String pa = this.encryptPassword(password);
        AppuserDTO userDTO = new AppuserDTO(id, name, phone,
                address, email, pa, appGroup);
        boolean result = userFacade.createRecord(userDTO);
        if (result) {
            return "success";
        } else {
            return "failure";
        }
    }
    
    public String addAdmin() {

        // check empId is null
        if (isNull(id)) {
            return "debug";
        }

        // all information seems to be valid
        // try add the employee
        appGroup = "ED-APP-ADMIN";
        AppuserDTO userDTO = new AppuserDTO(id, name, phone,
                address, email, password, appGroup);
        boolean result = userFacade.createRecord(userDTO);
        if (result) {
            return "success";
        } else {
            return "failure";
        }
    }

    public String setDetailsForChange() {
        // check empId is null
        if (isNull(id) || conversation == null) {
            return "debug";
        }

        if (!userFacade.checkUser(id)) {
            return "failure";
        }

        // get employee details
        return setDetails();
    }

    public String editDetails() {
        // check empId is null
        if (isNull(id)) {
            return "debug";
        }

        AppuserDTO userDTO = new AppuserDTO(id, name, phone,
                address, email, password, appGroup);
        boolean result = userFacade.updateRecord(userDTO);

        if (result) {
            return "success";
        } else {
            return "failure";
        }
    }

    public void validateNewPassword(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {
        // get new password
        String oldPwd = (String) value;

        // get old password
        UIInput newPwdComponent = (UIInput) componentToValidate.getAttributes().get("newpwd");
        String newPwd = (String) newPwdComponent.getSubmittedValue();

        if (oldPwd.equals(newPwd)) {
            FacesMessage message = new FacesMessage(
                    "Old Password and New Password are the same! They must be different.");
            throw new ValidatorException(message);
        }

    }

    public void validatePasswordPair(FacesContext context,
            UIComponent componentToValidate,
            Object pwdValue) throws ValidatorException {

        // get password
        String pwd = (String) pwdValue;

        // get confirm password
        UIInput cnfPwdComponent = (UIInput) componentToValidate.getAttributes().get("cnfpwd");
        String cnfPwd = (String) cnfPwdComponent.getSubmittedValue();

        System.out.println("password : " + pwd);
        System.out.println("confirm password : " + cnfPwd);

        if (!pwd.equals(cnfPwd)) {
            FacesMessage message = new FacesMessage(
                    "Password and Confirm Password are not the same! They must be the same.");
            throw new ValidatorException(message);
        }
    }

    public void validateNewPasswordPair(FacesContext context,
            UIComponent componentToValidate,
            Object newValue) throws ValidatorException {

        // get new password
        String newPwd = (String) newValue;

        // get confirm password
        UIInput cnfPwdComponent = (UIInput) componentToValidate.getAttributes().get("cnfpwd");
        String cnfPwd = (String) cnfPwdComponent.getSubmittedValue();

        System.out.println("new password : " + newPwd);
        System.out.println("confirm password : " + cnfPwd);

        if (!newPwd.equals(cnfPwd)) {
            FacesMessage message = new FacesMessage(
                    "New Password and Confirm New Password are not the same! They must be the same.");
            throw new ValidatorException(message);
        }
    }

    public String editPassword() {
        // check empId is null
        if (isNull(id)) {
            return "debug";
        }

        // newPassword and confirmPassword are the same - checked by the validator during input to JSF form
        boolean result = userFacade.updatePassword(id, newPassword);

        System.out.println("result = " + result);

        if (result) {
            return "success";
        } else {
            return "failure";
        }
    }

    public String deleteUser() {
        // check empId is null
        if (isNull(id)) {
            return "debug";
        }
        boolean result = userFacade.deleteRecord(id);
        if (result) {
            return "success";
        } else {
            return "failure";
        }
    }

    public String displayDetails() {
        // check empId is null
        if (isNull(id)) {
            return "debug";
        }

        return setDetails();
    }

    private boolean isNull(String s) {
        return (s == null);
    }

    private String setDetails() {

        if (isNull(id)) {
            return "debug";
        }

        AppuserDTO user = userFacade.getRecord(id);

        if (user == null) {
            // no such employee
            return "failure";
        } else {
            // found - set details for display
            this.id = user.getId();
            this.name = user.getName();
            this.phone = user.getPhone();
            this.address = user.getAddress();
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.appGroup = user.getAppGroup();
            return "success";
        }
    }

    public void retrieveDetail(String userID) {

        AppuserDTO user = userFacade.getRecord(userID);

        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.appGroup = user.getAppGroup();

    }

    public String checkUserEmail() {
        // check empId is null
        if (isNull(id)) {
            return "debug";
        }
        AppuserDTO user = userFacade.getRecord(id);
        this.name = user.getName();
        if (email.equals(user.getEmail())) {
            sendEmailToUser();
            return "success";
        } else {
            return "failure";
        }
    }

    public String checkRecoveryCode() {
        if (PIN.equals(recoveryCode)) {
            return "success";
        } else {
            return "failure";
        }
    }

    public void sendEmailToUser() {
        String smtpServer = "smtp.office365.com";
        String from = "101985894@student.swin.edu.au";
        String to = this.email;
        String subject = "BOOK MOVIE TICKET ONLINE RECOVERY PASSWORD";
        String pass = "G@ld051199";
        String emailUser = from;
        // Generate recovery code for changing password
        int randomPIN = (int) (Math.random() * 9000) + 1000;
        recoveryCode = String.valueOf(randomPIN);

        this.setRecoveryCode(recoveryCode);
        this.setName(name);

        String body = "Hi " + name + " ,\nThis is your Recovery Code: " + recoveryCode + "\nRegards,\nBooking Movie Ticket Online Company\n";
        try {
            Properties props = System.getProperties();
            // --Attaching to   default Session, or we could start a new one --
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.port", 587);
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", true);
            // --prepare a password authenticator --
            MyAuthenticator myPA = new MyAuthenticator(emailUser, pass);
            // see MyAuthenticator class
            // get a session
            Session session = Session.getInstance(props, myPA);
            // --Create a new message --
            Message msg = new MimeMessage(session);
            // --Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            // --Set the subject and body text --
            msg.setSubject(subject);
            msg.setText(body);
            // --Set some other header information --
            msg.setHeader("X-Mailer", "Swinburne");
            msg.setSentDate(new Date());
            // --Send the message --
            Transport.send(msg);
            Transport.send(msg, emailUser, pass);
            System.out.println("Message sent OK.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public class MyAuthenticator extends Authenticator {

        PasswordAuthentication mypa;

        public MyAuthenticator(String username, String password) {
            mypa = new PasswordAuthentication(username, password);
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return mypa;
        }
    }

}
