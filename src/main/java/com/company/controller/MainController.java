package com.company.controller;

import com.company.Main;
import com.company.container.ComponentContainer;
import com.company.database.Database;
import com.company.enums.CustomerStatus;
import com.company.enums.Data;
import com.company.model.Customer;
import com.company.model.Product;
import com.company.service.CategoryService;
import com.company.service.CustomerService;
import com.company.service.ProductService;
import com.company.util.InlineKeyboardUtil;
import com.company.util.KeyboardUtil;
import com.company.util.PageTransition;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.List;

public class MainController {
    public AdminController adminController = new AdminController();

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

    }

    private void handleContact(User user, Message message) {
        Contact contact = message.getContact();
        String customerId = String.valueOf(contact.getUserId());

        Customer customer = CustomerService.getCustomerById(customerId);
        if (customer == null) {
//            System.out.println("ssssss");
            customer = new Customer(customerId, contact.getFirstName(),
                    contact.getLastName(), contact.getPhoneNumber(), CustomerStatus.SHARE_CONTACT);

            CustomerService.addCustomer(customer);

            SendMessage sendMessage = new SendMessage();
         //  sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
            sendMessage.setChatId(customerId);
            sendMessage.setText("<b>💻Assalomu Aleykum nout.uz Botiga Hush Kelibsiz ! \n\n Menudan Birortasini Tanlang</b>\n");
            sendMessage.setParseMode(ParseMode.HTML);
            InlineKeyboardMarkup menu = InlineKeyboardUtil.Menu();
            sendMessage.setReplyMarkup(menu);

            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        }
    }

    private void handleText(User user, Message message) {
        String text = message.getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        Customer customer = CustomerService.getCustomerById(String.valueOf(message.getChatId()));

        if (text.equals("/start")) {

            if (customer == null) {
                String messageText = String.format("ID: %s\nFirst name:%s\n" +
                                "Last name: %s\nUsername: %s\n\nclicked START.",
                        user.getId(), user.getFirstName(), user.getLastName(), user.getUserName());
                adminController.notificationToAdmin(messageText);
                sendMessage.setText("<b>Assalomu Aleykum!</b>\n" +
                        "Raqamingizmi jo'nating.");
                sendMessage.setParseMode(ParseMode.HTML);
                sendMessage.setReplyMarkup(KeyboardUtil.contactMarkup());
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);

            } else{
                sendMessage.setText("<b>💻Assalomu Aleykum nout.uz Botiga Hush Kelibsiz ! \n\n Menudan Birortasini Tanlang</b>\n");
                sendMessage.setParseMode(ParseMode.HTML);

                sendMessage.setReplyMarkup(InlineKeyboardUtil.Menu());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }




        }
//        else if(text.equals(InlineKeyboardUtil.Menu())){
//            sendMessage.setText("Menu ga hush kelibsiz !!!\n\n" +
//                    "Pastdagilardan birortasini tanlang : "
//            );
//            sendMessage.setReplyMarkup(InlineKeyboardUtil.Menu());
//        }

    }


    public void handleCallBack(User user, Message message, String data) {
        String chatId = String.valueOf(message.getChatId());

        if (data.equals(String.valueOf(Data.OUR_CONTACTS))) {

            DeleteMessage deleteMessage = new DeleteMessage(
                    chatId, message.getMessageId()
            );
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            SendMessage sendMessage = new SendMessage(
                    chatId, "BIZNING IJTIMOIY SAHIFA : ");
            sendMessage.setReplyMarkup(InlineKeyboardUtil.NetAddressMenu());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        } else if (data.equals(String.valueOf(Data.PRODUCT_MENU))) {
            DeleteMessage deleteMessage = new DeleteMessage(
                    chatId, message.getMessageId()
            );
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            SendMessage sendMessage = new SendMessage(
                    chatId, "Brand lardan Birini tanlang :\n\n Pastagilardan birortasini tanlang 🔽 ");
            sendMessage.setReplyMarkup(InlineKeyboardUtil.Brands());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        } else if (data.equals(String.valueOf(Data.BACK))) {
            DeleteMessage deleteMessage = new DeleteMessage(
                    chatId, message.getMessageId()
            );
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            SendMessage sendMessage = new SendMessage(
                    chatId, "Assosiy Menu  :\n\n Pastagilardan birortasini tanlang 🔽");
            sendMessage.setReplyMarkup(InlineKeyboardUtil.Menu());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        }
     else if (data.equals(String.valueOf(Data.ASUS))){
            DeleteMessage deleteMessage = new DeleteMessage(
                    chatId, message.getMessageId()
            );
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);
            ProductService.loadProductList();
            List<Product> products = Database.productList.stream().filter(product -> product.getCategoryId().equals(product.getId())).toList();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(user.getId()));
            //sendMessage.setText(products.get(1).getName() + products.get(2).);
            sendMessage.setReplyMarkup(InlineKeyboardUtil.productMenu(products));
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);

//            List<Product> products = Database.productList.stream().filter(product -> product.getCategoryId().equals(product.getCategoryId())).toList();
//            sendMessage.setChatId(String.valueOf(user.getId()));
//                for (Product product : Database.productList) {
//                SendPhoto sendPhoto = new SendPhoto(chatId, new InputFile(product.getImage()));
//                sendPhoto.setCaption(String.format("Kategoriya: %s\n" +
//                                "Mahsulot: %s \n Narxi: %s\n",
//                        CategoryService.getCategoryById(product.getCategoryId()).getName(),
//                        product.getName(), product.getPrice()));
//                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);
//            }
        }
    }
}
