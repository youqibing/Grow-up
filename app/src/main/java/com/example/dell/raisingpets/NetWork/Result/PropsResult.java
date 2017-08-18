package com.example.dell.raisingpets.NetWork.Result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 16-11-20.
 */

public class PropsResult {

    private List<PropsResult.PropsList> props;

    public List<PropsResult.PropsList> getBackgroud(){
        return props;
    }

    public void setCharacters(List<PropsResult.PropsList> props){
        this.props = props;
    }

    public static class PropsList implements Serializable {
        String name;
        String describe;
        String image;
        String price;
        String HP;
        String hungry_point;
        String category;
        String com_id;

        //private boolean isLocked;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getHP() {
            return HP;
        }

        public void setHP(String HP) {
            this.HP = HP;
        }

        public String getHungry_point() {
            return hungry_point;
        }

        public void setHungry_point(String hungry_point) {
            this.hungry_point = hungry_point;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCom_id() {
            return com_id;
        }

        public void setCom_id(String com_id) {
            this.com_id = com_id;
        }

    }
}
