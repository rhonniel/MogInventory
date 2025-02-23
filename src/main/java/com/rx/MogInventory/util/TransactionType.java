package com.rx.MogInventory.util;

import org.springframework.context.annotation.EnableMBeanExport;

public enum TransactionType {
    IN,OUT;
   public static  boolean isValid( String type){
         try {
             valueOf(type);
             return true;
         } catch (IllegalArgumentException e) {
             return false;
         }

    }
}
