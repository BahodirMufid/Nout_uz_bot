package com.company.util;
// PROJECT NAME shop_bot
// TIME 16:35
// MONTH 04
// DAY 12

import com.company.model.Product;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class PageTransition {


    public static InlineKeyboardMarkup getShowMyProduct(String userId ,int productId, List<Product> products) {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        if (productId != 0) {

            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("⏪ Orqaga");
            button.setCallbackData("My/" + userId + "/" + (productId - 1));
            row.add(button);
        }

        String id = String.valueOf(products.get(productId).getId());
                //.get(productId).getId();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("\uD83D\uDDD1");
        button.setCallbackData("MyD/" + userId + "/" + id);
        row.add(button);

        if (products.size() - 1 > productId) {

            InlineKeyboardButton button1 = new InlineKeyboardButton();
            button1.setText("⏩");
            button1.setCallbackData("My/" + userId + "/" + (productId + 1));
            row.add(button1);
        }
        rowList.add(row);


        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("\uD83D\uDD1A Chiqish ");
        button2.setCallbackData("page_back");
        row2.add(button2);
        rowList.add(row2);

        markup.setKeyboard(rowList);
        return markup;

    }
}
