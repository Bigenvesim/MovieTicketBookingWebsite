/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.CartItem;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author Jason
 */
@Remote
public interface CartRemote {

    ArrayList<CartItem> getCart();

    public boolean addCartItem(CartItem cartItem);

    boolean deleteCartItem(int itemId);

    boolean updateCartItem(CartItem cartItem);

}
