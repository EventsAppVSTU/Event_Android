package com.example.eventsapp;

public class DateWork {

    public String getDate (String startDate, String endDate){
        String dateResult = "";
        String [] sDate = startDate.split(" ");
        String [] eDate = endDate.split(" ");
        String [] DMYs = sDate[0].split("-");
        String [] DMYe = eDate[0].split("-");

        String sMonth = "";
        String eMonth = "";

        switch (DMYs[1]){
            case ("00"):
                sMonth = "не опознанная дата";
                break;
            case ("01"):
                sMonth = "января";
                break;
            case ("02"):
                sMonth = "февраля";
                break;
            case ("03"):
                sMonth = "марта";
                break;
            case ("04"):
                sMonth = "апреля";
                break;
            case ("05"):
                sMonth = "мая";
                break;
            case ("06"):
                sMonth = "июня";
                break;
            case ("07"):
                sMonth = "июля";
                break;
            case ("08"):
                sMonth = "августа";
                break;
            case ("09"):
                sMonth = "сентября";
                break;
            case ("10"):
                sMonth = "октября";
                break;
            case ("11"):
                sMonth = "ноября";
                break;
            case ("12"):
                sMonth = "декабря";
                break;
        }

        switch (DMYe[1]){
            case ("00"):
                eMonth = "не опознанная дата";
                break;
            case ("01"):
                eMonth = "января";
                break;
            case ("02"):
                eMonth = "февраля";
                break;
            case ("03"):
                eMonth = "марта";
                break;
            case ("04"):
                eMonth = "апреля";
                break;
            case ("05"):
                eMonth = "мая";
                break;
            case ("06"):
                eMonth = "июня";
                break;
            case ("07"):
                eMonth = "июля";
                break;
            case ("08"):
                eMonth = "августа";
                break;
            case ("09"):
                eMonth = "сентября";
                break;
            case ("10"):
                eMonth = "октября";
                break;
            case ("11"):
                eMonth = "ноября";
                break;
            case ("12"):
                eMonth = "декабря";
                break;
        }

        if(sMonth.equals(eMonth)){
            dateResult += DMYs[2] + " - " + DMYe[2] + " " + sMonth;
        }
        else {
            dateResult += DMYs[2] + " " + sMonth + " - " + DMYe[2] + " " + eMonth;
        }
        return dateResult;
    }

    public String getPerfDate (String datePerf){
        String dateResult = "";
        String[] sDate = datePerf.split(" ");
        String[] DMYs = sDate[0].split("-");

        String sMonth = "";

        switch (DMYs[1]) {
            case ("00"):
                sMonth = "не опознанная дата";
                break;
            case ("01"):
                sMonth = "января";
                break;
            case ("02"):
                sMonth = "февраля";
                break;
            case ("03"):
                sMonth = "марта";
                break;
            case ("04"):
                sMonth = "апреля";
                break;
            case ("05"):
                sMonth = "мая";
                break;
            case ("06"):
                sMonth = "июня";
                break;
            case ("07"):
                sMonth = "июля";
                break;
            case ("08"):
                sMonth = "августа";
                break;
            case ("09"):
                sMonth = "сентября";
                break;
            case ("10"):
                sMonth = "октября";
                break;
            case ("11"):
                sMonth = "ноября";
                break;
            case ("12"):
                sMonth = "декабря";
                break;
        }

        dateResult = DMYs[2] + " " + sMonth;
        return dateResult;

    }

}
