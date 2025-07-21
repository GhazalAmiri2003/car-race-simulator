package carrace.core;


public abstract class RaceEvent implements Comparable<RaceEvent> {
    protected final double eventTime;


    public RaceEvent(double eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * زمان اجرا (بر حسب ثانیه)
     */
    public double getEventTime() {
        return eventTime;
    }

    /**
     * منطق اجرای رویداد در شبیه‌ساز
     */
    public abstract void execute(RaceSimulator simulator);



//    To define a natural ordering for RaceEvent objects based on eventTime.
//    This allows the PriorityQueue<RaceEvent> to always pick
//    and execute the earliest event in the simulation timeline.



    @Override               //برای ترتیب زمنیه و به پول کمک میکنه بفهمه نوبت کیه
    public int compareTo(RaceEvent other) {
        return Double.compare(this.eventTime, other.eventTime);
    }
}
//با کمک مثبت و منفی