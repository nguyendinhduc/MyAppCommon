package com.ducnd.contacts;

import android.net.Uri;

/**
 * Created by ducnd on 24/07/2015.
 */
public class ItemContact {
    private String name, phone;
    private Uri uriIcon;
    public ItemContact ( String name, String phone,  Uri uriIcon) {
        this.name = name;
        this.phone = phone;
        this.uriIcon = uriIcon;
    }

    public ItemContact ( String name, String phone ) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Uri getUriIcon() {
        return uriIcon;
    }

    public void setUriIcon(Uri uriIcon) {
        this.uriIcon = uriIcon;
    }
}
