/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.MovieDTO;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;
import session.MovieFacadeRemote;

/**
 *
 * @author Jason
 */
@Named(value = "movieManagedBean")
@SessionScoped
public class MovieManagedBean implements Serializable {

    @Inject
    private Conversation conversation;
    @EJB
    private MovieFacadeRemote movieFacade;

    private int id;
    private String name;
    private String genre;
    private String duration;
    private String description;
    private String imgfile;
    private String videofile;
    private BigDecimal price;

    private List<MovieDTO> movieList = new ArrayList<>();

    public List<MovieDTO> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<MovieDTO> movieList) {
        this.movieList = movieList;
    }

    public void MovieList() {
        movieList = movieFacade.movieList();
    }

    
  
    
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Creates a new instance of MovieManagedBean
     */
    public MovieManagedBean() {
        this.id = 0;
        this.name = null;
        this.genre = null;
        this.duration = null;
        this.description = null;
        this.imgfile = null;
        this.videofile = null;
        this.price = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgfile() {
        return imgfile;
    }

    public void setImgfile(String imgfile) {
        this.imgfile = imgfile;
    }

    public String getVideofile() {
        return videofile;
    }

    public void setVideofile(String videofile) {
        this.videofile = videofile;
    }

    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    public String addMovie() {

        // all information seems to be valid
        // try add the employee
        MovieDTO movieDTO = new MovieDTO(id, name, genre,
                duration, description, imgfile, videofile, price);
        boolean result = movieFacade.createRecord(movieDTO);
        if (result) {
            return "success";
        } else {
            return "failure";
        }
    }

    public String editDetails() {
        // check empId is null
        MovieDTO movieDTO = new MovieDTO(id, name, genre, duration, description, imgfile, videofile, price);
        boolean result = movieFacade.updateRecord(movieDTO);

        if (result) {
            return "success";
        } else {
            return "failure";
        }
    }

    public String deleteMovie() {
        // check empId is null
        boolean result = movieFacade.deleteRecord(id);
        if (result) {
            return "success";
        } else {
            return "failure";
        }
    }

    public String checkMovie() {
        if (movieFacade.checkMovie(id)) {
            return "success";
        }
        return "failure";
    }

    public String displayMovieInfo() {
        return setMovieDetails();
    }

    private String setMovieDetails() {

        if (id == 0 || conversation == null) {
            return "debug";
        }

        MovieDTO movie = movieFacade.getRecord(id);

        if (movie == null) {
            // no such employee
            return "failure";
        } else {
            // found - set details for display
            this.id = movie.getId();
            this.name = movie.getName();
            this.genre = movie.getGenre();
            this.duration = movie.getDuration();
            this.description = movie.getDescription();
            this.imgfile = movie.getImgfile();
            this.videofile = movie.getVideofile();
            this.price = movie.getPrice();
            return "success";
        }
    }

}
