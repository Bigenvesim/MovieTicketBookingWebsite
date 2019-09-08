/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.MovieDTO;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Jason
 */
@Remote
public interface MovieFacadeRemote {

    boolean checkMovie(int id);

    boolean createRecord(MovieDTO movieDTO);

    MovieDTO getRecord(int id);

    boolean updateRecord(MovieDTO movieDTO);

    boolean deleteRecord(int id);

    List<MovieDTO> movieList();

}
