import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Stock {
    String symbol;
    double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Market {
    private ArrayList<Stock> stocks;

    public Market() {
        stocks = new ArrayList<>();
        stocks.add(new Stock("AAPL", 150.00));
        stocks.add(new Stock("GOOGL", 2800.00));
        stocks.add(new Stock("AMZN", 3500.00));
        stocks.add(new Stock("MSFT", 300.00));
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public Stock getStock(String symbol) {
        for (Stock stock : stocks) {
            if (stock.getSymbol().equalsIgnoreCase(symbol)) {
                return stock;
            }
        }
        return null;
    }
}

class Portfolio {
    private HashMap<String, Integer> holdings;
    private double cash;

    public Portfolio(double initialCash) {
        holdings = new HashMap<>();
        cash = initialCash;
    }

    public void buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (cost <= cash) {
            cash -= cost;
            holdings.put(stock.getSymbol(), holdings.getOrDefault(stock.getSymbol(), 0) + quantity);
            System.out.println("Bought " + quantity + " shares of " + stock.getSymbol());
        } else {
            System.out.println("Not enough cash to buy " + quantity + " shares of " + stock.getSymbol());
        }
    }

    public void sellStock(Stock stock, int quantity) {
        int currentHolding = holdings.getOrDefault(stock.getSymbol(), 0);
        if (currentHolding >= quantity) {
            cash += stock.getPrice() * quantity;
            holdings.put(stock.getSymbol(), currentHolding - quantity);
            System.out.println("Sold " + quantity + " shares of " + stock.getSymbol());
        } else {
            System.out.println("Not enough shares to sell " + quantity + " shares of " + stock.getSymbol());
        }
    }

    public void viewPortfolio(Market market) {
        System.out.println("Portfolio:");
        for (String symbol : holdings.keySet()) {
            int quantity = holdings.get(symbol);
            double price = market.getStock(symbol).getPrice();
            System.out.println(symbol + ": " + quantity + " shares at $" + price + " each");
        }
        System.out.println("Cash: $" + cash);
    }

    public double getTotalValue(Market market) {
        double totalValue = cash;
        for (String symbol : holdings.keySet()) {
            int quantity = holdings.get(symbol);
            double price = market.getStock(symbol).getPrice();
            totalValue += quantity * price;
        }
        return totalValue;
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Market market = new Market();
        Portfolio portfolio = new Portfolio(10000.00); // Initial cash

        while (true) {
            System.out.println("\nStock Trading Platform");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Portfolio Value");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nMarket Data:");
                    for (Stock stock : market.getStocks()) {
                        System.out.println(stock.getSymbol() + ": $" + stock.getPrice());
                    }
                    break;
                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.next();
                    System.out.print("Enter quantity to buy: ");
                    int buyQuantity = scanner.nextInt();
                    Stock buyStock = market.getStock(buySymbol);
                    if (buyStock != null) {
                        portfolio.buyStock(buyStock, buyQuantity);
                    } else {
                        System.out.println("Stock symbol not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.next();
                    System.out.print("Enter quantity to sell: ");
                    int sellQuantity = scanner.nextInt();
                    Stock sellStock = market.getStock(sellSymbol);
                    if (sellStock != null) {
                        portfolio.sellStock(sellStock, sellQuantity);
                    } else {
                        System.out.println("Stock symbol not found.");
                    }
                    break;
                case 4:
                    portfolio.viewPortfolio(market);
                    break;
                case 5:
                    System.out.println("Total Portfolio Value: $" + portfolio.getTotalValue(market));
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
