import React, { useEffect, useState } from 'react';
import axios from "axios";
import { Link } from "react-router-dom";
import { FaTrashAlt, FaEdit, FaShoppingCart, FaEye } from "react-icons/fa"

const TradersView = () => {
    const[traders, setTraders] = useState([])

    useEffect(() => {
      loadTraders();
    }, []);

    const loadTraders = async()=>{
      const result = await axios.get(
        "http://localhost:8080/traders");

        setTraders(result.data);
    };

    const handleDelete = async (id) => {
      const confirmed = window.confirm("Are you sure you want to delete this trader?");
      if (!confirmed) return;
    
      try {
        await axios.delete(`http://localhost:8080/traders/${id}`);
        loadTraders();
      } catch (error) {
        console.error("Error deleting trader:", error);
        alert("Failed to delete the trader.");
      }
    };

  return (
    <div
 			style={{
 			  backgroundColor: 'rgba(255, 255, 255, 0.9)',
 			  borderRadius: '10px'
 			}}>
        <h2 className="text-center my-4"> Traders</h2>
			<hr/>
      <section>
        <table className="table table-striped table-bordered">
          <thead>
              <tr className='text-center'>
                  <th>№</th>
                  <th>First name</th>
                  <th>Last name</th>
                  <th>Email</th>
                  <th colSpan="4">Actions</th>
              </tr>
          </thead>

          <tbody className='text-center'>
            {traders.map((trader, index)=>(
              <tr key={trader.id}>
                <th scope="row" key={index}>
                  {index + 1}
                </th>
                <td>{trader.firstName}</td>  
                <td>{trader.lastName}</td> 
                <td>{trader.email}</td> 
                <td className='mx-2'>
                <Link to={`/trader-profile/${trader.id}`} className='btn btn-info'>
                    <FaEye />
                </Link>
                </td> 
                <td className='mx-2'>
                  <Link to={`/edit-trader/${trader.id}`} className='btn btn-warning'>
                    <FaEdit />
                  </Link>
                </td> 
                <td className='mx-2'>
                  <Link to={`/trader-shopping-cart/${trader.id}`} className="btn btn-success">
                    <FaShoppingCart />
                  </Link>
                </td> 
                <td className='mx-2'>
                  <button 
                          className='btn btn-danger' 
                          onClick={() =>
		  									  handleDelete(trader.id)
		  								    }>
                            <FaTrashAlt />
                  </button>
                </td> 

              </tr>
            ))}
          </tbody>
        </table>
      </section>
    </div>
  )
}

export default TradersView
