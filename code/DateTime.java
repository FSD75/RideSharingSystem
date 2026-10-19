public class DateTime implements IDateTime {
private final int year,month,day,hour,minute;

//  Validate and initializes data and time.
public DateTime(int year,int month,int day,int hour,int minute) {
	if(year >0 && month <=12 && month >0 && day >0 && hour <= 23 && minute <=59 && day <32 && minute >=0 && hour >=0 ) {

			this.year=year;
	this.month=month;
	this.day=day;
	this.hour=hour;
	this.minute=minute;
	}
	else
		throw new IllegalArgumentException("Invalid dates or times");
	
	
}



@Override
//Compares two date or time values chronologically.
public int compareTo(IDateTime other) {
	if(getYear() != other.getYear())
		return	Integer.compare(getYear(), other.getYear());
	
	if(getMonth() != other.getMonth())
		return	Integer.compare(getMonth(), other.getMonth());
	
	if(getDay() != other.getDay())
		return	Integer.compare(getDay(), other.getDay());

	if(getHour() != other.getHour())
		return	Integer.compare(getHour(), other.getHour());

	
				return	Integer.compare(getMinute(), other.getMinute());

}






//Returns the date and time in MM/DD/YYYY HH:MM format
public String format() {
	String month=""+getMonth(); String day=""+getDay(); 	String hour=""+getHour(); 	String minute=""+getMinute();



	
	if(getMonth()<10)
		month="0"+month;
	if(getDay()<10)
		day="0"+day;
	if(getHour()<10)
		hour="0"+hour;
	if(getMinute()<10)
		minute="0"+minute;
	
	
	
	return month + "/" + day + "/" + year + " "+ hour + ":" + minute;
}



//Return the year
public int getYear() {
	return year;
}

//Return the month  
public int getMonth() {
	return month;
}

//Return the day
public int getDay() {
	return day;
}

//Return the hour 
public int getHour() {
	return hour;
}
//Return the minute
public int getMinute() {
	return minute;
}

	
	
}
