import React from "react";
import { Link, useLocation } from "react-router-dom";

const NavBar = () => {
	const location = useLocation();

	return (
		<nav className="navbar navbar-expand-lg navbar-dark bg-dark mb-5">
			<div className="container-fluid">
				<Link className="navbar-brand" to={"/"}>
					Home
				</Link>
				<button
					className="navbar-toggler"
					type="button"
					data-bs-toggle="collapse"
					data-bs-target="#navbarNav"
					aria-controls="navbarNav"
					aria-expanded="false"
					aria-label="Toggle navigation">
					<span className="navbar-toggler-icon"></span>
				</button>
				<div
					className="collapse navbar-collapse"
					id="navbarNav"
                    style={{ fontSize: '20px', paddingLeft: '10px' }}>
					<ul className="navbar-nav">
						<li className="nav-item" style={{ paddingLeft: '10px' }}>
							<Link
								className={`nav-link ${location.pathname === "/traders" ? "active" : ""}`}
								aria-current="page"
								to={"/traders"}>
								Traders
							</Link>
						</li>
						<li className="nav-item" style={{ paddingLeft: '10px' }}>
							<Link
								className={`nav-link ${location.pathname === "/add-trader" ? "active" : ""}`}
								to={"/add-trader"}>
								Add
							</Link>
						</li>
                        <li className="nav-item" style={{ paddingLeft: '10px' }}>
							<Link
								className={`nav-link ${location.pathname === "/cryptos" ? "active" : ""}`}
								to={"/cryptos"}>
								Cryptos
							</Link>
						</li>
					</ul>
				</div>
			</div>
		</nav>
	);
};

export default NavBar;