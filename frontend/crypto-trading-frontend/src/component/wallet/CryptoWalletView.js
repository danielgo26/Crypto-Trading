import React, { useEffect, useState } from 'react';
import axios from "axios";
import { useParams } from "react-router-dom";
import { useLocation, Link } from "react-router-dom";

const CryptoWalletView = () => {
    const [walletEntities, setWalletEntities] = useState([]);
    const [trader, setTrader] = useState({});
    const { id } = useParams();
    const [tickers, setTickers] = useState({});
    const location = useLocation();

    useEffect(() => {
      loadWalletEntities();
      loadTrader();
      loadTickers();
    }, []);

    const loadWalletEntities = async () => {
      const result = await axios.get(
        `http://localhost:8080/traders/${id}/crypto-wallet`
      );
      setWalletEntities(result.data);
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

  return (
    <div
 			style={{
 			  backgroundColor: 'rgba(255, 255, 255, 0.9)',
 			  borderRadius: '10px'
 			}}>
      <h2 className="mt-5 text-center">
        Crypto wallet of {trader.firstName + " " + trader.lastName}
      </h2>
      <hr />
      <section
      className="shadow"
      style={{ backgroundColor: "whitesmoke" }}>
        <table className="table table-striped table-bordered">
          <thead>
            <tr className="text-center">
              <th>№</th>
              <th>Crypto name</th>
              <th>Amount</th>
              <th>Date of last trading</th>
            </tr>
          </thead>

          <tbody className="text-center">
            {Object.values(walletEntities).map((walletEntity, index) => (
              <tr key={walletEntity.id}>
                <th scope="row">{index + 1}</th>
                <td>{walletEntity.cryptoCurrencyName} ({walletEntity.cryptoCurrencySymbol})</td>
                <td>
                  {Number(walletEntity.amount).toLocaleString("en-US",
		  									 { minimumFractionDigits: 2, maximumFractionDigits: 4 })}
                </td>
                <td>{walletEntity.lastModified}</td>
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

export default CryptoWalletView;
