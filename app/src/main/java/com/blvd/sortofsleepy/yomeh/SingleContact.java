package com.blvd.sortofsleepy.yomeh;

/**
 * Created by sortofsleepy on 8/1/14.
 */
public class SingleContact {
    String name,number;
    int contactId;

    SingleContact(){}
    void setContactName(String contactName){
        name = contactName;
    }

    void setContactNumber(String contactNumber){
        number = contactNumber;
    }

    void setPhoneContactID(int id){
        contactId = id;
    }
}
