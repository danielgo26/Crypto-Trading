import React, {
	useEffect,
	useState,
} from "react";
import { useParams, Link} from "react-router-dom";
import axios from "axios";

const TraderPofile = () => {
	const { id } = useParams();

	const [trader, setTrader] = useState({
		firstName: "",
		lastName: "",
		email: "",
		balance: "",
	});

	useEffect(() => {
		loadTrader();
	}, []);

	const loadTrader = async () => {
		const result = await axios.get(
			`http://localhost:8080/traders/${id}`
		);
		setTrader(result.data);
	};

	return (
		<div
 			style={{
 			  backgroundColor: 'rgba(255, 255, 255, 0.9)',
 			  borderRadius: '10px'
 			}}>
			<h2 className="mt-5 text-center">Trader profile</h2>
			<hr/>
			<section
				className="shadow"
				style={{ backgroundColor: "whitesmoke" }}>
				<div className="container py-5">
					<div className="row">

						<div className="col-lg-3">
							<div className="card mb-4">
									<div className="card-body text-center">
										<img
											src={process.env.PUBLIC_URL + '/images/traderProfilePicture.jpg'}
											alt="avatar"
											className="rounded-circle img-fluid"
											style={{ width: 175 }}
										/>
									</div>
							</div>           

        	                <div style={{ display: 'flex', justifyContent: 'center', gap: '20px', paddingTop: '20px', paddingBottom: '20px' }}>
        	                    <Link
								        to={`/trader-profile/${trader.id}/crypto-wallet`}
								        type="submit"
								        className="btn btn-outline-primary">
								            View wallet
							    </Link>
        	                    <Link
								        to={`/trader-profile/${trader.id}/transactions`}
								        type="submit"
								        className="btn btn-outline-primary">
								            View Transactions
							    </Link>
							</div>
						</div>
		
						<div className="col-lg-9">
							<div className="card mb-4">
								<div className="card-body">
									<hr />

									<div className="row">
										<div className="col-sm-3">
											<h5 className="mb-0">
												First name
											</h5>
										</div>

										<div className="col-sm-9">
											<p className="text-muted mb-0">
												{trader.firstName}
											</p>
										</div>
									</div>

									<hr />

									<div className="row">
										<div className="col-sm-3">
											<h5 className="mb-0">
												Last name
											</h5>
										</div>

										<div className="col-sm-9">
											<p className="text-muted mb-0">
												{trader.lastName}
											</p>
										</div>
									</div>
									<hr />

									<div className="row">
										<div className="col-sm-3">
											<h5 className="mb-0">
												Email
											</h5>
										</div>

										<div className="col-sm-9">
											<p className="text-muted mb-0">
												{trader.email}
											</p>
										</div>
									</div>
									<hr />

        	                        <div className="row">
										<div className="col-sm-3">
											<h5 className="mb-0">
												Balance
											</h5>
										</div>

										<div className="col-sm-9">
											<p className="text-muted mb-0">
												$ {Number(trader.balance).toLocaleString("en-US",
												 { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
											</p>
										</div>
									</div>
									<hr />

								</div>
							</div>
						</div>
					</div>
				</div>
				<div
      			  style={{
      			    display: 'flex',
      			    justifyContent: 'center',
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
		</div>
	);
};

export default TraderPofile;