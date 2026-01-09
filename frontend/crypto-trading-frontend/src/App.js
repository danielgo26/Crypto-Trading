import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";
import {
	Routes,
	Route,
} from "react-router-dom";
import './App.css';
import Home from './Home';
import TradersView from './component/trader/TradersView';
import NavBar from "./component/common/NavBar";
import AddTrader from "./component/trader/AddTrader";
import TraderShoppingCartView from "./component/trader/TraderShoppingCartView";
import EditTrader from "./component/trader/EditTrader";
import TraderPofile from "./component/trader/TraderProfile";
import TransactionsView from "./component/transaction/TransactionsView";
import CryptosView from "./component/crypto/CryptosView";
import BuyCryptoView from "./component/crypto/BuyCryptoView";
import SellCryptoView from "./component/crypto/SellCryptoView";
import CryptoWalletView from "./component/wallet/CryptoWalletView";

function App() {
  return (
    <main className="container mt-5"> 
      <NavBar />
      <Routes>
        <Route exact path="/" element={<Home />}></Route>
			  <Route exact path="/traders" element={<TradersView />}></Route>
        <Route exact path="/add-trader" element={<AddTrader />}></Route>
        <Route exact path="/edit-trader/:id" element={<EditTrader />}></Route>
        <Route exact path="/trader-shopping-cart/:id" element={<TraderShoppingCartView />}></Route>
        <Route exact path="/trader-profile/:id" element={<TraderPofile />}></Route>
        <Route exact path="/trader-profile/:id/transactions" element={<TransactionsView />}></Route>
        <Route exact path="/trader-profile/:id/crypto-wallet" element={<CryptoWalletView />}></Route>
        <Route exact path="/cryptos" element={<CryptosView />}></Route>
        <Route exact path="/trader-shopping-cart/:id/buy" element={<BuyCryptoView />}></Route>
        <Route exact path="/trader-shopping-cart/:id/sell" element={<SellCryptoView />}></Route>
      </Routes>
    </main>
  );
}

export default App;
