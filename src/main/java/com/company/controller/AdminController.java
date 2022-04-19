package com.company.controller;

import com.company.container.ComponentContainer;
import com.company.database.Database;
import com.company.enums.AdminStatus;
import com.company.enums.CustomerStatus;
import com.company.enums.Data;
import com.company.model.Customer;
import com.company.model.Product;
import com.company.service.CategoryService;
import com.company.service.CustomerService;
import com.company.service.ProductService;
import com.company.util.InlineKeyboardUtil;
import com.company.util.KeyboardUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;

import java.io.File;
import java.util.List;

public class AdminController {
    public void handleMessage(User user, Message message) {
        if (message.hasText()) {
            handleText(user, message);
        } else if (message.hasContact()) {
            handleContact(user, message);
        } else if (message.hasPhoto()) {
            handlePhoto(user, message);
        }
    }

    private void handlePhoto(User user, Message message) {
        List<PhotoSize> photoSizeList = message.getPhoto();

        String chatId = String.valueOf(message.getChatId());

        if(ComponentContainer.productStepMap.containsKey(chatId)){
            Product product = ComponentContainer.productMap.get(chatId);

            if(ComponentContainer.productStepMap.get(chatId).equals(AdminStatus.ENTERED_PRODUCT_PRICE)){
                product.setImage(photoSizeList.get(photoSizeList.size()-1).getFileId());

                SendPhoto sendPhoto = new SendPhoto(chatId, new InputFile(product.getImage()));
                sendPhoto.setCaption(String.format("Kategoriya: %s\n" +
                        "Mahsulot: %s \n Narxi: %s\n\n Quyidagi mahsulot bazaga qo'shilsinmi?",
                        CategoryService.getCategoryById(product.getCategoryId()).getName(),
                        product.getName(), product.getPrice()));
                sendPhoto.setReplyMarkup(InlineKeyboardUtil.confirmAddProductMarkup());

                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);
            }
        }

    }

    private void handleContact(User user, Message message) {

    }

    private void handleText(User user, Message message) {
        String text = message.getText();
        String chatId = String.valueOf(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));






        if (text.equals("/start")) {
            sendMessage.setText("Amalni tanlang:");
            sendMessage.setReplyMarkup(InlineKeyboardUtil.productAdminMenu());
        } else if (ComponentContainer.productStepMap.containsKey(chatId)) {

            Product product = ComponentContainer.productMap.get(chatId);

            if (ComponentContainer.productStepMap.get(chatId).equals(AdminStatus.SELECT_CATEGORY_FOR_ADD_PRODUCT)) {
                product.setName(text);
                ComponentContainer.productStepMap.put(chatId, AdminStatus.ENTERED_PRODUCT_NAME);

                sendMessage.setText("Mahsulot narxini kiriting(haqiqiy musbat son): ");

            } else if (ComponentContainer.productStepMap.get(chatId).equals(AdminStatus.ENTERED_PRODUCT_NAME)) {
                double price = 0;
                try {
                    price = Double.parseDouble(text.trim());
                } catch (NumberFormatException e) {
                }

                if (price <= 0) {
                    sendMessage.setText("Narx noto'g'ri kiritildi, Qaytadan narxni kiriting: ");
                }  else {
                    product.setPrice(price);
                    ComponentContainer.productStepMap.put(chatId, AdminStatus.ENTERED_PRODUCT_PRICE);

                    sendMessage.setText("Mahsulotning rasmini jo'nating: ");
                }
            }
        }

        ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
    }

    public void handleCallBack(User user, Message message, String data) {
        String chatId = String.valueOf(message.getChatId());

        if (data.equals("add_product")) {
            DeleteMessage deleteMessage = new DeleteMessage(
                    chatId, message.getMessageId()
            );
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            SendMessage sendMessage = new SendMessage(
                    chatId, "Kategoriyalardan birini tanlang:"
            );
            sendMessage.setReplyMarkup(InlineKeyboardUtil.categoryInlineMarkup());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);

            ComponentContainer.productMap.remove(chatId);
            ComponentContainer.productStepMap.remove(chatId);

            ComponentContainer.productStepMap.put(chatId, AdminStatus.CLICKED_ADD_PRODUCT);
            ComponentContainer.productMap.put(chatId,
                    new Product(null, null, null, null,null,null,false));

        } else if (data.startsWith("add_product_category_id")) {
            DeleteMessage deleteMessage = new DeleteMessage(
                    chatId, message.getMessageId()
            );
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            int categoryId = Integer.parseInt(data.split("/")[1]);

            SendMessage sendMessage = new SendMessage(
                    chatId, "Mahsulot nomini kiriting : "
            );
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);

            ComponentContainer.productStepMap.put(chatId, AdminStatus.SELECT_CATEGORY_FOR_ADD_PRODUCT);
            Product product = ComponentContainer.productMap.get(chatId);
            product.setCategoryId(categoryId);
        }
        else if(data.equals(String.valueOf(Data.ADD_PRODUCT_COMMIT))){
            DeleteMessage deleteMessage = new DeleteMessage(
                    chatId, message.getMessageId()
            );
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            Product product = ComponentContainer.productMap.get(chatId);

            ProductService.addProduct(product);

            ComponentContainer.productMap.remove(chatId);
            ComponentContainer.productStepMap.remove(chatId);

            SendMessage sendMessage = new SendMessage(
                    chatId, product.getName()+" saqlandi.\n\n"+"Amalni tanlang:"
            );
            sendMessage.setReplyMarkup(InlineKeyboardUtil.productAdminMenu());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);

        }else if(data.equals( String.valueOf(Data.ADD_PRODUCT_CANCEL))){
            DeleteMessage deleteMessage = new DeleteMessage(
                    chatId, message.getMessageId()
            );
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            ComponentContainer.productMap.remove(chatId);
            ComponentContainer.productStepMap.remove(chatId);

            SendMessage sendMessage = new SendMessage(
                    chatId, "Amalni tanlang:"
            );
            sendMessage.setReplyMarkup(InlineKeyboardUtil.productAdminMenu());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        }
        else if(data.equals("show_product_list")){
            DeleteMessage deleteMessage = new DeleteMessage(
                    chatId, message.getMessageId()
            );
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            ProductService.loadProductList();

            for (Product product : Database.productList) {
                SendPhoto sendPhoto = new SendPhoto(chatId, new InputFile(product.getImage()));
                sendPhoto.setCaption(String.format("Kategoriya: %s\n" +
                                "Mahsulot: %s \n Narxi: %s\n",
                        CategoryService.getCategoryById(product.getCategoryId()).getName(),
                        product.getName(), product.getPrice()));
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);
            }

            SendMessage sendMessage = new SendMessage(
                    chatId, "Amalni tanlang:"
            );
            sendMessage.setReplyMarkup(InlineKeyboardUtil.productAdminMenu());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        }else if (data.equals("delete_product")){
            DeleteMessage deleteMessage = new DeleteMessage(
                    chatId, message.getMessageId()
            );
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            ProductService.loadProductList();

            ComponentContainer.productMap.remove(chatId);
            ComponentContainer.productStepMap.remove(chatId);
//            for (Product product : Database.productList) {
//                SendPhoto sendPhoto = new SendPhoto(chatId, new InputFile(product.getImage()));
//                sendPhoto.setCaption(String.format("Kategoriya: %s\n" +
//                                "Mahsulot: %s \n Narxi: %s\n",
//                        CategoryService.getCategoryById(product.getCategoryId()).getName(),
//                        product.getName(), product.getPrice()));
//            ProductService.deleteProduct(product);
               // ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);


//            ComponentContainer.productMap.remove(chatId);
//            ComponentContainer.productStepMap.remove(chatId);

            SendMessage sendMessage = new SendMessage(
                    chatId, "Amalni tanlang:"
            );
            sendMessage.setReplyMarkup(InlineKeyboardUtil.productAdminMenu());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);

        }

    }




    public void notificationToAdmin(String message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(ComponentContainer.ADMIN_ID);
        sendMessage.setText(message);
        ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
    }
}
