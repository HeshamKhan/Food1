package com.food.foodonroad;

public class foodclass {
    private int Idfood;
    private String nfood;
    private int idrest;
    private double price;
    private String maxtime;
    private String description;
    private String image;
    public foodclass()
    {

    }
    public foodclass(int p1,String p2,int p3,double p4,String p5,String p6,String p7){
         Idfood=p1;
         nfood=p2;
         idrest=p3;
        price=p4;
         maxtime=p5;
         description=p6;
         image=p7;
    }

    public int GetIdfood()    {        return(Idfood);    }
    public void SetIdfood(int p)    {        Idfood=p;    }

    public String  Getnfood()    {        return(nfood);    }
    public void Setnfood(String p)    {        nfood=p;    }

    public int  Getidrest()    {        return(idrest);    }
    public void Setidrest(int p)    {        idrest=p;    }

    public double  Getprice()    {        return(price);    }
    public void Setprice(double p)    {        price=p;    }

    public String  Getmaxtime()    {        return(maxtime);    }
    public void Setmaxtime(String p)    {        maxtime=p;    }

    public String  Getdescription()    {        return(description);    }
    public void Setdescription(String p)    {        description=p;    }

    public String  Getimage()    {        return(image);    }
    public void Setimage(String p)    {        image=p;    }

}
