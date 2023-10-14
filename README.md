# Match Order Engine

## Starting Frontend
Run the following commands in Terminal
1. `cd client`
2. `npm i`
3. `npm run dev`

## Starting Backend

Use Java18 SDK

On IntelliJ, include the dependencies

1. File > Project Structure > Libraries > + > Java > Choose the `json-20230227.jar` file from `src/Utils` > Apply

Start the server in `Server.java`

1. Click the run button next to the method `public static void main`

## Features

### Submit Orders

On the Submit Orders Page, orders can be inputed line by line in the text area to faciliate bulk orders and enhance efficiency. 

Buy and sell orders have the following format: <B||S> <price> <quantity> <instrument>. Eg. `B 10.5 100 BTC` represents a buy order for 100 BTC at price 10.5.

Cancel orders have the following format: C <id of order to be cancelled>. Eg. `C 14` represents a cancel order to the order with id 14.

### Limit Order Book

A limit order book of a specified instrument is displayed upon inputting the query.

### Executed Trades

Executed trades of a specified instrument is displayed upon inputting the query.


