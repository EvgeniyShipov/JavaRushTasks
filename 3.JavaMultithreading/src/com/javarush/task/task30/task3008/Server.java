package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server {
    final private static Map<String, Connection> connectionMap = new java.util.concurrent.ConcurrentHashMap<>();

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            super();
            this.socket = socket;
        }

        public void run() {
            ConsoleHelper.writeMessage("Установлено соединение с удаленным клиентом с адресом: " + socket.getRemoteSocketAddress());
            Connection connection = null;
            String clientName = null;
            try {
                connection = new Connection(socket);
                clientName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, clientName));
                sendListOfUsers(connection, clientName);
                serverMainLoop(connection, clientName);
            } catch (IOException e) {
                handleHandlerException(e, connection);
            } catch (ClassNotFoundException e) {
                handleHandlerException(e, connection);
            }
            if (clientName != null) {
                connectionMap.remove(clientName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, clientName));
            }
            ConsoleHelper.writeMessage(String.format("Соединение с удаленным адресом (%s) закрыто.", socket.getRemoteSocketAddress()));
        }

        private void handleHandlerException(Exception e, Connection connection) {
            ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удаленным адресом: " +
                    socket.getRemoteSocketAddress() + "%n" +
                    "Тип ошибки: " + e.getClass().getSimpleName() + "%n" +
                    "Текст ошибки: " + e.getMessage());
            try {
                if (connection != null)
                    connection.close();
                socket.close();
            } catch (IOException e_) {

            }
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            boolean accepted = false;
            String name = null;
            while (!accepted) {
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message message = connection.receive();
                if (message.getType() == MessageType.USER_NAME) {
                    name = message.getData();
                    if (!name.isEmpty() && connectionMap.get(name) == null) {
                        connectionMap.put(name, connection);
                        connection.send(new Message(MessageType.NAME_ACCEPTED));
                        accepted = true;
                    }
                }
            }
            return name;
        }

        private void sendListOfUsers(Connection connection, String userName) throws IOException {
            for (String clientName : connectionMap.keySet()) {
                if (!clientName.equals(userName))
                    connection.send(new Message(MessageType.USER_ADDED, clientName));
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws
                IOException, ClassNotFoundException {
            while (!Thread.currentThread().isInterrupted()) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    String messageText = userName + ": " + message.getData();
                    sendBroadcastMessage(new Message(MessageType.TEXT, messageText));
                } else ConsoleHelper.writeMessage(
                        String.format("Ошибка! Недопустимый тип сообщения (MessageType.%s) от клиента: %s",
                                message.getType().toString(), userName)
                );
            }
        }
    }

    public static void main(String[] args) {
        ConsoleHelper.writeMessage("Введите порт сервера:");
        int port = ConsoleHelper.readInt();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            ConsoleHelper.writeMessage("Сервер запущен на порту: " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Handler(socket).start();
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage(e.getMessage());
        }
    }

    public static void sendBroadcastMessage(Message message) {
        for (String clientName : connectionMap.keySet()) {
            try {
                connectionMap.get(clientName).send(message);
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Не могу отправить сообщение клиенту с именем: " + clientName);
            }
        }
    }
}