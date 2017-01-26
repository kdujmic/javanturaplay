package services;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kreso on 26.1.2017..
 */
public class ReturnTimeFromJava {

    static public Date justReturnTime(){
        return Calendar.getInstance().getTime();
    }
}
