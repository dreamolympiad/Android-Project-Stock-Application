package com.example.sudeshna.stocksearch;

/**
 * Created by Dip Patel on 05/04/2016.
 */
public class Favorites {
    private String symbol;
    private String company_name;
    private String stock_price;
    private String changepercent;
    private String marketcap;

    public Favorites(String symbol,String company_name,String stock_price,String changepercent,String marketcap){
        this.symbol=symbol;
        this.company_name=company_name;
        this.stock_price=stock_price;
        this.changepercent=changepercent;
        this.marketcap=marketcap;
    }
    public String getSymbol() {
        return symbol;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getStock_price() {
        return stock_price;
    }

    public String getChangepercent() {
        return changepercent;
    }

    public String getMarketcap() {
        return marketcap;
    }
}
