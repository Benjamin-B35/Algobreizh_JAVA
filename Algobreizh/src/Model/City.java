/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author braul
 */
public class City {
    
    private int city_id;
    private String city_name;
    private Area Area;

    public City(int city_id, String city_name, Area area) {
        this.city_id = city_id;
        this.city_name = city_name;
        this.Area = area;
    }

    public City(String city_name, Area Area) {
        this.city_name = city_name;
        this.Area = Area;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Area getArea() {
        return Area;
    }

    public void setArea(Area area) {
        this.Area = area;
    }
    
    
}
