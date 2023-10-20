import { Button, Snackbar, Alert, Avatar } from "@mui/material";
import { green } from "@mui/material/colors";
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { currentUser, register } from "../../redux/auth/action";

const Signup = () => {
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const dispatch = useDispatch();
  const { auth } = useSelector((store) => store);
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const [inputData, setInputData] = useState({
    fullName: "",
    email: "",
    password: "",
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("handle submit", inputData);
    dispatch(register(inputData));
    setOpenSnackbar(true);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setInputData((values) => ({ ...values, [name]: value }));
  };
  const handleSnackbarClose = () => {
    setOpenSnackbar(false);
  };
  useEffect(() => {
    if (token) {
      dispatch(currentUser(token));
    }
  }, [token]);

  useEffect(() => {
    if (auth.reqUser?.fullName) {
      navigate("/");
    }
  }, [auth.reqUser]);
  return (
    <div>
      <div>
        <div className="flex flex-col justify-center min-h-screen items-center">
        <Avatar sx={{width:"10rem",height:"10rem"}}
          alt="profile icon"   src={"https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/WhatsApp.svg/2044px-WhatsApp.svg.png"}/>
          <div className="w-[30%] p-10 shadow-md bg-white rounded-md">
            <form onSubmit={handleSubmit} className="space-y-5">
              <div>
                <p className="mb-2">Full Name</p>
                <input
                  placeholder="Enter your full name"
                  onChange={(e) => handleChange(e)}
                  value={inputData.fullName}
                  type="text"
                  name="fullName"
                  className="py-2 px-3 border-4 w-full rounded-md "
                />
              </div>
              <div>
                <p className="mb-2">Email</p>
                <input
                  placeholder="Enter your email"
                  onChange={(e) => handleChange(e)}
                  value={inputData.email}
                  type="text"
                  name="email"
                  className="py-2 px-3 border-4 w-full rounded-md "
                />
              </div>
              <div>
                <p className="mb-2">Password</p>
                <input
                  placeholder="Enter your password"
                  onChange={(e) => handleChange(e)}
                  value={inputData.password}
                  type="password"
                  name="password"
                  className="py-2 px-3 border-4 w-full rounded-md "
                />
              </div>
              <div>
                <Button
                  type="submit"
                  sx={{ bgcolor: green[700], padding: ".5rem 0rem" }}
                  className="w-full"
                  variant="contained"
                >
                  Sign Up
                </Button>
              </div>
            </form>
            <div className="flex space-x-3 items-center mt-5">
              <p className="m-0">Already have an account?</p>
              <Button variant="text" onClick={() => navigate("/signin")}>
                Sign In
              </Button>
            </div>
          </div>
        </div>
        <Snackbar
          open={openSnackbar}
          autoHideDuration={6000}
          onClose={handleSnackbarClose}
        >
          <Alert
            onClose={handleSnackbarClose}
            severity="success"
            sx={{ width: "100%" }}
          >
            Account created successfully
          </Alert>
        </Snackbar>
      </div>
    </div>
  );
};

export default Signup;
