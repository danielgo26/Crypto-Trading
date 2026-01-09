import React, { useEffect, useState } from 'react';
import axios from 'axios';

const CryptosView = () => {
  const [tickers, setTickers] = useState({});
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    const fetchData = () => {
        axios.get('http://localhost:8080/cryptos')
            .then(response => setTickers(response.data))
            .catch((error) => { 
              setErrorMessage(("Error fetching cryptos"));
              console.error(error);});
    };

    fetchData();

    const interval = setInterval(fetchData, 1000);

    return () => clearInterval(interval);
}, []);

  return (
    <div
 			style={{
 			  backgroundColor: 'rgba(255, 255, 255, 0.9)',
 			  borderRadius: '10px'
 			}}>
      <h2 className="text-center my-4">Crypto currencies rates</h2>
      <hr />
      
      {errorMessage && (
          <div className="alert alert-danger" role="alert">
            {errorMessage}
          </div>
        )}

      <table className="table table-striped table-bordered">
        <thead>
          <tr>
            <th>№</th>
            <th>Symbol</th>
            <th>Name</th>
            <th>Highest price for celling</th>
            <th>Lowest price for buying</th>
            <th>Amount traded (last 24 hours)</th>
          </tr>
        </thead>
        <tbody>
          {Object.entries(tickers).map(([key, ticker], index) => (
            <tr key={key}>
                <th scope="row" key={index}>
                {index + 1}
                </th>
                <td>{ticker.cryptoSymbol}</td>
                <td>{ticker.fullName}</td>
                <td> 
                  $ {Number(ticker.bid).toLocaleString("en-US",
											 { minimumFractionDigits: 2, maximumFractionDigits: 10 })}
                </td>
                <td>
                  $ {Number(ticker.ask).toLocaleString("en-US",
											 { minimumFractionDigits: 2, maximumFractionDigits: 10 })}
                </td>
                <td>
                  $ {Number(ticker.volume).toLocaleString("en-US",
											 { minimumFractionDigits: 2, maximumFractionDigits: 4 })}
                </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CryptosView;
