package org.example;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.example.service.TelegramBotUpdateHandler;
import org.example.utils.PropertiesUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final String token = PropertiesUtil.get("BOT_TOKEN");
    private static final ExecutorService executorService = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final ThreadLocal<TelegramBotUpdateHandler> threadLocalUpdateHandler = ThreadLocal
            .withInitial(TelegramBotUpdateHandler::new);

    public static void main(String[] args) {
        TelegramBot telegramBot = new TelegramBot(token);
        telegramBot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                CompletableFuture.runAsync(() -> threadLocalUpdateHandler.get().handle(update),
                        executorService);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, Throwable::printStackTrace);
    }
}