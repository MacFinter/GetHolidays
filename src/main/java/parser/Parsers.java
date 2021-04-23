package parser;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Parsers {

    private String link = "http://xmlcalendar.ru/";
    private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    private static final String MONTHS = "months";
    private static final String MONTH = "month";
    private static final String DAYS = "days";
    private static final String HREF = "href";

    public List<Calendar> getHolidays() {

        List<Calendar> resultDays = new ArrayList<>();
        List<String> links = getStartedLinks(link);
        JSONParser parser = new JSONParser();

        try {
            Document doc = Jsoup.connect(links.get(0)).ignoreContentType(true).get();
            JSONObject jsonObject = (JSONObject) parser.parse(doc.body().text());

            JSONArray objectArrays = (JSONArray) jsonObject.get(MONTHS);

            for (Object objMonth : objectArrays) {
                int getMonthNum = Integer.parseInt(((JSONObject) objMonth).get(MONTH).toString());

                String[] days = ((JSONObject) objMonth).get(DAYS).toString()
                        .replace("*", "").split(",");

                for (int i = 0; i < getMonthNum; i++) {

                    for (String day : days) {
                        Calendar calendar = new GregorianCalendar();
                        calendar.set(currentYear, getMonthNum - 1, Integer.parseInt(day), 20, 59, 59);
                        resultDays.add(calendar);
                    }
                }
            }
            return resultDays;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return resultDays;
    }

    private List<String> getStartedLinks(String html) {

        String regexp = ".+" + currentYear + ".+[.json]$";
        List<String> list = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(html).get();
            Elements elements = doc.select("ul li a[href]");

            for (Element element : elements) {

                if (element.attributes().get(HREF).matches(regexp)) {
                    list.add(html + element.attributes().get(HREF));
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
