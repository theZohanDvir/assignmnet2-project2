package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;

public class CheckAvailability  implements Event<Integer> {
    private String bookName;
    int credit;
    public CheckAvailability(String bookName, int credit)
    {
        this.bookName = bookName;
        this.credit = credit;
    }


    public String getBookName()
    {
        return bookName;
    }
    public int getCredit()
    {
        return credit;
    }
}
