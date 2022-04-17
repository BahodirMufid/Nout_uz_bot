package com.company.util;


import com.company.database.Database;
import com.company.model.Category;
import com.company.model.Product;
import com.company.service.CategoryService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InlineKeyboardUtil {
    public static InlineKeyboardMarkup productAdminMenu() {
        InlineKeyboardButton addButton = getButton("➕Add product", "add_product");
        InlineKeyboardButton updateButton = getButton("⏫Update product", "update_product");
        InlineKeyboardButton deleteButton = getButton("✖Delete product", "delete_product");
        InlineKeyboardButton listButton = getButton("📜Show product list", "show_product_list");

        List<InlineKeyboardButton> row1 = getRow(addButton, updateButton,listButton);
        List<InlineKeyboardButton> row2 = getRow(deleteButton);


        List<List<InlineKeyboardButton>> rowList = getRowList(row1, row2);
        return new InlineKeyboardMarkup(rowList);
    }


    public static InlineKeyboardMarkup NetAddressMenu() {
        InlineKeyboardButton instagram = getButton("📤 Instgram", "instagram_");
        InlineKeyboardButton telegram = getButton("📤 Telegram", "telegram_");
        InlineKeyboardButton facebook = getButton("📤 Facebook", "facebook");
        InlineKeyboardButton webAddres = getButton("🌐 WebAddress", "web_");
        InlineKeyboardButton back = getButton("◀ BACK", "back");

        instagram.setUrl("https://www.instagram.com/noutuz");
        telegram.setUrl("https://t.me/nout_uz");
        facebook.setUrl("https://www.facebook.com/www.nout.uz");
        webAddres.setUrl("https://nout.uz/");


        List<InlineKeyboardButton> row1 = getRow(instagram,telegram,facebook);
        List<InlineKeyboardButton> row2 = getRow(webAddres);
         List<InlineKeyboardButton> row3 = getRow(back);

        List<List<InlineKeyboardButton>> rowList = getRowList(row1,row2 ,row3);
        return new InlineKeyboardMarkup(rowList);
    }


    public static InlineKeyboardMarkup Menu() {
        InlineKeyboardButton menuButton = getButton("📠PRODUCT MENU", "product_menu");
        InlineKeyboardButton magazineButton = getButton("🏪MAGAZINE", "magazine");
        InlineKeyboardButton contactButton = getButton("📞OUR CONTACTS", "our_contacts");
        InlineKeyboardButton helpButton = getButton("💬HELP", "help");

        magazineButton.setUrl("https://www.google.com/maps/place/Nout.uz+-+%D0%9C%D0%B0%D0%B3%D0%B0%D0%B7%D0%B8%D0%BD+%D0%9D%D0%BE%D1%83%D1%82%D0%B1%D1%83%D0%BA%D0%BE%D0%B2+N1/@41.3386395,69.2723408,15z/data=!4m5!3m4!1s0x0:0x7e8c159c995773ea!8m2!3d41.3386395!4d69.2723408");
        helpButton.setUrl("https://t.me/nout_uz");

        List<InlineKeyboardButton> row1 = getRow(menuButton);
        List<InlineKeyboardButton> row2 = getRow(magazineButton , contactButton);
        List<InlineKeyboardButton> row3 = getRow(helpButton);

        List<List<InlineKeyboardButton>> rowList = getRowList(row1, row2,row3);

        return new InlineKeyboardMarkup(rowList);
    }

//    public static InlineKeyboardMarkup ProductMenu() {
//        InlineKeyboardButton noutbookBrands = getButton("💻 Noutbook brands", "noutbook_brands");
//        InlineKeyboardButton computersButton = getButton("🖥 Computers", "computers");
//        InlineKeyboardButton accesoriesButton = getButton("🎧 Accesories", "accesories");
//        InlineKeyboardButton back = getButton("◀ BACK", "back");
//
//        List<InlineKeyboardButton> row1 = getRow(noutbookBrands,computersButton);
//        List<InlineKeyboardButton> row2 = getRow(accesoriesButton);
//        List<InlineKeyboardButton> row3 = getRow(back);
//
//
//        List<List<InlineKeyboardButton>> rowList = getRowList(row1, row2,row3);
//
//        return new InlineKeyboardMarkup(rowList);
//    }
    public static InlineKeyboardMarkup Brands() {
        InlineKeyboardButton asus = getButton("💻 ASUS", "asus");
        InlineKeyboardButton aser = getButton("💻 ASER", "aser");
        InlineKeyboardButton lenovo = getButton("💻 LENOVO", "lenovo");
        InlineKeyboardButton msi = getButton("💻 MSI", "msi");
        InlineKeyboardButton dell = getButton("💻 DELL", "dell");
        InlineKeyboardButton hp = getButton("💻 HP", "hp");
        InlineKeyboardButton microsoftOfice = getButton("💻 MICROSOFT OFICE", "microsoft_ofice");
        InlineKeyboardButton razer = getButton("💻 RAZER", "razer");
        InlineKeyboardButton lg = getButton("💻 LG", "lg");
        InlineKeyboardButton samsung = getButton("💻 SAMSUNG", "samsung");
        InlineKeyboardButton back = getButton("◀ BACK", "back");


        List<InlineKeyboardButton> row1 = getRow(aser,asus);
        List<InlineKeyboardButton> row2 = getRow(lenovo,msi);
        List<InlineKeyboardButton> row3 = getRow(dell,hp);
        List<InlineKeyboardButton> row4 = getRow(lg,razer);
        List<InlineKeyboardButton> row5 = getRow(samsung,microsoftOfice);
        List<InlineKeyboardButton> row6 = getRow(back);



        List<List<InlineKeyboardButton>> rowList = getRowList(row1, row2, row3,row4,row5,row6);

        return new InlineKeyboardMarkup(rowList);
    }

    public static InlineKeyboardMarkup productMenu(List<Product> productList) {

        List<List<InlineKeyboardButton>> rowList = new LinkedList<>();

        for (Product product : productList) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(product.getName());
            button.setCallbackData("product/"+product.getId());
            List<InlineKeyboardButton> row = getRow(button);

            rowList.add(row);
        }

        return new InlineKeyboardMarkup(rowList);
    }


    private static InlineKeyboardButton getButton(String demo, String data) {
        InlineKeyboardButton button = new InlineKeyboardButton(demo);
        button.setCallbackData(data);
        return button;
    }

    private static List<InlineKeyboardButton> getRow(InlineKeyboardButton... buttons) {
        return Arrays.asList(buttons);
    }

    private static List<List<InlineKeyboardButton>> getRowList(List<InlineKeyboardButton>... rows) {
        return Arrays.asList(rows);
    }



    public static InlineKeyboardMarkup categoryInlineMarkup() {

        CategoryService.loadCategoryList();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (Category category : Database.categoryList) {
            List<InlineKeyboardButton> buttonList = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton(category.getName());
            button.setCallbackData("add_product_category_id/" + category.getId());
            buttonList.add(button);
            rowList.add(buttonList);
        }
        return new InlineKeyboardMarkup(rowList);
    }

    public static InlineKeyboardMarkup confirmAddProductMarkup() {

        InlineKeyboardButton commit = getButton("Ha", "add_product_commit");
        InlineKeyboardButton cancel = getButton("Yo'q", "add_product_cancel");

        return new InlineKeyboardMarkup(getRowList(getRow(commit, cancel)));
    }


}
