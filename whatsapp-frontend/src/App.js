import { Route, Routes } from "react-router-dom";
import HomePage from "./components/homepage/HomePage";
import Signin from "./components/register/Signin";
import Signup from "./components/register/Signup";

const App = () => {
  return (
    <div>
      <Routes>
        <Route path="/" element={<HomePage />}></Route>
        <Route path="/signin" element={<Signin />}></Route>
        <Route path="/signup" element={<Signup />}></Route>
      </Routes>
    </div>
  );
};

export default App;
