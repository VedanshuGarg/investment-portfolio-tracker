# Investment Portfolio Tracker API

A robust, backend-first REST API built to track stock market trades, aggregate portfolio holdings, and calculate real-time valuations. 

## Tech Stack
* **Language:** Java 17
* **Framework:** Spring Boot 3.x
* **Database:** PostgreSQL (via Spring Data JPA)
* **Market Data Integration:** [Alpha Vantage / Yahoo Finance] (Planned)

## Core Features (MVP)
* **Trade Ledger:** Record, update, and delete individual stock transactions (Buy/Sell).
* **Portfolio Aggregation:** Calculate total holdings per asset based on historical trades.
* **Live Valuation:** Fetch real-time market prices to calculate current portfolio value and overall Profit/Loss (P&L).

## Local Development Setup
1. Clone the repository: `git clone https://github.com/yourusername/portfolio-tracker-api.git`
2. Ensure PostgreSQL is running locally and create a database named `portfolio_db`.
3. Update the `application.yml` with your local database credentials.
4. Run the application via your IDE or using Maven: `./mvnw spring-boot:run`
