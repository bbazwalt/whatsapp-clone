import { Button } from "@mui/material";
import { green } from "@mui/material/colors";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const Signin = () => {
  const [inputData, setInputData] = useState({ email: "", password: "" });
  const navigate = useNavigate();
  const handleSubmit = () => {
    console.log("handle submit");
  };

  const handleChange = () => {};
  return (
    <div>
      {/* <div className="flex justify-center items-center">
      <img
            className=""
            src="data:image/png;base64,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"
            alt=""
          />
        </div>  */}
      <div className="flex justify-center h-screen items-center">
        
        <div className="w-[30%] p-10 shadow-md bg-white rounded-md">
          <form onSubmit={handleSubmit} className="space-y-5">
            <div>
              <p className="mb-2">Email</p>
              <input
                placeholder=" Enter your email"
                onChange={handleChange}
                value={inputData.email}
                type="text"
                className="py-2 border-4 w-full rounded-md "
              />
            </div>
            <div>
              <p className="mb-2">Password</p>
              <input
                placeholder=" Enter your password"
                onChange={handleChange}
                value={inputData.password}
                type="password"
                className="py-2 border-4 border-blue w-full rounded-md "
              />
            </div>
            <div>
              <Button
                type="submit"
                sx={{ bgcolor: green[700], padding: ".5rem 0rem" }}
                className="w-full"
                variant="contained"
              >
                Sign In
              </Button>
            </div>
          </form>
          <div className="flex space-x-3 items-center mt-5">
            <p className="m-0">Don't have an account yet?</p>
            <Button variant="text" onClick={() => navigate("/signup")}>
              Sign Up
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Signin;
