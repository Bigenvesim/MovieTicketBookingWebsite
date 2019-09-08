/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.CartItem;
import java.util.ArrayList;
import javax.ejb.Remove;
import javax.ejb.Stateful;

/**
 *
 * @author Jason
 */
@Stateful
public class Cart implements CartRemote {

  
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private ArrayList<CartItem> cart;

    public Cart() {
        cart = new ArrayList<CartItem>();
    }

    private boolean add(CartItem cartItem) {
        boolean result = false;
        try {
            result = cart.add(cartItem);
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    @Remove
    public void remove() {
        cart = null;
    }

    @Override
    public ArrayList<CartItem> getCart() {
        return cart;
    }

    @Override
    public boolean addCartItem(CartItem cartItem) {
        if (cart.isEmpty())
        {
            return add(cartItem);
        }
        try {
            for (CartItem item : cart) {
                if (item.getItemId() == cartItem.getItemId()) {
                    item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                    return true;
                } else {
                    return add(cartItem);
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteCartItem(int itemId) {
        for(CartItem item : cart)
        {
            if(item.getItemId() == itemId)
            {
                cart.remove(item);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateCartItem(CartItem cartItem) {
        for(CartItem item : cart)
        {
            if(item.getItemId() == cartItem.getItemId())
            {
                cart.remove(item);
                item = cartItem;
                cart.add(item);
                return true;
            }
        }
        return false;
    }
    
    
}
