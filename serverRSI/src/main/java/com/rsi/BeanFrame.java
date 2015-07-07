package com.rsi;

import java.util.Arrays;
import java.util.Date;

/**
 *
 * Created by juanrafael.perez on 07/07/2015.
 */
public class BeanFrame {

    // Type
    private String typeFrame;
    // Panel serial number
    private String panelSerialNumber;
    // Customer account
    private String customerAccount;
    // Date
    private Date dateFrame;
    // Specials fields
    private String[] specialsFields;

    /**
     * Constructor with all paramas
     * @param typeFrame
     * @param panelSerialNumber
     * @param customerAccount
     * @param dateFrame
     * @param specialsFields
     */
    public BeanFrame(String typeFrame, String panelSerialNumber, String customerAccount, Date dateFrame, String[] specialsFields) {
        this.typeFrame = typeFrame;
        this.panelSerialNumber = panelSerialNumber;
        this.customerAccount = customerAccount;
        this.dateFrame = dateFrame;
        this.specialsFields = specialsFields;
    }

    public String getTypeFrame() {
        return typeFrame;
    }

    public String getPanelSerialNumber() {
        return panelSerialNumber;
    }

    public String getCustomerAccount() {
        return customerAccount;
    }

    public Date getDateFrame() {
        return dateFrame;
    }

    public String[] getSpecialsFields() {
        return specialsFields;
    }

    @Override
    public String toString() {
        return "BeanFrame{" +
                "typeFrame='" + typeFrame + '\'' +
                ", panelSerialNumber='" + panelSerialNumber + '\'' +
                ", customerAccount='" + customerAccount + '\'' +
                ", dateFrame=" + dateFrame +
                ", specialsFields=" + Arrays.toString(specialsFields) +
                '}';
    }
}
