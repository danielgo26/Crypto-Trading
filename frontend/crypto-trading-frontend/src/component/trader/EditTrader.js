import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useLocation, useNavigate, useParams } from "react-router-dom";

const EditTrader = () => {
  let navigate = useNavigate();
  const location = useLocation();
  const [showSuccess, setShowSuccess] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const { id } = useParams();

  const [trader, setTrader] = useState({
    firstName: "",
    lastName: "",
    email: "",
  });
  const { firstName, lastName, email, balance } = trader;

  useEffect(() => {
    loadTrader();
  }, []);

  const loadTrader = async () => {
    const result = await axios.get(`http://localhost:8080/traders/${id}`);
    setTrader(result.data);
  };

  const handleInputChange = (e) => {
    setTrader({
      ...trader,
      [e.target.name]: e.target.value,
    });
  };

  const updateTrader = async (e) => {
    e.preventDefault();
    await axios.put(`http://localhost:8080/traders/${id}`, trader);
    navigate("/traders");
  };

  const resetTraderAccount = async (e) => {
    e.preventDefault();
    const resetTraiderResponse = await axios.post(
      `http://localhost:8080/traders/${id}/reset`
    );

    setTrader(resetTraiderResponse.data);
    setSuccessMessage("Successfully reset state!");
    setShowSuccess(true);

    // Hide success message after 3 seconds
    setTimeout(() => {
      setShowSuccess(false);
    }, 3000);
  };

  return (
    <div
      className="col-sm-8 py-2 px-5 offset-2 shadow"
      style={{
        backgroundColor: 'rgba(255, 255, 255, 0.9)',
        borderRadius: '10px'
      }}
    >
      <h2 className="mt-5">Edit trader</h2>
      <hr />
      <form onSubmit={updateTrader}>
        <div className="input-group mb-5">
          <label className="input-group-text" htmlFor="firstName">
            First Name
          </label>
          <input
            className="form-control col-sm-6"
            type="text"
            name="firstName"
            id="firstName"
            required
            value={firstName}
            onChange={handleInputChange}
          />
        </div>

        <div className="input-group mb-5">
          <label className="input-group-text" htmlFor="lastName">
            Last Name
          </label>
          <input
            className="form-control col-sm-6"
            type="text"
            name="lastName"
            id="lastName"
            required
            value={lastName}
            onChange={handleInputChange}
          />
        </div>

        <div className="input-group mb-5">
          <label className="input-group-text" htmlFor="email">
            Email
          </label>
          <input
            className="form-control col-sm-6"
            type="email"
            name="email"
            id="email"
            required
            value={email}
            onChange={handleInputChange}
          />
        </div>

        {showSuccess && (
          <div className="alert alert-success text-center" role="alert">
            {successMessage}
          </div>
        )}

        <div className="row mb-5">
          <div className="col d-flex justify-content-between gap-x-3">
            <button type="submit" className="btn btn-outline-success btn-lg px-4 gap-x-3">
              Save
            </button>

            <button
              type="button"
              className="btn btn-outline-danger btn-lg px-4"
              onClick={resetTraderAccount}
            >
              Reset Account
            </button>

            <Link to="/traders" className="btn btn-outline-warning btn-lg">
              Cancel
            </Link>
          </div>
        </div>
      </form>
    </div>
  );
};

export default EditTrader;
