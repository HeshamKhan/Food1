package com.food.foodonroad;

public class orderclass {

    private int Idfood;
    private String nfood;
    private double price;
    private String maxtime;
    private int qyt;


    public int GetIdfood()    {        return(Idfood);    }
    public void SetIdfood(int p)    {        Idfood=p;    }

    public String  Getnfood()    {        return(nfood);    }
    public void Setnfood(String p)    {        nfood=p;    }

    public double  Getprice()    {        return(price);    }
    public void Setprice(double p)    {        price=p;    }

    public String  Getmaxtime()    {        return(maxtime);    }
    public void Setmaxtime(String p)    {        maxtime=p;    }

    public int  Getqyt()    {        return(qyt);    }
    public void Setqyt(int p)    {        qyt=p;    }
}
