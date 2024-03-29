package com.trip.tripjava.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tourist")
public class TouristEntity {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contentid")
    private String contentid;

    // 관광타입(12:관광지, 32:숙박, 39:음식점)
    @Column(name = "contenttypeid")
    private String contenttypeid;

    @Column(name = "title")
    private String title;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "areacode")
    private String areacode;

    @Column(name = "sigungucode")
    private String sigungucode;

    @Column(name = "firstimage")
    private String firstimage;

    /*
    @Column(name = "overview")
    private String overview;
    */

    @Column(name = "mapx")
    private String mapx;

    @Column(name = "mapy")
    private String mapy;

    /*
    @Column(name = "cat1")
    private String cat1;

    @Column(name = "cat2")
    private String cat2;

    @Column(name = "cat3")
    private String cat3;
    */
}
