# 📈 Investment Portfolio Tracker API

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.x-brightgreen.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)
![Spring WebFlux](https://img.shields.io/badge/WebFlux-Reactive-blueviolet.svg)
![License](https://img.shields.io/badge/License-MIT-gray.svg)

A robust, enterprise-grade REST API built to track stock market trades, aggregate portfolio holdings, and calculate real-time valuations using live market data. 

This project demonstrates core backend engineering principles, including database design, transactional integrity, data aggregation using Java Streams, and reactive third-party API integration.

## 🏗️ Architecture Overview

The application follows a standard layered architecture (Controller -> Service -> Repository) and integrates asynchronously with external market data providers.


### Tech Stack
* **Language:** Java 17 (Records, Streams, Pattern Matching)
* **Framework:** Spring Boot 3.x
* **Persistence:** Spring Data JPA / Hibernate
* **Database:** PostgreSQL
* **HTTP Client:** Spring WebFlux (`WebClient`) for non-blocking API calls
* **External Integration:** Alpha Vantage Global Quote API

---

## ✨ Core Features

1. **Trade Ledger Management:** * Record, update, and persist individual stock transactions (Buy/Sell) with strict validation.
   * Transactional guarantees (`@Transactional`) ensure database state remains consistent.
2. **Dynamic Portfolio Aggregation:** * Calculates net holdings per asset in real-time based on historical ledger entries.
3. **Live Valuation Engine:** * Reactively fetches live stock prices via `WebClient`.
   * Maps complex JSON payloads into immutable Java 17 Records.
   * Calculates total portfolio profit/loss and individual asset breakdowns.

---

## 📡 API Endpoints

### 1. Record a Trade
Adds a new Buy or Sell transaction to the ledger.
* **URL:** `/api/v1/trades`
* **Method:** `POST`
* **Body:**
  ```json
  {
    "tickerSymbol": "RELIANCE.NS",
    "tradeType": "BUY",
    "quantity": 10,
    "pricePerShare": 2950.50,
    "tradeDate": "2026-03-14T10:15:30"
  }
  ```
* **Success Response:** 201 Created

### 2. View Trade History
Retrieves the complete, chronological ledger of all executed trades.
* **URL:** /api/v1/trades
* **Method:** GET
* **Success Response:** 200 OK (Returns an array of Trade objects) 

### 3. Get Portfolio Holdings
Aggregates the ledger to show the net shares currently owned for each ticker.
* **URL:** /api/v1/portfolio/holdings
* **Method:** GET
  ```json
  {
    "RELIANCE.NS": 10,
    "AAPL": 15
  }
  ```
* **Success Response:** 200 OK

### 4. Get Real-Time Valuation
Calculates the live monetary value of the portfolio using real-time market data.
* **URL:** /api/v1/portfolio/valuation
* **Method:** GET
  ```json
  {
  "assetBreakdown": {
    "RELIANCE.NS": {
      "quantityOwned": 10,
      "currentPricePerShare": 3000.00,
      "totalAssetValue": 30000.00
    }
  },
  "totalPortfolioValue": 30000.00
  }
  ```
* **Success Response:** 200 OK

## 🚀 Local Development Setup

### 1. Clone the repository:
   ```bash
   git clone [https://github.com/yourusername/investment-portfolio-tracker.git](https://github.com/yourusername/investment-portfolio-tracker.git)
   cd investment-portfolio-tracker
   ```

### 2. Spin up the Database:
Use the included Docker Compose file to start PostgreSQL.   
  ```bash
  docker-compose up -d
  ```

### 3. Configure Market Data API:
Obtain a free API key from Alpha Vantage.
Update the application.yml file with your key:
  ```yaml
  market-data:
    alpha-vantage:
      api-key: YOUR_API_KEY
  ```

### 4. Run the Application:
  ```bash
  ./mvnw spring-boot:run
  ```
