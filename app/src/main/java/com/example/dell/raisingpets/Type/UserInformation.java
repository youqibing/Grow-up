package com.example.dell.raisingpets.Type;

import com.example.dell.raisingpets.Whole.NetWork.BaseInformation;
import com.example.dell.raisingpets.Whole.NetWork.BaseRequestData;

/**
 * Created by dell on 2016/8/15.
 */

public class UserInformation extends BaseInformation {

    String name;
    String head;
    int HP;
    int hungry_point;
    int today_pace_num;
    int all_pace_num;
    int money;
    int state;
    int live_days;
    String background;
    String character;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getHungry_point() {
        return hungry_point;
    }

    public void setHungry_point(int hungry_point) {
        this.hungry_point = hungry_point;
    }

    public int getToday_pace_num() {
        return today_pace_num;
    }

    public void setToday_pace_num(int today_pace_num) {
        this.today_pace_num = today_pace_num;
    }

    public int getAll_pace_num() {
        return all_pace_num;
    }

    public void setAll_pace_num(int all_pace_num) {
        this.all_pace_num = all_pace_num;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getLive_days() {
        return live_days;
    }

    public void setLive_days(int live_days) {
        this.live_days = live_days;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    /*
    public class background{
        String id;
        String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public class character{
        String id;
        String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
    */

/*
    public static class UserRequestData extends BaseRequestData{

        Data data;

        static class Data{
            UserInformation userInformation;
            String token;

            public UserInformation getUserInformation(){
                return userInformation;
            }

            public String getToken(){
                return token;
            }
        }

        public UserInformation getUserInformation(){
            if(data != null){
                return data.getUserInformation();
            }else {
                return null;
            }
        }

        public String getToken(){
            if(data != null){
                return data.getToken();
            }else {
                return null;
            }
        }
    }

    */

}
