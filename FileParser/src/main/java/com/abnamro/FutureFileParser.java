package com.abnamro;

import com.abnamro.entity.Client;
import com.abnamro.entity.Product;
import com.abnamro.entity.Transaction;
import com.abnamro.validation.CommonValidator;
import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FutureFileParser {

    private final static Logger LOGGER =  LogManager.getLogger(FutureFileParser.class.getName());

    public void process(String filename){
        //parseFile
        Map<Transaction,Long> transactionMap = parseFile(filename);

        //genereate report
        if(!transactionMap.isEmpty()){
            generateCSVFile(transactionMap);
        }
    }

    /**
     * parese whole file and store info to a Map
     * @param fileName
     * @return
     */
    public Map<Transaction,Long> parseFile(String fileName){
        LOGGER.info("Start parse File "+fileName);
        BufferedReader reader;
        Map<Transaction,Long> transactionMap = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            //Set line number or log purpose
            int lineNo = 1;
            while (line != null) {
                Transaction transaction = parseSingleLine(line,lineNo);
                if(transaction!=null){
                    if(transactionMap.containsKey(transaction)){
                        long totalAmount = transactionMap.get(transaction)+transaction.getQtyLong()-transaction.getQtyShort();
                        transactionMap.put(transaction,totalAmount);
                    }else {
                        transactionMap.put(transaction,transaction.getQtyLong()-transaction.getQtyShort());
                    }
                }

                // read next line
                line = reader.readLine();
                lineNo++;
            }
            reader.close();
        } catch (IOException e) {
            LOGGER.error("ERROR: parse File "+fileName);
            e.printStackTrace();
        }
        return transactionMap;
    }

    /**
     * parse one line each time and store info to Transaction object
     * @param record
     * @param lineNo
     * @return
     */
    public Transaction parseSingleLine(String record, int lineNo){
        LOGGER.info("Start parse line: "+lineNo);
        //validation
        if(!CommonValidator.commonValidate(record, lineNo)){
            return null;
        }

        //Parser to client
        String clientType =record.substring(3,7);
        int clientNumber=0;
        int accountNumber=0;
        int subAccountNumber=0;

        try {
            clientNumber=Integer.parseInt(record.substring(7,11));
            accountNumber=Integer.parseInt(record.substring(11,15));
            subAccountNumber=Integer.parseInt(record.substring(15,19));
        } catch (NumberFormatException e) {
            LOGGER.error("Error - create client info");
            e.printStackTrace();
        }

        LOGGER.info("clientType is: "+clientType+" clientNumber is: "+clientNumber+" accountNumber is:"+accountNumber
            +" subAccountNumber is: "+subAccountNumber);

        Client client = new Client(clientType,clientNumber,accountNumber,subAccountNumber);

        //Parse Product
        String exchangeCode=record.substring(27,31);
        String productGroup=record.substring(25,27);
        String symbol=record.substring(31,37);
        Date expirationDate=null;

        try {
            String dateString = record.substring(37,45);
            expirationDate =new SimpleDateFormat("yyyyMMdd").parse(dateString);
        } catch (ParseException e) {
            LOGGER.error("Error - expiration Date is invalidate");
            e.printStackTrace();
        }

        Product product = new Product(exchangeCode,productGroup,symbol,expirationDate);
        LOGGER.debug("exchangeCode is: "+exchangeCode+" productGroup is: "+productGroup+" symbol is:"+symbol
                +" expirationDate is: "+expirationDate);

        //Parse Long & Short
        long qtyLong = Long.parseLong(record.substring(53,62));
        long qtyShort = Long.parseLong(record.substring(63,73));
        LOGGER.debug("qtyLong is: "+qtyLong+" qtyShort is: "+qtyShort);

        Transaction transaction = new Transaction();
        transaction.setClient(client);
        transaction.setProduct(product);
        transaction.setQtyLong(qtyLong);
        transaction.setQtyShort(qtyShort);

        LOGGER.info("Finish parse line "+lineNo);
        return transaction;
    }

    /**
     * Generate CSV file
     * @param transactionMap
     */
    public void generateCSVFile(Map<Transaction,Long> transactionMap){
        LOGGER.info("Start generate CSVFile");
        File file = new File("Output.csv");
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "CLIENT TYPE", "CLIENT NUMBER", "ACCOUNT NUMBER","SUBACCOUNT NUMBER",
                    "PRODUCT GROUP CODE","EXCHANGE CODE","SYMBOL","EXPIRATION DATE","TOTAL TRANSACTION AMOUNT"};

            List<String[]> data = new ArrayList<>();
            data.add(header);

            String pattern = "yyyyMMdd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            for (Map.Entry<Transaction, Long> entry : transactionMap.entrySet()) {
                Client client = entry.getKey().getClient();
                Product product = entry.getKey().getProduct();
                data.add(new String[]{client.getClientType(),String.valueOf(client.getClientNumber()),String.valueOf(client.getAccountNumber()),
                    String.valueOf(client.getSubAccountNumber()),product.getProductGroup(),product.getExchangeCode(),product.getSymbol(),
                    simpleDateFormat.format(product.getExpirationDate()),String.valueOf(entry.getValue())});
            }

            writer.writeAll(data);
            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            LOGGER.error("Error-- generate CSVFile");
            e.printStackTrace();
        }
        LOGGER.info("Finish generate CSVFile");
    }

    public static void main(String[] args)
    {
        String filename = "Input.txt";
        if (0 < args.length) {
             filename = args[0];
        }
        FutureFileParser armoFileParser = new FutureFileParser();
        armoFileParser.process(filename);
    }
}
