package com.comic.entidades;

import lombok.Getter;
import lombok.Setter;

public class UserBuyer {
    @Getter @Setter
    private String title;
    @Getter @Setter
    private int quantity;
    @Getter @Setter
    private int unit_price;
}
