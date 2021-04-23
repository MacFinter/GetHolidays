package creator;

import connect.CreateConnectionDB;
import exceptions.HaveNoIndexException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import parser.Parsers;
import pojo.Holiday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class AddData {

    CreateConnectionDB connection = new CreateConnectionDB("config/hibernate.cfg.xml");
    Parsers parsers = new Parsers();

    public void run() {
        addData();
    }

    private List<Holiday> getListHoliday() {
        Calendar currentServerTime = new GregorianCalendar();
        currentServerTime.add(Calendar.HOUR, -3);

        List<Holiday> holidays = new ArrayList<>();

        for (Calendar calendar : parsers.getHolidays()) {
            Holiday holiday = new Holiday();

            holiday.setEndAt(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            holiday.setStartAt(calendar.getTime());
            holiday.setEventTypeId(4);
            holiday.setAuthorId(59201);
            holiday.setCreatedAt(currentServerTime.getTime());
            holiday.setUpdatedAt(currentServerTime.getTime());
            holidays.add(holiday);
        }

        return holidays;
    }

    private int checkIndex() {
        String sqlCreateIndex = "select max(" +
                "case when indexname = 'holidays_on_end_at_start_at' then 1 else 0 end) as result " +
                "from pg_indexes\n" +
                "where tablename = 'holidays'";

        try (Session session = connection.getSessionFactory().openSession()) {
            session.createSQLQuery(sqlCreateIndex).getSingleResult();
            int result = Integer.parseInt(session.createSQLQuery(sqlCreateIndex).getSingleResult().toString());
            return result;
        }
    }

    private void addData() {

        List<Holiday> holidays = getListHoliday();

        try (Session session = connection.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            if (checkIndex() == 1) {
                holidays.forEach(h -> {

                    String adding = ("INSERT INTO holidays (event_type_id,end_at,start_at,created_at,updated_at) VALUES(" +
                            h.getEventTypeId()
                            + ",'" + h.getEndAt()
                            + "','" + h.getStartAt()
                            + "','" + h.getCreatedAt()
                            + "','" + h.getUpdatedAt() + "') on conflict do nothing"
                    );
                    session.createSQLQuery(adding).executeUpdate();
                });
            } else {
                throw new HaveNoIndexException("Need create unique index holidays_on_end_at_start_at in table holiday");
            }
            transaction.commit();
        } catch (HaveNoIndexException e) {
            e.printStackTrace();
        }
    }
}
