/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.AppuserDTO;
import javax.ejb.Remote;

/**
 *
 * @author Jason
 */
@Remote
public interface UserFacadeRemote {

    public boolean checkUser(String userID);

    public boolean createRecord(AppuserDTO userDTO);

    public AppuserDTO getRecord(String userID);

    public boolean updateRecord(AppuserDTO userDTO);
    
    public boolean updatePassword(String id, String password);

    public boolean deleteRecord(String userID);

}
