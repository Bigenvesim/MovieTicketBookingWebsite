/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Appuser;
import entity.AppuserDTO;
import entity.Movie;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jason
 */
@Stateless
public class UserFacade implements UserFacadeRemote {

    @PersistenceContext(unitName = "BOOKMOVIETICKET-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    private void create(Appuser user) {
        em.persist(user);
    }

    private void edit(Appuser user) {
        em.merge(user);
    }

    private void remove(Appuser user) {
        em.remove(em.merge(user));
    }

    private Appuser find(Object id) {
        return em.find(Appuser.class, id);
    }

    private Appuser myDTO2DAO(AppuserDTO userDTO) {
        Appuser user = new Appuser();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAppgroup(userDTO.getAppGroup());
        return user;
    }

    @Override
    public boolean checkUser(String userID) {
        if (find(userID) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean createRecord(AppuserDTO userDTO) {
        if (find(userDTO.getId()) != null) {
// user whose userid can be found
            return false;
        }
// user whose userid could not be found
        try {
            Appuser user = this.myDTO2DAO(userDTO);
            this.create(user); // add to database
            return true;
        } catch (Exception ex) {
            return false; // something is wrong, should not be here though
        }
    }

    private AppuserDTO myDAO2DTO(Appuser user) {
        String id = user.getId();
        String name = user.getName();
        String phone = user.getPhone();
        String address = user.getAddress();
        String email = user.getEmail();
        String password = user.getPassword();
        String appgroup = user.getAppgroup();
        AppuserDTO userDTO = new AppuserDTO(id, name, phone, address, email, password, appgroup);
        return userDTO;
    }

    @Override
    public AppuserDTO getRecord(String userID) {
        Appuser user = new Appuser();
        user = find(userID);
        if (user != null) {
            // can find a customer with the same custid

            AppuserDTO userDTO = myDAO2DTO(user);
            // System.out.println(id + userName + userPass + userEmail + userPhone + userAdd + qn + ans);
            return userDTO;
        } else {
            System.out.println("no user found");
            return null;
        }
    }

    @Override
    public boolean updateRecord(AppuserDTO userDTO) {
        boolean result = false;

        Appuser user = em.find(Appuser.class, userDTO.getId());

        if (user == null) {
            result = false;
        } else {
            try {
                user = this.myDTO2DAO(userDTO);
                em.merge(user);
                result = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public boolean updatePassword(String id, String password) {
        // find the employee
        Appuser user = find(id);

        // check again - just to play it safe
        if (user == null) {
            return false;
        }

        // only need to update the password field
        user.setPassword(password);
        return true;
    }

    @Override
    public boolean deleteRecord(String userID) {
        boolean result = false;

        Appuser user = em.find(Appuser.class, userID);

        if (user == null) {
            // cannot find a customer - cannot delete
            result = false;
        } else {
            // can now remove the customer found
            try {
                em.remove(user);

                result = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
