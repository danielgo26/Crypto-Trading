import React, { useEffect, useState } from 'react';
import axios from "axios";
import { useParams } from "react-router-dom";
import { useLocation, Link } from "react-router-dom";

const TransactionsView = () => {
    const [transactions, setTransactions] = useState([]);
    const [trader, setTrader] = useState({});
    const { id } = useParams();
    const [tickers, setTickers] = useState({});
    const [showSuccess, setShowSuccess] = useState(false);
    const location = useLocation();
    const [successMessage, setSuccessMessage] = useState(location.state?.successMessage || "");

    useEffect(() => {
      loadTransactions();
      loadTrader();
      loadTickers();
      
      if (successMessage) {

        setTimeout(() => {
          setShowSuccess(false);
          setSuccessMessage("");
        }, 3000);
      }
    }, [successMessage]);
      
    const loadTransactions = async () => {
      const result = await axios.get(
        `http://localhost:8080/traders/${id}/transactions`
      );
      setTransactions(result.data);
    };
  
    const loadTrader = async () => {
        const result = await axios.get(
            `http://localhost:8080/traders/${id}`
        );
        setTrader(result.data);
    };

    const loadTickers = async () => {
      axios.get('http://localhost:8080/cryptos')
      .then(response => setTickers(response.data))
      .catch(error => console.error('Error fetching data:', error));
    };

    const getPostfixProfitNote = (transaction) => {
      if (transaction.transactionType === "BUY") {
        return " (expectable)";
      }
      
      return " (probable)";
    };

  return (
    <div
 			style={{
 			  backgroundColor: 'rgba(255, 255, 255, 0.9)',
 			  borderRadius: '10px'
 			}}>
      <h2 className="mt-5 text-center">
        Transaction history of {trader.firstName + " " + trader.lastName}
      </h2>
      <hr/>
      <section
		  	className="shadow"
		  	style={{ backgroundColor: "whitesmoke" }}>
        {successMessage && (
          <div className="alert alert-success text-center" role="alert">
            {successMessage}
          </div>
        )}

        <table className="table table-striped table-bordered">
          <thead>
            <tr className="text-center">
              <th>№</th>
              <th>Crypto name</th>
              <th>Amount</th>
              <th>Traded for</th>
              <th>Type</th>
              <th>Date</th>
              <th>Profit</th>
            </tr>
          </thead>

          <tbody className="text-center">
            {transactions.map((transaction, index) => (
              <tr key={transaction.id}>
                <th scope="row">{index + 1}</th>
                <td>{transaction.cryptoCurrencyName}</td>
                <td>
                  {Number(transaction.amount).toLocaleString("en-US",
		  									 { minimumFractionDigits: 2, maximumFractionDigits: 4 })}
                </td>
                <td>
                  $ {Number(transaction.cryptoCurrencyTradedPrice).toLocaleString("en-US",
		  									 { minimumFractionDigits: 2, maximumFractionDigits: 10 })}
                </td>
                <td>{transaction.transactionType}</td>
                <td>{transaction.transactionDate}</td>
                <td>
                  <span
                    style={{
                      fontSize: '1.1rem',
                      fontWeight: 'bold',
                      color:
                        transaction.profit > 0
                          ? 'green'
                          : transaction.profit < 0
                          ? 'red'
                          : 'gray',
                    }}
                  >
                    {transaction.profit > 0
                      ? '+'
                      : transaction.profit < 0
                      ? '-'
                      : ''}
                    $ {Math.abs(Number(transaction.profit)).toLocaleString('en-US', {
                      minimumFractionDigits: 2,
                      maximumFractionDigits: 10,
                    })} {getPostfixProfitNote(transaction)}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        <div
          style={{
            display: 'flex',
            justifyContent: 'center',
            gap: '20px',
            paddingTop: '20px',
            paddingBottom: '20px',
          }}>
          <Link
            to={`/trader-profile/${id}`}
            type="submit"
            className="btn btn-outline-primary"
          >
            Back
          </Link>
        </div>
      </section>
    </div>
  );
};

export default TransactionsView;
