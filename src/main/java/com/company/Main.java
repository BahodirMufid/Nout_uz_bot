package com.company;

import com.company.container.ComponentContainer;
import com.company.database.Database;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

            Noutuz noutuz = new Noutuz();
            ComponentContainer.MY_TELEGRAM_BOT = noutuz;

            telegramBotsApi.registerBot(noutuz);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
