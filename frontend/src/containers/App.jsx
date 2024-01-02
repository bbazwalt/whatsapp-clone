import { Route, Routes } from "react-router-dom";
import Home from "../pages/Home";
import Signin from "../pages/Signin";
import Signup from "../pages/Signup";
const App = () => {
  return (
    <div>
      <Routes>
        <Route exact path="/" element={<Home />}></Route>
        <Route path="/signin" element={<Signin />}></Route>
        <Route path="/signup" element={<Signup />}></Route>
      </Routes>
    </div>
  );
};

export default App;
