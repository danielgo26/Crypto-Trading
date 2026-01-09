import { useState } from "react";
import {
	Link,
	useNavigate,
} from "react-router-dom";
import axios from "axios";

const AddTrader = () => {
	let navigate = useNavigate();
	const [trader, setTrader] = useState({
		firstName: "",
		lastName: "",
		email: "",
	});
	
	const {
		firstName,
		lastName,
		email,
	} = trader;

	const handleInputChange = (e) => {
		setTrader({
			...trader,
			[e.target.name]: e.target.value,
		});
	};
	const [errorMessage, setErrorMessage] = useState("");
	const saveTrader = async (e) => {
		e.preventDefault();
		await axios.post(
			"http://localhost:8080/traders",
			trader
		).then(() => {
			setErrorMessage("");
			navigate("/traders");})
		.catch((error) => setErrorMessage(error.response.data.message || "Submission failed!"));
	
		
	};

	return (
		<div
 			className="col-sm-8 py-2 px-5 offset-2 shadow"
 			style={{
 			  backgroundColor: 'rgba(255, 255, 255, 0.9)', // white with slight transparency
 			  borderRadius: '10px' // optional, for a nicer look
 			}}>
			<h2 className="mt-5"> Add Trader</h2>
			<hr />
			<form onSubmit={(e) => saveTrader(e)}>
				<div className="input-group mb-5">
					<label
						className="input-group-text"
						htmlFor="fristName">
						First Name
					</label>
					<input
						className="form-control col-sm-6"
						type="text"
						name="firstName"
						id="firstName"
						required
						value={firstName}
						onChange={(e) => handleInputChange(e)}
					/>
				</div>

				<div className="input-group mb-5">
					<label
						className="input-group-text"
						htmlFor="lastName">
						Last Name
					</label>
					<input
						className="form-control col-sm-6"
						type="text"
						name="lastName"
						id="lastName"
						required
						value={lastName}
						onChange={(e) => handleInputChange(e)}
					/>
				</div>

				<div className="input-group mb-5">
					<label
						className="input-group-text"
						htmlFor="email">
						Email
					</label>
					<input
						className="form-control col-sm-6"
						type="email"
						name="email"
						id="email"
						required
						value={email}
						onChange={(e) => handleInputChange(e)}
					/>
				</div>

				{errorMessage && (
				  <div className="alert alert-danger text-center" role="alert">
				    {errorMessage}
				  </div>
				)}

				<div className="row mb-5">
					<div className="col-sm-2">
						<button
							type="submit"
							className="btn btn-outline-success btn-lg">
							Save
						</button>
					</div>

					<div className="col-sm-2">
						<Link
							to={"/traders"}
							type="submit"
							className="btn btn-outline-warning btn-lg">
							Cancel
						</Link>
					</div>
				</div>
			</form>
		</div>
	);
};

export default AddTrader;