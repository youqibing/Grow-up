package com.example.dell.raisingpets.NetWork.Result;

/**
 * Created by root on 16-12-19.
 */

public class PurchaseFoodResult {
    int money;
    int HP;
    int hungry_point;

    public int getHungry_point() {
        return hungry_point;
    }

    public void setHungry_point(int hungry_point) {
        this.hungry_point = hungry_point;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

}
