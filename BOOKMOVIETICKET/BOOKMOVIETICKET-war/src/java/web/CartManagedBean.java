/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.CartItem;
import static entity.Movie_.name;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import session.CartRemote;

/**
 *
 * @author Jason
 */
@Named(value = "cartManagedBean")
@SessionScoped
public class CartManagedBean implements Serializable {

    private List<CartItem> itemList= new ArrayList<>();
    @EJB
    private CartRemote cart;
    private String userId;
    private String userName;
    private String userEmail;
    private int itemId;
    private String movieName;
    private BigDecimal unitPrice;
    private int quantity;
    private int numberOfItem;
    private BigDecimal total;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getNumberOfItem() {
        return numberOfItem;
    }

    public void setNumberOfItem(int numberOfItem) {
        this.numberOfItem = numberOfItem;
    }

    public List<CartItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<CartItem> itemList) {
        this.itemList = itemList;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private BigDecimal subTotal;

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public CartManagedBean() {
        this.cart = null;
        this.itemId = 0;
        this.movieName = null;
        this.unitPrice = null;
        this.quantity = 0;
        this.total = BigDecimal.ZERO;
    }

    public CartRemote getCart() {
        return cart;
    }

    public void setCart(CartRemote cart) {
        this.cart = cart;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String addItemtoCart() {
        // all information seems to be valid
        // try add the employee
        if (quantity == 0) {
            return "failure";
        } else {
            CartItem item = new CartItem(itemId, movieName, unitPrice, quantity);
            boolean result = cart.addCartItem(item);
            if (result) {
                return "success";
            } else {
                return "failure";
            }
        }
    }
    

    public String removeItem() {
        if (cart.deleteCartItem(itemId)) {
            return "success";
        } else {
            return "failure";
        }
    }

    public void displayCart() {
        itemList = cart.getCart();
        numberOfItem = itemList.size();
        if (itemList.isEmpty()) {
            System.out.println("The shopping cart is empty!");
            return;
        }
        System.out.println("Your shopping cart has the following items:");
        total = BigDecimal.ZERO;
        for (CartItem item : itemList) {
            unitPrice = item.getUnitPrice();
            quantity = item.getQuantity();
            subTotal = BigDecimal.valueOf(quantity).multiply(unitPrice);
            System.out.println("Item: " + item.getMovieName()
                    + "\tUnit Price: " + item.getUnitPrice()
                    + "\tQuantity: " + item.getQuantity()
                    + "\tSub-Total: " + subTotal);
            total = total.add(subTotal);
        }
        System.out.println("---");
        System.out.println("Total price: " + total);
        System.out.println("----End of Shopping Cart---");
    }

    public String displayItem() {
        if (name == null) {
            return "failure";
        } else {
            return "success";
        }
    }
    
    public String confirm() {
        itemList = cart.getCart();
        if (userName == null || itemList.size() < 1) {
            return "failure";
        } else {
            sendEmailToUser();
            return "success";
        }
    }


    public void sendEmailToUser() {
        itemList = cart.getCart();
        String smtpServer = "smtp.office365.com";
        String from = "101985894@student.swin.edu.au";
        String to = this.userEmail;
        String subject = "BOOK MOVIE TICKET ONLINE RECEIPT";
        String pass = "G@ld051199";
        String emailUser = from;
        // Generate recovery code for changing password
        total = BigDecimal.ZERO;
        String body = "Hi " + userName + " ,\nThis is your order details: ";
        for (CartItem item : itemList) {
            unitPrice = item.getUnitPrice();
            quantity = item.getQuantity();
            subTotal = BigDecimal.valueOf(quantity).multiply(unitPrice);
            body += "\nItem: " + item.getMovieName()
                    + "\tUnit Price: " + item.getUnitPrice()
                    + "\tQuantity: " + item.getQuantity()
                    + "\tSub-Total: " + subTotal;
            total = total.add(subTotal);
        }
        body += "\n---";
        body += "\nTotal price: " + total;
        body += "\nRegards,\nBooking Movie Ticket Online Company\n";
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
