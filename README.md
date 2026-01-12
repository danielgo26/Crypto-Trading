Documentation

Short summary:
A crypto trading application.

User functionalities:
1. CRUD operations on account level. Currently the app supports only admin mode, so a user can access and control all system accounts. Special option for reseting account's initial state.
2. See available cryptos with their up-to-date prices and amounts.
3. Buy and Sell cryptos at a specified price. On each such action, an email with the corresponding trade details is being generated and sent.
4. See their wallet with cryptos.
5. See their transactions.
6. Option for showing an expectable loss/profit from a deal.

Used technologies:
1. Backend Services

  Framework & Language: Backend logic implemented using Java and the Spring Boot framework.
  Architecture: The system is partitioned into two distinct, decoupled application services (e.g., Core Trading Service and Email Notification Service).

2. Frontend Interface

  Framework: Client-side user interface developed using React.js.

3. Communication & Protocols

  Client-Server: RESTful APIs over HTTP utilized for synchronous communication between the Frontend client and Backend services.

  Inter-Service Communication: Apache Kafka employed for asynchronous, event-driven messaging between the CryptoCurrencyTrading and EmailSender services to ensure loose coupling.

  Real-Time Data: WebSocket protocol implemented for the low-latency ingestion of live cryptocurrency market data.

4. Data Persistence

  Database: PostgreSQL Relational Database Management System, utilizing JDBC for high-performance data access.

5. Third-Party Integrations

  Market Data: Integration with the Kraken Exchange for real-time asset pricing.

  Notifications: Integration with Brevo for handling transactional email delivery.
