package moneycalculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * @author angeles
 */
public class MoneyCalculator {
    
    private double amount;
    private double exchangeRate;
    private String currencyTo;
    private String currencyFrom;

    public static void main(String[] args) throws Exception {
        MoneyCalculator moneyCalculator = new MoneyCalculator();
        moneyCalculator.execute();
    }
   

    private void execute() throws Exception {
        input();
        process();
        output();
    }

    private void input() {
        System.out.println("Introduzca cantidad: ");
        Scanner scanner = new Scanner (System.in);
        amount = Double.parseDouble(scanner.next());
        
        System.out.println("Introduzca la divisa origen: ");
        currencyFrom = scanner.next();
        
        System.out.println("introduzca la divisa destino: ");
        currencyTo = scanner.next();
    }

    private void process() throws Exception {
        exchangeRate = getExchangeRate(currencyFrom,currencyTo);
    }

    private void output() {
        System.out.println(amount+ " "+currencyFrom+ "equivalen a "+amount * exchangeRate +" "+ currencyTo);
    }

    private double getExchangeRate(String from, String to) throws Exception{
        URL url = new URL ("http://api.fixer.io/latest?base="+from+ "&symbols="+to);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStreamReader input = new InputStreamReader (connection.getInputStream());
        try(BufferedReader reader = new BufferedReader (input)){
            String line = reader.readLine();
            line = line.substring(line.indexOf(to)+5,line.indexOf("}"));
            return Double.parseDouble(line);
        }
        
    }
    
}
