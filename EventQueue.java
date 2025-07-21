// دی این کلاس با استفاده از کامپر تو میفهیمم کدوم باید زوتر اجراشه و سورت بشه
package carrace.core;

import java.util.PriorityQueue;

/**
 * صف اولویت‌دار رویدادها بر اساس زمان اجرایشان
 */
public class EventQueue {
    private final PriorityQueue<RaceEvent> queue = new PriorityQueue<>();

    /**
     * افزودن رویداد جدید به صف
     */
    //از offer() استفاده شده (به‌جای add())، که اگر صف به هر دلیلی جا نداشت (در محدودیت‌های خاص)، بدون exception عمل می‌کنه
    public void addEvent(RaceEvent event) {
        queue.offer(event);
    }

    /**
     * دریافت و حذف رویداد بعدی (کمترین eventTime)
     */
    //پل کوچیکترین عضو رو اد میکنه و حذف میکنه
    public RaceEvent getNextEvent() {
        return queue.poll();
    }

    /**
     * آیا صف خالی است؟
     */
    //استفاده شده در سیمیولیتور برای ادد
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

