import React from "react";
import {
	Link,
	useParams,
} from "react-router-dom";
import { FaShoppingCart, FaDollarSign } from "react-icons/fa";

const TraderShoppingCartView = () => {

const { id } = useParams();

  return (
    <section className="my-5">
       <h2 className="mt-5 text-center">Choose option</h2>
        <hr/>
        <div className="d-flex justify-content-center gap-4">
          <Link
            to={`/trader-shopping-cart/${id}/buy`}
            className="btn btn-success btn-lg px-5 py-4 shadow rounded-4 d-flex align-items-center gap-3"
          >
            <FaShoppingCart size={24} />
            <span className="fs-3">Buy</span>
          </Link>

          <Link
            to={`/trader-shopping-cart/${id}/sell`}
            className="btn btn-danger btn-lg px-5 py-4 shadow rounded-4 d-flex align-items-center gap-3"
          >
            <FaDollarSign size={24} />
            <span className="fs-3">Sell</span>
          </Link>
        </div>
        <div
      		  style={{
      		    display: 'flex',
      		    justifyContent: 'center',
              paddingTop: '40px',
      		    gap: '0px',
      		    paddingBottom: '20px',
      		  }}>
      		  <Link
      		    to={"/traders"}
      		    type="submit"
      		    className="btn btn-outline-primary"
      		  >
      		    Back
      		  </Link>
      		</div>
</section>
  )
}

export default TraderShoppingCartView
