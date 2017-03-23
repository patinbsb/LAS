/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package las;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 * @author Patrick Goodson
 */
public class ReportsManager
{
    /**
     * generates a list of books issued over time.
     * @return 
     */
    public void getReport(int mode) throws SQLException, IOException
    {
        // Get transactions
        ArrayList<Transaction> transactionList = DBConnector.getTransactionTable();
        HashMap<Integer, Integer> dateList = new HashMap<>();
        // Assign transactions to hashmap
        int key, count;
        for (Transaction transaction : transactionList)
        {
            Calendar tempDate = Calendar.getInstance();
            tempDate.setTimeInMillis(transaction.getDateTime().getTime());
            switch (mode)
            {
                case 0:
                    key = tempDate.get(Calendar.DAY_OF_YEAR);
                    count = dateList.containsKey(key) ? dateList.get(key) : 0;
                    dateList.put(key, count + 1);
                    break;
                case 1:
                    key = tempDate.get(Calendar.WEEK_OF_YEAR);
                    count = dateList.containsKey(key) ? dateList.get(key) : 0;
                    dateList.put(key, count + 1);
                    break;
                case 2:
                    key = tempDate.get(Calendar.MONTH);
                    count = dateList.containsKey(key) ? dateList.get(key) : 0;
                    dateList.put(key, count + 1);
                    break;
            }
        }
        Calendar tempDate = Calendar.getInstance();
        int max = -1;
        switch(mode)
        {
            case 0:
                max = tempDate.getActualMaximum(Calendar.DAY_OF_YEAR);
                break;
            case 1:
                max = tempDate.getActualMaximum(Calendar.WEEK_OF_YEAR);
                break;
            case 2:
                max = tempDate.getActualMaximum(Calendar.MONTH);
                break;
        }
        generatecsv(dateList, max, mode);
    }

    private void generatecsv(HashMap<Integer, Integer> dateList, int max, int mode) throws IOException
    {
        CSVWriter writer = new CSVWriter(new FileWriter(("src/reports/" + Long.toString(System.currentTimeMillis())) + ".csv"), '\r', CSVWriter.NO_QUOTE_CHARACTER);
        String[] entries = new String[max];
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i<max; i++)
        {
            dateList.putIfAbsent(i,0);
            switch (mode)
            {
                case 0:
                    calendar.set(Calendar.DAY_OF_YEAR, i);
                    break;
                case 1:
                    calendar.set(Calendar.WEEK_OF_YEAR, i);
                    break;
                case 2:
                    calendar.set(Calendar.MONTH, i);
                    break;
            }
            entries[i] = sdf.format(calendar.getTime()) + ", " + Integer.toString(dateList.get(i));
        }
        writer.writeNext(entries);
        writer.close();
    }
}
