/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Jason
 */
public class MovieDTO implements Serializable {
    private final int id;
    private final String name;
    private final String genre;
    private final String duration;
    private final String description;
    private final String imgfile;
    private final String videofile;
    private final BigDecimal price;

    public MovieDTO(int id, String name, String genre, String duration, String description, String imgfile, String videofile, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.duration = duration;
        this.description = description;
        this.imgfile = imgfile;
        this.videofile = videofile;
        this.price = price;
    }

    public String getVideofile() {
        return videofile;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public String getImgfile() {
        return imgfile;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
}

