package com.example.grocerycounter;

class ShopObject {
    int id;
    String name;
    String date;
    int amount;
    float rate;

    public ShopObject(int id,String name,  int amount, float rate,String date) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.rate = rate;
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
