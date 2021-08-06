package edu.wit.mobileapp.enjoyby;

import java.util.Date;

public class ListItem {
    // Maybe an image?
    public int Id;
    public String name;
    public Date purchaseDate;
    public Date expireDate;

    public class SQL {
        public static final String Table = "foods";
        public static final String Id = "id";
        public static final String Name = "name";
        public static final String PurchaseDate = "purchase_date";
        public static final String ExpireDate = "expire_date";
    }

}
