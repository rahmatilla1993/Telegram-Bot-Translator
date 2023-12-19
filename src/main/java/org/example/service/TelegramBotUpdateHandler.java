package org.example.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import org.example.types.TranslateType;
import org.example.utils.PropertiesUtil;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class TelegramBotUpdateHandler {

    private static final String token = PropertiesUtil.get("BOT_TOKEN");
    private final TelegramBot telegramBot = new TelegramBot(token);
    private final ApiService apiService = new ApiService();
    private static final ConcurrentHashMap<Long, TranslateType> selectTranslateType = new ConcurrentHashMap<>();

    public void handle(@NotNull Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Message message = update.message();
        if (message != null) {
            Long chatID = message.chat().id();
            String text = message.text();
            if (text.equals("/start")) {
                var sendMessage = new SendMessage(chatID, "Tarjima botga xush kelibsiz\nTarjima rejimini tanlang");
                sendMessage.replyMarkup(getInlinemarkUpKeyBoard());
                telegramBot.execute(sendMessage);
            } else if (selectTranslateType.get(chatID) != null) {
                TranslateType type = selectTranslateType.get(chatID);
                String translate = apiService.getTranslate(text, type.getFrom(), type.getTo());
                var sendMessage = new SendMessage(chatID, String.format("Tarjimasi: %s", translate));
                telegramBot.execute(sendMessage);
            } else {
                var deleteMessage = new DeleteMessage(chatID, message.messageId());
                telegramBot.execute(deleteMessage);
            }
        } else {
            Message message1 = callbackQuery.message();
            Chat chat = message1.chat();
            Long chatID = chat.id();
            TranslateType type = Stream.of(TranslateType.values())
                    .filter(translateType -> translateType.name().equals(callbackQuery.data()))
                    .findFirst()
                    .orElse(null);
            assert type != null;
            String content = type.getContent();
            String textMessage = "Siz %s rejimini tanladingiz.\nIltimos %s jumla kiriting"
                    .formatted(content, content.split("-")[0]);
            selectTranslateType.put(chatID, type);
            SendMessage sendMessage = new SendMessage(chatID, textMessage);
            telegramBot.execute(sendMessage);
        }
    }

    private Keyboard getInlinemarkUpKeyBoard() {
        TranslateType[] translateTypes = TranslateType.values();
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        for (TranslateType translateType : translateTypes) {
            var inlineKeyboardButton = new InlineKeyboardButton(translateType.getContent());
            inlineKeyboardButton.callbackData(translateType.name());
            inlineKeyboardMarkup.addRow(inlineKeyboardButton);
        }
        return inlineKeyboardMarkup;
    }
}
