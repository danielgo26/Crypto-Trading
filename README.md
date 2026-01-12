## A crypto trading application.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### User functionalities
1. CRUD operations on account level. Currently the app supports only admin mode, so a user can access and control all system accounts. Special option for reseting account's initial state is present.
2. See available cryptos with their up-to-date prices and amounts.
3. Buy and Sell cryptos at a specified price. On each such action, an email with the corresponding trade details is being generated and sent.
4. See their wallet with cryptos.
5. See their transaction history.
6. Option for showing an expectable loss/profit from a deal.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### Architecture
- Several services instead a single Monolith (two BE ones - CryptoCurrencyTrading and EmailSender, and a FE one - crypto-trading-frontend).
- MVC architecture in both BE services.
- Event-Driven Architecture used when sending emails.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### Component Breakdown & Responsibilities

| Component | Type | Responsibility |
| :--- | :--- | :--- |
| **Frontend Client** | React | Manages user interaction, state management, and visualizes real-time data. |
| **Core Trading Service** | Spring Boot | Acts as the source of truth. Manages User Accounts, Wallets, Transactions, and Kraken API integration. |
| **Email Service** | Spring Boot | Listens for trade events and interfaces with the Brevo API to send transactional emails. |
| **PostgreSQL** | RDBMS | Persists relational data. |
| **Kafka Cluster** | Message Broker | Buffers and routes events between services (topics: `crypto-sell-events`, `crypto-buy-events`). |
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### Used technologies
1. Backend Services

  Backend logic implemented using Java and the Spring Boot framework.
  The system is partitioned into two distinct, decoupled application services (e.g., Core Trading Service and Email Notification Service).

2. Frontend Interface

  Client-side user interface on React.js using Node.js.

3. Communication & Protocols

  Client-Server: RESTful APIs over HTTP utilized for synchronous communication between the Frontend client and Backend services.

  Inter-Service Communication: Apache Kafka employed for asynchronous, event-driven messaging between the CryptoCurrencyTrading and EmailSender services to ensure loose coupling.

  Real-Time Data: WebSocket protocol implemented for the low-latency ingestion of live cryptocurrency market data.

4. Data Persistence

  Database: PostgreSQL Relational Database Management System, utilizing JDBC for high-performance data access.

5. Third-Party Integrations

  Market Data: Integration with the Kraken Exchange for real-time asset pricing.

  Notifications: Integration with Brevo for handling transactional email delivery.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### Local start-up:
  ToDo
