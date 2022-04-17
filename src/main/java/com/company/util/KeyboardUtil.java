package com.company.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;

public class KeyboardUtil {
    public static ReplyKeyboardMarkup contactMarkup(){
        KeyboardButton contactButton = new KeyboardButton("Raqamni jo'natish");
        contactButton.setRequestContact(true);
        KeyboardRow row = getRow(contactButton);

        List<KeyboardRow> rowList = getRowList(row);
        return getMarkup(rowList);
    }

//    public static ReplyKeyboardMarkup getMenuKeyboard() {
//
//        KeyboardButton menu = getButton();
//        KeyboardRow row1 = getRow(menu);
//
//        KeyboardButton cart = getButton();
//        KeyboardButton settings = getButton();
//        KeyboardRow row2 = getRow(cart, settings);
//
//        List<KeyboardRow> rowList = getRowList(row1, row2);
//
//        return getMarkup(rowList);
//    }

    private static KeyboardButton getButton(String demo){
        return new KeyboardButton(demo);
    }

    private static KeyboardRow getRow(KeyboardButton ... buttons){
        return new KeyboardRow(Arrays.asList(buttons));
    }

    private static List<KeyboardRow> getRowList(KeyboardRow ... rows){
        return Arrays.asList(rows);
    }

    private static ReplyKeyboardMarkup getMarkup(List<KeyboardRow> keyboard){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }
}
