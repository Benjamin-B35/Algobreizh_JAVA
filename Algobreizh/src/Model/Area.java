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
public class Area {
    
    private int area_id;
    private String area_name;
    
    public Area(int area_id, String area_name) {
        this.area_id = area_id;
        this.area_name = area_name;
    }

    public Area(String area_name) {
        this.area_name = area_name;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }
    
    
    
}
