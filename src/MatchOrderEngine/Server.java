package MatchOrderEngine;

import Utils.Utils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;

public class Server {
    static public HashMap<String, LimitOrderBook> limitOrderBooks = new HashMap<>();

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/submitOrder", new submitOrder());
        server.createContext("/getLimitOrderBook", new getLimitOrderBook());
        server.createContext("/getExecutedTrades", new getExecutedTrades());

        server.setExecutor(null);
        server.start();
    }

    static class submitOrder implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if (!t.getRequestMethod().equals("POST")) {
                return;
            }
            String input = parseReqBody(t.getRequestBody()).toUpperCase();
            List<String> inputList = Arrays.asList(input.split(","));

            Vector<String> logs = new Vector<>();
            HashMap<Integer, Order> standingOrders = new MatchOrderEngine().getStandingOrders();

            for (String s : inputList) {
                Order order = parseOrder(s);
                if (order == null) {
                    logs.add("<ParseError: Invalid Input> in \"" + s + "\"");
                    continue;
                }

                // Process Orders
                standingOrders.put(order.getId(), order);
                String instrument;
                if (order.getAction().equals(Action.CANCEL)) {
                    if (order.linkOrderToBeCancelled()) {
                        order.linkOrderToBeCancelled();
                        instrument = order.getOrderToBeCancelled().getInstrument();
                    } else {
                        logs.add("<Error: Order to be cancelled does not exist or has already been executed.>");
                        continue;
                    }
                } else {
                    instrument = order.getInstrument();
                    if (limitOrderBooks.get(instrument) == null) {
                        limitOrderBooks.put(instrument, new LimitOrderBook(instrument));
                    }
                }
                Vector<String> newLogs = limitOrderBooks.get(instrument).process(order);
                logs.addAll(newLogs);
            }

            // JSON
            JSONArray list = new JSONArray();
            for (String log : logs) {
                list.put(log);
            }
            var responseJson = list.toString();

            // Response
            t.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            t.getResponseHeaders().set("Access-Control-Allow-Credentials", "false");
            t.sendResponseHeaders(200, responseJson.length());
            OutputStream os = t.getResponseBody();
            os.write(responseJson.getBytes());
            os.close();
        }
    }

    static class getLimitOrderBook implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> input = queryToMap(t.getRequestURI().getQuery());
            String instrument = input.get("instrument").toUpperCase();
            LimitOrderBook limitOrderBook = limitOrderBooks.get(instrument);

            // JSON
            JSONArray bid = new JSONArray();
            Map<Double, Vector<Order>> bidTreeMap = new TreeMap<>(limitOrderBook.getLOBBid());
            bidTreeMap.forEach((price, orders) -> {
                Double size = orders.stream().mapToDouble(Order::getQuantity).sum();
                if (!size.equals(Double.valueOf(0))) {
                    JSONObject priceSize = new JSONObject();
                    priceSize.put("price", price);
                    priceSize.put("size", size);
                    bid.put(priceSize);
                }
            });

            JSONArray ask = new JSONArray();
            Map<Double, Vector<Order>> askTreeMap = new TreeMap<>(limitOrderBook.getLOBAsk());
            askTreeMap.forEach((price, orders) -> {
                Double size = orders.stream().mapToDouble(Order::getQuantity).sum();
                if (!size.equals(Double.valueOf(0))) {
                    JSONObject priceSize = new JSONObject();
                    priceSize.put("price", price);
                    priceSize.put("size", size);
                    ask.put(priceSize);
                }
            });

            JSONObject bidAsk = new JSONObject();
            bidAsk.put("bid", bid);
            bidAsk.put("ask", ask);
            var responseJson = bidAsk.toString();

            // Response
            t.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            t.getResponseHeaders().set("Access-Control-Allow-Credentials", "false");
            t.sendResponseHeaders(200, responseJson.length());
            OutputStream os = t.getResponseBody();
            os.write(responseJson.getBytes());
            os.close();
        }
    }

    static class getExecutedTrades implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> input = queryToMap(t.getRequestURI().getQuery());
            String instrument = input.get("instrument").toUpperCase();
            LimitOrderBook limitOrderBook = limitOrderBooks.get(instrument);

            // JSON
            JSONArray trades = new JSONArray();
            Collections.reverse(limitOrderBook.getTrades());
            limitOrderBook.getTrades().forEach((trade) -> {
                JSONObject tradeObj = new JSONObject();
                tradeObj.put("txId", trade.getTxId());
                tradeObj.put("timestamp", trade.getTimestamp().format(Utils.formatter));
                tradeObj.put("price", trade.getPrice());
                tradeObj.put("quantity", trade.getQuantity());
                tradeObj.put("buyOrderId", trade.getBuyOrderId());
                tradeObj.put("sellOrderId", trade.getSellOrderId());
                trades.put(tradeObj);
            });
            var responseJson = trades.toString();

            // Response
            t.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            t.getResponseHeaders().set("Access-Control-Allow-Credentials", "false");
            t.sendResponseHeaders(200, responseJson.length());
            OutputStream os = t.getResponseBody();
            os.write(responseJson.getBytes());
            os.close();
        }
    }

    @Test
    public void testdouble() {
        Double d = Double.parseDouble("0");
        System.out.println(d==0);
        System.out.println(d.equals(0));
        System.out.println(d.equals(0.0));
        System.out.println(d.equals(Double.valueOf(0)));

    }

    static private Order parseOrder(String s) {
        ArrayList<String> orderDetails = new ArrayList<>(Arrays.asList(s.split(" ")));
        orderDetails.removeAll(Arrays.asList("", null));

        if (orderDetails.size() == 0) {
            return null;
        }

        Order order = null;
        if (orderDetails.get(0).equals("C")) {
            // Check number of parameters
            if (orderDetails.size() != 2) {
                return null;
            }
            // Check Types
            try {
                Integer idOrderToBeCancelled = Integer.parseInt(orderDetails.get(1));
                order = new Order(Action.CANCEL, idOrderToBeCancelled);
            } catch (NumberFormatException e) {
                return null;
            }
        } else if (orderDetails.get(0).equals("B") || orderDetails.get(0).equals("S")) {
            // Check number of parameters
            if (orderDetails.size() != 4) {
                return null;
            }
            // Check Types
            try {
                Action action = switch (orderDetails.get(0)) {
                    case "B" -> Action.BUY;
                    default -> Action.SELL;
                };
                Double price = Double.parseDouble(orderDetails.get(1));
                Double quantity = Double.parseDouble(orderDetails.get(2));
                if (quantity <= 0) {
                    return null;
                }
                String instrument = orderDetails.get(3);
                order = new Order(action, price, quantity, instrument);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return order;
    }

    static public Map<String, String> queryToMap(String query) {
        if (query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }

    static public String parseReqBody(InputStream reqBody) {
        StringBuilder body = new StringBuilder();
        try (InputStreamReader reader = new InputStreamReader(reqBody, "UTF-8")) {
            char[] buffer = new char[256];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                body.append(buffer, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return body.toString();
    }
}

