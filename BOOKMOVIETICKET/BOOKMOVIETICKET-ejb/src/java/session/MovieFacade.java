/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Movie;
import entity.MovieDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Jason
 */
@Stateless
public class MovieFacade implements MovieFacadeRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "BOOKMOVIETICKET-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    private void create(Movie movie) {
        //em.createQuery("Select m from Movie m");
        em.persist(movie);
    }

    private void edit(Movie movie) {
        em.merge(movie);
    }

    private void remove(Movie movie) {
        em.remove(em.merge(movie));
    }

    private Movie find(Object id) {
        return em.find(Movie.class, id);
    }

    @Override
    public List<MovieDTO> movieList() {
        List<Movie> listMovie = em.createNamedQuery("Movie.findAll").getResultList();
        List<MovieDTO> listMovieDTO = new ArrayList<>();

        for (Movie movie : listMovie) {
            MovieDTO movieDTO = myDAO2DTO(movie);
            listMovieDTO.add(movieDTO);
        }
        return listMovieDTO;
    }

    private Movie myDTO2DAO(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setId(movieDTO.getId());
        movie.setName(movieDTO.getName());
        movie.setGenre(movieDTO.getGenre());
        movie.setDuration(movieDTO.getDuration());
        movie.setDescription(movieDTO.getDescription());
        movie.setImagefile(movieDTO.getImgfile());
        movie.setVideofile(movieDTO.getVideofile());
        movie.setPrice(movieDTO.getPrice());
        return movie;
    }

    @Override
    public boolean checkMovie(int id) {
        if (find(id) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean createRecord(MovieDTO movieDTO) {
        if (find(movieDTO.getId()) != null) {
// user whose userid can be found
            return false;
        }
// user whose userid could not be found
        try {
            Movie movive = this.myDTO2DAO(movieDTO);
            this.create(movive); // add to database
            return true;
        } catch (Exception ex) {
            return false; // something is wrong, should not be here though
        }
    }

    private MovieDTO myDAO2DTO(Movie movie) {
        int id = movie.getId();
        String name = movie.getName();
        String genre = movie.getGenre();
        String duration = movie.getDuration();
        String description = movie.getDescription();
        String imgfile = movie.getImagefile();
        String videofile = movie.getVideofile();
        BigDecimal price = movie.getPrice();
        MovieDTO movieDTO = new MovieDTO(id, name, genre, duration, description, imgfile, videofile, price);
        return movieDTO;
    }

    @Override
    public MovieDTO getRecord(int id) {
        Movie movie = new Movie();
        movie = find(id);
        if (movie != null) {
            // can find a customer with the same custid

            MovieDTO movieDTO = myDAO2DTO(movie);
            // System.out.println(id + userName + userPass + userEmail + userPhone + userAdd + qn + ans);
            return movieDTO;
        } else {
            System.out.println("no movie found");
            return null;
        }
    }

    @Override
    public boolean updateRecord(MovieDTO movieDTO) {
        boolean result = false;

        Movie movie = em.find(Movie.class, movieDTO.getId());

        if (movie == null) {
            result = false;
        } else {
            try {
                movie = this.myDTO2DAO(movieDTO);
                em.merge(movie);
                result = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public boolean deleteRecord(int id) {
        boolean result = false;

        Movie movie = em.find(Movie.class, id);

        if (movie == null) {
            // cannot find a customer - cannot delete
            result = false;
        } else {
            // can now remove the customer found
            try {
                em.remove(movie);

                result = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}
