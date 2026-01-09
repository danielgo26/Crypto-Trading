import React, {
	useEffect,
	useState,
} from "react";
import { useParams, Link, useNavigate} from "react-router-dom";
import axios from "axios";

const SellCryptoView = () => {
  let navigate = useNavigate();
  const { id } = useParams();
  const [walletEntities, setWalletEntities] = useState([]);
  const [availableCryptos, setAvailableCryptos] = useState({});
  const [errorMessage, setErrorMessage] = useState("");
  const [transaction, setTransaction] = useState({
    cryptoName: "",
    cryptoSymbol: "",
    amount: 0,
    tradindPrice: 0,
  });
  const [trader, setTrader] = useState({
    firstName: "",
    lastName: "",
    email: "",
    balance: 0
    });

    useEffect(() => {
      loadWalletEntities();
    }, []);
    
    const loadWalletEntities = async () => {
      const result = await axios.get(
        `http://localhost:8080/traders/${id}/crypto-wallet`
      ).catch((error) => { 
        setErrorMessage(("Error fetching cryptos from wallet"));
        console.error(error);});

      setWalletEntities(result.data);
    };

    const loadTrader = async () => {
        const result = await axios.get(
            `http://localhost:8080/traders/${id}`
        ).catch((error) => { 
          setErrorMessage(("Error fetching trader"));
          console.error(error);});

        setTrader(result.data);
    };

    const getSelectedCrypto = (symbol) => Object.entries(walletEntities).find(
      ([key, crypto]) => crypto.cryptoCurrencySymbol === symbol
    );

        useEffect(() => {
            loadTrader();

            const fetchData = () => {
                axios.get("http://localhost:8080/cryptos")
                .then((res) => {
                    setAvailableCryptos(res.data);
            
                    if (transaction.cryptoSymbol) {
                      const selected = Object.values(res.data).find(
                        (ticker) => ticker.cryptoSymbol === transaction.cryptoSymbol
                      );

                      if (selected) {
                        setTransaction((prev) => ({
                          ...prev,
                          tradindPrice: selected.bid,
                        }));
                      }
                    }
                  }).catch((error) => { 
                    setErrorMessage(("Error fetching cryptos"));
                    console.error(error);});
            }
          
            fetchData();

            const interval = setInterval(fetchData, 1000);

            return () => clearInterval(interval);
          }, [transaction.cryptoSymbol]);
          
          const handleChange = (e) => {
            const { name, value } = e.target;
          
            if (name === "crypto") {
              const selectedTicker = Object.values(availableCryptos).find(
                (ticker) => ticker.cryptoSymbol === value
              );
          
              if (selectedTicker) {
                setTransaction((prev) => ({
                  ...prev,
                  cryptoName: selectedTicker.fullName,
                  cryptoSymbol: selectedTicker.cryptoSymbol,
                  tradindPrice: selectedTicker.ask
                }));
              }
            } else {
              setTransaction((prevState) => ({
                ...prevState,
                [name]: value
              }));
            }
          };
          

  const handleSell = async (e) => {
    e.preventDefault();
    const selectedCrypto = transaction.cryptoSymbol
    const amountToSell = parseFloat(transaction.amount);
    const amountAvailable = getSelectedCrypto(selectedCrypto)[1].amount;

    if (amountToSell > amountAvailable) {
      setErrorMessage("Insufficient crypto amount to complete the sale.");
    } else {

        const transactionToSend = {
            traderId: id, 
            amount: amountToSell,
            cryptoCurrencyTradedPrice: transaction.tradindPrice,
            cryptoCurrencyName: transaction.cryptoName,
            cryptoCurrencySymbol: transaction.cryptoSymbol,
            transactionType: "SELL",
            transactionDate: new Date(),
            
          };      

      await axios.post(
	  	`http://localhost:8080/traders/${id}/transactions`,
	  	transactionToSend
	  ).catch((error) => { 
      setErrorMessage(("Error fetching trader transactions"));
      console.error(error);});

      navigate(`/trader-profile/${id}/transactions`, {
        state: { successMessage: "Transaction successful!" }
      });
    }
  };

  return (
    <div
 			className="col-sm-8 py-2 px-5 offset-2 shadow"
 			style={{
 			  backgroundColor: 'rgba(255, 255, 255, 0.9)',
 			  borderRadius: '10px'
 			}}>
		<h2 className="mt-5">Sell Crypto</h2>
        <hr/>
        <form onSubmit={handleSell}>
          <div className="input-group mb-4">
            <label className="input-group-text" htmlFor="crypto">
              Crypto
            </label>
            <select
              name="crypto"
              className="form-select"
              value={transaction.cryptoSymbol}
              onChange={handleChange}
              required
            >
              <option value="">Choose...</option>
                {Object.values(walletEntities).map((walletEntity, index) => (
                  <option
                    key={index}
                    value={walletEntity.cryptoCurrencySymbol}>
                    {walletEntity.cryptoCurrencyName} ({walletEntity.cryptoCurrencySymbol})
                  </option>
              ))}
            </select>
          </div>

          {transaction.cryptoSymbol && (
            <div className="input-group mb-4">
              <label className="input-group-text" htmlFor="price">
                Amount available
              </label>
              <input
                type="text"
                name="price"
                className="form-control"
                value={getSelectedCrypto(transaction.cryptoSymbol)[1].amount}
                readOnly
              />
            </div>
          )}

          <div className="input-group mb-4">
            <label className="input-group-text" htmlFor="amount">
              Amount to sell
            </label>
            <input
              type="number"
              name="amount"
              className="form-control"
              min="0.001"
              step="any"
              value={transaction.amount}
              onChange={handleChange}
              required
            />
          </div>

          {transaction.cryptoSymbol && (
            <div className="input-group mb-4">
              <label className="input-group-text" htmlFor="price">
                Price (per unit)
              </label>
              <input
                type="text"
                name="price"
                className="form-control"
                value={
                    `$ ${Number(transaction.tradindPrice).toLocaleString("en-US",
                    { minimumFractionDigits: 2, maximumFractionDigits: 10 })}`
                }
                readOnly
              />
            </div>
          )}

          {transaction.cryptoSymbol && (
            <div className="input-group mb-4">
              <label className="input-group-text" htmlFor="price">
                Total sell price:
              </label>
              <input
                type="text"
                name="price"
                className="form-control"
                value={
                    `$ ${Number(transaction.tradindPrice * transaction.amount).toLocaleString("en-US",
                    { minimumFractionDigits: 2, maximumFractionDigits: 10 })}`
                }
                readOnly
              />
            </div>
          )}

        {errorMessage && (
          <div className="alert alert-danger" role="alert">
            {errorMessage}
          </div>
        )}

          <div className="row mb-4">
            <div className="col-sm-2">
              <button type="submit" className="btn btn-outline-success btn-lg">
                Sell
              </button>
            </div>

            <div className="col-sm-2">
              <Link to={`/trader-shopping-cart/${id}`} className="btn btn-outline-warning btn-lg">
                Cancel
              </Link>
            </div>
          </div>
        </form>
    </div>
  );
};

export default SellCryptoView